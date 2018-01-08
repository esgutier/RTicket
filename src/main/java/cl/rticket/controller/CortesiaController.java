package cl.rticket.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.pdf.codec.Base64.OutputStream;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.AjaxResponseBody;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;
import cl.rticket.model.Usuario;
import cl.rticket.services.HinchaService;
import cl.rticket.services.ItemService;
import cl.rticket.utils.GenerarEntradasCortesia;

@Controller
public class CortesiaController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	HinchaService hinchaService;
	
	@RequestMapping(value="/carga-pagina-cortesia", method=RequestMethod.GET)
	public String cargaPaginaCortesia(Model model) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entidades", hinchaService.obtenerEntidades(user.getIdEquipo()));
		
		return "content/cortesia";
	}
	
	
	@RequestMapping(value="/carga-entradas-cortesia", method=RequestMethod.POST)
	public String cargaEntradasCortesia(Model model, Entrada entrada) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido(), user.getIdEquipo()));
		model.addAttribute("entidades", hinchaService.obtenerEntidades(user.getIdEquipo()));
		return "content/cortesia";
	}
	
	
	
	@RequestMapping(value="/obtener-totales-entidad-cortesia", method={RequestMethod.POST,RequestMethod.GET})
	public String obtenerTotalesEntidadCortesia(Model model, Entrada entrada) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TotalesEntrada> totales = new ArrayList<TotalesEntrada>();
		//validar que el idPartido no sea 0
		if(entrada.getIdPartido() == null && entrada.getIdEntrada().toString().equals("0")) {
			model.addAttribute("error", "Debe seleccionar un Partido");
		} else {
			//obtener totales
			totales = itemService.obtenerTotalesCortesiaPorEntidad(entrada.getIdPartido(), entrada.getRut());
		}		
		model.addAttribute("totales", totales);	
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido(), user.getIdEquipo()));
		model.addAttribute("entidades", hinchaService.obtenerEntidades(user.getIdEquipo()));
		return "content/cortesia";
	}
	
	@RequestMapping(value="/generar-entradas-cortesia", method=RequestMethod.POST)
	public String generarEntradasCortesia(Model model, Entrada entrada, RedirectAttributes flash) {
		
		Usuario usuario = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		Entrada ent =itemService.obtenerEntrada(entrada.getIdEntrada(), usuario.getIdEquipo());
		//validar numero ingresado
		Integer totalvendidas = itemService.obtenerTotalSectorVendidas(entrada.getIdEntrada(), entrada.getIdPartido());
		System.out.println("-->totalvendidas:"+totalvendidas);
		System.out.println("-->maximo sector:"+ent.getMaximo());
		Integer cantidadIngresada =  entrada.getMaximo();
		Integer cantidadDisponible = ent.getMaximo() - totalvendidas;
		if( cantidadDisponible < cantidadIngresada) {
			flash.addFlashAttribute("error", "La cantidad de entradas ingresadas es superior a las entradas disponibles");
			flash.addFlashAttribute("entrada", entrada);
			return "redirect:/obtener-totales-entidad-cortesia";
		}
		
		//insertar en bd
		for(int i = 0 ; i < entrada.getMaximo().intValue(); i++) {
			//generar el objeto compra
			Compra compra = new Compra();
			compra.setIdPartido(entrada.getIdPartido());
			compra.setIdEquipo(usuario.getIdEquipo());
			compra.setIdEntrada(entrada.getIdEntrada());			
			compra.setRut(entrada.getRut());			
			compra.setUsername(usuario.getUsername());
			compra.setMonto(0);
			compra.setToken("0"); 
			compra.setTipo("C");
			compra.setAnulada("N");					

			try {
				itemService.insertarCompra(compra);
			} catch (UpdateException e) {
				model.addAttribute("error", "Ocurrio un error al generar los tickets en Base de datos - UpdateException");
				return "content/cortesia";
			} catch(Exception e) {
				model.addAttribute("error", "Ocurrio un error al generar los tickets en Base de datos - Exception");
				return "content/cortesia";
			}
		}
		entrada.setMaximo(null);
		
		flash.addFlashAttribute("exito", "Los tickets fueron generados correctamente en la Base de Datos.");
		flash.addFlashAttribute("entrada", entrada);		
		return "redirect:/obtener-totales-entidad-cortesia";

	}
	
	/*@RequestMapping(value="/imprimir-entradas-cortesia", method=RequestMethod.GET)
	public String imprimirEntradasCortesia(Model model, 
			                             @RequestParam(value="idEntrada")Integer idEntrada,
			                             @RequestParam(value="idPartido")Integer idPartido,
			                             @RequestParam(value="rut")Integer rut,
			                             RedirectAttributes flash) {
		System.out.println("idEntrada:"+idEntrada);
		
		//obtener datos de los tickets
		ArrayList<Ticket> tickets = itemService.obtenerDatosTicketRut(idEntrada, rut, "C");
		ImpresionCortesia impresionCortesia = new ImpresionCortesia();
		PrintService service = impresionCortesia.obtenerImpresoraService();
		
		for(Ticket t : tickets) {
			System.out.println("--->"+t.getToken());
			try {
				impresionCortesia.imprimirTicket(t,service);
			} catch (ImpresoraNoDisponibleException e) {
				flash.addFlashAttribute("error", "ERROR: La impresora no esta disponible");
				break;
			}
			
		}
		
		Entrada entrada = new Entrada();
		entrada.setIdEntrada(idEntrada);
		entrada.setIdPartido(idPartido);
		entrada.setRut(rut);
		flash.addFlashAttribute("entrada", entrada);
		return "redirect:/obtener-totales-entidad-cortesia";
	}*/
	
	@RequestMapping(value="/imprimir-entradas-cortesia-ajax", method=RequestMethod.POST)
	public  @ResponseBody AjaxResponseBody  imprimirEntradasCortesiaAjax(
			                             @RequestParam(value="idEntrada")Integer idEntrada,
			                             @RequestParam(value="idPartido")Integer idPartido,
			                             @RequestParam(value="rut")Integer rut
			                            ) {
		
		
		//obtener datos de los tickets
		ArrayList<Ticket> tickets = itemService.obtenerDatosTicketRut(idEntrada, rut, "C");
		System.out.println(""+tickets.size());
		return new AjaxResponseBody("1","",tickets);	
		
	}
	
	
	@RequestMapping(value="/generar-pdf-cortesia", method=RequestMethod.POST)
	public void imprimirEntradasCortesia(HttpServletRequest request, 
			                             HttpServletResponse response,			                       
			                             Entrada entrada) {
		
		//final ServletContext servletContext = request.getSession().getServletContext();
	    //final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    //final String temperotyFilePath = tempDirectory.getAbsolutePath();
 
	    String fileName = "tickets-cortesia.pdf";
	    response.setContentType("application/pdf");
	    
 
	    try {
	    	ArrayList<Ticket> tickets = itemService.obtenerDatosTicketCortesia(entrada.getIdPartido(), entrada.getRut(), "C");
	    	if(tickets.size() > 0 ) {
	    		Ticket tmp = tickets.get(0);
	    		fileName = "cortesia-"+tmp.getNombres().toUpperCase()+"-"+tmp.getRival()+".pdf";
	    	}
	    	response.setHeader("Content-disposition", "attachment; filename="+ fileName);
	        //GenerarEntradasCortesia.generaPDF(temperotyFilePath+"\\"+fileName);
	        GenerarEntradasCortesia.generaPDF(fileName, tickets);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = convertPDFToByteArrayOutputStream(fileName);
	        ServletOutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	}
	
	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {
		 
		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
 
			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
 
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos;
	}

}
