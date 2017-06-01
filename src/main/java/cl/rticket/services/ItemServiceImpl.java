package cl.rticket.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.rticket.exception.UpdateException;
import cl.rticket.mappers.ItemMapper;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;
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
	public ArrayList<Ticket> insertarCompra(ArrayList<Compra> list) throws UpdateException {
		
		ArrayList<Ticket> listaTickets = new ArrayList<Ticket>();
		for(Compra compra: list) {
			//inserto compra con token 0
			itemMapper.insertarCompra(compra);			
			if(compra.getIdCompra() < 1) {
				throw new UpdateException();
			} else {
				//la compra inserto bien, ahora creo el token con el id de secuencia retornado mas 
				//un numero random y actualizo la tabla compra con el id correspondiente
				String tmp = compra.getIdCompra()+""+Util.randomNumber();
				Integer token = new Integer(tmp);
				int result = itemMapper.actualizarTokenCompra(compra.getIdCompra(),token);
				compra.setToken(token);
				if(result < 1) {
					throw new UpdateException();
				}
				//ahora obtengo la info que va a ir impresa en el ticket nominativo
				Ticket ticket = itemMapper.obtenerDatosTicketNominativo(compra.getIdCompra());
				listaTickets.add(ticket);
			}
		}	
		return listaTickets;
	}
	
	@Transactional(rollbackFor={UpdateException.class, Exception.class})
	public void insertarCompra(Compra compra) throws UpdateException {
		
			//inserto compra con token 0
			itemMapper.insertarCompra(compra);			
			if(compra.getIdCompra() < 1) {
				throw new UpdateException();
			} else {
				//la compra inserto bien, ahora creo el token con el id de secuencia retornado mas 
				//un numero random y actualizo la tabla compra con el id correspondiente
				String tmp = compra.getIdCompra()+""+Util.randomNumber();
				Integer token = new Integer(tmp);
				int result = itemMapper.actualizarTokenCompra(compra.getIdCompra(),token);				
				if(result < 1) {
					throw new UpdateException();
				}
				
			}
			
	}
	
	
	
	
	
	
	
	public int obtenerTotalSectorVendidas(Integer idEntrada, Integer idPartido) {
		return itemMapper.obtenerTotalSectorVendidas(idEntrada, idPartido);
	}
	
	
	public HashMap<Integer,TotalesEntrada> obtenerTotalesEntradas(Integer idPartido) {
		HashMap<Integer,TotalesEntrada> map = new HashMap<Integer,TotalesEntrada>();
		
		ArrayList<TotalesEntrada> list = itemMapper.obtenerTotalesEntradas(idPartido);
		for(TotalesEntrada t: list) {
			TotalesEntrada tmp = (TotalesEntrada)map.get(t.getIdEntrada());
			if(tmp == null) {
				tmp = new TotalesEntrada();
				if(t.equals("N")) {
					tmp.setTotalNominativa(t.getTotal());
				} else if(t.equals("R")) {
					tmp.setTotalNormales(t.getTotal());
				} else if(t.equals("C")) {
					tmp.setTotalCortesia(t.getTotal());
				}
				tmp.setIdEntrada(t.getIdEntrada());
				tmp.setNombreSector(t.getNombreSector());
				tmp.setMaximo(t.getMaximo());
				map.put(tmp.getIdEntrada(), tmp);
			} else {
				if(t.equals("N")) {
					tmp.setTotalNominativa(t.getTotal());
				} else if(t.equals("R")) {
					tmp.setTotalNormales(t.getTotal());
				} else if(t.equals("C")) {
					tmp.setTotalCortesia(t.getTotal());
				}			
			}
		}
		return map;
	}
	
	

}
