package cl.rticket.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import cl.rticket.model.Usuario;
import cl.rticket.services.LoginService;

public class AccesoRealm extends AuthorizingRealm {
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
	/*	SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		ApplicationContextProvider appContext = new ApplicationContextProvider();
		LoginService service = appContext.getApplicationContext().getBean("loginService", LoginService.class);
		String username = (String) principals.getPrimaryPrincipal();
        ArrayList<String> roles = service.obtenerRoles(username);
        System.out.println(">>>>> Cargando Roles  ");
		//cargar los roles del usuario
		for(String s : roles) {
			System.out.println("--->"+s);
			sai.addRole(s);
		}
		
		return sai;*/
		return null;
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) {
		ApplicationContextProvider appContext = new ApplicationContextProvider();
		LoginService service = appContext.getApplicationContext().getBean("loginService", LoginService.class);		
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        Usuario usuario = service.obtenerUsuario(upToken.getUsername());
		
        String password = "";
        if(usuario !=null){
            Subject subject = SecurityUtils.getSubject();            
            password = usuario.getPassword();          
            usuario.setUsername(upToken.getUsername());
            subject.getSession().setAttribute("usuario", usuario);
        }
		return new SimpleAuthenticationInfo(upToken.getUsername(),
				password, getName());
		
	}
}