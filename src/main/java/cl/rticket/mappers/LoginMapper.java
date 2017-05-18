package cl.rticket.mappers;

import cl.rticket.model.Usuario;

public interface LoginMapper {
	
	public Usuario obtenerUsuario(String username);

}
