package cl.rticket.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Masiva;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;

public interface ItemMapper {
   
	public ArrayList<Partido>   obtenerPartidos(@Param("idEquipo")Integer idEquipo);
	public ArrayList<Sector>    obtenerSectores(@Param("idEquipo")Integer idEquipo);
	public ArrayList<Entrada>    obtenerEntradas(@Param("idPartido")Integer idPartido, @Param("idEquipo")Integer idEquipo);
	
	
	public void insertarEntrada(Entrada entrada);
	public int eliminarEntrada( @Param("idEntrada")Integer idEntrada)throws DataIntegrityViolationException;
	public int actualizarEntrada(Entrada entrada);
	
	
	public Entrada obtenerEntrada(@Param("idEntrada") Integer idEntrada, @Param("idEquipo")Integer idEquipo);
	
	public Integer esTicketNominativo(@Param("idEquipo")Integer idEquipo,
						            @Param("idPartido")Integer idPartido,
						            @Param("idSector")Integer idSector,
						            @Param("rut")Integer rut);
	public void insertarCompra(Compra compra);
	public int actualizarTokenCompra(@Param("idCompra")Integer idCompra,
			                         @Param("token")String token);
	public String buscaTicketCompraToken(@Param("idEquipo")Integer idEquipo,
            @Param("idPartido")Integer idPartido,
            @Param("idSector")Integer idSector,
            @Param("rut")Integer rut);
	
	public int anularTicket(String token);
	public Integer obtenerTotalSectorVendidas(@Param("idEntrada")Integer idEntrada,
			                                  @Param("idPartido")Integer idPartido);
	
	public Ticket obtenerDatosTicketNominativo(@Param("idCompra")Integer idCompra);
	
	public ArrayList<TotalesEntrada> obtenerTotalesEntradas(@Param("idPartido")Integer idPartido);
	
	public Masiva obtenerDatosTicketMasivo(@Param("idSector")Integer idSector,
													  @Param("tipo")String tipo);
	
	public ArrayList<TotalesEntrada> obtenerTotalesCortesiaPorEntidad(@Param("idPartido")Integer idPartido,
			                                                          @Param("rut")Integer rut);
	
	public ArrayList<Ticket> obtenerDatosTicketRut(@Param("idEntrada")Integer idEntrada,
			                                       @Param("rut")Integer rut,
			                                       @Param("tipo")String tipo);
	
	public ArrayList<Ticket> obtenerDatosTicketCortesia(@Param("idPartido")Integer idPartido,
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
	/*
	public int actualizarAccesoNominativo(@Param("idEquipo")Integer idEquipo,
			                              @Param("idPartido")Integer idPartido,
			                              @Param("idSector")Integer idSector,
			                              @Param("rut")Integer rut);
	
	public int actualizarAccesoNormal(@Param("idTicket")String idTicket); */
	
	public Integer existeTicket(@Param("idEquipo")Integer idEquipo, 
								@Param("token")String token,
								@Param("idPartido") Integer idPartido, 
								@Param("idSector") Integer idSector);
	
	public Integer esAbonadoVigente(@Param("idEquipo")Integer idEquipo,
								@Param("idSector")Integer idSector,
								@Param("rut")Integer rut);
	
	public ArrayList<String> obtenerEntradasNormalesPorSector(@Param("idPartido")Integer idPartido,@Param("idSector")Integer idSector );
	public ArrayList<Integer> obtenerEntradasNominativasPorSector(@Param("idPartido")Integer idPartido,@Param("idSector")Integer idSector);
	public ArrayList<Integer> obtenerTotalListaNegra();
	public ArrayList<Integer> obtenerAbonadosPorSector(@Param("idSector")Integer idSector);
	
	public int insertarAccesoEstadio(@Param("idEquipo")Integer idEquipo, @Param("id")String id, @Param("idPartido")Integer idPartido, @Param("idSector")Integer idSector) throws DuplicateKeyException;

}
