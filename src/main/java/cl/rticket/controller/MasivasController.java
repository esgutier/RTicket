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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.exception.ImpresoraNoDisponibleException;
import cl.rticket.exception.UpdateException;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Ticket;
import cl.rticket.model.Usuario;
import cl.rticket.services.ItemService;
import cl.rticket.utils.ImpresionMasiva;

@Controller
public class MasivasController {
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/carga-pagina-masivo", method=RequestMethod.GET)
	public String cargaPaginaMasivo(Model model) {
		
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		
		
		return "content/masiva";
	}
	
	@RequestMapping(value="/carga-entradas-masivo",method={RequestMethod.POST,RequestMethod.GET})
	public String cargaIngresoMasivo(Model model, Entrada entrada) {
		
		System.out.println("entrada id ===>"+entrada.getIdEntrada());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido()));
		//cargar totales
		System.out.println("--->"+itemService.obtenerTotalesEntradas(entrada.getIdPartido()).size());
		model.addAttribute("totales",itemService.obtenerTotalesEntradas(entrada.getIdPartido()));
		
		
		return "content/masiva";
	}
	
	//genera las entradas en la base de datos
	@RequestMapping(value="/generar-entradas-masivo", method=RequestMethod.POST)
	public String generarEntradasMasivo(Model model, Entrada entrada, RedirectAttributes flash) {
		
		
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
			return "redirect:/carga-entradas-masivo";
		}
		
		//insertar en bd
		for(int i = 0 ; i < entrada.getMaximo().intValue(); i++) {
			//generar el objeto compra
			Compra compra = new Compra();
			compra.setIdEntrada(entrada.getIdEntrada());			
			compra.setRut(11111111);			
			compra.setUsername(usuario.getUsername());
			compra.setMonto(ent.getPrecio());
			compra.setToken("0"); 
			compra.setTipo("R");
			compra.setAnulada("N");					

			try {
				itemService.insertarCompra(compra);
			} catch (UpdateException e) {
				model.addAttribute("error", "Ocurrio un error al generar los tickets en Base de datos - UpdateException");
				return "content/masiva";
			} catch(Exception e) {
				System.out.println("--->"+e.getMessage());
				model.addAttribute("error", "Ocurrio un error al generar los tickets en Base de datos - Exception");
				return "content/masiva";
			}
		}
		entrada.setMaximo(null);
		
		flash.addFlashAttribute("exito", "Los tickets fueron generados correctamente en la Base de Datos.");
		flash.addFlashAttribute("entrada", entrada);
		//return cargaIngresoMasivo(model,entrada);
		return "redirect:/carga-entradas-masivo";
	}
	
	@RequestMapping(value="/imprimir-entradas-masivo", method=RequestMethod.GET)
	public String imprimirEntradasMasivo(Model model, 
			                             @RequestParam(value="idEntrada")Integer idEntrada,
			                             @RequestParam(value="idPartido")Integer idPartido,
			                             RedirectAttributes flash) {
		System.out.println("idEntrada:"+idEntrada);
		
		//obtener datos de los tickets
		ArrayList<Ticket> tickets = itemService.obtenerDatosTicketMasivo(idEntrada, "R");
		ImpresionMasiva impresionMasiva = new ImpresionMasiva();
		PrintService service = impresionMasiva.obtenerImpresoraService();
		
		for(Ticket t : tickets) {
			System.out.println("--->"+t.getToken());
			try {
				impresionMasiva.imprimirTicket(t,service);
			} catch (ImpresoraNoDisponibleException e) {
				flash.addFlashAttribute("error", "ERROR: La impresora no esta disponible");
				break;
			}
			
		}
		
		Entrada entrada = new Entrada();
		entrada.setIdEntrada(idEntrada);
		entrada.setIdPartido(idPartido);
		flash.addFlashAttribute("entrada", entrada);
		return "redirect:/carga-entradas-masivo";
	}

}