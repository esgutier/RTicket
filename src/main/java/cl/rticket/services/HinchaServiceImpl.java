package cl.rticket.services;

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
}
