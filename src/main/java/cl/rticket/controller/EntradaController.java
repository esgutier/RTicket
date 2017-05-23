package cl.rticket.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cl.rticket.model.Entrada;
import cl.rticket.model.Usuario;
import cl.rticket.services.ItemService;

@Controller
public class EntradaController {

	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/carga-ingreso-entrada", method=RequestMethod.GET)
	public String cargaIngresoEntrada(Model model) {
		
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("sectores", itemService.obtenerSectores());
		return "content/entrada";
	}
	
	@RequestMapping(value="/carga-entradas-partido", method=RequestMethod.POST)
	public String cargaEntradasPartido(Model model, Entrada entrada) {	
		
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("sectores", itemService.obtenerSectores());
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido()));
		return "content/entrada";
	}
	
	@RequestMapping(value="/insertar-entrada", method=RequestMethod.POST)
	public String cargaIngresoEntrada(Model model, Entrada entrada) {	
		//validar datos de entrada
		int flagError = 0;
		if(entrada.getIdPartido() == null || entrada.getIdPartido() == 0) {
			model.addAttribute("error", "Debe seleccionar un partido.");
			flagError = 1;
		}else if(entrada.getIdSector() == null || entrada.getIdSector() == 0) {
			model.addAttribute("error", "Debe seleccionar un sector.");
			flagError = 1;
		} else if(entrada.getPrecio() == null || entrada.getPrecio() < 1) {
			model.addAttribute("error", "Debe ingresar un precio correcto.");
			flagError = 1;
		}
			
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("sectores", itemService.obtenerSectores());					
		if(flagError == 0) {			
		     itemService.insertarEntrada(entrada);
		}
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido()));
		return "content/entrada";
	}
	
	@RequestMapping(value="/eliminar-entrada", method=RequestMethod.GET)
	public String eliminarEntrada(Model model, 
			                      @RequestParam(value="idEntrada")Integer idEntrada,
			                      @RequestParam(value="idPartido")Integer idPartido) {	
		
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("sectores", itemService.obtenerSectores());
		itemService.eliminarEntrada(idEntrada);
		model.addAttribute("entradas", itemService.obtenerEntradas(idPartido));
		return "content/entrada";
	}
	
	
	
}
