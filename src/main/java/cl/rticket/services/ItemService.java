package cl.rticket.services;

import java.util.ArrayList;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;

public interface ItemService {

	public ArrayList<Partido>   obtenerPartidos();
	public ArrayList<Sector>    obtenerSectores();
	public ArrayList<Sector>    obtenerEntradas(Integer idPartido);
	public void insertarEntrada(Entrada entrada);
	public void eliminarEntrada(Integer idEntrada);
	public Entrada obtenerEntrada(Integer idEntrada);
	public void insertarCompra(ArrayList<Compra> list) throws UpdateException;
	public int obtenerTotalSectorVendidas(Integer idEntrada, Integer idPartido);
}
