package cl.rticket.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	public int eliminarEntrada( Integer idEntrada) throws DataIntegrityViolationException {
		return itemMapper.eliminarEntrada( idEntrada);
	}
	
	public int actualizarEntrada(Entrada entrada) {
		return itemMapper.actualizarEntrada(entrada);
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
				String token = "E"+compra.getIdCompra()+Util.randomNumber();
				
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
				String token = "E"+compra.getIdCompra()+Util.randomNumber();			
				int result = itemMapper.actualizarTokenCompra(compra.getIdCompra(),token);					
				if(result < 1) {
					throw new UpdateException();
				}
				
			}
			
	}
	
	public ArrayList<Ticket> obtenerDatosTicketMasivo(Integer idSector, String tipo) {
		return itemMapper.obtenerDatosTicketMasivo(idSector, tipo);
	}
	
	
	public int anularTicket(String token) {
		return itemMapper.anularTicket(token);
	}
	
	
	
	
	public int obtenerTotalSectorVendidas(Integer idEntrada, Integer idPartido) {
		return itemMapper.obtenerTotalSectorVendidas(idEntrada, idPartido);
	}
	
	
	public HashMap<Integer,TotalesEntrada> obtenerTotalesEntradas(Integer idPartido) {
		HashMap<Integer,TotalesEntrada> map = new HashMap<Integer,TotalesEntrada>();
		
		ArrayList<TotalesEntrada> list = itemMapper.obtenerTotalesEntradas(idPartido);
		System.out.println("-->"+list.size());
		for(TotalesEntrada t: list) {
			TotalesEntrada tmp = (TotalesEntrada)map.get(t.getIdEntrada());
			System.out.println("-->"+tmp);
			if(tmp == null) {
				tmp = new TotalesEntrada();
				System.out.println("tmp  null  total: "+t.getTotal());
				if(t.getTipo().equals("N")) {
					System.out.println("tmp null  ->N");
					tmp.setTotalNominativa(t.getTotal());
				} else if(t.getTipo().equals("R")) {
					System.out.println("tmp null  ->R");
					tmp.setTotalNormales(t.getTotal());
				} else if(t.getTipo().equals("C")) {
					System.out.println("tmp null  ->C");
					tmp.setTotalCortesia(t.getTotal());
				}
				
				tmp.setIdEntrada(t.getIdEntrada());
				tmp.setIdPartido(t.getIdPartido());
				tmp.setNombreSector(t.getNombreSector());
				tmp.setMaximo(t.getMaximo());
				tmp.setTipo(t.getTipo());
				map.put(tmp.getIdEntrada(), tmp);
			} else {
				System.out.println("tmp NO null  total: "+t.getTotal());
				tmp.setTipo(t.getTipo());
				if(t.getTipo().equals("N")) {
					System.out.println("tmp NO null  ->N");
					tmp.setTotalNominativa(t.getTotal());
				} else if(t.getTipo().equals("R")) {
					System.out.println("tmp NO null  ->R");
					tmp.setTotalNormales(t.getTotal());
				} else if(t.getTipo().equals("C")) {
					System.out.println("tmp NO null  ->C");
					tmp.setTotalCortesia(t.getTotal());
				}			
			}
		}
		return map;
	}
	
	public ArrayList<TotalesEntrada> obtenerTotalesCortesiaPorEntidad(Integer idPartido,Integer rut) {
		return itemMapper.obtenerTotalesCortesiaPorEntidad(idPartido, rut);
	}
	
	public ArrayList<Ticket> obtenerDatosTicketRut(Integer idEntrada,Integer rut, String tipo) {
		return itemMapper.obtenerDatosTicketRut(idEntrada, rut, tipo);
	}
	
	//Mantenedor de Partidos -----------------------------------------------------------------------------
	public int actualizarPartido(Partido partido) {
		return itemMapper.actualizarPartido(partido);
	}
	public int insertarPartido(Partido partido) {
		return itemMapper.insertarPartido(partido);
	}
	public Partido obtenerPartido(Integer idPartido) {
		return itemMapper.obtenerPartido(idPartido);
	}
	public int eliminarPartido(Integer idPartido) throws DataIntegrityViolationException {
		return itemMapper.eliminarPartido(idPartido);
	}
	
	//Mantenedor de sectores
	public int actualizarSector(Sector sector) {
		return itemMapper.actualizarSector(sector);
	}
	public int insertarSector(Sector sector) {
		return itemMapper.insertarSector(sector);
	}
	public Sector obtenerSector(Integer idSector) {
		return itemMapper.obtenerSector(idSector);
	}
	public int eliminarSector(Integer idSector) throws DataIntegrityViolationException {
		return itemMapper.eliminarSector(idSector);
	}
	
	//control de acceso
	public HashMap<String, Integer> obtenerEntradasNormalesPorSector(Integer idPartido,Integer idSector ) {
		ArrayList<String> list = itemMapper.obtenerEntradasNormalesPorSector(idPartido, idSector);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(String i: list) {
			map.put(i, 0);
		}
		return map;
	}
	
	public ArrayList<String> listaEntradasNormalesPorSector(Integer idPartido,Integer idSector) {
		ArrayList<String> list = itemMapper.obtenerEntradasNormalesPorSector(idPartido, idSector);
		return list;
	}
	
	public HashMap<Integer, Integer> obtenerEntradasNominativasPorSector(Integer idPartido,Integer idSector) {
		ArrayList<Integer> list = itemMapper.obtenerEntradasNominativasPorSector(idPartido, idSector);
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(Integer i: list) {
			map.put(i, 0);
		}
		return map;
	}
	
	public ArrayList<Integer> listaEntradasNominativasPorSector(Integer idPartido,Integer idSector) {
		ArrayList<Integer> list = itemMapper.obtenerEntradasNominativasPorSector(idPartido, idSector);
		return list;
	}

}
