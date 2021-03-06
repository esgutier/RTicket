package cl.rticket.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.rticket.mappers.LoginMapper;
import cl.rticket.model.Usuario;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	@Autowired
	LoginMapper loginMapper;
	
	public Usuario obtenerUsuario(String username) {
		
		return loginMapper.obtenerUsuario(username);
	}
	
	public ArrayList<String> obtenerUsuarioRoles(String username) {
		return loginMapper.obtenerUsuarioRoles(username);
	}
}
