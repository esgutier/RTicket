package cl.rticket.mappers;

import cl.rticket.model.Hincha;

public interface HinchaMapper {

	
	public Hincha obtenerHincha(Integer rut);
	public int insertarHincha(Hincha hincha);
	public int actualizarHincha(Hincha hincha);
	
}
