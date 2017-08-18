package cl.rticket.controller;

import java.util.ArrayList;

import javax.print.PrintService;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.exception.ImpresoraNoDisponibleException;
import cl.rticket.exception.UpdateException;
import cl.rticket.model.AjaxResponseBody;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;
import cl.rticket.model.Usuario;
import cl.rticket.services.HinchaService;
import cl.rticket.services.ItemService;
import cl.rticket.utils.ImpresionCortesia;
import cl.rticket.utils.ImpresionMasiva;

@Controller
public class CortesiaController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	HinchaService hinchaService;
	
	@RequestMapping(value="/carga-pagina-cortesia", method=RequestMethod.GET)
	public String cargaPaginaCortesia(Model model) {
		
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entidades", hinchaService.obtenerEntidades());
		
		return "content/cortesia";
	}
	
	
	@RequestMapping(value="/carga-entradas-cortesia", method=RequestMethod.POST)
	public String cargaEntradasCortesia(Model model, Entrada entrada) {
		
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido()));
		model.addAttribute("entidades", hinchaService.obtenerEntidades());
		return "content/cortesia";
	}
	
	
	
	@RequestMapping(value="/obtener-totales-entidad-cortesia", method={RequestMethod.POST,RequestMethod.GET})
	public String obtenerTotalesEntidadCortesia(Model model, Entrada entrada) {
		
		ArrayList<TotalesEntrada> totales = new ArrayList<TotalesEntrada>();
		//validar que el idPartido no sea 0
		if(entrada.getIdPartido() == null && entrada.getIdEntrada().toString().equals("0")) {
			model.addAttribute("error", "Debe seleccionar un Partido");
		} else {
			//obtener totales
			totales = itemService.obtenerTotalesCortesiaPorEntidad(entrada.getIdPartido(), entrada.getRut());
		}		
		model.addAttribute("totales", totales);	
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido()));
		model.addAttribute("entidades", hinchaService.obtenerEntidades());
		return "content/cortesia";
	}
	
	@RequestMapping(value="/generar-entradas-cortesia", method=RequestMethod.POST)
	public String generarEntradasCortesia(Model model, Entrada entrada, RedirectAttributes flash) {
		
		Usuario usuario = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		Entrada ent =itemService.obtenerEntrada(entrada.getIdEntrada());
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
	

}
