package cl.rticket.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Hincha;
import cl.rticket.model.RUT;
import cl.rticket.model.TotalesEntrada;
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
	
	
	//Ingresar hincha desde una compra
	@RequestMapping(value="/cargar-hincha-mantenedor", method=RequestMethod.GET)
	public String cargarHinchaMantenedor(Model model) {
		model.addAttribute("hincha", new Hincha());
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
		return "content/hinchaMantenedor";
	}
	
	@RequestMapping(value="/buscar-hincha-mantenedor", method=RequestMethod.POST)
	public String buscarHinchaMantenedor(Model model, Hincha hincha) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		if(Util.verificaRUT(hincha.getRutCompleto().trim())) {
			String[] parts = hincha.getRutCompleto().trim().split("-");
			String nro = parts[0]; 	
			String dv = parts[1];
			Integer rut = Integer.parseInt(nro);
					
			Hincha hin = hinchaService.obtenerHincha(rut, user.getIdEquipo());
			if(hin == null) {
				model.addAttribute("accion", "INSERTAR");
				hin = new Hincha();
				hin.setRutCompleto(hincha.getRutCompleto());
				hin.setRut(rut);
				hin.setDv(dv);
				model.addAttribute("hincha", hin);
				model.addAttribute("info", "Hincha no se encuentra registrado");
			} else {
				hin.setRutCompleto(hincha.getRutCompleto());
				model.addAttribute("accion", "ACTUALIZAR");
				//verificar si es abonado				
				model.addAttribute("hincha", hin);
			}
		} else {
			model.addAttribute("error", "El Rut ingresado no es válido");
		}
		
		model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
		return "content/hinchaMantenedor";
	}
	
	@RequestMapping(value="/actualizar-hincha-mantenedor", method=RequestMethod.POST)
	public String actualizarHinchaMantenedor(Model model, Hincha hincha, RedirectAttributes flash) {
		//validacion de inputs
		int error = 0;
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		//valida fecha de nacimiento
		if(!Util.validaFecha(hincha.getFechaNac(), "dd/mm/yyyy")) {
			model.addAttribute("error", "Fecha de Nacimiento no es válida");
			error = 1;
		}
		
		if(hincha.getCategoria().equals("A")) {
			if(hincha.getIdSector() == null || hincha.getIdSector().intValue() == 0) {
				model.addAttribute("error", "Debe indicar sector para el abonado");
				error = 1;
			} else if(hincha.getMesVigencia() == null || hincha.getMesVigencia().isEmpty()) {
				model.addAttribute("error", "Debe indicar un mes de vigencia para el abonado");
				error = 1;
			} else if(hincha.getAnioVigencia() == null || hincha.getAnioVigencia().isEmpty()) {
				model.addAttribute("error", "Debe indicar un año de vigencia para el abonado");
				error = 1;
			}
		}
		if(error == 1) {
			model.addAttribute("accion", "ACTUALIZAR");
			model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
			return "content/hinchaMantenedor";
		}
				
		try {
			hincha.setIdEquipo(user.getIdEquipo());
			hinchaService.actualizarHincha(hincha);
			flash.addFlashAttribute("info", "La actualización se realizó correctamente");
			return "redirect:/cargar-hincha-mantenedor";
		} catch (UpdateException e) {
			model.addAttribute("error", "Error: No se puedo realizar la actualización");
		}
		
		model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
		return "content/hinchaMantenedor";
	}
	
	@RequestMapping(value="/insertar-hincha-mantenedor", method=RequestMethod.POST)
	public String insertarHinchaMantenedor(Model model, Hincha hincha, RedirectAttributes flash) {
		//validar inputs
		int error = 0;
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		//valida fecha de nacimiento
		if(!Util.validaFecha(hincha.getFechaNac(), "dd/mm/yyyy")) {
					model.addAttribute("error", "Fecha de Nacimiento no es válida");
					error = 1;
		}
		
		if(hincha.getCategoria().equals("A")) {
			if(hincha.getIdSector() == null || hincha.getIdSector().intValue() == 0) {
				model.addAttribute("error", "Debe indicar sector para el abonado");
				error = 1;
			} else if(hincha.getMesVigencia() == null || hincha.getMesVigencia().isEmpty()) {
				model.addAttribute("error", "Debe indicar un mes de vigencia para el abonado");
				error = 1;
			} else if(hincha.getAnioVigencia() == null || hincha.getAnioVigencia().isEmpty()) {
				model.addAttribute("error", "Debe indicar un año de vigencia para el abonado");
				error = 1;
			}
		}
	  
		if(error == 1) {
			model.addAttribute("accion", "INSERTAR");
			model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
			return "content/hinchaMantenedor";
		}
		
		try {
			hincha.setIdEquipo(user.getIdEquipo());
			hinchaService.insertarHincha(hincha);
			flash.addFlashAttribute("info", "El registro fue agregado correctamente");
			return "redirect:/cargar-hincha-mantenedor";
		} catch (UpdateException e) {
			model.addAttribute("sectores", itemService.obtenerSectores(user.getIdEquipo()));
			model.addAttribute("error", "Error: No se puedo realizar el ingreso del registro");
		}
		
		
		return "content/hinchaMantenedor";
	}
	
//===================================================================================================
// MANTENEDOR DE ENTIDADES
//===================================================================================================
	
	@RequestMapping(value="/cargar-entidad-lista", method=RequestMethod.GET)
	public String cargarEntidadLista(Model model) {	
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("entidades", hinchaService.obtenerEntidades(user.getIdEquipo()));
		return "content/entidadesLista";
	}
	
	@RequestMapping(value="/buscar-entidad", method=RequestMethod.GET)
	public String buscarEntidad(Model model, Hincha hincha,
			                    @RequestParam(value="rut")Integer rut) {
		    Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
			Hincha hin = hinchaService.obtenerHincha(rut, user.getIdEquipo());
			hin.setRutCompleto(hin.getRut()+"-"+hin.getDv());
			model.addAttribute("hincha", hin);
			
		return "content/entidadesBuscar";
	}
	
	@RequestMapping(value="/actualizar-entidad", method=RequestMethod.POST)
	public String actualizarEntidad(Model model, Hincha hincha, RedirectAttributes flash) {
		
		try {
			Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
			hincha.setIdEquipo(user.getIdEquipo());
			hinchaService.actualizarHincha(hincha);
			flash.addFlashAttribute("exito", "La actualización se realizó correctamente");
			return "redirect:/cargar-entidad-lista";
		} catch (UpdateException e) {
			model.addAttribute("error", "Error: No se puedo realizar la actualización");
		}
		
		return "content/entidadesBuscar";
	}
	
	@RequestMapping(value="/cargar-entidad-ingresar", method=RequestMethod.GET)
	public String cargarEntidadIngresar(Model model) {	
		model.addAttribute("hincha", new Hincha());
		return "content/entidadesIngresar";
	}
	
	@RequestMapping(value="/ingresar-entidad", method=RequestMethod.POST)
	public String insertarEntidad(Model model, Hincha hincha, RedirectAttributes flash) {
		//validar inputs
		if(Util.verificaRUT(hincha.getRutCompleto().trim())) {
			String[] parts = hincha.getRutCompleto().trim().split("-");
			String nro = parts[0]; 	
			String dv = parts[1];
			hincha.setRut(Integer.parseInt(nro));
			hincha.setDv(dv);	
			Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
			try {
				hincha.setIdEquipo(user.getIdEquipo());
				hinchaService.insertarHincha(hincha);
				flash.addFlashAttribute("exito", "El registro fue agregado correctamente");
				return "redirect:/cargar-entidad-lista";
			} catch (UpdateException e) {
				model.addAttribute("error", "Error: No se puedo realizar el ingreso del registro "+e.getMensaje());
			}
			
		} else {
			model.addAttribute("error", "El Rut ingresado no es válido");
		}
		
		
		return "content/entidadesIngresar";
	}
	
	
// ===================================================================================================	
	
	
	@RequestMapping(value="/buscar-hincha-compra", method=RequestMethod.POST)
	public String buscarHinchaCompra(Model model, Compra compra) {
		
		//System.out.println("rut escaneado ="+compra.getRutEscaneado());
		RUT rut = null;
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		if(compra.getRutEscaneado() != null && !compra.getRutEscaneado().isEmpty()) {
			rut = Util.obtieneRUT(compra.getRutEscaneado());
		} else {
			model.addAttribute("error", "Debe ingresar el RUT del Hincha");
			model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
			model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
			HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
			model.addAttribute("total", map.get(compra.getIdEntrada()));
			return "content/compra";
		}
		
			//validar formato de rut
			if(Util.verificaRUT(rut.rutCompleto())) {
				
				
				//verificar en lista negra
				if(hinchaService.estaEnListaNegra(rut.getNumero())) {
					model.addAttribute("error", "El Hincha se encuentra en Lista Negra");
					model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
					model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
					HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
					model.addAttribute("total", map.get(compra.getIdEntrada()));
					return "content/compra";
				}
				
				//validar rut
				
				Hincha hincha = hinchaService.obtenerHincha(rut.getNumero(), user.getIdEquipo());
				if(hincha == null) {
					//hincha no está redireccionar a formulario de ingreso de hincha				
					model.addAttribute("compra", compra);
					hincha = new Hincha();
					hincha.setRutCompleto(rut.rutCompleto());					
					model.addAttribute("hincha", hincha);
					return "content/ingresoHinchaCompra";
				} else {
					//validar si un hincha ya tiene una entrada para el partido
					if(hinchaService.tieneEntradaPartido(compra.getIdPartido(), rut.getNumero())) {
						model.addAttribute("error", "El Hincha ya posee un ticket para el partido seleccionado");
						model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
						model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
						HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
						model.addAttribute("total", map.get(compra.getIdEntrada()));
						return "content/compra";
					}
					
				   compra.setNombreHincha(hincha.getNombres()+" "+hincha.getApellidos());
				    //agregar la entrada al carro
					Entrada entrada =itemService.obtenerEntrada(compra.getIdEntrada(), user.getIdEquipo());
					Usuario usuario = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
					Compra ticket = new Compra();
					ticket.setIdPartido(entrada.getIdPartido());
					//aqui setear el idSector
					ticket.setIdEntrada(compra.getIdEntrada());
					ticket.setRut(rut.getNumero());
					ticket.setRutCompleto(rut.rutCompleto());
					ticket.setUsername(usuario.getUsername());
					ticket.setMonto(entrada.getPrecio());
					ticket.setToken("0"); //debe ser string
					ticket.setTipo("N");
					ticket.setAnulada("N");					
					ticket.setDescPartido(entrada.getDescPartido());
					ticket.setDescSector(entrada.getDescSector());
					ticket.setNombreHincha(hincha.getNombres()+" "+hincha.getApellidos());
					
					ArrayList<Compra> ticketList= (ArrayList<Compra>) SecurityUtils.getSubject().getSession().getAttribute("carro");
					//verificar que el rut no este en el carro 
					if(!estaEnCarro(ticketList, ticket)) {
						if(ticketList != null) {
						    ticketList.add(ticket);
						} else {
							ticketList = new ArrayList<Compra>();
							 ticketList.add(ticket);
						}
					} else {
						model.addAttribute("error", "El Hincha ya se encuentra agregado al carro de compras");
						model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
						model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
						HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
						model.addAttribute("total", map.get(compra.getIdEntrada()));
						return "content/compra";
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
	
			
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
		HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
		model.addAttribute("total", map.get(compra.getIdEntrada()));
		
		
		
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
			model.addAttribute("error", "Debe indicar el género");
			error = 1;
		} else if(hincha.getCategoria().equals("0")) {
			model.addAttribute("error", "Debe indicbar la categoría");
			error = 1;
		}
		if(error == 1) {
			model.addAttribute("compra", compra);
			return "content/ingresoHinchaCompra";
		}
		
		String[] parts = hincha.getRutCompleto().split("-");
		String nro = parts[0]; 	
		String dv = parts[1]; 
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		Integer rut = Integer.parseInt(nro);
		hincha.setRut(rut);
		hincha.setDv(dv);
		try {
			hincha.setIdEquipo(user.getIdEquipo());
			hinchaService.insertarHincha(hincha);
			Hincha tmp = hinchaService.obtenerHincha(rut, user.getIdEquipo());
			compra.setNombreHincha(tmp.getNombres()+" "+tmp.getApellidos());
			 //agregar la entrada al carro
			Entrada entrada =itemService.obtenerEntrada(compra.getIdEntrada(), user.getIdEquipo());
			
			Usuario usuario = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
			Compra ticket = new Compra();
			ticket.setIdPartido(entrada.getIdPartido());
			ticket.setIdEntrada(compra.getIdEntrada());
			ticket.setRut(rut);
			ticket.setRutCompleto(tmp.getRut()+"-"+tmp.getDv());
			ticket.setUsername(usuario.getUsername());
			ticket.setMonto(entrada.getPrecio());
			ticket.setToken("0"); 
			ticket.setTipo("N");
			ticket.setAnulada("N");
			ticket.setDescPartido(entrada.getDescPartido());
			ticket.setDescSector(entrada.getDescSector());
			ticket.setNombreHincha(tmp.getNombres()+" "+tmp.getApellidos());
			
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
		} catch (UpdateException e) {
			model.addAttribute("error", "No se pudo ingresar el hincha");
		}
		
		model.addAttribute("compra", compra);
		model.addAttribute("partidos", itemService.obtenerPartidos(user.getIdEquipo()));
		model.addAttribute("entradas", itemService.obtenerEntradas(compra.getIdPartido(), user.getIdEquipo()));
		HashMap<Integer,TotalesEntrada> map = itemService.obtenerTotalesEntradas(compra.getIdPartido());
		model.addAttribute("total", map.get(compra.getIdEntrada()));
		
		return "content/compra";	
	}
	
	// -----------------------------------------------------------------------------------
	// LISTA NEGRA
	//------------------------------------------------------------------------------------
	
	@RequestMapping(value="/carga-pagina-lista-negra", method=RequestMethod.GET)
	public String cargarPaginaListaNegra(Model model) {			
		    model.addAttribute("total",hinchaService.totalListaNegra());
			return "content/ingresoListaNegra";
	}
	
	@RequestMapping(value="/ingresar-lista-negra", method=RequestMethod.POST)
	public String ingresarListaNegra(Model model, MultipartFile fileData,final RedirectAttributes flash) {
		try {
			//validar formato csv
			if(!fileData.getOriginalFilename().toUpperCase().endsWith("CSV")) {			
				flash.addFlashAttribute("error",  "El formato del archivo no es correcto, debe ser csv delimitado por comas.");  
				return "redirect:/carga-pagina-lista-negra";
			}
			
			File convFile = new File(fileData.getOriginalFilename());
		    convFile.createNewFile(); 
		    FileOutputStream fos = new FileOutputStream(convFile); 
		    fos.write(fileData.getBytes());
		    fos.close(); 
		    List<String> lines = FileUtils.readLines(convFile, "UTF-8");
		    lines.remove(0); //quitar la cabecera
		    ArrayList<Hincha> impedidos = new ArrayList<Hincha>();
		    ArrayList<String> errores = new ArrayList<String>();
		    for (String line : lines) {
		    	String[] columnas = line.split(";");                 
                String rut = columnas[4].substring(0, columnas[4].length() - 1);              
                String dv  = columnas[4].substring(columnas[4].length() - 1);
                String nombre = columnas[2]+" "+columnas[3]+" "+columnas[1];
                //System.out.println(" "+rut+"-"+dv+"   "+nombre);
                Hincha impedido = new Hincha();
                try {
                	if(Util.verificaRUT(rut+"-"+dv)) {
                       impedido.setRut(new Integer(rut));
                       impedido.setDv(dv);
                       impedido.setNombres(nombre);  
                       impedidos.add(impedido);
                	} else {
                		errores.add("N° "+columnas[0]+" El RUT "+rut+" no es válido");
                	}
                } catch(NumberFormatException e) {
                	errores.add("N° "+columnas[0]+" El RUT "+rut+" no es válido");
                }  
            }
		    
		    Integer[] resumen = hinchaService.ingresarListaNegra(impedidos);
		    flash.addFlashAttribute("totalIngresados", resumen[0]);
		    flash.addFlashAttribute("totalDuplicados", resumen[1]);
		    //flash.addFlashAttribute("totalProcesados", resumen[2]);
		    
		    flash.addFlashAttribute("errores", errores);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/carga-pagina-lista-negra";
	}
	
	
	
	//----------------------------------------------------------------------
	// Metodo para verificar si un rut ya se encuentra en el carro de compra
	//----------------------------------------------------------------------
	
	private boolean estaEnCarro(ArrayList<Compra> ticketList, Compra ticket) {
		boolean esta = false;
		if(ticketList != null) {
			for(Compra c: ticketList) {
				//System.out.println("->"+c.getRut() +" -->"+ticket.getRut());
				if(c.getRut().intValue() == ticket.getRut().intValue()) {
					//System.out.println("esta en carro");
					esta = true;
					break;
				}
			}
		}
		return esta;
	}
	
}
