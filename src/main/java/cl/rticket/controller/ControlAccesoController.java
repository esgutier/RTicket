package cl.rticket.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.rticket.model.Entrada;
import cl.rticket.services.ItemService;

@Controller
public class ControlAccesoController {

	@Autowired
	ItemService itemService;
	
	private HashMap<Integer,String> listaNegra;
	private HashMap<Integer,Integer> nominativas;
	private HashMap<Integer,Integer> normales;
	
	@RequestMapping(value="/carga-pagina-control", method=RequestMethod.GET)
	public String cargaPaginaSectores(Model model) {
		model.addAttribute("entrada", new Entrada());
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("sectores", itemService.obtenerSectores());
		
		return "content/controlAcceso";
	}
	
	@RequestMapping(value="/cargar-control-acceso", method=RequestMethod.POST)
	public String cargarAccesoSector(Model model, Entrada entrada) {
		
		model.addAttribute("partidos", itemService.obtenerPartidos());
		model.addAttribute("sectores", itemService.obtenerSectores());
		this.setNormales(itemService.obtenerEntradasNormalesPorSector(entrada.getIdPartido(), entrada.getIdSector()));
		this.setNominativas(itemService.obtenerEntradasNominativasPorSector(entrada.getIdPartido(), entrada.getIdSector()));
		
		model.addAttribute("totalNominativas", this.getNominativas() == null ? 0 : this.getNominativas().size());
		model.addAttribute("totalNormales", this.getNormales() == null ? 0 : this.getNormales().size());
		
		return "content/controlAcceso";
	}
	
	
	
	
	
	
	public HashMap<Integer,String> getListaNegra() {
		return listaNegra;
	}
	public void setListaNegra(HashMap<Integer,String> listaNegra) {
		this.listaNegra = listaNegra;
	}
	public HashMap<Integer,Integer> getNominativas() {
		return nominativas;
	}
	public void setNominativas(HashMap<Integer,Integer> nominativas) {
		this.nominativas = nominativas;
	}
	public HashMap<Integer,Integer> getNormales() {
		return normales;
	}
	public void setNormales(HashMap<Integer,Integer> normales) {
		this.normales = normales;
	}
}
