package cl.rticket.controller;

import java.util.ArrayList;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cl.rticket.model.Compra;
import cl.rticket.services.ItemService;

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
	
	@RequestMapping(value="/eliminar-entrada-carro", method=RequestMethod.GET)
	public String eliminarEntradaCarro(Model model, Compra compra, @RequestParam(value="rut")Integer rut) {

		ArrayList<Compra> ticketList= (ArrayList<Compra>) SecurityUtils.getSubject().getSession().getAttribute("carro");
		for(Compra c: ticketList) {
			if(c.getRut() == rut) {
				ticketList.remove(c);
			}
		}
		SecurityUtils.getSubject().getSession().setAttribute("carro",ticketList);
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido()));
		
		return "content/compra";
	}
	
	
}
