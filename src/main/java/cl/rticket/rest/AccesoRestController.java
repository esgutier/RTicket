package cl.rticket.rest;

import java.util.ArrayList;
import java.util.HashMap;

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
import cl.rticket.services.ItemService;

@RestController
public class AccesoRestController {
	
	private static final Logger logger = Logger.getLogger(AccesoRestController.class);
	
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
					
					try {
					    itemService.insertarAccesoEstadio(input, controlAcceso.getIdPartido(), idSector);
					    response = 10; // ticket ok;
						entradasSector.put(input, 1);
						controlAcceso.setTotalEscaneado(controlAcceso.getTotalEscaneado() + 1);						
					} catch(DuplicateKeyException e) {
						response = 12; // acceso no permitido - ticket ya utilizado
					}
				}
			} else {
				response = -1; //datos no cargados
			}
			
		} else if(input.length() < 9) {
			//es una cedula nueva
			
			int rut = Integer.parseInt(input);

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
									//System.out.println("Estadio Seguro: Cedula OK");
									response = 23; //cedula ok - estadio seguro
									logger.info("HINCHA_REGULAR|"+input+"-"+idSector+"-"+controlAcceso.getIdPartido());
								} else {
									//System.out.println("Estadio Seguro: Acceso no permitido");
									response = 0; //acceso no permitido - estadio seguro
								}								
							} else if(value == 1) {
								//System.out.println("Abonado: Cedula ya utilizada");
								response = 22; //acceso no permitido - cedula ya utilizada
							} else {
								//en abonados no verifico lista negra	
								try {
								    itemService.insertarAccesoEstadio(""+rut, controlAcceso.getIdPartido(), idSector);
								    abonadosSector.put(rut, 1);
									response = 21; // cedula ok - abonado
									controlAcceso.setTotalEscaneado(controlAcceso.getTotalEscaneado() + 1);									
								} catch (DuplicateKeyException e)	{
									//System.out.println("Abonado: Cedula ya utilizada (DuplicateKeyException)");
									response = 22; //acceso no permitido - cedula ya utilizada
								}
								
								
							}
						} else {
							response = -1; //datos no cargados
						}
												
						//response = 21; // acceso no permitido - cedula sin registro
					} else if(value == 1) {	
						//System.out.println("Nominativa: Cedula ya utilizada");
						response = 22; //acceso no permitido - cedula ya utilizada
					} else {		
                        try {
							itemService.insertarAccesoEstadio(""+rut, controlAcceso.getIdPartido(), idSector);
							nominativasSector.put(rut, 1);
							response = 20; // cedula ok - nominativa
							controlAcceso.setTotalEscaneado(controlAcceso.getTotalEscaneado() + 1);
                        } catch (DuplicateKeyException e) {
                        	response = 22; //acceso no permitido - cedula ya utilizada
                        	//System.out.println("Nominativa: Cedula ya utilizada (DuplicateKeyException)");
                        }
						
						
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
