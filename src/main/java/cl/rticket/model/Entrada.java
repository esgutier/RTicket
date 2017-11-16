package cl.rticket.model;

import java.io.Serializable;

public class Entrada implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6106597309665239368L;
	
	private Integer idEquipo;
	private Integer idEntrada;
	private Integer idPartido;
	private String  rutCompleto;
	private Integer rut;
	private String  nombre;
	private String  descPartido;
	private Integer idSector;
	private String  descSector;
	private Integer precio;
	private Integer maximo;
	private String comentario;
	private String scan;
	private Integer inicio; //rango inicial
	private Integer fin;    //rango final
	
	
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
	public String getRutCompleto() {
		return rutCompleto;
	}
	public void setRutCompleto(String rutCompleto) {
		this.rutCompleto = rutCompleto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getRut() {
		return rut;
	}
	public void setRut(Integer rut) {
		this.rut = rut;
	}
	public String getScan() {
		return scan;
	}
	public void setScan(String scan) {
		this.scan = scan;
	}
	public Integer getInicio() {
		return inicio;
	}
	public void setInicio(Integer inicio) {
		this.inicio = inicio;
	}
	public Integer getFin() {
		return fin;
	}
	public void setFin(Integer fin) {
		this.fin = fin;
	}
	public Integer getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}
	

}
