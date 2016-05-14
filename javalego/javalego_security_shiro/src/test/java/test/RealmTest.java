package test;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Custom Realm Shiro para JAVALEGO
 */
@SuppressWarnings("deprecation")
public class RealmTest extends AuthorizingRealm {

	private Sha256CredentialsMatcher matcher;

	public RealmTest() {
    	
        setName("MyRealm"); //This name must match the name in the User class's getPrincipals() method
        matcher = new Sha256CredentialsMatcher();
        setCredentialsMatcher(matcher);
        matcher.setStoredCredentialsHexEncoded(false);
    }

    /**
     * Simulates a call to an underlying data store - in a 'real' application, this call would communicate with
     * an underlying data store via an EIS API (JDBC, JPA, Hibernate, etc).
     * <p/>
     * Note that when implementing your own realm, there is no need to check against a password (or other credentials)
     * in this method. The {@link org.apache.shiro.realm.AuthenticatingRealm AuthenticatingRealm} superclass will do
     * that automatically via the use of a configured
     * {@link org.apache.shiro.authc.credential.CredentialsMatcher CredentialsMatcher} (see this example's corresponding
     * {@code shiro.ini} file to see a configured credentials matcher).
     * <p/>
     * All that is required is that the account information include directly the credentials found in the EIS.
     *
     * @param username the username for the account data to retrieve
     * @return the Account information corresponding to the specified username:
     */
    protected SimpleAccount getAccount(String username) {
        //just create a dummy.  A real app would construct one based on EIS access.
    	
    	// Verificar el usuario.
    	if (!validUser(username)) {
    		return null;
    	}
    	
    	// Obtener el password desde base de datos.
        SimpleAccount account = new SimpleAccount(username, getCredentials(username), getName());

        //simulate some roles and permissions:
        getRoles(account);
        getPermissions(account);

        return account;
    }

    private void getRoles(SimpleAccount account) {
    	account.addRole(getRoles(account.getPrincipals().toString()));
	}

    public List<String> getRoles(String principal) {
    	return Arrays.asList(new String[] {"user","admin"});
    }
    
    private void getPermissions(SimpleAccount account) {
        //most applications would assign permissions to Roles instead of users directly because this is much more
        //flexible (it is easier to configure roles and then change role-to-user assignments than it is to maintain
        // permissions for each user).
        // But these next lines assign permissions directly to this trivial account object just for simulation's sake:
        account.addStringPermission("blogEntry:edit"); //this user is allowed to 'edit' _any_ blogEntry
        //fine-grained instance level permission:
        account.addStringPermission("printer:print:laserjet2000"); //allowed to 'print' to the 'printer' identified
        //by the id 'laserjet2000'
	}

	/**
     * Obtener la credenciales del usuario.
     * @param username
     * @return
     */
    private String getCredentials(String username) {
		return "47ehxf8NSp1hmFUmdMCw6z7dVGFOtABJXu9J+SzuLPA=";
	}

	/**
     * Validar el usuario
     * @param username
     * @return
     */
    private boolean validUser(String username) {
		return "guest".equals(username) || "roberto".equals(username); 
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //we can safely cast to a UsernamePasswordToken here, because this class 'supports' UsernamePasswordToken
        //objects.  See the Realm.supports() method if your application will use a different type of token.
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        return getAccount(upToken.getUsername());
    }

    @Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //get the principal this realm cares about:
        String username = (String) getAvailablePrincipal(principals);

        //call the underlying EIS for the account data:
        return getAccount(username);
    }
}

