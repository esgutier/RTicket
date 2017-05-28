package cl.rticket.services;

import cl.rticket.model.Hincha;

public interface HinchaService {

	public Hincha obtenerHincha(Integer rut);
	public int insertarHincha(Hincha hincha);
	public int actualizarHincha(Hincha hincha);
}