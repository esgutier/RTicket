package cl.rticket.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.rticket.mappers.ReporteMapper;
import cl.rticket.model.modelReporte.AbonosPorSector;
import cl.rticket.model.modelReporte.AbonosPorSectorFecha;
import cl.rticket.model.modelReporte.AccesoPorPartido;
import cl.rticket.model.modelReporte.Partidos;
import cl.rticket.model.modelReporte.TicketEntradasPorMesSector;
import cl.rticket.model.modelReporte.TicketListaSector;
import cl.rticket.model.modelReporte.TicketPartioPorSector;
import cl.rticket.model.modelReporte.TicketPorDia;
import cl.rticket.model.modelReporte.TicketPorPartido;


@Service("reporteService")
public class ReporteServiceImpl implements ReporteService {
	@Autowired
	ReporteMapper reporteMapper ;

	public ArrayList<Partidos> obtenerListaDePartidos( Integer idEquipo ) {
		return reporteMapper.obtenerListaDePartidos( idEquipo );
	}
	
	public ArrayList<TicketPorPartido> obtenerListaTicketPartido( Integer idEquipo) {
		return reporteMapper.obtenerListaTicketPartido( idEquipo );
	}
	
	public ArrayList<TicketPartioPorSector> obtenerListaTicketPartidoPorSector ( Integer idEquipo ){
		return reporteMapper.obtenerListaTicketPartidoPorSector( idEquipo );
	}
	
	public ArrayList<TicketListaSector> obtenerListaTicketSector ( Integer idEquipo ){
		return reporteMapper.obtenerListaTicketSector( idEquipo );
	}

	public ArrayList<AccesoPorPartido> obtenerListaAccesoPorPartido(Integer idEquipo) {
		return reporteMapper.obtenerListaAccesoPorPartido( idEquipo );
	}

	public ArrayList<TicketPorDia> obtenerListaTicketPorDia( Integer idEquipo ) {
		
		return reporteMapper.obtenerListaTicketPorDia( idEquipo );
	}

	public ArrayList<TicketEntradasPorMesSector> obtenerListaTicketEntradasPorMesSector(Integer idEquipo) {
		
		return reporteMapper.obtenerListaTicketEntradasPorMesSector(idEquipo);
	}

	public ArrayList<AbonosPorSector> obtenerListaAbonosPorSector(Integer idEquipo) {
		
		return reporteMapper.obtenerListaAbonosPorSector(idEquipo);
	}

	public ArrayList<AbonosPorSectorFecha> obtenerListaAbonosPorSectorFecha(Integer idEquipo) {
		
		return reporteMapper.obtenerListaAbonosPorSectorFecha(idEquipo);
	}
	
}
