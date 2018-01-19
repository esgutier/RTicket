package cl.rticket.model.modelReporte;

public class AbonosPorSector {
	private Integer idEquipo;
	private String nombreEquipo ;
	private String sector ;
	private Integer monto ;
	private Integer Cantidad ;
	
	public AbonosPorSector ( ) {}
	public AbonosPorSector(Integer idEquipo, String nombreEquipo, String sector,  Integer monto, Integer cantidad) {
		super();
		this.idEquipo = idEquipo;
		this.nombreEquipo = nombreEquipo ;
		this.sector = sector;
		this.monto = monto;
		Cantidad = cantidad;
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
		return Cantidad;
	}
	public void setCantidad(Integer cantidad) {
		Cantidad = cantidad;
	}
	
	
}
