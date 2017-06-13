package cl.rticket.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cl.rticket.services.ItemService;

@RestController
public class AccesoRestController {
	
	@Autowired
	ItemService itemService;
	
	@GetMapping("rest/normales/partido/{idPartido}/sector/{idSector}")
	public ResponseEntity obtenerNormales(@PathVariable("idPartido") Integer idPartido,
			                          @PathVariable("idSector") Integer idSector) {

		ArrayList<String> lista = itemService.listaEntradasNormalesPorSector(idPartido, idSector);
		if (lista == null) {
			return new ResponseEntity("No hay registros ", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(lista, HttpStatus.OK);
	}
	
	@GetMapping("rest/nominativas/partido/{idPartido}/sector/{idSector}")
	public ResponseEntity obtenerNominativas(@PathVariable("idPartido") Integer idPartido,
			                          @PathVariable("idSector") Integer idSector) {

		ArrayList<Integer> lista = itemService.listaEntradasNominativasPorSector(idPartido, idSector);
		if (lista == null) {
			return new ResponseEntity("No hay registros ", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(lista, HttpStatus.OK);
	}

}
