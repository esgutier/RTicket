package cl.rticket.rest;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cl.rticket.controller.ControlAccesoController;
import cl.rticket.model.Sector;
import cl.rticket.services.HinchaService;
import cl.rticket.services.ItemService;

@RestController
public class AccesoRestController {
	
	private static final Logger logger = Logger.getLogger(AccesoRestController.class);
	
	@Autowired
	ControlAccesoController controlAcceso;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	HinchaService hinchaService;
	
	
	@GetMapping("api/rest/validar-acceso/{idEquipo}/{idPartido}/{idSector}/{input}")
	public ResponseEntity<Integer> validarAcceso(@PathVariable("input") String input, 
			                                     @PathVariable("idSector") Integer idSector,
			                                     @PathVariable("idEquipo") Integer idEquipo,
			                                     @PathVariable("idPartido") Integer idPartido) {
		
		
		String first = input.substring(0, 1);
		int response = 0; // 0 : acceso no permitido
			
		if(first.equals("E")) {
			//lectura del codigo del ticket
			try {
			   Integer existe = itemService.existeTicket(idEquipo, input, idPartido, idSector);
			   if(existe != null) {
				   itemService.insertarAccesoEstadio(idEquipo, input, idPartido, idSector);
				   response = 10; //ticket ok
			   } else {
				   response = 11; //ticket invalido
			   }
			  
			} catch (DuplicateKeyException e) {
				response = 12; // ticket ya utilizado
			}
			
		} else if(input.length() < 9) {
			//es una cedula 
			
			int rut = Integer.parseInt(input);
		    // es abonado
			Integer result = itemService.esAbonadoVigente(idEquipo, idSector, rut);
			if(result != null) {
				//es abonado vigente
				try {
					   itemService.insertarAccesoEstadio(idEquipo, input, idPartido, idSector);
					   response = 21; //abonado ok
				} catch (DuplicateKeyException e) {
						response = 22; // acceso no permitido (abonado) - cedula ya utilizada 
				}
			} else {
				//no es abonado, buscar en lista negra
				if(hinchaService.estaEnListaNegra(rut)) {
					response = 0; //acceso no permitido - estadio seguro
				} else {
					result = itemService.esTicketNominativo(idEquipo, idPartido, idSector, rut);
					if(result != null) {
						try {
							itemService.insertarAccesoEstadio(idEquipo, input, idPartido, idSector);
							response = 20; //nominativa ok
						} catch (DuplicateKeyException e) {
							response = 22; // acceso no permitido  - cedula ya utilizada 
						}
					}else {
						response = 23; //cedula ok - estadio seguro
					}
				}
			}	
		} else {
			response = 99; //texto no reconocido				
		}		
		
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("api/rest/validar-acceso/sectores/{idEquipo}")
	public ResponseEntity<ArrayList<Sector>> obtenerSectores( @PathVariable("idEquipo") Integer idEquipo) {
		return new ResponseEntity<ArrayList<Sector>>(itemService.obtenerSectores(idEquipo), HttpStatus.OK);
	}
	
}
