package cl.rticket.utils;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

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
		/*ApplicationContextProvider appContext = new ApplicationContextProvider();
		LoginService service = appContext.getApplicationContext().getBean("loginService", LoginService.class);		
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        Usuario usuario = service.obtenerDatosUsuario(upToken.getUsername());
		//String password = service.obtenerClave(upToken.getUsername(),1);
        String password = "";
        if(usuario !=null){
            Subject subject = SecurityUtils.getSubject();
            //subject.getSession().setAttribute("username", upToken.getUsername());
            password = usuario.getPassword();
           
            usuario.setUsuario(upToken.getUsername());
            subject.getSession().setAttribute("usuario", usuario);
        }
		return new SimpleAuthenticationInfo(upToken.getUsername(),
				password, getName());*/
		return null;
	}
}