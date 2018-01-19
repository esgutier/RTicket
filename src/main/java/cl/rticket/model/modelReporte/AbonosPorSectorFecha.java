package cl.rticket.model.modelReporte;

public class AbonosPorSectorFecha {
	private Integer idEquipo;
	private String nombreEquipo ;
	private String sector ;
	private String fechaIngreso ;
	private Integer monto ;
	private Integer cantidad ;
	
	public AbonosPorSectorFecha ( ) {}
	public AbonosPorSectorFecha(Integer idEquipo,String nombreEquipo, String sector, String fechaIngreso, Integer monto, Integer cantidad) {
		super();
		this.idEquipo = idEquipo;
		this.nombreEquipo = nombreEquipo;
		this.sector = sector;
		this.fechaIngreso = fechaIngreso ;
		this.monto = monto;
		this.cantidad = cantidad;
	}
	
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	public Integer getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}
	public Integer getMonto() {
		return monto;
	}
	public void setMonto(Integer monto) {
		this.monto = monto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
