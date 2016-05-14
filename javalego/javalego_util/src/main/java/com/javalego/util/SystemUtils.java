package com.javalego.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.UUID;

import com.javalego.exception.JavaLegoException;

/**
 * Funciones genéricas.
 * 
 * @author ROBERTO RANZ
 */
public final class SystemUtils {

	// static private final Logger logger = Logger.getLogger(Functions.class);

	private static SystemUtils f = new SystemUtils();

	/**
	 * Ejecutar un programa externo.
	 * 
	 * @param cmdline
	 * @throws Exception
	 */
	static public void CmdExec(String cmdline) throws Exception {
		/* Process proc = */Runtime.getRuntime().exec(cmdline);

		/*
		 * int exitVal = proc.waitFor(); // este código permitiría capturar la
		 * salida de la ejecución de un programa externo. BufferedReader input =
		 * new BufferedReader(new InputStreamReader(proc.getInputStream()));
		 * try{ String line = null; while ((line = input.readLine()) != null) {
		 * System.out.println(line); } } finally{ input.close(); } return
		 * exitVal;
		 */
	}

	/**
	 * Ejecutar un programa externo que devuelve su salida en una cadena de
	 * caracteres.
	 * 
	 * @param cmdline
	 * @throws Exception
	 */
	static public String CmdExecToStream(String cmdline) throws Exception {

		Process proc = Runtime.getRuntime().exec(cmdline);
		String list = "";
		/* int exitVal = *//* proc.waitFor(); */
		// este código permitiría capturar la salida de la ejecución de un
		// programa
		// externo.
		if (proc != null) {
			BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					list += line + "\n";
				}
			}
			finally {
				input.close();
			}
		}
		return list;
	}

	/**
	 * Relación de variables de entorno recogidas en System.getProperties().
	 */
	@SuppressWarnings("rawtypes")
	public String getSystemProperties() {

		String strValue = null;
		java.util.Properties p = System.getProperties();

		java.util.Enumeration en = p.propertyNames();

		while (en.hasMoreElements()) {
			String s = (String) en.nextElement();
			strValue = p.getProperty(s);
			// sb.append(s + "=<" + strValue + ">");
			// sb.append("\n");
		}
		return strValue;
		// System.out.println(sb.toString());
	}

	/**
	 * Ejecutar un comando y obtener su proceso asociado.
	 * 
	 * @param command
	 * @return
	 */
	static public Process getProcess(String command) throws Exception {

		Process process = null;

		Runtime rt = Runtime.getRuntime();

		String osName = System.getProperty("os.name");

		// Windows 95
		if (osName.equals("Windows 95")) {
			String[] cmd = new String[3];
			cmd[0] = "command.com";
			cmd[1] = "/C start";
			cmd[2] = command;
			process = rt.exec(cmd);
		}
		// Nt
		else if (osName.indexOf("Windows") == 0) {
			String[] cmd = new String[3];
			cmd[0] = "cmd.exe";
			cmd[1] = "/C";
			cmd[2] = command;
			process = rt.exec(cmd);
			// Unix
		}
		else {
			process = rt.exec(command);
		}

		return process;
	}

	/**
	 * Eliminar un carácter de una cadena.
	 * 
	 * @param oldChar
	 * @return String
	 */
	static public String removeAll(String value, char oldChar) {
		String convert = "";
		for (int i = 0; i < value.length(); i++)
			if (value.substring(i, i + 1).charAt(0) != oldChar)
				convert += value.substring(i, i + 1);
		return convert;
	}

	/**
	 * Obtenemos el path completo (Ej.: file://D:/com/gana/images/action.png de
	 * una imagen.
	 * 
	 * @param imageName
	 * @return ImageIcon
	 */
	static public String getFullPathImage(String imageName) {

		if (StringUtils.isEmpty(imageName))
			return null;

		return f.getClass().getResource(imageName).toString();
	}

	/**
	 * Ejecutar el browser por defecto y abrir una dirección web.
	 * 
	 * @param url
	 */
	static public void execBrowserUrl(String url) throws Exception {

		// CmdExec("rundll32 url.dll,FileProtocolHandler" + (url != null &&
		// !url.equals("") ? " " + url : "") );
		getProcess("start " + url);
	}

	/**
	 * Enviar correo utilizando la aplicación por defecto del sistema.
	 * 
	 * @param url
	 */
	public static void execEmail(String text) throws Exception {

		getProcess("start mailto:" + text);
	}

	/**
	 * Enviar correo con un anexo utilizando la aplicación por defecto del
	 * sistema.
	 * 
	 * @param url
	 */
	public static void execEmail(String text, String attach) throws Exception {

		getProcess("start mailto:" + text + "&attach=" + attach);
		// getProcess("start mailto:SomeN@Somewhere.com?subject=MySubject&Attach='d:\\result.csv'");
	}

	/**
	 * Comprueba si el recurso existe
	 * 
	 * @param resourceName
	 * @return
	 * @throws Exception
	 */
	static public boolean existResource(String resourceName) {

		try {
			getResource(resourceName);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Grabar un recurso en un fichero.
	 * 
	 * @param resourceName
	 */
	static public InputStream getResource(String resourceName) throws JavaLegoException {

		try {
			// logger.warn("Loading resource " + resourceName + " ...");
			InputStream s = f.getClass().getResourceAsStream(resourceName);

			if (s == null) {
				s = f.getClass().getClassLoader().getResourceAsStream(resourceName);
			}

			if (s == null)
				throw new JavaLegoException("Recurso '" + resourceName + "' inexistente.", JavaLegoException.ERROR);
			else
				return s;
		}
		// Gestiona stack overflow error
		catch (StackOverflowError eo) {
			// logger.warn("Error SEVERE access resource " + resourceName +
			// " ...");
			throw new JavaLegoException("Recurso '" + resourceName + "' inexistente.", JavaLegoException.ERROR, eo.getMessage());
		}
		catch (Exception e) {
			throw new JavaLegoException("Recurso '" + resourceName + "' inexistente.", JavaLegoException.ERROR, e);
		}
	}

	/**
	 * Grabar un recurso en un fichero.
	 * 
	 * @param resourceName
	 */
	static public URL getUrlResource(String resourceName) throws Exception {

		return f.getClass().getResource(resourceName);
	}

	/**
	 * Obtener el path del fichero en el equipo local desde un archivo de
	 * recursos existente en un jar.
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getPathResource(String resourceName) throws Exception {

		URL path = getUrlResource(resourceName);
		if (path == null)
			throw new JavaLegoException("Recurso '" + resourceName + "' inexistente.", JavaLegoException.WARNING);
		else
			return getUrlResource(resourceName).getFile().substring(1);
	}

	/**
	 * Grabar un recurso en un fichero.
	 * 
	 * @param resourceName
	 */
	static public void saveResourceToFile(String resourceName, String fileName) throws Exception {

		InputStream s = f.getClass().getResourceAsStream(resourceName);
		if (s == null)
			throw new JavaLegoException(resourceName + " NULL ");

		DataInputStream dis = new DataInputStream(s);
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			while (true) {
				out.writeByte(dis.readByte());
			}
		}
		// Esta excepción no se controla ya que siempre se produce al llegar al
		// final del archivo.
		catch (Exception e) {
		}
		finally {
			if (s != null)
				s.close();
			if (dis != null)
				dis.close();
			if (out != null)
				out.close();
		}
	}

	/**
	 * Ejecutar el recolector de basura de Java.
	 */
	static public void runGarbageCollector() {
		System.gc();
	}

	/**
	 * Nombre del localhost.
	 * 
	 * @return
	 */
	static public String getLocalHost() throws Exception {

		return InetAddress.getLocalHost().getHostName();
	}

	/**
	 * Obtener la ip de nuestro equipo.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getIPLocalHost() throws Exception {

		InetAddress direccion = InetAddress.getLocalHost();
		return direccion.getHostAddress();
	}

	/**
	 * Obtener un array de bytes desde un InputStream
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] getBytesFromResource(String resourceName) throws Exception {

		return FileUtils.getBytesFromStream(f.getClass().getResourceAsStream(resourceName));
	}

	/**
	 * Obtener un id de documento de tipo string válido.
	 * 
	 * @return
	 */
	public static String getIdDocument() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * Número de la versión de Java
	 * 
	 * @return
	 */
	public static double getJavaVersion() {

		String version = System.getProperty("java.version");
		int pos = version.indexOf('.');
		pos = version.indexOf('.', pos + 1);
		return Double.parseDouble(version.substring(0, pos));
	}
}
