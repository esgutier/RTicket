package cl.rticket.exception;

public class UpdateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5470261971286947459L;
	private String mensaje;
	
	public UpdateException() {
		this.mensaje ="";
	}
	
	public UpdateException(String msg) {
		this.mensaje = msg;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
