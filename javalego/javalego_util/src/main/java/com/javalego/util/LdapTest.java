package com.javalego.util;

import java.util.Hashtable;
import javax.naming.*;
import javax.naming.directory.*;

/**
 * LDAP
 * @author ROBERTO RANZ
 * 
 *         Use Custom Java Code to Integrate with LDAP I create class for this
 *         purpose for test as below We need some properites of LDAP 1-host name
 *         or IP of LDAP Server and port
 * 
 *         protected static String MY_HOST = "ldap://10.32.209.230:389";
 *         2-Search base in LDAP
 * 
 *         protected static String MY_SEARCHBASE = "DC=MCIT,DC=LOCAL"; 3- User
 *         in LDAP to connect by it in LDAP
 * 
 *         protected static String MGR_DN = "crmtest"; 4-password of user which
 *         we connect by it.
 * 
 *         protected static String MGR_PW = "mcit@****";
 * 
 *         We should enter search criteria to LDAP, In our example we search
 *         about use in LDAP, so I used below filter
 * 
 *         protected static String MY_FILTER = "sAMAccountName=mmahmoud"; as in
 *         previous example mmahmoud is user id which I search about it in LDAP
 */

public class LdapTest {
	protected static String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
	protected static String MY_HOST = "ldap://10.32.209.230:389";
	protected static String MY_SEARCHBASE = "DC=MCIT,DC=LOCAL";
	protected static String MY_FILTER = "sAMAccountName=mmahmoud";
	protected static String MGR_DN = "crmtest";
	protected static String MGR_PW = "mcit@****";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		try {
			Hashtable params = new Hashtable();
			params.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
			params.put(Context.PROVIDER_URL, MY_HOST);
			params.put(Context.SECURITY_AUTHENTICATION, "simple");
			params.put(Context.SECURITY_PRINCIPAL, MGR_DN);
			params.put(Context.SECURITY_CREDENTIALS, MGR_PW);

			DirContext ctx = new InitialDirContext(params);

			SearchControls constraints = new SearchControls();

			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration results = ctx.search(MY_SEARCHBASE, MY_FILTER, constraints);

			if (results != null && results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				String dn = sr.getName();
				System.out.println("Desc name is " + dn);
				Attributes attrs = sr.getAttributes();
				System.out.println(attrs.get("sAMAccountName"));
			} else {
				System.out.println("Not exist User");
			}
		} catch (AuthenticationException e) {
			System.out.println("You aren't authenticated on LDAP");
		} catch (PartialResultException e) {
			System.out.println(MY_FILTER + " Not exists in LDAP");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
