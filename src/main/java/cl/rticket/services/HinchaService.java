package cl.rticket.services;

import java.util.ArrayList;

import cl.rticket.model.Hincha;

public interface HinchaService {

	public Hincha obtenerHincha(Integer rut);
	public int insertarHincha(Hincha hincha);
	public int actualizarHincha(Hincha hincha);
	public ArrayList<Hincha> obtenerEntidades();
	public Integer[] ingresarListaNegra(ArrayList<Hincha> impedidos);
	public Integer totalListaNegra();
	public boolean estaEnListaNegra(Integer rut);
	
}
