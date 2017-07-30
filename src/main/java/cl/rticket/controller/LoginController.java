package cl.rticket.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cl.rticket.exception.ImpresoraNoDisponibleException;
import cl.rticket.model.Usuario;
import cl.rticket.utils.ImpresionTest;



@Controller
public class LoginController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {	
		model.addAttribute("usuario", new Usuario());
		return "login";
	}
	
	@RequestMapping(value="/acceso", method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("content/dashboard");
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(usuario.getUsername(), usuario.getPassword());
		try { 
		    subject.login(token);  
		} catch (IncorrectCredentialsException ex) {          
			mv.addObject("error_message", "Credenciales incorrectas");
			mv.setViewName("login");
	   		
	    }catch (UnknownAccountException ex) { 	    	
	    	mv.addObject("error_message", "Usuario no existe");
	    	mv.setViewName("login");	    	
	    }
	     finally {
		   		token.clear();
	      }
		return mv;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout() {		
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/";
	}
	
	@RequestMapping(value="/test-impresora", method=RequestMethod.GET)
	public String testImpresora(Model model) {		
		ImpresionTest printer = new ImpresionTest();
		try {
			printer.imprimirTest();
		} catch (ImpresoraNoDisponibleException e) {
			model.addAttribute("error", "Impresora no disponible");
		}
		return "content/dashboard";
	}
	
	

}
