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
	
	public ArrayList<Partido> obtenerPartidos(Integer idEquipo) {
		return itemMapper.obtenerPartidos(idEquipo);
	}
	public ArrayList<Sector> obtenerSectores(Integer idEquipo) {
		return itemMapper.obtenerSectores(idEquipo);
	}
	
	public ArrayList<Sector> obtenerEntradas(Integer idEquipo, Integer idPartido) {
		return itemMapper.obtenerEntradas(idEquipo, idPartido);
	}
	
	public void insertarEntrada(Entrada entrada) {
		itemMapper.insertarEntrada(entrada);
	}
	
	public void eliminarEntrada(Integer idEquipo, Integer idEntrada) {
		itemMapper.eliminarEntrada(idEquipo, idEntrada);
	}
	
	public Entrada obtenerEntrada(Integer idEntrada) {
		return itemMapper.obtenerEntrada(idEntrada);
	}

}
