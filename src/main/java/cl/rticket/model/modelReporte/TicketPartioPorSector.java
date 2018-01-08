package cl.rticket.model.modelReporte;

public class TicketPartioPorSector {
	private Integer idEquipo ;
	private String equipo ;
	private String rival ;
	private String fecha ;
	private String sector ;
	private Integer cantidadEntradas ;
	private Integer monto ;
	
	public TicketPartioPorSector ( ) { }

	public Integer getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getEquipo() {
		return equipo;
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
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
