package cl.rticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class LoginController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {		
		return "login";
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String dashboard(Model model) {		
		return "content/dashboard";
	}

}
