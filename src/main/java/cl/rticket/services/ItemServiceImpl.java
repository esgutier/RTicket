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
import cl.rticket.utils.Util;

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
	
	@Transactional(rollbackFor={UpdateException.class, Exception.class})
	public void insertarCompra(ArrayList<Compra> list) throws UpdateException {
		
		for(Compra ticket: list) {
			//inserto compra con token 0
			itemMapper.insertarCompra(ticket);			
			if(ticket.getIdCompra() < 1) {
				throw new UpdateException();
			} else {
				//la compra inserto bien, ahora creo el token con el id de secuencia retornado mas 
				//un numero random y actualizo la tabla compra con el id correspondiente
				String tmp = ticket.getIdCompra()+""+Util.randomNumber();
				Integer token = new Integer(tmp);
				int result = itemMapper.actualizarTokenCompra(ticket.getIdCompra(),token);
				ticket.setToken(token);
				if(result < 1) {
					throw new UpdateException();
				}
			}
		}		
	}
	
	public int obtenerTotalSectorVendidas(Integer idEntrada, Integer idPartido) {
		return itemMapper.obtenerTotalSectorVendidas(idEntrada, idPartido);
	}

}
