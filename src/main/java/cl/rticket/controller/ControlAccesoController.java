package cl.rticket.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;
import cl.rticket.services.HinchaService;
import cl.rticket.services.ItemService;

@Controller
public class ControlAccesoController {

	@Autowired
	ItemService itemService;
	
	@Autowired
	HinchaService hinchaService;
	
	private HashMap<Integer,Integer> listaNegra = new HashMap<Integer,Integer>();
	private HashMap<Integer,HashMap<Integer,Integer>> nominativas = new HashMap<Integer,HashMap<Integer,Integer>>();
	private HashMap<Integer,HashMap<String,Integer>> normales = new HashMap<Integer,HashMap<String,Integer>>();
	
	private ArrayList<Partido> partidos;
	private ArrayList<Sector> sectores;
	
	private Integer idPartido;
	private Integer totalNormales = 0;
	private Integer totalNominativas = 0;
	private Integer totalListaNegra = 0;
	
	
	@PostConstruct
	public void init() {
		//this.setPartidos(itemService.obtenerPartidos());
		//this.setSectores(itemService.obtenerSectores());
	}
	
	
	
	
	@RequestMapping(value="/carga-pagina-control", method=RequestMethod.GET)
	public String cargaPaginaSectores(Model model) {
		Partido partido = new Partido();
		partido.setIdPartido(this.idPartido);
		model.addAttribute("partido", partido);
		this.setPartidos(itemService.obtenerPartidos());
		this.setSectores(itemService.obtenerSectores());
		model.addAttribute("partidos", this.getPartidos());
		model.addAttribute("sectores", this.getSectores());
		
		model.addAttribute("totalNormales", this.totalNormales);
		model.addAttribute("totalNominativas", this.totalNominativas);
		model.addAttribute("totalListaNegra", this.totalListaNegra);
		

		return "content/controlAcceso";
	}
	
	@RequestMapping(value="/cargar-control-acceso", method=RequestMethod.POST)
	public String cargarAccesoSector(Model model, Partido partido) {
		
		model.addAttribute("partidos", this.getPartidos());
		this.setIdPartido(partido.getIdPartido());
		
		//cargar todas los tickets por sector
		
		for(Sector sec: this.getSectores()) {
			HashMap<String,Integer> normalesPorSector = itemService.obtenerEntradasNormalesPorSector(partido.getIdPartido(), sec.getIdSector());
			this.getNormales().put(sec.getIdSector(), normalesPorSector);
			this.totalNormales = this.totalNormales + normalesPorSector.size();
			
			HashMap<Integer,Integer> nominativasPorSector = itemService.obtenerEntradasNominativasPorSector(partido.getIdPartido(), sec.getIdSector());
			this.getNominativas().put(sec.getIdSector(), nominativasPorSector);
			this.totalNominativas = this.totalNominativas + nominativasPorSector.size();
		}
		
		//obtener lista negra
		
		this.setListaNegra(itemService.obtenerTotalListaNegra());
        this.totalListaNegra = this.getListaNegra().size();
		
		model.addAttribute("totalNormales", totalNormales);
		model.addAttribute("totalNominativas", totalNominativas);
		model.addAttribute("totalListaNegra", this.totalListaNegra);
		
		return "content/controlAcceso";
	}
	
	/*
	@RequestMapping(value="/validar-acceso", method=RequestMethod.POST)
	public String validarAcceso(Model model, Entrada entrada) {
		
		String scan = entrada.getScan().trim();
		String first = scan.substring(0, 1);
		model.addAttribute("respuesta", " ACCESO NO PERMITIDO");
		System.out.println("------>"+first);
		if(first.equals("E")) {
			System.out.println("scan:"+scan);
			Integer value = this.getNormales().get(scan);
			System.out.println("value:"+value);
			if(value == null) {
				model.addAttribute("respuesta", " TICKET INVÁLIDO");
			} else {
				model.addAttribute("respuesta", "TICKET OK");
			}
		} else if(first.equals("h")) {
			//es una cedula nueva
			String RUN = "0";
			String aux[] = scan.split("\\?");
			String queryParam = aux[1];
			System.out.println("query param:"+queryParam);
			String params[] = queryParam.split("&");
			for(int i=0; i < params.length; i++) {
				String valores[] = params[i].split("=");
				if(valores[0].equals("RUN")) {
					RUN = valores[1];
					break;
				}
				//System.out.println("nombre ="+valores[0]+" valor="+valores[1]);			
			}
			
			System.out.println("RUN ="+RUN);
			aux = RUN.split("-");
			System.out.println("RUN sin dv ="+aux[0]);
			//buscar en lista negra
			if(!hinchaService.estaEnListaNegra(new Integer(aux[0]))) {
				//buscar en nominativas
				Integer value = this.getNominativas().get(new Integer(aux[0]));
				if(value != null) {
					model.addAttribute("respuesta", "NOMINATIVA OK");
				}
			} else {
				model.addAttribute("respuesta", "ACCESO NO PERMITIDO - EN LISTA NEGRA");
			}
			
			
		} else {
			//es cedula antigua pdf417
			String RUN = "0";
			if(first.equals("1")) {
				//es un rut con 8 digitos
				RUN =  scan.substring(0, 8);
				System.out.println("PDF417 RUN(8) sin dv:"+RUN);
			} else{
				//es un rut con 7 digitos
				RUN =  scan.substring(0, 7);
				System.out.println("PDF417 RUN(7) sin dv:"+RUN);
			}
			//buscar en lista negra
			if(!hinchaService.estaEnListaNegra(new Integer(new Integer(RUN)))) {
				//buscar en nominativas
				Integer value = this.getNominativas().get(new Integer(RUN));
				if(value != null) {
					model.addAttribute("respuesta", "NOMINATIVA OK");
				}
			} else {
				model.addAttribute("respuesta", "ACCESO NO PERMITIDO - EN LISTA NEGRA");
			}
			
			
		}
		
		model.addAttribute("totalNominativas", this.getNominativas() == null ? 0 : this.getNominativas().size());
		model.addAttribute("totalNormales", this.getNormales() == null ? 0 : this.getNormales().size());
		model.addAttribute("partidos", this.getPartidos());
		model.addAttribute("sectores", this.getSectores());
		
		return "content/controlAcceso";
	}
	*/
	
	
	
	
	
	
	
	public ArrayList<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(ArrayList<Partido> partidos) {
		this.partidos = partidos;
	}

	public ArrayList<Sector> getSectores() {
		return sectores;
	}

	public void setSectores(ArrayList<Sector> sectores) {
		this.sectores = sectores;
	}




	public HashMap<Integer,HashMap<String,Integer>> getNormales() {
		return normales;
	}




	public void setNormales(HashMap<Integer,HashMap<String,Integer>> normales) {
		this.normales = normales;
	}




	public HashMap<Integer,HashMap<Integer,Integer>> getNominativas() {
		return nominativas;
	}




	public void setNominativas(HashMap<Integer,HashMap<Integer,Integer>> nominativas) {
		this.nominativas = nominativas;
	}




	public HashMap<Integer,Integer> getListaNegra() {
		return listaNegra;
	}




	public void setListaNegra(HashMap<Integer,Integer> listaNegra) {
		this.listaNegra = listaNegra;
	}




	public Integer getIdPartido() {
		return idPartido;
	}




	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}
}
