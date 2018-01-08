package cl.rticket.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
	
	public Hincha obtenerHincha(Integer rut , Integer idEquipo) {
		Hincha hin = hinchaMapper.obtenerHincha(rut, idEquipo);
		if(hin != null && hin.getCategoria().equals("A")) {
			Hincha datos = hinchaMapper.obtenerDatosAbonado(rut, idEquipo);
			hin.setMesVigencia(datos.getMesVigencia());
			hin.setAnioVigencia(datos.getAnioVigencia());
			hin.setIdSector(datos.getIdSector());
		}
		return hin;
	}
	
	@Transactional(rollbackFor={UpdateException.class, Exception.class})
	public void insertarHincha(Hincha hincha) throws UpdateException {
		
		try {
			int res = hinchaMapper.insertarHincha(hincha);
			if(res > 0) {
				if(hincha.getCategoria().equals("A")) {				
					//insertar datos abonados
					//construir la fecha
					String date ="01/"+hincha.getMesVigencia()+"/"+hincha.getAnioVigencia();				
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Date convertedDate;
					try {
						convertedDate = dateFormat.parse(date);
						Calendar c = Calendar.getInstance();
						c.setTime(convertedDate);
						c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));					
						date = c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH) + 1)+"/"+c.get(Calendar.YEAR);
						hincha.setVigencia(date);
						
					} catch (ParseException e) {
						throw new UpdateException();
					}
					
					res = hinchaMapper.insertarAbonado(hincha);
					if(res < 1) {
						throw new UpdateException();
					}
				}
			} else {
				throw new UpdateException();
			}
		} catch(DuplicateKeyException e) {
			throw new UpdateException("(Identificador ya existe)");
		}
		
	}
	
	@Transactional(rollbackFor={UpdateException.class, Exception.class})
	public void actualizarHincha(Hincha hincha) throws UpdateException {
		
		   int res = hinchaMapper.actualizarHincha(hincha);
		   if(res > 0) {
			   if(hincha.getCategoria().equals("A")) {				
					//insertar datos abonados
					//construir la fecha
					String date ="01/"+hincha.getMesVigencia()+"/"+hincha.getAnioVigencia();				
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Date convertedDate;
					try {
						convertedDate = dateFormat.parse(date);
						Calendar c = Calendar.getInstance();
						c.setTime(convertedDate);
						c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));					
						date = c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH) + 1)+"/"+c.get(Calendar.YEAR);
						hincha.setVigencia(date);
						
					} catch (ParseException e) {
						throw new UpdateException();
					}
					res = hinchaMapper.actualizarAbonado(hincha);
					if(res < 1) {
						//entonces de hincha normal esta pasando a ser abonado
						res = hinchaMapper.insertarAbonado(hincha);
						if(res < 1) {
							throw new UpdateException();
						}
						
					}
				}
			   
		   } else {
			   throw new UpdateException();
		   }
		
	}
	public ArrayList<Hincha> obtenerListaAbonados(Integer idEquipo){
		return hinchaMapper.obtenerListaAbonados(idEquipo);
	}
	public ArrayList<Hincha> obtenerEntidades(Integer idEquipo) {
		return hinchaMapper.obtenerEntidades(idEquipo);
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
	
	public Hincha obtenerDatosAbonado(Integer rut, Integer idEquipo) {
		return hinchaMapper.obtenerDatosAbonado(rut, idEquipo);
	}
	
	public boolean tieneEntradaPartido(Integer idPartido, Integer rut) {
		Integer res = hinchaMapper.tieneEntradaPartido(idPartido, rut);
		if(res == null) {
			return false;
		}
		return true;
	}
	
}
