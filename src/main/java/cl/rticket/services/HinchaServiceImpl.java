package cl.rticket.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.rticket.mappers.HinchaMapper;
import cl.rticket.model.Hincha;

@Service("hinchaService")
public class HinchaServiceImpl implements HinchaService{

	@Autowired
	HinchaMapper hinchaMapper ;
	
	public Hincha obtenerHincha(Integer rut) {
		return hinchaMapper.obtenerHincha(rut);
	}
	
	public int insertarHincha(Hincha hincha) {
		return hinchaMapper.insertarHincha(hincha);
	}
	
	public int actualizarHincha(Hincha hincha) {
		return hinchaMapper.actualizarHincha(hincha);
	}
	
	public ArrayList<Hincha> obtenerEntidades() {
		return hinchaMapper.obtenerEntidades();
	}
}
