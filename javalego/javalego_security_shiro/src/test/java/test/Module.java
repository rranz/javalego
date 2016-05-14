package test;

import com.javalego.security.annotation.RolesAllowed;

// Las anotaciones de Shiro requieren activar los aspectos en nuestra aplicación con AOP, Spring, Guice, ...
//@RequiresRoles("admin,warden")
//@RequiresPermissions("winnebago:drive:eagle5")
@RolesAllowed({"admin", "warden"})
public class Module {

}
