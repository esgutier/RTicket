package cl.rticket.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;


import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;

public interface ItemMapper {
   
	public ArrayList<Partido>   obtenerPartidos(Integer idEquipo);
	public ArrayList<Sector>    obtenerSectores(Integer idEquipo);
	public ArrayList<Sector>    obtenerEntradas(@Param("idEquipo") Integer idEquipo,@Param("idPartido")Integer idPartido);
	public void insertarEntrada(Entrada entrada);
	public void eliminarEntrada(@Param("idEquipo") Integer idEquipo, @Param("idEntrada")Integer idEntrada);
	public Entrada obtenerEntrada(Integer idEntrada);
}
