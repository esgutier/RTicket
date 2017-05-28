package cl.rticket.model;

import java.io.Serializable;

public class Compra implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8713347487018145144L;
	private Integer idCompra;
	private Integer idPartido;	
	private String descPartido;
	private String descSector;
	private Integer idEntrada;
	private String idHincha;
	private String nombreHincha;
	private Integer rut;
	private String nominativa;
	private String username;
	private String rutEscaneado;
	private String rutDigitado;
	private String rutCompleto;
	private Integer monto;
	private Integer token;
	
	
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
	public Integer getMonto() {
		return monto;
	}
	public void setMonto(Integer monto) {
		this.monto = monto;
	}
	public Integer getToken() {
		return token;
	}
	public void setToken(Integer token) {
		this.token = token;
	}
	public String getDescPartido() {
		return descPartido;
	}
	public void setDescPartido(String descPartido) {
		this.descPartido = descPartido;
	}
	public String getRutCompleto() {
		return rutCompleto;
	}
	public void setRutCompleto(String rutCompleto) {
		this.rutCompleto = rutCompleto;
	}
	public String getDescSector() {
		return descSector;
	}
	public void setDescSector(String descSector) {
		this.descSector = descSector;
	}
	public Integer getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}
	
}
