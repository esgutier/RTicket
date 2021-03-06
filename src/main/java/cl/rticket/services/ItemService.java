package cl.rticket.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.Compra;
import cl.rticket.model.Entrada;
import cl.rticket.model.Masiva;
import cl.rticket.model.Partido;
import cl.rticket.model.Sector;
import cl.rticket.model.Ticket;
import cl.rticket.model.TotalesEntrada;

public interface ItemService {

	public ArrayList<Partido>   obtenerPartidos(Integer idEquipo);
	public ArrayList<Sector>    obtenerSectores(Integer idEquipo);
	public ArrayList<Entrada>    obtenerEntradas(Integer idPartido, Integer idEquipo);
	
	public void insertarEntrada(Entrada entrada);
	public int eliminarEntrada(Integer idEntrada)throws DataIntegrityViolationException;
	public int actualizarEntrada(Entrada entrada);	
	public Entrada obtenerEntrada(Integer idEntrada, Integer idEquipo);
	
	public Integer esTicketNominativo(Integer idEquipo,Integer idPartido,Integer idSector,Integer rut);
	public ArrayList<Ticket> insertarCompra(ArrayList<Compra> list, Integer idEquipo) throws UpdateException;
	public void insertarCompra(Compra compra) throws UpdateException;
	public int anularTicket(String token);
	
	public int obtenerTotalSectorVendidas(Integer idEntrada, Integer idPartido);
	//public Ticket obtenerDatosTicketNominativo(Integer idCompra);
	
	public HashMap<Integer,TotalesEntrada> obtenerTotalesEntradas(Integer idPartido) ;
	
	public Masiva obtenerDatosTicketMasivo(Integer idSector, String tipo);
	
	public ArrayList<TotalesEntrada> obtenerTotalesCortesiaPorEntidad(Integer idPartido,Integer rut);
	
	public ArrayList<Ticket> obtenerDatosTicketRut(Integer idEntrada,Integer rut, String tipo);
	
	public ArrayList<Ticket> obtenerDatosTicketCortesia(Integer idPartido, Integer rut, String tipo);
	
	//Mantenedor de Partidos
	public int actualizarPartido(Partido partido);
	public int insertarPartido(Partido partido);
	public Partido obtenerPartido(Integer idPartido);
	public int eliminarPartido(Integer idPartido) throws DataIntegrityViolationException ;
	
	//mantenedor de sectores
	public int actualizarSector(Sector sector);
	public int insertarSector(Sector sector);
	public Sector obtenerSector(Integer idSector);
	public int eliminarSector(Integer idSector) throws DataIntegrityViolationException;
	
	//control de acceso
	
	/*public int actualizarAccesoNominativo(Integer idEquipo,Integer idPartido,Integer idSector,Integer rut);

    public int actualizarAccesoNormal(String idTicket);*/
	
	public Integer existeTicket(Integer idEquipo, String token,Integer idPartido, Integer idSector);
    public Integer esAbonadoVigente(Integer idEquipo,Integer idSector,Integer rut);
    public String buscaTicketCompraToken(Integer idEquipo, Integer idPartido, Integer idSector, Integer rut);
	public HashMap<String, Integer> obtenerEntradasNormalesPorSector(Integer idPartido,Integer idSector );
	public HashMap<Integer, Integer> obtenerEntradasNominativasPorSector(Integer idPartido,Integer idSector);
	public HashMap<Integer, Integer> obtenerAbonadosPorSector(Integer idSector);
	public ArrayList<String> listaEntradasNormalesPorSector(Integer idPartido,Integer idSector );
	public ArrayList<Integer> listaEntradasNominativasPorSector(Integer idPartido,Integer idSector);
	public HashMap<Integer,Integer> obtenerTotalListaNegra();
	
	public void insertarAccesoEstadio(Integer idEquipo,String id, Integer idPartido, Integer idSector) throws DuplicateKeyException;
	
}
