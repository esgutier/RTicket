package cl.rticket.model.modelReporte;

public class TicketPorDia {
	private Integer idEquipo ;
	private String equipo ;
	private Integer idPartido ;
	private String rival ;
	private String fechaCompra ;
	//private String sector ;
	private Integer cantidadEntrada ;
	private Integer monto ;

	public TicketPorDia ( ) { }

	public Integer getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getEquipo() {
		return equipo;
	}

	public Integer getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
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

	public String getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
/**
	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}*/

	public Integer getCantidadEntrada() {
		return cantidadEntrada;
	}

	public void setCantidadEntrada(Integer cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}

	public Integer getMonto() {
		return monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	} 
	
	
	
}
