package cl.rticket.model;

import java.io.Serializable;

public class Hincha implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4570698422292479318L;	
	private Integer idEquipo;
	private Integer rut;
	private String dv;
	private String rutCompleto;
	private String nombres;
	private String apellidos;
	private String direccion;
	private String genero;
	private String telefono;
	private String email;
	private String fechaNac;
	private String categoria;
	private String mesVigencia;
	private String anioVigencia;
	private String vigencia;
	private Integer idSector;
	private String asiento ;
	private Integer dinero ;
	
	public Integer getRut() {
		return rut;
	}
	
	public Integer getDinero() {
		return dinero;
	}

	public void setDinero(Integer dinero) {
		this.dinero = dinero;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public void setRut(Integer rut) {
		this.rut = rut;
	}
	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getRutCompleto() {
		return rutCompleto;
	}
	public void setRutCompleto(String rutCompleto) {
		this.rutCompleto = rutCompleto;
	}
	public String getMesVigencia() {
		return mesVigencia;
	}
	public void setMesVigencia(String mesVigencia) {
		this.mesVigencia = mesVigencia;
	}
	public String getAnioVigencia() {
		return anioVigencia;
	}
	public void setAnioVigencia(String anioVigencia) {
		this.anioVigencia = anioVigencia;
	}
	public Integer getIdSector() {
		return idSector;
	}
	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	public Integer getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}

}


