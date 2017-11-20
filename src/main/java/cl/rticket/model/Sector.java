package cl.rticket.model;

import java.io.Serializable;

public class Sector implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8222184296659798446L;
	
	private Integer idEquipo;
	private Integer idSector;
	private String descripcion;
	
	public Integer getIdSector() {
		return idSector;
	}
	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}
	

}
