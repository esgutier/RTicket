package cl.rticket.rest;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cl.rticket.controller.ControlAccesoController;
import cl.rticket.model.Sector;
import cl.rticket.services.ItemService;

@RestController
public class AccesoRestController {
	
	
	
	@Autowired
	ControlAccesoController controlAcceso;
	
	@Autowired
	ItemService itemService;
	
	
	@GetMapping("api/rest/validar-acceso/{input}/sector/{idSector}")
	public ResponseEntity<Integer> validarAcceso(@PathVariable("input") String input, @PathVariable("idSector") Integer idSector) {
		
		
		String first = input.substring(0, 1);
		int response = 0; // 0 : acceso no permitido
		
		
		if(first.equals("E")) {
			
			HashMap<String,Integer> entradasSector  = controlAcceso.getNormales().get(idSector);
			
			if(entradasSector != null) {
				Integer value = entradasSector.get(input);
				if(value == null) {					
					response = 11; // acceso no permitido - ticket invalido
				} else if(value == 1){					
					response = 12; // acceso no permitido - ticket ya utilizado
				} else {					
					response = 10; // ticket ok;
					entradasSector.put(input, 1);
					controlAcceso.setTotalEscaneado(controlAcceso.getTotalEscaneado() + 1);
				}
			} else {
				response = -1; //datos no cargados
			}
			
		} else if(input.length() < 9) {
			//es una cedula nueva
			//RUT rut = Util.obtieneRUT(input);
			int rut = Integer.parseInt(input);
			
			//buscar en lista negra
			//if(controlAcceso.getListaNegra().get(rut) == null) {
				//buscar en nominativas
				HashMap<Integer,Integer> nominativasSector = controlAcceso.getNominativas().get(idSector);
				if(nominativasSector != null) {
					Integer value = nominativasSector.get(rut);
					if(value == null) {		
						//no esta en nominativas, buscar en abonados
						HashMap<Integer,Integer> abonadosSector = controlAcceso.getAbonados().get(idSector);
						
						if(abonadosSector != null) {
							value = abonadosSector.get(rut);
							if(value == null) {							
								//no esta en abonados, verificar estadio seguro
								if(!estaEnListaNegra(controlAcceso.getListaNegra(),rut))  {
									response = 23; //cedula ok - estadio seguro
								} else {
									response = 0; //acceso no permitido - estadio seguro
								}								
							} else if(value == 1) {							
								response = 22; //acceso no permitido - cedula ya utilizada
							} else {
								//abonados no verifico lista negra								
								abonadosSector.put(rut, 1);
								response = 21; // cedula ok - abonado
								controlAcceso.setTotalEscaneado(controlAcceso.getTotalEscaneado() + 1);
							}
						} else {
							response = -1; //datos no cargados
						}
												
						//response = 21; // acceso no permitido - cedula sin registro
					} else if(value == 1) {						
						response = 22; //acceso no permitido - cedula ya utilizada
					} else {						
						nominativasSector.put(rut, 1);
						response = 20; // cedula ok - nominativa
						controlAcceso.setTotalEscaneado(controlAcceso.getTotalEscaneado() + 1);
					}
				} else {
					response = -1; //datos no cargados
				}
			
		} else {
			response = 99; //texto no reconocido				
		}		
		
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("api/rest/validar-acceso/sectores")
	public ResponseEntity<ArrayList<Sector>> obtenerSectores() {
		return new ResponseEntity<ArrayList<Sector>>(itemService.obtenerSectores(), HttpStatus.OK);
	}
	
	private boolean estaEnListaNegra(HashMap<Integer,Integer> listaNegra, int rut) {
		boolean esta = false;
		if(listaNegra.get(rut) != null) {
			esta = true;
		}
		return esta;
	}

}
