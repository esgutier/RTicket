package cl.rticket.services;

import java.util.ArrayList;
import java.util.HashMap;

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
	public void eliminarEntrada(Integer idEntrada);
	public Entrada obtenerEntrada(Integer idEntrada);
	public ArrayList<Ticket> insertarCompra(ArrayList<Compra> list) throws UpdateException;
	public void insertarCompra(Compra compra) throws UpdateException;
	
	public int obtenerTotalSectorVendidas(Integer idEntrada, Integer idPartido);
	//public Ticket obtenerDatosTicketNominativo(Integer idCompra);
	
	public HashMap<Integer,TotalesEntrada> obtenerTotalesEntradas(Integer idPartido) ;
}
