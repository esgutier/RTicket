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
	private Integer maximo;
	private String comentario;
	
	
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
		return descSector +" - ($"+this.precio+") "+this.comentario;
	}
	public void setDescSector(String descSector) {
		this.descSector = descSector;
	}
	public Integer getMaximo() {
		return maximo;
	}
	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	

}
