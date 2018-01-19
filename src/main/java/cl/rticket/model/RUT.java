package cl.rticket.model;

public class RUT {

	private Integer numero;
	private String dv;
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
	}
	
	public String rutCompleto() {
		return this.numero+"-"+this.dv;
	}
}
