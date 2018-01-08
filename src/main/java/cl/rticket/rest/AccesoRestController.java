package cl.rticket.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cl.rticket.controller.ControlAccesoController;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;
import cl.rticket.model.VentaEstadio;
import cl.rticket.services.HinchaService;
import cl.rticket.services.ItemService;
import cl.rticket.services.VentaEstadioService;

@RestController
public class AccesoRestController {

	private static final Logger logger = Logger.getLogger(AccesoRestController.class);
	@Autowired
	VentaEstadioService ventaEstadioService;
	@Autowired
	ControlAccesoController controlAcceso;

	@Autowired
	ItemService itemService;

	@Autowired
	HinchaService hinchaService;

	@GetMapping("api/rest/obtener-partido/{idEquipo}")
	public ResponseEntity<Integer> obtenerPartido(@PathVariable("idEquipo") Integer idEquipo) {
		int response = 0;// no hay partidos para el dia
		Calendar fecha = new GregorianCalendar();
		int año = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH);
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		String dia2 = "";
		String mes2 = "";
		if (fecha.get(Calendar.DAY_OF_MONTH) < 10) {
			dia2 = "0" + fecha.get(Calendar.DAY_OF_MONTH);
		} else {
			dia2 = "" + fecha.get(Calendar.DAY_OF_MONTH);
		}
		if (fecha.get(Calendar.MONTH) < 10) {
			mes2 = "0" + (fecha.get(Calendar.MONTH) + 1);
		} else {
			mes2 = "" + (fecha.get(Calendar.MONTH) + 1);
		}

		System.out.println("El dia dos es " + dia2);
		System.out.println("El Mes 2 es " + mes2);
		String fechaSt = "";

		fechaSt = dia2 + "-" + mes2 + "-" + año;

		System.out.println("Fecha de sistema " + fechaSt);

		ArrayList<Partido> partidos = itemService.obtenerPartidos(idEquipo);
		System.out.println("Tamaño lista " + partidos.size());
		for (int i = 0; i < partidos.size(); i++) {
			System.out.println("descripcion antes del if  " + partidos.get(i).getDescripcion());
			System.out.println("fecha bdd " + partidos.get(i).getFecha().substring(0, 10));
			if (partidos.get(i).getFecha().substring(0, 10).equalsIgnoreCase(fechaSt.trim())) {

				System.out.println("entre a el ==");
				return new ResponseEntity<Integer>(partidos.get(i).getIdPartido(), HttpStatus.OK);
			}

		}
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}

	@GetMapping("api/rest/validar-acceso/{idEquipo}/{idPartido}/{idSector}/{input}")
	public ResponseEntity<Integer> validarAcceso(@PathVariable("input") String input,
			@PathVariable("idSector") Integer idSector, @PathVariable("idEquipo") Integer idEquipo,
			@PathVariable("idPartido") Integer idPartido) {

		String first = input.substring(0, 1);
		int response = 0; // 0 : acceso no permitido

		if (first.equals("E")) {
			// lectura del codigo del ticket
			try {
				Integer existe = itemService.existeTicket(idEquipo, input, idPartido, idSector);
				if (existe != null) {
					itemService.insertarAccesoEstadio(idEquipo, input, idPartido, idSector);
					response = 10; // ticket ok
				} else {
					response = 11; // ticket invalido
				}

			} catch (DuplicateKeyException e) {
				response = 12; // ticket ya utilizado
			}

		} else if (input.length() < 9) {
			// es una cedula

			int rut = Integer.parseInt(input);
			// es abonado
			Integer result = itemService.esAbonadoVigente(idEquipo, idSector, rut);
			if (result != null) {
				// es abonado vigente
				try {
					itemService.insertarAccesoEstadio(idEquipo, input, idPartido, idSector);
					response = 21; // abonado ok
				} catch (DuplicateKeyException e) {
					response = 22; // acceso no permitido (abonado) - cedula ya utilizada
				}
			} else {
				// no es abonado, buscar en lista negra
				if (hinchaService.estaEnListaNegra(rut)) {
					response = 0; // acceso no permitido - estadio seguro
				} else {
					result = itemService.esTicketNominativo(idEquipo, idPartido, idSector, rut);
					if (result != null) {
						try {
							itemService.insertarAccesoEstadio(idEquipo, input, idPartido, idSector);
							response = 20; // nominativa ok
						} catch (DuplicateKeyException e) {
							response = 22; // acceso no permitido - cedula ya utilizada
						}
					} else {
						response = 23; // cedula ok - estadio seguro
					}
				}
			}
		} else {
			response = 99; // texto no reconocido
		}

		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}

	@GetMapping("api/rest/validar-acceso/sectores/{idEquipo}")
	public ResponseEntity<ArrayList<Sector>> obtenerSectores(@PathVariable("idEquipo") Integer idEquipo) {
		return new ResponseEntity<ArrayList<Sector>>(itemService.obtenerSectores(idEquipo), HttpStatus.OK);
	}

	@GetMapping("api/rest/validar-compra-estadio/{idEquipo}/{idPartido}/{input}/{input2}")
	public ResponseEntity<Integer> compraEstadio(@PathVariable("input") String input,
			@PathVariable("input2") String input2, @PathVariable("idEquipo") Integer idEquipo,
			@PathVariable("idPartido") Integer idPartido) {

		System.out.println("el input 1 es" + input);
		System.out.println("el input 2 es" + input2);
		System.out.println("El id equipo es " + idEquipo);
		System.out.println("El id partido es " + idPartido);
		VentaEstadio ventaEstadio;
		int error = 0;
		if (input == null || input2 == null) {

			error = 1;
		}
		if (error == 1) {
			// 100 corresponde a un dato malo
			return new ResponseEntity<Integer>(100, HttpStatus.OK);
		}
		if (input.charAt(0) == 'E') {
			ventaEstadio = new VentaEstadio();
			ventaEstadio.setEntrada(input);
			ventaEstadio.setRut(Integer.parseInt(input2));
			Calendar fecha = new GregorianCalendar();
			int año = fecha.get(Calendar.YEAR);
			int mes = fecha.get(Calendar.MONTH);
			int dia = fecha.get(Calendar.DAY_OF_MONTH);
			String fechaSt = +dia + "/" + (mes + 1) + "/" + año;
			System.out.println(fechaSt);

			ventaEstadio.setIdEquipo(idEquipo);
			ventaEstadio.setUsuario("TICKET");
			ventaEstadio.setFecha(fechaSt);
			ventaEstadioService.insertarVentaEstadio(ventaEstadio);
		} else {
			ventaEstadio = new VentaEstadio();
			ventaEstadio.setEntrada(input2);
			ventaEstadio.setRut(Integer.parseInt(input));
			Calendar fecha = new GregorianCalendar();
			int año = fecha.get(Calendar.YEAR);
			int mes = fecha.get(Calendar.MONTH);
			int dia = fecha.get(Calendar.DAY_OF_MONTH);
			String fechaSt = +dia + "/" + (mes + 1) + "/" + año;
			System.out.println(fechaSt);

			ventaEstadio.setIdEquipo(idEquipo);
			ventaEstadio.setUsuario("TICKET");
			ventaEstadio.setFecha(fechaSt);
			ventaEstadioService.insertarVentaEstadio(ventaEstadio);
		}

		return new ResponseEntity<Integer>(101, HttpStatus.OK);
	}

}
