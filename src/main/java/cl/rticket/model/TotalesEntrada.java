package cl.rticket.model;

import java.io.Serializable;

public class TotalesEntrada implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1688356943313454866L;
	private Integer idEntrada;
	private String nombreSector;
	private Integer maximo;
	private String tipo;
	private Integer total;
	private Integer totalNominativa = 0;
	private Integer totalNormales = 0;
	private Integer totalCortesia = 0;
	
	
	
	public Integer getIdEntrada() {
		return idEntrada;
	}
	public void setIdEntrada(Integer idEntrada) {
		this.idEntrada = idEntrada;
	}
	public String getNombreSector() {
		return nombreSector;
	}
	public void setNombreSector(String nombreSector) {
		this.nombreSector = nombreSector;
	}
	public Integer getMaximo() {
		return maximo;
	}
	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}
	public Integer getTotalNominativa() {
		return totalNominativa;
	}
	public void setTotalNominativa(Integer totalNominativa) {
		this.totalNominativa = totalNominativa;
	}
	public Integer getTotalNormales() {
		return totalNormales;
	}
	public void setTotalNormales(Integer totalNormales) {
		this.totalNormales = totalNormales;
	}
	public Integer getTotalCortesia() {
		return totalCortesia;
	}
	public void setTotalCortesia(Integer totalCortesia) {
		this.totalCortesia = totalCortesia;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
