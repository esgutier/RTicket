package cl.rticket.services;

import java.util.ArrayList;

import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;

public interface ItemService {

	public ArrayList<Partido>   obtenerPartidos(Integer idEquipo);
	public ArrayList<Sector>    obtenerSectores(Integer idEquipo);
	public ArrayList<Sector>    obtenerEntradas(Integer idEquipo, Integer idPartido);
	public void insertarEntrada(Entrada entrada);
	public void eliminarEntrada(Integer idEquipo,Integer idEntrada);
	public Entrada obtenerEntrada(Integer idEntrada);
}
