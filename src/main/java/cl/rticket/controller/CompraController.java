package cl.rticket.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.AjaxResponseBody;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;
import cl.rticket.model.Usuario;
import cl.rticket.services.ItemService;

@Controller
public class CompraController {

	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/carga-ingreso-compra", method=RequestMethod.GET)
	public String cargaIngresoCompra(Model model) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("compra", new Compra());
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		SecurityUtils.getSubject().getSession().removeAttribute("carro");
		SecurityUtils.getSubject().getSession().removeAttribute("totalCompra");
		
		return "content/compra";
	}
	
	@RequestMapping(value="/carga-anular-ticket", method=RequestMethod.GET)
	public String cargaAnularTicket(Model model) {
		
		model.addAttribute("ticket", new Ticket());
		return "content/anularTicket";
	}
	
	@RequestMapping(value="/anular-ticket", method=RequestMethod.POST)
	public String anularTicket(Model model, Ticket ticket, RedirectAttributes flash) {
		
		String token = "";
		//System.out.println("token digitado:"+ticket.getTokenDigitado());
		//System.out.println("token escaneado:"+ticket.getTokenEscaneado());
		if(ticket.getTokenEscaneado() != null && !ticket.getTokenEscaneado().isEmpty()){
			token = ticket.getTokenEscaneado();
		} if(ticket.getTokenDigitado() != null && !ticket.getTokenDigitado().isEmpty()) {
			token = ticket.getTokenDigitado();
		}
		
		if(!token.equals("")) {
			int res = itemService.anularTicket(token);
			if(res < 1) {
				flash.addFlashAttribute("error", "El c�digo del ticket no existe");
			} else {
				flash.addFlashAttribute("exito", "Ticket anulado correctamente");
			}
		} else {
			flash.addFlashAttribute("error", "El c�digo del ticket es vac�o");
		}		
		return "redirect:/carga-anular-ticket";
	}
	
	

	@RequestMapping(value="/carga-entradas-disponibles", method=RequestMethod.POST)
	public String cargaEntradasDisponibles(Model model, Compra compra) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
		
		return "content/compra";
	}
	
	@RequestMapping(value="/obtener-disponibilidad-sector", method=RequestMethod.POST)
	public String obtenerDisponibilidadSector(Model model, Compra compra) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
		HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
		TotalesEntrada total = map.get(compra.getIdEntrada());		
		model.addAttribute("total", total);
		if(total.getMaximo() <= (total.getTotalCortesia() + total.getTotalNominativa() + total.getTotalNormales())) {
				model.addAttribute("agotadas", "agotadas");
				model.addAttribute("error", "Entradas agotadas para el sector especificado");
		}	

		return "content/compra";
	}
	
	
	@RequestMapping(value="/compra-buscar-hincha", method=RequestMethod.POST)
	public String buscarHinchaCompra(Model model, Compra compra) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
		
		return "content/compra";
	}
	
	@RequestMapping(value="/agregar-entrada-carro", method=RequestMethod.POST)
	public String agregarEntradaCarro(Model model, Compra compra) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
		HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
		model.addAttribute("total", map.get(compra.getIdEntrada()));
		
		return "content/compra";
	}
	
	//elimina solo del carro que esta en la sesion
	@RequestMapping(value="/eliminar-entrada-carro", method=RequestMethod.GET)
	public String eliminarEntradaCarro(Model model, Compra compra,
			                           @RequestParam(value="rut")Integer rut,
			                           @RequestParam(value="idPartido")Integer idPartido,
			                           @RequestParam(value="idEntrada")Integer idEntrada) {

		ArrayList<Compra> ticketList= (ArrayList<Compra>) SecurityUtils.getSubject().getSession().getAttribute("carro");
		int total = 0;
		for (Iterator<Compra> iterator = ticketList.iterator(); iterator.hasNext(); ) {
		    Compra value = iterator.next();
		    total = total + value.getMonto();
		    if(value.getRut().intValue() == rut.intValue()) {
		        iterator.remove();
		        total = total - value.getMonto();
		    }
		}
		SecurityUtils.getSubject().getSession().setAttribute("totalCompra",total);
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(idPartido, user.getIdEquipo()));
		compra.setIdPartido(idPartido);
		compra.setIdEntrada(idEntrada);
		HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
		model.addAttribute("total", map.get(compra.getIdEntrada()));
		
		return "content/compra";
	}
	
	@RequestMapping(value="/confirmar-compra", method=RequestMethod.POST)
	public String confirmarCompra(Model model, Compra compra,RedirectAttributes flash) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<Compra> compraList= (ArrayList<Compra>) SecurityUtils.getSubject().getSession().getAttribute("carro");
		
		//aca se valida la disponibilidad de las entradas
		Entrada entrada =itemService.obtenerEntrada(compra.getIdEntrada(), user.getIdEquipo());
		//System.out.println("BUSCAR HINCHA Entrada     idEntrada="+compra.getIdEntrada()+"   idPartido="+compra.getIdPartido());
		Integer totalvendidas = itemService.obtenerTotalSectorVendidas(compra.getIdEntrada(), compra.getIdPartido());
		//System.out.println("BUSCAR HINCHA Entrada     MAXIMO="+entrada.getMaximo()+"   TOTAL="+totalvendidas);
		if((totalvendidas + compraList.size())  > entrada.getMaximo()){
			model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
			model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(),user.getIdEquipo()));
			HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
			model.addAttribute("total", map.get(compra.getIdEntrada()));
			model.addAttribute("error", "Error: La venta excede el total de entradas disponibles para el sector seleccionado");
			return "content/compra";
		}
			
		try {
			ArrayList<Ticket> listaTicket = itemService.insertarCompra(compraList, user.getIdEquipo());	
			
		} catch (UpdateException e) {
			model.addAttribute("error", "Error: No se pudo registrar la compra");
			SecurityUtils.getSubject().getSession().removeAttribute("carro");
			SecurityUtils.getSubject().getSession().removeAttribute("totalCompra");
			return "content/compra";
			
		}
		flash.addFlashAttribute("exito", "La compra fue registrada correctamente");
		SecurityUtils.getSubject().getSession().removeAttribute("carro");
		SecurityUtils.getSubject().getSession().removeAttribute("totalCompra");
		//qui debe ir la impresion
      /*  PrinterService printerService = new PrinterService();
		
		System.out.println(printerService.getPrinters());
				
		//print some stuff
		printerService.printString("zebra", "\n\n testing testing 1 2 3eeeee \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		// cut that paper!
		byte[] cutP = new byte[] { 0x1d, 'V', 1 };

		printerService.printBytes("zebra", cutP);*/
		return "redirect:/carga-ingreso-compra";
	}
	
	
	@RequestMapping(value="/confirmar-compra-ajax", method=RequestMethod.POST)
	public @ResponseBody AjaxResponseBody confirmarCompraAjax( @RequestParam(value="idPartido")Integer idPartido,
                                                               @RequestParam(value="idEntrada")Integer idEntrada) {
		
		ArrayList<Compra> compraList= (ArrayList<Compra>) SecurityUtils.getSubject().getSession().getAttribute("carro");
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		System.out.println("idPartido:"+idPartido);
		System.out.println("idEntrada:"+idEntrada);
		
		//aca se valida la disponibilidad de las entradas
		Entrada entrada =itemService.obtenerEntrada(idEntrada, user.getIdEquipo());
		//System.out.println("BUSCAR HINCHA Entrada     idEntrada="+compra.getIdEntrada()+"   idPartido="+compra.getIdPartido());
		Integer totalvendidas = itemService.obtenerTotalSectorVendidas(idEntrada, idPartido);
		//System.out.println("BUSCAR HINCHA Entrada     MAXIMO="+entrada.getMaximo()+"   TOTAL="+totalvendidas);
		if((totalvendidas + compraList.size())  > entrada.getMaximo()){			
			return new AjaxResponseBody("0","Error: La venta excede el total de entradas disponibles para el sector seleccionado",null);			
		}

		try {
			ArrayList<Ticket> listaTicket = itemService.insertarCompra(compraList, user.getIdEquipo());	
			
			return new AjaxResponseBody("1","La compra fue registrada correctamente",listaTicket);
			
			
		} catch (UpdateException e) {		
			return new AjaxResponseBody("0","Error: No se pudo registrar la compra",null);		
		}
		
		
		
	}
	
}
