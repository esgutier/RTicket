package cl.rticket.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.rticket.exception.UpdateException;
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
		if(hincha.getCategoria().equals("P")) {
		    return hinchaMapper.insertarHincha(hincha);
		} else {
			return hinchaMapper.insertarHinchaEntidad(hincha);
		}
	}
	
	public int actualizarHincha(Hincha hincha) {
		if(hincha.getCategoria().equals("P")) {
		   return hinchaMapper.actualizarHincha(hincha);
		} else {
			
			return hinchaMapper.actualizarHinchaEntidad(hincha);
		}
	}
	
	public ArrayList<Hincha> obtenerEntidades() {
		return hinchaMapper.obtenerEntidades();
	}
	
	public Integer[] ingresarListaNegra(ArrayList<Hincha> impedidos) {
		
		Integer[] resumen = new Integer[3];
		//borrar lista negra
		hinchaMapper.borrarListaNegra();
		int totalProcesados = 0;
		int totalDuplicados = 0;
		int totalIngresados = 0;
		for(Hincha h: impedidos) {
			try {
			   //System.out.println("------>"+h.getRut()+"-"+h.getDv()+"   "+h.getNombres());
			   hinchaMapper.insertarImpedido(h);
			   totalIngresados++;
			} catch(DuplicateKeyException e) {
				totalDuplicados++;
			}
			totalProcesados++;
		}
		resumen[0] = totalIngresados;
		resumen[1] = totalDuplicados;
		resumen[2] = totalProcesados;
		return resumen;
	}
	
	public Integer totalListaNegra() {
		return hinchaMapper.totalListaNegra();
	}
	
	public boolean estaEnListaNegra(Integer rut) {
		Integer res = hinchaMapper.estaEnListaNegra(rut);
		if(res != null && res.intValue() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public Hincha obtenerHinchaAbonado(Integer rut) {
		return hinchaMapper.obtenerHinchaAbonado(rut);
	}
	
	
	@Transactional(rollbackFor={UpdateException.class, Exception.class})
	public void insertarAbonado(Hincha hincha) throws UpdateException {
		Integer res = hinchaMapper.insertarHincha(hincha);
	    if(res < 1) {
	    	throw new UpdateException();
	    }
	    res = hinchaMapper.insertarAbonado(hincha);
	    if( res < 1) {
	    	throw new UpdateException();
	    }
	}
	
	@Transactional(rollbackFor={UpdateException.class, Exception.class})
	public void actualizarAbonado(Hincha hincha)throws UpdateException {
		Integer res = hinchaMapper.actualizarHincha(hincha);
		if(res < 1) {
	    	throw new UpdateException();
	    }
		res = hinchaMapper.actualizarAbonado(hincha);
		if( res < 1) {
		    	throw new UpdateException();
		}
	}
	
}
