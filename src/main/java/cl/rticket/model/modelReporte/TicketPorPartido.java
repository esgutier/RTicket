package cl.rticket.model.modelReporte;

public class TicketPorPartido {
	private String idEquipo ;
	private String equipo ;
	private String rival ;
	private String fecha ;
	private Integer cantidadEntradas ;
	private Integer monto ;
	
	public TicketPorPartido( ) {}

	public String getEquipo() {
		return equipo;
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getRival() {
		return rival;
	}

	public void setRival(String rival) {
		this.rival = rival;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Integer getCantidadEntradas() {
		return cantidadEntradas;
	}

	public void setCantidadEntradas(Integer cantidadEntradas) {
		this.cantidadEntradas = cantidadEntradas;
	}

	public Integer getMonto() {
		return monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	}
	
	
}
