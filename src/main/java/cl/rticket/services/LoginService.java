package cl.rticket.services;

import java.util.ArrayList;

import cl.rticket.model.Usuario;


public interface LoginService {

	public Usuario obtenerUsuario(String username);
	public ArrayList<String> obtenerUsuarioRoles(String username);
}
