package cl.rticket.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.RUT;
import cl.rticket.model.Sector;
import cl.rticket.model.Usuario;
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
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("entrada", entrada);
		model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		
		return "content/control";
	}


	@RequestMapping(value="/validar-acceso", method=RequestMethod.POST)
	public String validarAcceso(Model model, Entrada entrada) {
		
		String scan = entrada.getScan().trim();
		String first = scan.substring(0, 1);
		model.addAttribute("respuesta", " ACCESO NO PERMITIDO");
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");	
		if(first.equals("E")) {
			//lectura del codigo del ticket
			try {
				   Integer existe = itemService.existeTicket(user.getIdEquipo(), scan, entrada.getIdPartido(), entrada.getIdSector());
				   if(existe != null) {
					   itemService.insertarAccesoEstadio(user.getIdEquipo(), scan, entrada.getIdPartido(), entrada.getIdSector());
					   model.addAttribute("respuesta", "<font color=\"green\">"+scan+"<br/>TICKET OK!</font>"); //ticket ok
				   } else {
					   model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/>"+scan+"<br/>TICKET INVÁLIDO</font>"); //ticket invalido
				   }
				  
			} catch (DuplicateKeyException e) {
				model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/>"+scan+"<br/>TICKET YA UTILIZADO</font>"); // ticket ya utilizado
			}
			
		} else if(scan.length() < 11) {
			//es una cedula 
			
			RUT rut = Util.obtieneRUT(scan);
		    // es abonado
			Integer result = itemService.esAbonadoVigente(user.getIdEquipo(), entrada.getIdEntrada(), rut.getNumero());
			if(result != null) {
				//es abonado vigente
				try {
					   itemService.insertarAccesoEstadio(user.getIdEquipo(), ""+rut.getNumero(), entrada.getIdPartido(), entrada.getIdSector());
					   model.addAttribute("respuesta", "<font color=\"green\">"+rut.rutCompleto()+" <br/>CÉDULA OK! (ABONADO)</font>"); //abonado ok
				} catch (DuplicateKeyException e) {
					model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/> "+rut.rutCompleto()+" <br/>CEDULA YA UTILIZADA (ABONADO)</font>"); // acceso no permitido (abonado) - cedula ya utilizada 
				}
			} else {
				//no es abonado, buscar en lista negra
				if(hinchaService.estaEnListaNegra(rut.getNumero())) {
					model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/> "+rut.rutCompleto()+" <br/>ESTADIO SEGURO</font>"); //acceso no permitido - estadio seguro
				} else {
					result = itemService.esTicketNominativo(user.getIdEquipo(), entrada.getIdPartido(), entrada.getIdSector(), rut.getNumero());
					if(result != null) {
						try {
							itemService.insertarAccesoEstadio(user.getIdEquipo(), ""+rut.getNumero(), entrada.getIdPartido(), entrada.getIdSector());
							model.addAttribute("respuesta", "<font color=\"green\">"+rut.rutCompleto()+" <br/>CÉDULA OK! (NOMINATIVA)</font>"); //nominativa ok
						} catch (DuplicateKeyException e) {
							model.addAttribute("respuesta", "<font color=\"red\">ACCESO NO PERMITIDO<br/> "+rut.rutCompleto()+" <br/>CEDULA YA UTILIZADA (NOMINATIVA)</font>"); // acceso no permitido  - cedula ya utilizada 
						}
					}else {
						model.addAttribute("respuesta", "<font color=\"green\">"+rut.rutCompleto()+" <br/>CÉDULA OK! (ESTADIO SEGURO)</font>"); //cedula ok - estadio seguro
					}
				}
			}	
		} else {
			model.addAttribute("respuesta", "TEXTO ESCANEADO NO RECONOCIDO"); //texto no reconocido				
		}		
		
		model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));			
		return "content/control";
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
