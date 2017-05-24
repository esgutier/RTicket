package cl.rticket.controller;

import java.util.ArrayList;

import org.apache.commons.validator.GenericValidator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Hincha;
import cl.rticket.model.Usuario;
import cl.rticket.services.HinchaService;
import cl.rticket.services.ItemService;
import cl.rticket.utils.Util;

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
			if(Util.verificaRUT(compra.getRutDigitado())) {
				String[] parts = compra.getRutDigitado().split("-");
				String nro = parts[0]; 				
				Integer rut = Integer.parseInt(nro);
				//validar rut
				
				Hincha hincha = hinchaService.obtenerHincha(rut);
				if(hincha == null) {
					//hincha no está redireccionar a formulario de ingreso de hincha				
					model.addAttribute("compra", compra);
					hincha = new Hincha();
					hincha.setRutCompleto(compra.getRutDigitado());					
					model.addAttribute("hincha", hincha);
					return "content/ingresoHinchaCompra";
				} else {
				   compra.setNombreHincha(hincha.getNombres()+" "+hincha.getApellidos());
				    //agregar la entrada al carro
					Entrada entrada =itemService.obtenerEntrada(compra.getIdEntrada());
					Usuario usuario = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
					Compra ticket = new Compra();
					ticket.setIdPartido(entrada.getIdPartido());
					ticket.setIdEntrada(compra.getIdEntrada());
					ticket.setRut(rut);
					ticket.setRutCompleto(compra.getRutDigitado());
					ticket.setUsername(usuario.getUsername());
					ticket.setMonto(entrada.getPrecio());
					ticket.setToken(rut+""+Util.random()); //debe ser string
					ticket.setNominativa("S");
					ticket.setDescPartido(entrada.getDescPartido());
					ticket.setDescSector(entrada.getDescSector());
					ticket.setNombreHincha(hincha.getNombres()+" "+hincha.getApellidos());
					
					ArrayList<Compra> ticketList= (ArrayList<Compra>) SecurityUtils.getSubject().getSession().getAttribute("carro");
					if(ticketList != null) {
					    ticketList.add(ticket);
					} else {
						ticketList = new ArrayList<Compra>();
						 ticketList.add(ticket);
					}
					SecurityUtils.getSubject().getSession().setAttribute("carro",ticketList);
					int total = 0;
					for(Compra c: ticketList) {
						total = total + c.getMonto();
					}
					SecurityUtils.getSubject().getSession().setAttribute("totalCompra",total);
				}
			} else {
				model.addAttribute("error", "El Rut ingresado no es válido");
			}
			
			
		} else {
			model.addAttribute("error", "El Rut ingresado no es válido");
		}
			
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido()));
		
		
		
		return "content/compra";
	}
	
	//Ingresar hincha desde una compra
	@RequestMapping(value="/ingresar-hincha-compra", method=RequestMethod.POST)
	public String insertarHinchaCompra(Model model, 
			                     @RequestParam(value="idPartido")Integer idPartido,
			                     @RequestParam(value="idEntrada")Integer idEntrada,
			                     Hincha hincha,
			                     final RedirectAttributes redirectAttributes) {
		
		//System.out.println("idPartido:"+idPartido);
		//System.out.println("idEntrada:"+idEntrada);
		Compra compra = new Compra();
		compra.setIdEntrada(idEntrada);
		compra.setIdPartido(idPartido);
		//validar datos de entrada
		int error = 0;
	    if(GenericValidator.isBlankOrNull(hincha.getGenero())) {
			model.addAttribute("error", "Debe ingresar nombres");
			error = 1;
		} else if(hincha.getCategoria().equals("0")) {
			model.addAttribute("error", "Debe ingresar nombres");
			error = 1;
		}
		if(error == 1) {
			model.addAttribute("compra", compra);
			return "content/ingresoHinchaCompra";
		}
		
		String[] parts = hincha.getRutCompleto().split("-");
		String nro = parts[0]; 	
		String dv = parts[1]; 
		
		Integer rut = Integer.parseInt(nro);
		hincha.setRut(rut);
		hincha.setDv(dv);
		int insert = hinchaService.insertarHincha(hincha);
		if(insert > 0) {
			Hincha tmp = hinchaService.obtenerHincha(rut);
			compra.setNombreHincha(tmp.getNombres()+" "+tmp.getApellidos());
			 //agregar la entrada al carro
			Entrada entrada =itemService.obtenerEntrada(compra.getIdEntrada());
			Usuario usuario = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
			Compra ticket = new Compra();
			ticket.setIdPartido(entrada.getIdPartido());
			ticket.setIdEntrada(compra.getIdEntrada());
			ticket.setRut(rut);
			ticket.setRutCompleto(compra.getRutDigitado());
			ticket.setUsername(usuario.getUsername());
			ticket.setMonto(entrada.getPrecio());
			ticket.setToken(rut+""+Util.random()); 
			ticket.setNominativa("S");
			ticket.setDescPartido(entrada.getDescPartido());
			ticket.setDescSector(entrada.getDescSector());
			ticket.setNombreHincha(hincha.getNombres()+" "+hincha.getApellidos());
			
			ArrayList<Compra> ticketList= (ArrayList<Compra>) SecurityUtils.getSubject().getSession().getAttribute("carro");
			if(ticketList != null) {
			    ticketList.add(ticket);
			} else {
				ticketList = new ArrayList<Compra>();
				 ticketList.add(ticket);
			}
			SecurityUtils.getSubject().getSession().setAttribute("carro",ticketList);
			int total = 0;
			for(Compra c: ticketList) {
				total = total + c.getMonto();
			}
			SecurityUtils.getSubject().getSession().setAttribute("totalCompra",total);
		} else {
			model.addAttribute("error", "No se pudo ingresar el hincha");
		}
		model.addAttribute("compra", compra);
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido()));
		
		return "content/compra";
		
		
	}
	
}
