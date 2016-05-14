/*
 * Created on 01-mar-2005
 */
package com.javalego.util.url;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Clase básica que permite definir los parámetros de conexión a una dirección
 * url.
 * 
 * @author ROBERTO RANZ
 */
public abstract class BasicUrlConnection {

	protected UrlAuthenticator authenticator = new UrlAuthenticator(null, null, null, null);
	protected String url = null;
	protected URLConnection connection = null;
	protected String port = "8080";

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return Returns the port.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            The port to set.
	 */
	public void setPort(String port) {
		this.url = port;
	}

	/**
	 * Conexión mediante usuario y contraseña.
	 * 
	 * @param user
	 * @param password
	 */
	public BasicUrlConnection(String user, String password) {
		authenticator.user = user;
		authenticator.password = password;
	}

	/**
	 * Conexión mediante proxy.
	 * 
	 * @param proxyHost
	 * @param proxyUser
	 * @param proxyPassword
	 */
	public BasicUrlConnection(String proxyHost, String proxyUser, String proxyPassword) {
		authenticator.proxyHost = proxyHost;
		authenticator.proxyUser = proxyUser;
		authenticator.proxyPassword = proxyPassword;
	}

	/**
	 * Conexión mediante usuario, contraseña y proxy.
	 * 
	 * @param user
	 * @param password
	 * @param proxyHost
	 * @param proxyUser
	 * @param proxyPassword
	 */
	public BasicUrlConnection(String user, String password, String proxyHost, String proxyUser, String proxyPassword) {
		authenticator.user = user;
		authenticator.password = password;
		authenticator.proxyHost = proxyHost;
		authenticator.proxyUser = proxyUser;
		authenticator.proxyPassword = proxyPassword;
	}

	/**
	 * Conexión mediante un archivo xml de configuración.
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public BasicUrlConnection(String fileName) throws Exception {
		loadPropertiesFromFile(fileName);
	}

	public BasicUrlConnection() {

	}

	/**
	 * Establece las propiedades de la conexión Ftp mediante variables
	 * contenidas en un archivo.
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void loadPropertiesFromFile(String fileName) throws Exception {
		if (fileName != null && !fileName.equals("")) {

			FileReader file = new FileReader(fileName);
			BufferedReader br = new BufferedReader(file);
			String line = null;

			// Recorrer líneas y asignar valores a propiedades.
			while ((line = br.readLine()) != null) {
				if (line.indexOf("ftp_proxy_server=") == 0)
					authenticator.proxyHost = line.substring(line.indexOf("=") + 1).replaceAll("\"", "");
				else if (line.indexOf("ftp_proxy_port=") == 0)
					port = line.substring(line.indexOf("=") + 1).replaceAll("\"", "");
				else if (line.indexOf("proxy_user=") == 0)
					authenticator.proxyUser = line.substring(line.indexOf("=") + 1).replaceAll("\"", "");
				else if (line.indexOf("proxy_pass=") == 0)
					authenticator.proxyPassword = line.substring(line.indexOf("=") + 1).replaceAll("\"", "");
				else if (line.indexOf("USER_SERVER=") == 0)
					authenticator.user = line.substring(line.indexOf("=") + 1).replaceAll("\"", "");
				else if (line.indexOf("PASS_SERVER=") == 0)
					authenticator.password = line.substring(line.indexOf("=") + 1).replaceAll("\"", "");
				else if (line.indexOf("URL=") == 0)
					url = line.substring(line.indexOf("=") + 1).replaceAll("\"", "");
			}
			br.close();
		}
	}

	/**
	 * Ejecutar proceso.
	 */
	public void execute() throws Exception {

		// Establecer variables de entorno necesarias para establecer conexión
		// ftp.
		Properties properties = System.getProperties();

		if (authenticator.proxyHost != null) {
			properties.put("http.proxyHost", authenticator.proxyHost);
			properties.put("https.proxyHost", authenticator.proxyHost);
			properties.put("ftp.proxyHost", authenticator.proxyHost);
			properties.put("ftps.proxyHost", authenticator.proxyHost);
		}

		properties.put("http.proxyPort", port);
		properties.put("https.proxyPort", port);
		properties.put("ftp.proxyPort", port);
		properties.put("ftps.proxyPort", port);

		System.getProperties().put("ftpProxyPrefix", "http://");
		System.getProperties().put("ftpProxySet", "true");
		if (authenticator.proxyHost != null)
			System.getProperties().put("ftpProxyHost", authenticator.proxyHost);
		System.getProperties().put("ftpProxyPort", port);

		try {
			URL urlProcess = new URL(url);

			authenticator.setHost(urlProcess.getHost(), urlProcess.getPort());
			authenticator.setProxy(authenticator.proxyHost, Integer.parseInt(port));
			UrlAuthenticator.setDefault(authenticator);

			connection = urlProcess.openConnection();
		}
		catch (IOException e) {
			throw new Exception("ERROR CONEXION " + url + ".");
		}
		// Por defecto la cache está a true y se considera necesario por
		// problemas de actualización poner a false esta propiedad.
		connection.setUseCaches(false);
	}
}
