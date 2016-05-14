/*
 * Created on 14-abr-2005
 */
package com.javalego.config;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.javalego.util.SystemUtils;

/**
 * Variables de entorno recogidas mediante archivo o mediante AcShellQuery() de AssetControl.
 * Descripción: se recogerán todas las variables de entorno necesarias para nuestros proyectos, descartando el resto.
 * De todas formas, se podrá consultar cualquier variable mediante el método FindByName();
 * @author ROBERTO RANZ
 */
public class EnvironmentVariables {

	/**
	 * Separador de directorios en Unix
	 */
	static public String UNIX_DIRECTORY_SEPARATOR = "/";
	
	static HashMap<String, String> items = null;

	/**
	 * Fuente por defecto para todos los componentes en Swing.
	 */
	private static String defaultFontName = "Dialog";
	
	/**
	 * Comprueba si el tema de la aplicación es oscuro (ver LookAndFeel del proyecto gana donde se estabece esta variable para
	 * que se pueda acceder en este proyecto a esta definición de la solución Gana).
	 */
	private static boolean darkTheme;
	
	/**
	 * Recargar variables
	 */
	static public void reload() {
		items = null;
		load();
	}
	
  /**
   * Cargar variables de entorno mediante la clase AcShellQuery de AssetControl 
   * (Nota: este comando está operativo actualmente, pero puede que su uso sea eliminado en un futuro).
   */
  static public void load() {
    
    if (items == null){
      
      items = new HashMap<String, String>();

      String value = null;
      String key = null;
			
      //-------------------------------------------------------------------------------
      // BUSCAR VARIABLES DE ENTORNO MEDIANTE LA CONEXIÓN ACTUAL DE ASSETCONTROL
      //-------------------------------------------------------------------------------
			// Script Unix que nos permite ejecutar cualquier comando, en este
			// caso utilizamos el comando set para obtener la relación de
			// variables de entorno definidas.
			
    	String osName = System.getProperty("os.name" );

    	String command = null;
    	// Windows 95 y Nt
      if(osName.indexOf("Windows") == 0)
        command = "set";
      else
      	command = "env"; // Unix
      
      Process child;
			try {
				child = SystemUtils.getProcess(command);
        BufferedInputStream in = (BufferedInputStream)child.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);

        while ((value = br.readLine()) != null) {
          if (value.indexOf("=") > -1){
            key   = value.substring(0, value.indexOf("="));
            value = value.substring(value.indexOf("=")+1);
            items.put(key, value);  
          }
				}
				br.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
      
    }
  }
	
	/**
	 * Directorio de usuario 
	 * @return String
	 */
	static public String getUserDir() {
		return transformPath(System.getProperty("user.dir"));
	}
	
	/**
	 * Directorio de usuario 
	 * @return String
	 */
	static public String getUserDir(boolean transform) {
		if (transform)
			return transformPath(System.getProperty("user.dir"));
		else
			return System.getProperty("user.dir");
	}
	
	/**
	 * Directorio temporal de grabación de archivos 
	 * @return String
	 */
	static public String getUserTmp() {
		return transformPath(System.getProperty("java.io.tmpdir"));
	}

	/**
	 * Directorio de usuario 
	 * @return String
	 */
	static public String getUserHome() {
		return transformPath(System.getProperty("user.home"));
	}
	
	/**
	 * Nombre de Usuario 
	 * @return String
	 */
	static public String getUserName() {
		return System.getProperty("user.name");
	}

	/**
	 * Path de los archivo de cache.
	 * @return
	 */
	static public String getCacheDir() {
		return System.getProperty("deployment.user.cachedir");
	}
	
  /**
   * Path de ubicación de los documentos de ayuda de las aplicaciones.
   * @return
   */
  static public String getPathWebHelpUserGuides() {
    return "/help/user_guides";
  }

  /**
   * Validar el path para incluir el carácter / al final del mismo. Esta función se incluye para Sistemas operativos como Linux que no incluyen este último carácter
   * y asi poder compatibilizar el código actual de Gana que usa estas variables de entorno.
   * @param path
   * @return
   */
  static private String transformPath(String path) {
  	
  	if (path != null && path.length() > 0) {
  	
  		String last = path.substring(path.length()-1);
  		
  		if (!last.equals("\\") && !last.equals("/"))
  			return path += "/";
  	}
  	return path;
  }
  
  /**
   * Buscar una variable por nombre.
   * @param name
   * @return String
   */
  static public String findByName(String name) {
    load();
    if (items.containsKey(name))
      return items.get(name).toString();
    else
      return null;
  }
  
	/**
	 * Traduce los tags ${VARIABLE_ENTORNO} para poder utilizar los diferentes entornos de ejecución en AssetControl.
	 * Ej.: ${AC_SYSTEM}/descriptors equivale a /usr/de/tepp/cfg/descriptors.
	 * @param value
	 * @return
	 */
	public static String translateValue(String value) {
		
		while(value.indexOf("${") > -1){
			
			int posInit = value.indexOf("${");
			int posEnd = value.indexOf("}", posInit);
			
			String item = value.substring(posInit+2,posEnd);
			value = value.substring(0, posInit) + EnvironmentVariables.findByName(item) + value.substring(posEnd+1);
		}
		return value;
	}

  /**
   * Lista de variables encontradas.
   * @return HashMap
   */
  static public HashMap<String, String> getValues() {
    load();
    return items; 
  }

  /**
   * Font por defecto.
   * @return
   */
	public static String getDefaultFontName() {
		return defaultFontName;
	}

	public static void setDefaultFontName(String defaultFontName) {
		EnvironmentVariables.defaultFontName = defaultFontName;
	}

	public static boolean isDarkTheme() {
		return darkTheme;
	}

	public static void setDarkTheme(boolean darkTheme) {
		EnvironmentVariables.darkTheme = darkTheme;
	}

	/**
	 * Obtener el color de font cuando el componente está inactivo.
	 */
	public static Color getInactiveForeground() {
		return isDarkTheme() ? Color.gray : Color.blue;
	}
	
}
