/*
 * Created on 01-mar-2005
 */
package com.javalego.util.url;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;

/**
 * Clase que permite obtener un archivo mediante una dirección web y grabarlo. 
 * @author ROBERTO RANZ
 */
public class UrlFile extends BasicUrlConnection {

  /**
   * Fichero de salida donde guardaremos la información recuperada en la conexión.
   */
  protected String fileName = null;
  
  /**
   * Conexión mediante usuario y contraseña.
   * @param user
   * @param password
   */
  public UrlFile(String user, String password) {
    super(user, password);
  }

  /**
   * Conexión mediante proxy. 
   * @param proxyHost
   * @param proxyUser
   * @param proxyPassword
   */
  public UrlFile(String proxyHost, String proxyUser, String proxyPassword) {
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
  public UrlFile(String user, String password, String proxyHost, String proxyUser, String proxyPassword) {
    super(user, password, proxyHost, proxyUser, proxyPassword);
  }
  
  /**
   * Conexión mediante un archivo xml de configuración.
   * @param fileName
   * @throws Exception
   */
  public UrlFile(String fileName) throws Exception{
    super(fileName);
  }
  
  public UrlFile(){
    
  }

  /**
   * @return Returns the fileName.
   */
  public String getFileName() {
    return fileName;
  }
  /**
   * @param fileName The fileName to set.
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  /*
   * Ejecutar proceso
   * @see com.gandi.ftp.BasicFtp#Execute()
   */
	@Override
  public void execute() throws Exception {
    super.execute();
    try {
		if (connection == null || connection.getInputStream() == null) {
			return;
		}
		//System.out.println("grabando archivo ...");
		DataInputStream dis = null;
		try{
		  //System.out.println("obteniendo archivo ...");
			dis = new DataInputStream(connection.getInputStream());
			
		  //System.out.println("grabando archivo ...");
		  if (fileName != null && !fileName.equals("")){ 
		    
		    DataOutputStream out = new DataOutputStream( new BufferedOutputStream( new FileOutputStream(fileName)));
		    try{
		      while(true){
		        out.writeByte(dis.readByte());
		      }
		    }
		    // controlar el final del fichero.
		    catch(EOFException ex){
		      
		    }
		    finally{
		      out.flush();
		      out.close();
		    }
		  }
		}
		finally{
		  if (dis != null)
		  	dis.close();
		}
	}
	catch (Exception e) {
		throw new Exception(e);
	}
  }
  
}
