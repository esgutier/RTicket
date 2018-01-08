package cl.rticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.rticket.mappers.VentaEstadioMapper;
import cl.rticket.model.VentaEstadio;

@Service("ventaEstadioService")
public class VentaEstadioServiceImpl implements VentaEstadioService{
	@Autowired
	VentaEstadioMapper ventaEstadioMapper ;

	public void insertarVentaEstadio(VentaEstadio ventaEstadio) {
		ventaEstadioMapper.insertarVentaEstadio(ventaEstadio);
		
	}
	
	
}
