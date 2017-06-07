package cl.rticket.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.dao.DataIntegrityViolationException;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;

public interface ItemService {

	public ArrayList<Partido>   obtenerPartidos();
	public ArrayList<Sector>    obtenerSectores();
	public ArrayList<Sector>    obtenerEntradas(Integer idPartido);
	
	public void insertarEntrada(Entrada entrada);
	public int eliminarEntrada(Integer idEntrada)throws DataIntegrityViolationException;
	public int actualizarEntrada(Entrada entrada);	
	public Entrada obtenerEntrada(Integer idEntrada);
	
	
	public ArrayList<Ticket> insertarCompra(ArrayList<Compra> list) throws UpdateException;
	public void insertarCompra(Compra compra) throws UpdateException;
	public int anularTicket(String token);
	
	public int obtenerTotalSectorVendidas(Integer idEntrada, Integer idPartido);
	//public Ticket obtenerDatosTicketNominativo(Integer idCompra);
	
	public HashMap<Integer,TotalesEntrada> obtenerTotalesEntradas(Integer idPartido) ;
	
	public ArrayList<Ticket> obtenerDatosTicketMasivo(Integer idSector, String tipo);
	
	public ArrayList<TotalesEntrada> obtenerTotalesCortesiaPorEntidad(Integer idPartido,Integer rut);
	
	public ArrayList<Ticket> obtenerDatosTicketRut(Integer idEntrada,Integer rut, String tipo);
	
	//Mantenedor de Partidos
	public int actualizarPartido(Partido partido);
	public int insertarPartido(Partido partido);
	public Partido obtenerPartido(Integer idPartido);
	public int eliminarPartido(Integer idPartido) throws DataIntegrityViolationException ;
	
	//mantenedor de sectores
	public int actualizarSector(Sector sector);
	public int insertarSector(Sector sector);
	public Sector obtenerSector(Integer idSector);
	public int eliminarSector(Integer idSector) throws DataIntegrityViolationException;
	
	//control de acceso
	public HashMap<Integer, Integer> obtenerEntradasNormalesPorSector(Integer idPartido,Integer idSector );
	public HashMap<Integer, Integer> obtenerEntradasNominativasPorSector(Integer idPartido,Integer idSector);
	
}
