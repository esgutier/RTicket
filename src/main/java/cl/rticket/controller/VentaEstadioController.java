package cl.rticket.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.model.Usuario;
import cl.rticket.model.VentaEstadio;
import cl.rticket.services.VentaEstadioService;

@Controller
public class VentaEstadioController {
	@Autowired
	VentaEstadioService ventaEstadioService ;
	
	@RequestMapping(value = "/venta-estadio", method = RequestMethod.GET)
	public String cargaPaginaPartidos(Model model) {
		model.addAttribute("ventaEstadio", new VentaEstadio());
		return "content/ventaEstadio";
	}

	@RequestMapping(value = "/insertar-venta-estadio", method = RequestMethod.POST)
	public String agregarEntradaCarro(Model model, VentaEstadio ventaEstadio,RedirectAttributes flash) {
		int error = 0 ; 
		if ( ventaEstadio.getRut() == null || ventaEstadio.getEntrada() == null ) {
			model.addAttribute("error", "Debe Ingresar ambos datos");
			error = 1 ;
		}
		if ( error == 1 ) {
			return "content/ventaEstadio";
		}
		Calendar fecha = new GregorianCalendar();
		int año = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH);
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		String fechaSt = + dia + "/" + (mes+1) + "/" + año ;
		System.out.println(fechaSt);
		Usuario user = (Usuario) SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ventaEstadio.setIdEquipo(user.getIdEquipo());
		ventaEstadio.setUsuario(user.getNombre());
		ventaEstadio.setFecha(fechaSt);
		ventaEstadioService.insertarVentaEstadio(ventaEstadio);
		flash.addFlashAttribute("exito", "se realizó correctamente");
		return "redirect:/venta-estadio";
		
	}
}
