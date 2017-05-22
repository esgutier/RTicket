package cl.rticket.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.rticket.model.Compra;
import cl.rticket.model.Hincha;
import cl.rticket.model.Usuario;
import cl.rticket.services.HinchaService;
import cl.rticket.services.ItemService;

@Controller
public class HinchaController {

	@Autowired
	HinchaService hinchaService;
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/buscar-hincha-compra", method=RequestMethod.POST)
	public String buscarHinchaCompra(Model model, Compra compra) {
		
		if(compra.getRutDigitado() != null && !compra.getRutDigitado().isEmpty()) {
			//el rut es digitado
			//validar formato de rut
			String[] parts = compra.getRutDigitado().split("-");
			String nro = parts[0]; // 004
			String dv = parts[1]; // 034556
			Integer rut = Integer.parseInt(nro);
			//validar rut
			
			Hincha hincha = hinchaService.obtenerHincha(rut);
			if(hincha == null) {
				//hincha no está redireccionar a formulario de ingreso de hincha
			} else {
			   compra.setNombreHincha(hincha.getNombres()+" "+hincha.getApellidos());
			}
			
			
		}
			
		model.addAttribute("partidos", itemService.obtenerPartidos(obtenerEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(obtenerEquipo(),compra.getIdPartido()));
		
		return "content/compra";
	}
	
	private Integer obtenerEquipo() {
		Usuario usuario = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		return usuario.getIdEquipo();
	}
}
