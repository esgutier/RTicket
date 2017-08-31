package cl.rticket.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import cl.rticket.model.Hincha;

public interface HinchaMapper {

	
	public Hincha obtenerHincha(Integer rut);
	public int insertarHincha(Hincha hincha)throws DuplicateKeyException;
	public int insertarHinchaEntidad(Hincha hincha) throws DuplicateKeyException;
	public int actualizarHincha(Hincha hincha);
	public int actualizarHinchaEntidad(Hincha hincha);
	public ArrayList<Hincha> obtenerEntidades();
	public int borrarListaNegra();
	public int insertarImpedido(Hincha hincha) throws DuplicateKeyException;
	public int totalListaNegra();
	public Integer estaEnListaNegra( @Param("rut")Integer rut);
	public Hincha obtenerHinchaAbonado(@Param("rut")Integer rut);
	public int insertarAbonado(Hincha hincha);
	public int actualizarAbonado(Hincha hincha);
	public Hincha obtenerDatosAbonado(@Param("rut")Integer rut);
	public Integer tieneEntradaPartido(@Param("idPartido")Integer idPartido, @Param("rut")Integer rut);
	
}
