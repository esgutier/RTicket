package cl.rticket.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.model.Entrada;
import cl.rticket.model.Usuario;
import cl.rticket.services.ItemService;

@Controller
public class EntradaController {

	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/carga-ingreso-entrada", method=RequestMethod.GET)
	public String cargaIngresoEntrada(Model model) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
		return "content/entrada";
	}
	
	@RequestMapping(value="/carga-entradas-partido", method={RequestMethod.POST,RequestMethod.GET})
	public String cargaEntradasPartido(Model model, Entrada entrada) {	
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido(), user.getIdEquipo()));
		return "content/entrada";
	}
	
	@RequestMapping(value="/carga-editar-entrada", method=RequestMethod.GET)
	public String cargaEditarEntrada(Model model, @RequestParam(value="idEntrada")Integer idEntrada,
			                                      @RequestParam(value="idPartido")Integer idPartido,
			                                      @RequestParam(value="idSector")Integer idSector) {	
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		Entrada entrada = itemService.obtenerEntrada(idEntrada, user.getIdEquipo());
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
		model.addAttribute("entrada", entrada);
		return "content/entradaEditar";
	}
	
	
	@RequestMapping(value="/insertar-entrada", method=RequestMethod.POST)
	public String cargaIngresoEntrada(Model model, Entrada entrada, RedirectAttributes flash) {	
		//validar datos de entrada
		String retorno = "content/entrada"; 
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
			
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");	
		entrada.setIdEquipo(user.getIdEquipo());
		if(flagError == 0) {			
		     itemService.insertarEntrada(entrada);
		     entrada.setMaximo(null);
			 entrada.setComentario("");
			 entrada.setPrecio(null);
			 entrada.setIdSector(null);
			 flash.addFlashAttribute("entrada", entrada);
			 flash.addFlashAttribute("exito", "Entrada ingresada correctamente");
			 retorno = "redirect:/carga-entradas-partido";
		} else {
		   
			model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
			model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));		
		}
		return retorno;
	}
	
	
	@RequestMapping(value="/actualizar-entrada", method=RequestMethod.POST)
	public String actualizarPartido(Model model, Entrada entrada, RedirectAttributes flash) {
		
		String retorno = "content/entradaEditar"; 

			int res = itemService.actualizarEntrada(entrada);
			if(res < 1) {
				model.addAttribute("error", "Error: No se pudo actualizar la entrada especificada");
			} else {
				entrada.setMaximo(null);
				entrada.setComentario("");
				entrada.setPrecio(null);
				flash.addFlashAttribute("exito", "Entrada actualizada correctamente");
				flash.addFlashAttribute("entrada", entrada);
				retorno = "redirect:/carga-entradas-partido";
			}		
		return retorno;
	}
	
	
	
	
	@RequestMapping(value="/eliminar-entrada", method=RequestMethod.GET)
	public String eliminarEntrada(Model model, 
			                      @RequestParam(value="idEntrada")Integer idEntrada,
			                      @RequestParam(value="idPartido")Integer idPartido,
			                      RedirectAttributes flash) {	
		
		String retorno = "content/entrada"; 
		try {
			int res = itemService.eliminarEntrada(idEntrada);
			if(res < 1) {
				model.addAttribute("error", "Error: No se pudo eliminar la entrada especificada");
				Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
				model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
				model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));	
			} else {
				Entrada entrada = new Entrada();
				entrada.setMaximo(null);
				entrada.setComentario("");
				entrada.setPrecio(null);
				entrada.setIdPartido(idPartido);
				flash.addFlashAttribute("exito", "Entrada eliminada correctamente");
				flash.addFlashAttribute("entrada", entrada);
				retorno = "redirect:/carga-entradas-partido";
		    }
		} catch(DataIntegrityViolationException ex) {
			flash.addFlashAttribute("error", "Error: No se pudo eliminar la entrada ya que se encuentra asociado a otros registros en la Base de Datos");
			retorno = "redirect:/carga-entradas-partido";
		}
		return retorno;
	}
	
	
	
}
