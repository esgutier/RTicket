package cl.rticket.mappers;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataIntegrityViolationException;

import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;

public interface ItemMapper {
   
	public ArrayList<Partido>   obtenerPartidos();
	public ArrayList<Sector>    obtenerSectores();
	public ArrayList<Entrada>    obtenerEntradas(@Param("idPartido")Integer idPartido);
	
	
	public void insertarEntrada(Entrada entrada);
	public int eliminarEntrada( @Param("idEntrada")Integer idEntrada)throws DataIntegrityViolationException;
	public int actualizarEntrada(Entrada entrada);
	
	
	public Entrada obtenerEntrada(Integer idEntrada);
	public void insertarCompra(Compra compra);
	public int actualizarTokenCompra(@Param("idCompra")Integer idCompra,
			                         @Param("token")String token);
	
	public int anularTicket(String token);
	public Integer obtenerTotalSectorVendidas(@Param("idEntrada")Integer idEntrada,
			                                  @Param("idPartido")Integer idPartido);
	
	public Ticket obtenerDatosTicketNominativo(@Param("idCompra")Integer idCompra);
	
	public ArrayList<TotalesEntrada> obtenerTotalesEntradas(@Param("idPartido")Integer idPartido);
	
	public ArrayList<Ticket> obtenerDatosTicketMasivo(@Param("idSector")Integer idSector,
													  @Param("tipo")String tipo);
	
	public ArrayList<TotalesEntrada> obtenerTotalesCortesiaPorEntidad(@Param("idPartido")Integer idPartido,
			                                                          @Param("rut")Integer rut);
	
	public ArrayList<Ticket> obtenerDatosTicketRut(@Param("idEntrada")Integer idEntrada,
			                                       @Param("rut")Integer rut,
			                                       @Param("tipo")String tipo);
	
	//MANTENEDOR DE PARTIDOS	
	public int actualizarPartido(Partido partido);
	public int insertarPartido(Partido partido);
	public Partido obtenerPartido(Integer idPartido);
	public int eliminarPartido(Integer idPartido) throws DataIntegrityViolationException ;
	
	//MANTENEDOR DE SECTOR
	public int actualizarSector(Sector sector);
	public int insertarSector(Sector sector);
	public Sector obtenerSector(Integer idSector);
	public int eliminarSector(Integer idSector) throws DataIntegrityViolationException;
	
	//Control de acceso
	public ArrayList<String> obtenerEntradasNormalesPorSector(@Param("idPartido")Integer idPartido,@Param("idSector")Integer idSector );
	public ArrayList<Integer> obtenerEntradasNominativasPorSector(@Param("idPartido")Integer idPartido,@Param("idSector")Integer idSector);
	public ArrayList<Integer> obtenerTotalListaNegra();
	public ArrayList<Integer> obtenerAbonadosPorSector(@Param("idSector")Integer idSector);

}
