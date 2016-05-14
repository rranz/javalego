/*
 * Created on 01-mar-2005
 */
package com.javalego.util.url;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que permite obtener un stream mediante una dirección web. 
 * @author ROBERTO RANZ
 */
public class UrlInputStream extends BasicUrlConnection {

  /**
   * Conexión mediante usuario y contraseña.
   * @param user
   * @param password
   */
  public UrlInputStream(String user, String password) {
    super(user, password);
  }

  /**
   * Conexión mediante proxy. 
   * @param proxyHost
   * @param proxyUser
   * @param proxyPassword
   */
  public UrlInputStream(String proxyHost, String proxyUser, String proxyPassword) {
    super(proxyHost, proxyUser, proxyPassword);
  }
  
  /**
   * Conexión mediante usuario, contraseña y proxy. 
   * @param user
   * @param password
   * @param proxyHost
   * @param proxyUser
   * @param proxyPassword
   */
  public UrlInputStream(String user, String password, String proxyHost, String proxyUser, String proxyPassword) {
    super(user, password, proxyHost, proxyUser, proxyPassword);
  }
  
  public UrlInputStream(){
  }

  /*
   * Ejecutar proceso
   */
	@Override
  public void execute() throws Exception {
    super.execute();
    try {
		if (connection == null || connection.getInputStream() == null) {
			System.out.println("UrlInputStream.0");
			return;
		}
	}
	catch (IOException e) {
		throw new Exception(e);
	}
  }
  
  /**
   * Obtiene el stream de la información recuperada de la dirección web.
   * @return
   * @throws Exception
   */
  public InputStream getInputStream() throws Exception {
  	if (connection != null)
  		return connection.getInputStream();
  	else
  		return null;
  }
  
}
