package cl.rticket.model;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2267388171945913348L;
	
	private String rival;
	private Date fecha;
	private String hora;
	private String sector;
	private String tipo;
	private String token;
	private String nombres;
	private String apellidos;
	private String precio;
	private String tokenEscaneado;
	private String tokenDigitado;
	private Integer secuencia;
	
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
	public String getTipo() {
		return tipo;
	}
	public void setCategoria(String tipo) {
		this.tipo = tipo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public void print() {
		System.out.println(" Rival:"+this.rival+" Fecha:"+this.fecha+" Hora:"+this.hora+" Nombre:"+this.nombres+" Apellidos:"+this.apellidos);
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getTokenEscaneado() {
		return tokenEscaneado;
	}
	public void setTokenEscaneado(String tokenEscaneado) {
		this.tokenEscaneado = tokenEscaneado;
	}
	public String getTokenDigitado() {
		return tokenDigitado;
	}
	public void setTokenDigitado(String tokenDigitado) {
		this.tokenDigitado = tokenDigitado;
	}
	public Integer getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}
	

}
