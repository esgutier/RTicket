package cl.rticket.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.rticket.exception.UpdateException;
import cl.rticket.mappers.ItemMapper;
import cl.rticket.model.Compra;
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
	public ArrayList<Sector> obtenerSectores( ) {
		return itemMapper.obtenerSectores();
	}
	
	public ArrayList<Sector> obtenerEntradas( Integer idPartido) {
		return itemMapper.obtenerEntradas(idPartido);
	}
	
	public void insertarEntrada(Entrada entrada) {
		itemMapper.insertarEntrada(entrada);
	}
	
	public void eliminarEntrada( Integer idEntrada) {
		itemMapper.eliminarEntrada( idEntrada);
	}
	
	public Entrada obtenerEntrada(Integer idEntrada) {
		return itemMapper.obtenerEntrada(idEntrada);
	}
	
	@Transactional(rollbackFor={UpdateException.class})
	public void insertarCompra(ArrayList<Compra> list) throws UpdateException {
		int inserta = 0;
		for(Compra ticket: list) {
			inserta = itemMapper.insertarCompra(ticket);
			if(inserta < 1) {
				throw new UpdateException();
			}
		}		
	}

}
