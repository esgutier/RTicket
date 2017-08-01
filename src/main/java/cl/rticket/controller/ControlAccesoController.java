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
import cl.rticket.model.RUT;
import cl.rticket.model.Sector;
import cl.rticket.services.HinchaService;
import cl.rticket.services.ItemService;
import cl.rticket.utils.Util;

@Controller
public class ControlAccesoController {

	@Autowired
	ItemService itemService;
	
	@Autowired
	HinchaService hinchaService;
	
	private HashMap<Integer,Integer> listaNegra = new HashMap<Integer,Integer>();
	private HashMap<Integer,HashMap<Integer,Integer>> nominativas = new HashMap<Integer,HashMap<Integer,Integer>>();
	private HashMap<Integer,HashMap<String,Integer>> normales = new HashMap<Integer,HashMap<String,Integer>>();
	private HashMap<Integer,HashMap<Integer,Integer>> abonados = new HashMap<Integer,HashMap<Integer,Integer>>();
	
	private ArrayList<Partido> partidos;
	private ArrayList<Sector> sectores;
	
	private Integer idPartido;
	private Integer totalNormales = 0;
	private Integer totalNominativas = 0;
	private Integer totalAbonados = 0;
	private Integer totalListaNegra = 0;
	private int totalEscaneado = 0;
	
	
	@PostConstruct
	public void init() {
		
	}

	@RequestMapping(value="/control", method=RequestMethod.GET)
	public String cargaControlPuerta(Model model) {
		Entrada entrada = new Entrada();		
		model.addAttribute("entrada", entrada);
		model.addAttribute("sectores", this.getSectores());
		
		return "content/control";
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
		model.addAttribute("totalAbonados", this.totalAbonados);
		model.addAttribute("total", this.getTotalEscaneado());
		

		return "content/controlAcceso";
	}
	
	@RequestMapping(value="/cargar-control-acceso", method=RequestMethod.POST)
	public String cargarAccesoSector(Model model, Partido partido) {
		
		model.addAttribute("partidos", this.getPartidos());
		this.setIdPartido(partido.getIdPartido());
		
		//cargar todas los tickets por sector
		
		this.normales.clear();
		this.nominativas.clear();
		this.abonados.clear();
		this.listaNegra.clear();
		this.totalNormales = 0;
		this.totalNominativas = 0;
		this.totalAbonados = 0;
		
		for(Sector sec: this.getSectores()) {
			HashMap<String,Integer> normalesPorSector = itemService.obtenerEntradasNormalesPorSector(partido.getIdPartido(), sec.getIdSector());
			this.getNormales().put(sec.getIdSector(), normalesPorSector);
			this.totalNormales = this.totalNormales + normalesPorSector.size();
			
			HashMap<Integer,Integer> nominativasPorSector = itemService.obtenerEntradasNominativasPorSector(partido.getIdPartido(), sec.getIdSector()); 
			this.getNominativas().put(sec.getIdSector(), nominativasPorSector);
			this.totalNominativas = this.totalNominativas + nominativasPorSector.size();
			
			HashMap<Integer,Integer> abonadosPorSector = itemService.obtenerAbonadosPorSector(sec.getIdSector()); 
			this.getAbonados().put(sec.getIdSector(), abonadosPorSector);
			this.totalAbonados = this.totalAbonados + abonadosPorSector.size();
		}
		
		//obtener lista negra
		
		this.setListaNegra(itemService.obtenerTotalListaNegra());
        this.totalListaNegra = this.getListaNegra().size();
		
		model.addAttribute("totalNormales", totalNormales);
		model.addAttribute("totalNominativas", totalNominativas);
		model.addAttribute("totalListaNegra", this.totalListaNegra);
		model.addAttribute("totalAbonados", this.totalAbonados);
		
		return "content/controlAcceso";
	}
	
	
	@RequestMapping(value="/validar-acceso", method=RequestMethod.POST)
	public String validarAcceso(Model model, Entrada entrada) {
		
		String scan = entrada.getScan().trim();
		String first = scan.substring(0, 1);
		model.addAttribute("respuesta", " ACCESO NO PERMITIDO");
		//System.out.println("------>"+first);
		if(first.equals("E")) {
			//System.out.println("scan:"+scan+" sector:"+entrada.getIdSector());
			HashMap<String,Integer> entradasSector  = this.getNormales().get(entrada.getIdSector());
			
			if(entradasSector != null) {
				Integer value = entradasSector.get(scan);
				if(value == null) {
					model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/>"+scan+"<br/>TICKET INVÁLIDO</font>");
				} else if(value == 1){
					model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/>"+scan+"<br/>TICKET YA UTILIZADO</font>");
				} else {
					model.addAttribute("respuesta", "<font color=\"green\">"+scan+"<br/>TICKET OK!</font>");
					entradasSector.put(scan, 1);
					this.totalEscaneado++;
				}
			} else {
				model.addAttribute("respuesta", "DATOS NO CARGADOS");
			}
			
		} else if(scan.length() < 11) {
			//es una cedula nueva
			RUT rut = Util.obtieneRUT(scan);
			
			//buscar en lista negra
			//if(this.getListaNegra().get(rut.getNumero()) == null) {
				//buscar en nominativas
				HashMap<Integer,Integer> nominativasSector = this.getNominativas().get(entrada.getIdSector());
				if(nominativasSector != null) {
					Integer value = nominativasSector.get(rut.getNumero());
					if(value == null) {
						//no esta en nominativas, buscar en abonados
						HashMap<Integer,Integer> abonadosSector = this.getAbonados().get(entrada.getIdSector());
						if(abonadosSector != null) {
							value = abonadosSector.get(rut.getNumero());
							if(value == null) {
								//no esta en abonados, buscar en estadio seguro
								if(!this.estaEnListaNegra(this.getListaNegra(),rut.getNumero()))  {
									model.addAttribute("respuesta", "<font color=\"green\">"+rut.rutCompleto()+" <br/>CÉDULA OK! (ESTADIO SEGURO)</font>");
								} else {
									model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/> "+rut.rutCompleto()+" <br/>ESTADIO SEGURO</font>");
								}																
							} else if(value == 1) {
								model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/> "+rut.rutCompleto()+" <br/>CEDULA YA UTILIZADA</font>");
							} else {
								model.addAttribute("respuesta", "<font color=\"green\">"+rut.rutCompleto()+" <br/>CÉDULA OK! (ABONADO)</font>");
								abonadosSector.put(rut.getNumero(), 1);
								this.totalEscaneado++;
							}
						} else {
							model.addAttribute("respuesta", "DATOS NO CARGADOS");	
						}
						
					} else if(value == 1) {
						model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/> "+rut.rutCompleto()+" <br/>CEDULA YA UTILIZADA</font>");
					} else {
						model.addAttribute("respuesta", "<font color=\"green\">"+rut.rutCompleto()+" <br/>CÉDULA OK! (NOMINATIVA)</font>");
						nominativasSector.put(rut.getNumero(), 1);
						this.totalEscaneado++;
					}
				} else { 
					model.addAttribute("respuesta", "DATOS NO CARGADOS");	
				}
			
		} else {
			model.addAttribute("respuesta", "TEXTO ESCANEADO NO RECONOCIDO");					
		}		
		model.addAttribute("sectores", this.getSectores());
		return "content/control";
	}
	
	private boolean estaEnListaNegra(HashMap<Integer,Integer> listaNegra, int rut) {
		boolean esta = false;
		if(listaNegra.get(rut) != null) {
			esta = true;
		}
		return esta;
	}
	
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

	public Integer getTotalAbonados() {
		return totalAbonados;
	}

	public void setTotalAbonados(Integer totalAbonados) {
		this.totalAbonados = totalAbonados;
	}

	public HashMap<Integer,HashMap<Integer,Integer>> getAbonados() {
		return abonados;
	}

	public void setAbonados(HashMap<Integer,HashMap<Integer,Integer>> abonados) {
		this.abonados = abonados;
	}

	public int getTotalEscaneado() {
		return totalEscaneado;
	}

	public void setTotalEscaneado(int totalEscaneado) {
		this.totalEscaneado = totalEscaneado;
	}
}
