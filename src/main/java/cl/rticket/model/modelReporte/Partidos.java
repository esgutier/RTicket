package cl.rticket.model.modelReporte;

public class Partidos {
	private Integer idPartido ;
	private Integer idEquipo ;
	private String partido ;
	
	public Partidos ( ) { }
	

	public String getPartido( ) {
		return partido;
	}
	public void setPartido( String partido ) {
		this.partido = partido;
	}
	
	public Integer getIdPartido( ) {
		return idPartido;
	}

	public void setIdPartido( Integer idPartido ) {
		this.idPartido = idPartido;
	}
	public Integer getIdEquipo( ) {
		return idEquipo;
	}

	public void setIdEquipo( Integer idEquipo ) {
		this.idEquipo = idEquipo;
	}
}
