package cl.rticket.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Usuario;
import cl.rticket.services.ItemService;

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
	
	@RequestMapping(value="/carga-entradas-masivo", method=RequestMethod.POST)
	public String cargaIngresoCompra(Model model, Entrada entrada) {
		
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(entrada.getIdPartido()));
		//cargar totales
		model.addAttribute("totales",itemService.obtenerTotalesEntradas(entrada.getIdPartido()));
		
		
		return "content/masiva";
	}
	
	//genera las entradas en la base de datos
	@RequestMapping(value="/generar-entradas-masivo", method=RequestMethod.POST)
	public String generarEntradasMasivo(Model model, Entrada entrada) {
		
		Usuario usuario = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		Entrada ent =itemService.obtenerEntrada(entrada.getIdEntrada());
		//validar numero ingresado
		
		
		
		//insertar en bd
		for(int i = 0 ; i < entrada.getMaximo().intValue(); i++) {
			//generar el objeto compra
			Compra compra = new Compra();
			compra.setIdEntrada(entrada.getIdEntrada());			
			compra.setRut(11111111);			
			compra.setUsername(usuario.getUsername());
			compra.setMonto(ent.getPrecio());
			compra.setToken(0); 
			compra.setTipo("R");
			compra.setAnulada("N");					

			try {
				itemService.insertarCompra(compra);
			} catch (UpdateException e) {
				model.addAttribute("error", "Ocurrio un error al generar los tickets en Base de datos - UpdateException");
			} catch(Exception e) {
				model.addAttribute("error", "Ocurrio un error al generar los tickets en Base de datos - Exception");
			}
		}
		return "content/masiva";
	}

}
