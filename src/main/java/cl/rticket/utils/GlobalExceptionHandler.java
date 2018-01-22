package cl.rticket.utils;

import javax.servlet.http.HttpServletRequest;



import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice

public class GlobalExceptionHandler {



	//private static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

	

	

	@ExceptionHandler(AuthorizationException.class)
	public String handleAuthorizationException(HttpServletRequest request, Exception ex, Model model){
		   // System.out.println("><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	        //logger.info("AuthorizationException Occured:: URL="+request.getRequestURL());
	        model.addAttribute("titulo", "NO AUTORIZADO.");
	        model.addAttribute("texto", "Usted no tiene los privilegios suficientes para acceder a esta funcionalidad.");
	        return "unauthorized";
	}		

}


