package cl.rticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.model.Partido;
import cl.rticket.services.ItemService;

@Controller
public class PartidoController {

	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/carga-pagina-partidos", method=RequestMethod.GET)
	public String cargaPaginaPartidos(Model model) {
		model.addAttribute("partido", new Partido());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		return "content/partidosListar";
	}
	
	@RequestMapping(value="/carga-editar-partido", method=RequestMethod.GET)
	public String cargaEditarPartidos(Model model, @RequestParam(value="idPartido")Integer idPartido) {
		
		model.addAttribute("partido", itemService.obtenerPartido(idPartido));
		return "content/partidosEditar";
	}
	
	@RequestMapping(value="/insertar-partido", method=RequestMethod.POST)
	public String insertarPartidos(Model model, Partido partido, RedirectAttributes flash) {
		
		String retorno = "content/partidosListar";
		partido.setFechaTexto(partido.getFecha()+" "+partido.getHora());		
		int res = itemService.insertarPartido(partido);
		if(res < 1) {
			model.addAttribute("error", "Error: No se pudo insertar el partido especificado");
			model.addAttribute("partidos", itemService.obtenerPartidos());
		} else {
			flash.addFlashAttribute("exito", "Partido ingresado correctamente");
			retorno = "redirect:/carga-pagina-partidos";
		}		
		return retorno;
	}
	
	@RequestMapping(value="/actualizar-partido", method=RequestMethod.POST)
	public String actualizarPartido(Model model, Partido partido, RedirectAttributes flash) {
		
		String retorno = "content/partidosEditar"; 
		partido.setFechaTexto(partido.getFecha()+" "+partido.getHora());	
		
			int res = itemService.actualizarPartido(partido);
			if(res < 1) {
				model.addAttribute("error", "Error: No se pudo actualizar el partido especificado");
			} else {
				flash.addFlashAttribute("exito", "Partido actualizado correctamente");
				retorno = "redirect:/carga-pagina-partidos";
			}
		
		return retorno;
	}
	
	@RequestMapping(value="/eliminar-partido", method=RequestMethod.GET)
	public String eliminarPartido(Model model, @RequestParam(value="idPartido")Integer idPartido, RedirectAttributes flash) {
		
		String retorno = "content/partidosListar";
		try {		
		int res = itemService.eliminarPartido(idPartido);
		if(res < 1) {
			model.addAttribute("error", "Error: No se pudo eliminar el partido especificado");
			model.addAttribute("partidos", itemService.obtenerPartidos());
		} else {
			flash.addFlashAttribute("exito", "Partido eliminado correctamente");
			retorno = "redirect:/carga-pagina-partidos";
		}	
		} catch(DataIntegrityViolationException ex) {
			flash.addFlashAttribute("error", "Error: No se pudo eliminar el partido ya que se encuentra asociado a otros registros en la Base de Datos");
			retorno = "redirect:/carga-pagina-partidos";
		}
		return retorno;
	}
}
