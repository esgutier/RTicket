package cl.rticket.model;

import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {

	@JsonView()
	public String codigo;
	
	@JsonView()
	public String mensaje;
	
	@JsonView()
	public Object object;
	
	public AjaxResponseBody(String codigo, String mensaje, Object object) {
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.object  = object;		
	}
	
	public AjaxResponseBody(){}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}

