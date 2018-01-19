package cl.rticket.services;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import cl.rticket.exception.UpdateException;
import cl.rticket.model.Hincha;

public interface HinchaService {

	public Hincha obtenerHincha(Integer rut, Integer idEquipo);
	public void insertarHincha(Hincha hincha) throws UpdateException;
	public void actualizarHincha(Hincha hincha) throws UpdateException;
	public ArrayList<Hincha> obtenerEntidades(Integer idEquipo);
	public ArrayList<Hincha> obtenerListaAbonados(Integer idEquipo);
	public Integer[] ingresarListaNegra(ArrayList<Hincha> impedidos);
	public Integer totalListaNegra();
	public boolean estaEnListaNegra(Integer rut);
	public boolean esAbonado( Integer rut);
	public Hincha obtenerHinchaAbonado(Integer rut);
	public void insertarAbonado(Hincha hincha)throws UpdateException;
	public void actualizarAbonado(Hincha hincha)throws UpdateException;
	public Hincha obtenerDatosAbonado(Integer rut, Integer idEquipo);
	public boolean tieneEntradaPartido(Integer idPartido, Integer rut);
	
}
