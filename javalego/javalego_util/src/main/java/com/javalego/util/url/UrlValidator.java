package com.javalego.util.url;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import com.javalego.util.StringUtils;

/**
 * Clase que nos permite realizar una validación de conexión a una Url. Si la conexión falla,
 * el sistema intentará la utilización del proxy si existe la conexión pero no tenemos permisos de
 * acceso. Existe un proxy de salida para realizar la conexión a dicha url.
 * @author ROBERTO RANZ
 */
public class UrlValidator {

	/**
	 * Dirección web a validar.
	 */
	private String url;
	
	/**
	 * Port proxy
	 */
	private String proxyPort;
	
	/**
	 * Host proxy
	 */
	private String proxyHost;
	
	/**
	 * Código de respuesta.
	 */
	private int responseCode;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UrlValidator(String url) {
		this.url = url;
	}
	
	public static boolean validateUrl(String url) throws Exception {
		UrlValidator ur = new UrlValidator(url);
		return ur.validate();
	}
	
	/**
	 * Validar la conexión a la url definida
	 * @return
	 */
	public boolean validate() throws Exception {

		hasProxy();
		
		System.setProperty("java.net.useSystemProxies","false");
		
		//clearyProxyProtocols();
		
		int codeconnect = getResponseCodeConnect(url);
		
		// Intentar la conexión con el proxy si procede
		if (codeconnect < 0) {
			return false;
		}
		// Validar el código de conexión
		else {
			URL ur = new URL(url);			
	    HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
	    responseCode = conn.getResponseCode();
	  	return !isError();
		}
	}

	/**
	 * Comprobar si existe un proxy de salida a Internet
	 * @return
	 * @throws Exception
	 */
	public boolean hasProxy() throws Exception {
		
		if (proxyHost != null) return true;
		
		System.setProperty("java.net.useSystemProxies","true");

		List<Proxy> list = ProxySelector.getDefault().select(new URI(url));
		
		// Definir el proxyHost y port de salida del proxy
		if (list != null && list.size() > 0 && list.get(0) != null) {
			Proxy proxy = list.get(0);
			InetSocketAddress address = (InetSocketAddress)proxy.address();
			proxyHost = address.getHostName();
			proxyPort = new Integer(address.getPort()).toString();
		}
		return proxyHost != null;
	}
	
	/**
	 * Validar si existe una conexión directa que no necesita proxy.
	 * @param url
	 * @return
	 */
	public int getResponseCodeConnect(String url) {
		
		try {
			URL ur = new URL(url);			
	
	    HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
	    conn.setConnectTimeout(3000);
	    conn.getInputStream();
	    return conn.getResponseCode();
		}
		catch(Exception e) {
			return -1;
		}
    
	}
	
	/**
	 * Comprueba si la conexión ha devuelto un error.
	 * @return
	 */
	public boolean isError() {
		return responseCode > 399 && responseCode < 500;
	}
	
	/**
	 * Comprueba si la conexión ha devuelto un error por falta de autentificación con el proxy.
	 * @return
	 */
	public boolean isAuthenticationProxyError() {
		return proxyHost != null && responseCode > 399 && responseCode < 500;
	}
	
	/**
	 * Interpretación del mensaje de error.
	 * @param code
	 * @return
	 */
	public String getMessageError(int code) {
		
		switch (code) {
		case 400:
			return "La petición contiene errores de sintaxis.";
		case 407:
			return "El necesario especificar un nombre de usuario y contraseña de conexión al proxy para salir a Internet.";
		case 401:
			return "Acceso no autorizado. Seguramente se requiere un usuario y contraseña para acceder a esta dirección web.";
		case 402:
			return "Acceso no autorizado. Seguramente requiere un pago digital.";
		case 403:
			return "Acceso legal pero el servidor está rechazando la petición.";
		case 404:
			return "Dirección inexistente.";
		case 408:
			return "Tiempo de conexión superado. Acceso cancelado.";
		default:
			return null;
		}
	}

	/**
	 * Aplicar propiedades para la configuración del proxy sobre un protocolo.
	 * @param protocol
	 * @param proxyHost
	 * @param proxyPort
	 * @param proxyUser
	 * @param proxyPassword
	 */
	public static void applyProxyProtocol(String protocol, String proxyHost, String proxyPort, String proxyUser, String proxyPassword) {
		
		// Puerto por defecto
		if (StringUtils.isEmpty(proxyPort)) proxyPort = "8080"; 

		Properties properties = System.getProperties();
		if (!StringUtils.isEmpty(proxyHost))
		  properties.put(protocol + ".proxyHost", proxyHost); 
		if (!StringUtils.isEmpty(proxyPort))
			properties.put(protocol + ".proxyPort", proxyPort); 
		if (!StringUtils.isEmpty(proxyUser))
			properties.put(protocol + ".proxyUser", proxyUser); 
		if (!StringUtils.isEmpty(proxyPassword))
			properties.put(protocol + ".proxyPassword", proxyPassword); 

		System.out.println("Apply proxy GANA: proxyHost " + proxyHost + " proxyPort " + proxyPort + " proxyUser " + proxyUser); // + " proxyPassword " + proxyPassword); 
	}

	/**
	 * Aplicar configuración de acceso al proxy por todos los protocolos.
	 * @param userName
	 * @param password
	 */
	public void applyProxyProtocols(String userName, String password) {
		
		if (proxyPort == null)
			proxyPort = "8080";
		
		applyProxyProtocol("http", proxyHost, proxyPort, userName, password);
		applyProxyProtocol("https", proxyHost, proxyPort, userName, password);
		applyProxyProtocol("ftp", proxyHost, proxyPort, userName, password);
	}

	/**
	 * Aplicar configuración de acceso al proxy por todos los protocolos.
	 * @param userName
	 * @param password
	 */
//	public void clearyProxyProtocols() {
//		applyProxyProtocol("http", "", "", "", "");
//		applyProxyProtocol("https", "", "", "", "");
//		applyProxyProtocol("ftp", "", "", "", "");
//	}

}
