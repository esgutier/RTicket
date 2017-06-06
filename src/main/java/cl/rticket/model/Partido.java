package cl.rticket.model;

import java.io.Serializable;

public class Partido implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4557967664465038694L;
	

	private Integer idPartido;
	private String descripcion;
	
	private String fecha;
	private String hora;
	private String fechaTexto;
	
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
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getFechaTexto() {
		return fechaTexto;
	}
	public void setFechaTexto(String fechaTexto) {
		this.fechaTexto = fechaTexto;
	}
	
	
}
