package cl.rticket.controller;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.services.ItemService;
import cl.rticket.utils.PrinterService;

@Controller
public class CompraController {

	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/carga-ingreso-compra", method=RequestMethod.GET)
	public String cargaIngresoCompra(Model model) {
		
		model.addAttribute("compra", new Compra());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		SecurityUtils.getSubject().getSession().removeAttribute("carro");
		SecurityUtils.getSubject().getSession().removeAttribute("totalCompra");
		
		return "content/compra";
	}

	@RequestMapping(value="/carga-entradas-disponibles", method=RequestMethod.POST)
	public String cargaEntradasDisponibles(Model model, Compra compra) {
		
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido()));
		
		return "content/compra";
	}
	
	@RequestMapping(value="/compra-buscar-hincha", method=RequestMethod.POST)
	public String buscarHinchaCompra(Model model, Compra compra) {
		
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido()));
		
		return "content/compra";
	}
	
	@RequestMapping(value="/agregar-entrada-carro", method=RequestMethod.POST)
	public String agregarEntradaCarro(Model model, Compra compra) {

		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido()));
		
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
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(idPartido));
		compra.setIdPartido(idPartido);
		compra.setIdEntrada(idEntrada);
		
		return "content/compra";
	}
	
	@RequestMapping(value="/confirmar-compra", method=RequestMethod.POST)
	public String confirmarCompra(Model model, Compra compra,RedirectAttributes flash) {
		
		ArrayList<Compra> ticketList= (ArrayList<Compra>) SecurityUtils.getSubject().getSession().getAttribute("carro");
		
		//aca se valida la disponibilidad de las entradas
		Entrada entrada =itemService.obtenerEntrada(compra.getIdEntrada());
		System.out.println("BUSCAR HINCHA Entrada     idEntrada="+compra.getIdEntrada()+"   idPartido="+compra.getIdPartido());
		Integer totalvendidas = itemService.obtenerTotalSectorVendidas(compra.getIdEntrada(), compra.getIdPartido());
		System.out.println("BUSCAR HINCHA Entrada     MAXIMO="+entrada.getMaximo()+"   TOTAL="+totalvendidas);
		if((totalvendidas + ticketList.size())  > entrada.getMaximo()){
			model.addAttribute("partidos", itemService.obtenerPartidos());
			model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido()));
			model.addAttribute("error", "Error: La venta excede el total de entradas disponibles para el sector seleccionado");
			return "content/compra";
		}
		
		//aca se chequea que este disponible la impresora
		
		
		try {
			itemService.insertarCompra(ticketList);
			
			for(Compra c: ticketList) {
				System.out.println("--->"+c.getIdCompra()+" "+c.getToken());
			}
			
			
			
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
	
	
}