package cl.rticket.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import cl.rticket.model.modelReporte.AccesoPorPartido;
import cl.rticket.model.modelReporte.Partidos;
import cl.rticket.model.modelReporte.TicketEntradasPorMesSector;
import cl.rticket.model.modelReporte.TicketListaSector;
import cl.rticket.model.modelReporte.TicketPartioPorSector;
import cl.rticket.model.modelReporte.TicketPorDia;
import cl.rticket.model.modelReporte.TicketPorPartido;

public interface ReporteMapper {
	
	public ArrayList<Partidos> obtenerListaDePartidos( Integer idEquipo );
	public ArrayList<TicketPorPartido> obtenerListaTicketPartido( Integer idEquipo );
	public ArrayList<TicketPartioPorSector> obtenerListaTicketPartidoPorSector( Integer idEquipo );
	public ArrayList<TicketListaSector> obtenerListaTicketSector( Integer idEquipo );
	public ArrayList<AccesoPorPartido> obtenerListaAccesoPorPartido( Integer idEquipo );
	public ArrayList<TicketPorDia> obtenerListaTicketPorDia( Integer idEquipo );
	public ArrayList<TicketEntradasPorMesSector> obtenerListaTicketEntradasPorMesSector( Integer idEquipo );
}
