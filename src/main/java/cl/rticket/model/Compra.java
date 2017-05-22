package cl.rticket.model;

import java.io.Serializable;

public class Compra implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8713347487018145144L;
	private Integer idEquipo;
	private Integer idPartido;
	private Integer idEntrada;
	private String idHincha;
	private String nombreHincha;
	private Integer rut;
	private String nominativa;
	private String username;
	private String rutEscaneado;
	private String rutDigitado;
	
	public Integer getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}
	public Integer getIdEntrada() {
		return idEntrada;
	}
	public void setIdEntrada(Integer idEntrada) {
		this.idEntrada = idEntrada;
	}
	public String getIdHincha() {
		return idHincha;
	}
	public void setIdHincha(String idHincha) {
		this.idHincha = idHincha;
	}
	public Integer getRut() {
		return rut;
	}
	public void setRut(Integer rut) {
		this.rut = rut;
	}
	public String getNominativa() {
		return nominativa;
	}
	public void setNominativa(String nominativa) {
		this.nominativa = nominativa;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getIdPartido() {
		return idPartido;
	}
	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}
	public String getRutEscaneado() {
		return rutEscaneado;
	}
	public void setRutEscaneado(String rutEscaneado) {
		this.rutEscaneado = rutEscaneado;
	}
	public String getRutDigitado() {
		return rutDigitado;
	}
	public void setRutDigitado(String rutDigitado) {
		this.rutDigitado = rutDigitado;
	}
	public String getNombreHincha() {
		return nombreHincha;
	}
	public void setNombreHincha(String nombreHincha) {
		this.nombreHincha = nombreHincha;
	}
	
}
