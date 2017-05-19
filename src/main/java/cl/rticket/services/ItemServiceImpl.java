package cl.rticket.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.rticket.mappers.ItemMapper;
import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;

@Service("itemService")
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	ItemMapper itemMapper ;
	
	public ArrayList<Partido> obtenerPartidos() {
		return itemMapper.obtenerPartidos();
	}
	public ArrayList<Sector> obtenerSectores() {
		return itemMapper.obtenerSectores();
	}
	
	public ArrayList<Sector> obtenerEntradas(Integer idPartido) {
		return itemMapper.obtenerEntradas(idPartido);
	}
	
	public void insertarEntrada(Entrada entrada) {
		itemMapper.insertarEntrada(entrada);
	}
	
	public void eliminarEntrada(Integer idEntrada) {
		itemMapper.eliminarEntrada(idEntrada);
	}

}
