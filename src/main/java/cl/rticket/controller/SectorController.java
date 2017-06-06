package cl.rticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.model.Sector;
import cl.rticket.services.ItemService;

@Controller
public class SectorController {
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/carga-pagina-sectores", method=RequestMethod.GET)
	public String cargaPaginaSectores(Model model) {
		model.addAttribute("sector", new Sector());
		model.addAttribute("sectores", itemService.obtenerSectores());
		return "content/sectoresListar";
	}
	
	@RequestMapping(value="/carga-editar-sector", method=RequestMethod.GET)
	public String cargaEditarSector(Model model, @RequestParam(value="idSector")Integer idSector) {
		
		model.addAttribute("sector", itemService.obtenerSector(idSector));
		return "content/sectoresEditar";
	}
	
	@RequestMapping(value="/insertar-sector", method=RequestMethod.POST)
	public String insertarSector(Model model, Sector sector, RedirectAttributes flash) {
		
		String retorno = "content/sectoresListar";
			
		int res = itemService.insertarSector(sector);
		if(res < 1) {
			model.addAttribute("error", "Error: No se pudo insertar el sector especificado");
			model.addAttribute("sectores", itemService.obtenerSectores());
		} else {
			flash.addFlashAttribute("exito", "Sector ingresado correctamente");
			retorno = "redirect:/carga-pagina-sectores";
		}		
		return retorno;
	}
	
	@RequestMapping(value="/actualizar-sector", method=RequestMethod.POST)
	public String actualizarPartido(Model model, Sector sector, RedirectAttributes flash) {
		
		String retorno = "content/sectoresEditar"; 

			int res = itemService.actualizarSector(sector);
			if(res < 1) {
				model.addAttribute("error", "Error: No se pudo actualizar el sector especificado");
			} else {
				flash.addFlashAttribute("exito", "Sector actualizado correctamente");
				retorno = "redirect:/carga-pagina-sectores";
			}		
		return retorno;
	}
	
	@RequestMapping(value="/eliminar-sector", method=RequestMethod.GET)
	public String eliminarPartido(Model model, @RequestParam(value="idSector")Integer idSector, RedirectAttributes flash) {
		
		String retorno = "content/sectoresListar";
		try {		
		int res = itemService.eliminarSector(idSector);
		if(res < 1) {
			model.addAttribute("error", "Error: No se pudo eliminar el sector especificado");
			model.addAttribute("sectores", itemService.obtenerSectores());
		} else {
			flash.addFlashAttribute("exito", "Sector eliminado correctamente");
			retorno = "redirect:/carga-pagina-sectores";
		}	
		} catch(DataIntegrityViolationException ex) {
			flash.addFlashAttribute("error", "Error: No se pudo eliminar el sector ya que se encuentra asociado a otros registros en la Base de Datos");
			retorno = "redirect:/carga-pagina-sectores";
		}
		return retorno;
	}

}
