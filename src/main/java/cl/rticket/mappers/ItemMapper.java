package cl.rticket.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;

public interface ItemMapper {
   
	public ArrayList<Partido>   obtenerPartidos();
	public ArrayList<Sector>    obtenerSectores();
	public ArrayList<Sector>    obtenerEntradas(@Param("idPartido")Integer idPartido);
	public void insertarEntrada(Entrada entrada);
	public void eliminarEntrada( @Param("idEntrada")Integer idEntrada);
	public Entrada obtenerEntrada(Integer idEntrada);
	public void insertarCompra(Compra compra);
	public int actualizarTokenCompra(@Param("idCompra")Integer idCompra,
			                         @Param("token")Integer token);
	public Integer obtenerTotalSectorVendidas(@Param("idEntrada")Integer idEntrada,
			                                  @Param("idPartido")Integer idPartido);
	
	public Ticket obtenerDatosTicketNominativo(@Param("idCompra")Integer idCompra);
	
	public ArrayList<TotalesEntrada> obtenerTotalesEntradas(@Param("idPartido")Integer idPartido);
	
	public ArrayList<Ticket> obtenerDatosTicketMasivo(@Param("idSector")Integer idSector,
													  @Param("tipo")String tipo);
}
