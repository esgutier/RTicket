package cl.rticket.mappers;

import java.util.ArrayList;

import cl.rticket.model.Hincha;

public interface HinchaMapper {

	
	public Hincha obtenerHincha(Integer rut);
	public int insertarHincha(Hincha hincha);
	public int actualizarHincha(Hincha hincha);
	public ArrayList<Hincha> obtenerEntidades();
	
}
