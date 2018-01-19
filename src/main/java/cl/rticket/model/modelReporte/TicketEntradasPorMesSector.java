package cl.rticket.model.modelReporte;

public class TicketEntradasPorMesSector {
	private Integer idEquipo ;
	private String equipo ;
	private Integer anio ;
	private Integer mes ;
	private String sector ;
	private Integer cantidadEntradas;
	private Integer Monto ;
	
	public TicketEntradasPorMesSector() {
		
	}

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

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
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
		return Monto;
	}

	public void setMonto(Integer monto) {
		Monto = monto;
	}
	
	
}
