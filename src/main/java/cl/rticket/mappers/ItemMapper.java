package cl.rticket.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;


import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;

public interface ItemMapper {
   
	public ArrayList<Partido>   obtenerPartidos();
	public ArrayList<Sector>    obtenerSectores();
	public ArrayList<Sector>    obtenerEntradas(@Param("idPartido")Integer idPartido);
	public void insertarEntrada(Entrada entrada);
	public void eliminarEntrada( @Param("idEntrada")Integer idEntrada);
	public Entrada obtenerEntrada(Integer idEntrada);
}
