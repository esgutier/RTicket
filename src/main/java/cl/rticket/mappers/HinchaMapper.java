package cl.rticket.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import cl.rticket.model.Hincha;

public interface HinchaMapper {

	
	public Hincha obtenerHincha(@Param("rut")Integer rut, @Param("idEquipo")Integer idEquipo);
	public int insertarHincha(Hincha hincha)throws DuplicateKeyException;
	public int insertarHinchaEntidad(Hincha hincha) throws DuplicateKeyException;
	public int actualizarHincha(Hincha hincha);
	public int actualizarHinchaEntidad(Hincha hincha);
	public ArrayList<Hincha> obtenerEntidades(@Param("idEquipo") Integer idEquipo);
	public ArrayList<Hincha> obtenerListaAbonados(Integer idEquipo);
	public int borrarListaNegra();
	public int insertarImpedido(Hincha hincha) throws DuplicateKeyException;
	public int totalListaNegra();
	public Integer estaEnListaNegra( @Param("rut")Integer rut);
	public Hincha obtenerHinchaAbonado(@Param("rut")Integer rut);
	public int insertarAbonado(Hincha hincha);
	public int actualizarAbonado(Hincha hincha);
	public Hincha obtenerDatosAbonado(@Param("rut")Integer rut, @Param("idEquipo")Integer idEquipo);
	public Integer tieneEntradaPartido(@Param("idPartido")Integer idPartido, @Param("rut")Integer rut);
	
}
