package com.javalego.util;

import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * 
 * @author gcano
 * 
 *         Clase que contiene los parámetros de acceso a ldap y accede a él. Su
 *         usa solo en el mantenimiento de usuarios de la aplición
 */
public class AccesoLdap {

	/**
	 * Logger de la aplicación
	 */
	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AccesoLdap.class.getCanonicalName());

	/**
	 * Contiene el sevidor y puesto de ldad recuperado del Name Space Bindings.
	 */
	private static String ldap = null;
	/**
	 * Contiene el usuario de conexión del ldap recuperado del Name Space
	 * Bindings.
	 */
	private static String userLDAP = null;
	/**
	 * Contiene la password del usuario conexión recuperado del Name Space
	 * Bindings.
	 */
	private static String passwdLDAP = null;
	/**
	 * Contiene la Rama del ldap en la cual se realiza la búsqueda de usuarios.
	 * Recuperado del Name Space Bindings.
	 */
	private static String ramaLDAP = null;
	/**
	 * Contiene el atributo Ldap que se utiliza para comparar. Recuperado del Name
	 * Space Bindings.
	 */
	private static String attributoLDAP = null;
	/**
	 * Contiene el grupoLDAP de los usuarios permitidos. Recuperado del Name Space
	 * Bindings.
	 */
	private static String grupoLDAP = null;
	/**
	 * Contiene el valor de la constante que se da de alta en el Name Space
	 * Bindings para apuntar al servidor de ldap
	 */
	private static final String JNDI_LDAP = "cell/persistent/LDAP";
	/**
	 * Contiene el valor de la constante que se da de alta en el Name Space
	 * Bindings para apuntar al usuario de ldap
	 */
	private static final String JNDI_USERLDAP = "cell/persistent/USERLDAPSGIE";
	/**
	 * Contiene el valor de la constante que se da de alta en el Name Space
	 * Bindings para apuntar a la password del usuario de ldap
	 */
	private static final String JNDI_PASSWDLDAP = "cell/persistent/PASSWDUSERLDAPSGIE";
	/**
	 * Contiene el valor de la constante que se da de alta en el Name Space
	 * Bindings para apuntar a la rama de ldap
	 */
	private static final String JNDI_RAMALDAP = "cell/persistent/RAMALDAP";
	/**
	 * Contiene el valor de la constante que se da de alta en el Name Space
	 * Bindings para apuntar al atributo de ldap
	 */
	private static final String JNDI_ATRIBUTOLDAP = "cell/persistent/ATRIBUTOLDAP";
	/**
	 * Contiene el valor de la constante que se da de alta en el Name Space
	 * Bindings para apuntar al grupo de ldap
	 */
	private static final String JNDI_GRUPOLDAP = "cell/persistent/GRUPOLDAP";

	/**
	 * Constructor
	 * 
	 */
	public AccesoLdap() {
		super();

	}

	/**
	 * Se buscan los datos necesarios para la conexión con el LDAP, a través de
	 * los Name Space Bindings.
	 * 
	 */
	public static void getJNDIAtributosLDAP() {
		try {
			final InitialDirContext local = new InitialDirContext();
			ldap = (String) local.lookup(JNDI_LDAP);
			userLDAP = (String) local.lookup(JNDI_USERLDAP);
			passwdLDAP = (String) local.lookup(JNDI_PASSWDLDAP);
			ramaLDAP = (String) local.lookup(JNDI_RAMALDAP);
			attributoLDAP = (String) local.lookup(JNDI_ATRIBUTOLDAP);
			grupoLDAP = ((String) local.lookup(JNDI_GRUPOLDAP)).toUpperCase();
			// if (logger.verificaNivel(java.util.logging.Logger.NIVEL_DEBUG)) {
			logger.info("ldap: " + ldap);
			logger.info("userLDAP: " + userLDAP);
			logger.info("passwdLDAP: " + passwdLDAP);
			logger.info("ramaLDAP: " + ramaLDAP);
			logger.info("attributoLDAP: " + attributoLDAP);
			logger.info("grupoLDAP: " + grupoLDAP);
			// }
		} catch (javax.naming.NamingException ne) {
			logger.info("Error: getJNDIAtributosLDAP" + ne);
		}
	}

	/**
	 * Establece la conexión de ldad
	 * 
	 * @return contexto de ldap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private DirContext establecerConexion() {

		final Hashtable envBinding = new Hashtable();
		DirContext dirCtx = null;

		getJNDIAtributosLDAP();

		envBinding.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		// envBinding.put(Context.PROVIDER_URL, "ldap://es-d-ma008462d:389/");
		envBinding.put(Context.PROVIDER_URL, ldap);
		envBinding.put(Context.SECURITY_AUTHENTICATION, "simple");
		// envBinding.put(Context.SECURITY_PRINCIPAL, "inicio");
		envBinding.put(Context.SECURITY_PRINCIPAL, userLDAP);
		// envBinding.put(Context.SECURITY_CREDENTIALS, "mcetpm");
		envBinding.put(Context.SECURITY_CREDENTIALS, passwdLDAP);

		try {
			// Conectando a la URL
			dirCtx = new InitialDirContext(envBinding);
			// Conectado a la URL
		} catch (javax.naming.CommunicationException ce) {
			logger.info("Error conexion ldap" + ce);
		} catch (javax.naming.AuthenticationException e) {
			logger.info("Error Autenticacion ldap" + e);
		} catch (javax.naming.NamingException ne) {
			logger.info("Error establecerConexion ldap" + ne);
		}
		return dirCtx;

	}

	/**
	 * Valida si el usario pasado está o no en el ldap
	 * 
	 * @param strUsuario
	 * @return Entero que devuelve si el usurio existe, si está en el grupo
	 *         adecuado o si ni siquiera existe
	 */
	@SuppressWarnings("rawtypes")
	public int validaUsuario(String strUsuario) {
		int resultado = 0;
		Attribute grupo;
		String valor;
		int pos;
		final DirContext dirCtx = establecerConexion();
		NamingEnumeration results = null;
		// Se establece el constraints y se realiza la búsqueda de los datos del
		// usuario
		final SearchControls constraints = new SearchControls();
		// if (logger.verificaNivel(Logger.NIVEL_INFO))
		// {
		logger.info("Rama LDAP " + ramaLDAP);
		// }
		// constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
		try {
			// results =
			// dirCtx.search(ramaLDAP,attributoLDAP+"="+strUsuario.trim(),constraints);
			results = dirCtx.search(ramaLDAP, attributoLDAP + "=" + strUsuario.trim(), constraints);

		} catch (NamingException ne) {
			logger.info("Error validaUsuario ldap " + ne);
		}
		try {
			if (results != null) {

				if (results.hasMore()) {
					final SearchResult sr = (SearchResult) results.next();
					grupo = sr.getAttributes().get("memberof");
					if (grupo == null) {
						resultado = 8;
					} else {
						NamingEnumeration enumu = grupo.getAll();
						while (enumu.hasMore()) {
							valor = ((String) enumu.next()).toUpperCase();
							pos = valor.indexOf(grupoLDAP);
							if (pos == -1) {
								resultado = 8;
							} else {
								resultado = 0;
								break;
							}
						}
					}
				} else {
					resultado = 9;
				}
			} else {
				resultado = 9;
			}
			return resultado;
		} catch (NamingException ne) {
			logger.info("Error validaUsuario ldap" + ne);
			resultado = 9;
		}

		return resultado;
	}

	/**
	 * Valida si el par usuario-contraseña es válido en el LDAP
	 * 
	 * @param strUsuario
	 *          Nombre de Usuario
	 * @param strPassword
	 *          Password del Usuario
	 * @return Entero que devuelve si el usurio tiene la password proporcionada.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int validaUsuario(String strUsuario, String strPassword) {
		int retorno = 0;
		try {
			Hashtable envBinding = new Hashtable();
			InitialDirContext local = new InitialDirContext();
			envBinding.put(Context.PROVIDER_URL, (String) local.lookup(JNDI_LDAP));
			envBinding.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			envBinding.put(Context.SECURITY_AUTHENTICATION, "simple");
			envBinding.put(Context.SECURITY_PRINCIPAL, strUsuario);
			envBinding.put(Context.SECURITY_CREDENTIALS, strPassword);
			local = new InitialDirContext(envBinding);

		} catch (javax.naming.CommunicationException ce) {
			logger.info("Error validaUsuario CE" + ce);
			retorno = 1;
		} catch (javax.naming.AuthenticationException e) {
			logger.info("Error validaUsuario AE" + e);
			retorno = 2;
		} catch (javax.naming.NamingException ne) {
			logger.info("Error validaUsuario NE" + ne);
			retorno = 3;
		}
		return retorno;

	}

	/**
	 * Recupera los datos del usario pasado
	 * 
	 * @param strUsuario
	 * @return Tabla con los datos del usuario
	 * @throws NamingException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Hashtable recuperaUsuario(String strUsuario) {
		final DirContext dirCtx = establecerConexion();
		String[] listaAtributos = { "givenName", "sn" };
		Hashtable usrData = new Hashtable();
		if (dirCtx != null) {
			try {
				// Buscamos el usuario en el directorio corporativo
				String filter = "(&(objectclass=user)(sAMAccountName=" + strUsuario + "))";
				SearchControls ctls = new SearchControls();
				ctls.setDerefLinkFlag(true);
				ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				ctls.setReturningObjFlag(false);
				NamingEnumeration answer = dirCtx.search(ramaLDAP, filter, ctls);
				if (answer.hasMore()) {
					SearchResult sr = (SearchResult) answer.next();
					Attributes atts = dirCtx.getAttributes(sr.getName() + "," + ramaLDAP, listaAtributos);
					usrData = new Properties();
					for (NamingEnumeration ae = atts.getAll(); ae.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						for (NamingEnumeration val = attr.getAll(); val.hasMore();) {
							String valor = (String) val.next();
							usrData.put(attr.getID(), valor);
						}
					}
				}
			} catch (NamingException m) {
				logger.info("Error al recuperar el usuario" + m);
			} finally {
				if (dirCtx != null) {
					try {
						dirCtx.close();
					} catch (javax.naming.NamingException ne) {
						logger.info("Error establecerConexion ldap" + ne);
					}
				}
			}
		}
		return usrData;
	}

}
