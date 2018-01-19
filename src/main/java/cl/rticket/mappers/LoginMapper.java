package cl.rticket.mappers;

import java.util.ArrayList;

import cl.rticket.model.Usuario;

public interface LoginMapper {
	
	public Usuario obtenerUsuario(String username);
	public ArrayList<String> obtenerUsuarioRoles(String username);

}
