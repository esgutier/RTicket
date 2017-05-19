package cl.rticket.model;

import java.io.Serializable;

public class Entrada implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6106597309665239368L;
	private Integer idEntrada;
	private Integer idPartido;
	private String  descPartido;
	private Integer idSector;
	private String  descSector;
	private Integer precio;
	
	
	public Integer getIdEntrada() {
		return idEntrada;
	}
	public void setIdEntrada(Integer idEntrada) {
		this.idEntrada = idEntrada;
	}
	public Integer getIdPartido() {
		return idPartido;
	}
	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}
	public Integer getPrecio() {
		return precio;
	}
	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	public Integer getIdSector() {
		return idSector;
	}
	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}
	public String getDescPartido() {
		return descPartido;
	}
	public void setDescPartido(String descPartido) {
		this.descPartido = descPartido;
	}
	public String getDescSector() {
		return descSector;
	}
	public void setDescSector(String descSector) {
		this.descSector = descSector;
	}

}
