package cl.rticket.model;

import java.io.Serializable;

public class Partido implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4557967664465038694L;
	

	private Integer idPartido;
	private String descripcion;
	
	public Integer getIdPartido() {
		return idPartido;
	}
	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
