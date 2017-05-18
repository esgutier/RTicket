package cl.rticket.services;

import cl.rticket.model.Usuario;


public interface LoginService {

	public Usuario obtenerUsuario(String username);
}
