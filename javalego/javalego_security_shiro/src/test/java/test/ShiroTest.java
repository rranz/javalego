package test;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.security.shiro.DefaultShiroRealm;
import com.javalego.security.shiro.SecurityShiro;

/**
 * Test de la implementación SecurityContext en Shiro.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ShiroTest {

	public static final Logger logger = Logger.getLogger(ShiroTest.class);

	@Test
	public void test() throws Exception {

		SecurityShiro shiro = new SecurityShiro(new DefaultShiroRealm(new TestUserServices())); // "classpath:shiro.ini");

		// Shiro cifra la password con el matcher definido en shiro.ini y la
		// clave resultante debe ser igual a la que Realm tiene asociada al
		// usuario.
		shiro.login("roberto", "RobertoAbc12");

		logger.info("Authenticated: " + shiro.isAuthenticated());

		// No se necesitan los roles. En la aplicación se puede preguntar al
		// MyRealm que implemente este método e invocarle directamente
		 System.out.println(shiro.isPermitted(new Module()));

		logger.info("Has admin role: " + shiro.hasRole("admin"));

	}

}
