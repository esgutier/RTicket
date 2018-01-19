package cl.rticket.model.modelReporte;

public class TicketListaSector {
	private Integer idEquipo ;
	private Integer idSector ;
	private String nombreSector ;
	
	public TicketListaSector ( ) { }

	public Integer getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}

	public Integer getIdSector() {
		return idSector;
	}

	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}

	public String getNombreSector() {
		return nombreSector;
	}

	public void setNombreSector(String nombreSector) {
		this.nombreSector = nombreSector;
	} 
	
	
}
