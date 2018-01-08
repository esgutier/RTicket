package cl.rticket.model.modelReporte;

public class AccesoPorPartido {
	private Integer idEquipo ;
	private String equipo ;
	private Integer idPartido ;
	private String rival ;
	private String fecha ;
	private Integer cantidad ;
	
	public AccesoPorPartido ( ) {
		
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

	public Integer getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
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

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
