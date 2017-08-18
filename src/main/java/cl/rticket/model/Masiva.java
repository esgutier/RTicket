package cl.rticket.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Masiva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5970813361957673960L;
	
	public String rival;
	
	public Date fecha;
	private String hora;
	private String sector;
	private String comentario;
	private String precio;
	private Integer secuencia;
	private ArrayList<String> tokens;
	
	
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public Integer getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}
	public ArrayList<String> getTokens() {
		return tokens;
	}
	public void setTokens(ArrayList<String> tokens) {
		this.tokens = tokens;
	}
	public String getRival() {
		return rival;
	}
	public void setRival(String rival) {
		this.rival = rival;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
