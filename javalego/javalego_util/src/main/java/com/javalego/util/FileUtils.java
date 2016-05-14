package com.javalego.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.MissingResourceException;

import org.apache.log4j.Logger;

import com.javalego.config.EnvironmentVariables;
import com.javalego.exception.JavaLegoException;

/**
 * Clase con utilidades referentes al sistema de ficheros.
 */
public final class FileUtils {

	private static final Logger log = Logger.getLogger(FileUtils.class);

	/**
	 * Constructor privado para cumplir con el patrÃ³n de Singleton.
	 */
	private FileUtils() {
		// Constructor privado para cumplir con el patrÃ³n de Singleton.
	}

	/**
	 * Copia de un fichero a otro.
	 * 
	 * @param source
	 *            fichero origen.
	 * @param target
	 *            fichero destino.
	 * @throws FileNotFoundException
	 *             si no se encuentra el fichero origen.
	 * @throws IOException
	 *             si hay algÃºn problema al copiar el fichero.
	 */
	public static void copyFile(File source, File target) throws FileNotFoundException, IOException {
		final InputStream is = new FileInputStream(source);
		final OutputStream os = new FileOutputStream(target);
		final byte[] buffer = new byte[65536]; // 64 KBytes de buffer
		int c;
		while ((c = is.read(buffer)) != -1) {
			os.write(buffer, 0, c);
		}
		os.close();
		is.close();
	}

	/**
	 * Devuelve la ruta absoluta hasta el directorio a partir del cual se puede
	 * obtener la clase. Si la clase estÃ¡ en un .class se obtiene la ruta hasta
	 * el directorio donde empezarÃ¡ el paquete de la clase (es decir los
	 * directorios com/foo/barr/... no estarÃ­an incluidos en el resultado). Si
	 * la clase estÃ¡ en un .jar se obtiene la ruta donde estÃ¡ el jar.
	 * <p>
	 * Para encontrar la clase se busacara haciendo un
	 * <code>getResource()</code> a la propia clase que se quiere localizar.
	 * <p>
	 * El directorio que se devuelve termina con el caracter separador de
	 * directorios (segÃºn la plataforma).
	 * 
	 * @param clazz
	 *            clase que se pretende localizar.
	 * @return ruta absoluta hasta el directorio donde se puede localizar la
	 *         clase.
	 */
	public static String getPathToClassOrJar(Class<?> clazz) {
		//
		// No utilizamos el separador del sistema porque en Windows y, al menos,
		// bajo Tomcat,
		// el método class.getResource() espera como parámetro el separador de
		// unix.
		//
		final String PATH_SEPARATOR = "/";
		String cn = PATH_SEPARATOR + clazz.getName().replace('.', PATH_SEPARATOR.charAt(0)) + ".class";
		final URL url = clazz.getResource(cn);
		String path = url.getPath();
		final int indexOfClass = path.indexOf(cn);
		final int indexOfClassMinusOne = indexOfClass - 1;

		// La URL que define donde está una clase es del estilo:
		// * file:/...class
		// * jar:file:/...jar!/...class
		// Con URL.getPath() se obtiene una cadena que ya tiene quitado el
		// primer protocolo (file: o jar:).
		final int begin;
		final int end;
		if (path.charAt(indexOfClassMinusOne) == '!') {
			// La clase estÃ¡ en un jar, asÃ­ que quitamos el protocolo 'file:'
			// del principio,
			// y el nombre del jar del final
			begin = path.indexOf(':') + 1;
			end = path.lastIndexOf(PATH_SEPARATOR, indexOfClassMinusOne) + 1;
		}
		else {
			begin = 0;
			end = indexOfClass + 1;
		}
		path = path.substring(begin, end);
		return path;
	}

	/**
	 * Devuelve el path completo donde se encuentra el fichero <tt>fileName</tt>
	 * . Este fichero <tt>fileName</tt> se busca en el la lista de directorios
	 * definida por <tt>searchPath</tt>. Esta es un lista de directorios del
	 * estilo del PATH o del LD_LIBRARY_PATH, es decir una lista de directorios
	 * donde el separador es ';' en Windows o ':' en Unix.
	 * 
	 * @see File#pathSeparatorChar
	 * @param searchPath
	 *            lista de directorios del estilo del PATH o del
	 *            LD_LIBRARY_PATH, es decir una lista de directorios donde el
	 *            separador es ';' en Windows o ':' en Unix
	 * @param fileName
	 *            nombre del fichero que se quiere buscar en <tt>searchPath</tt>
	 *            .
	 * @return el path completo donde se encuentra el fichero <tt>fileName</tt>.
	 * @throws MissingResourceException
	 *             si no se encuentra <tt>fileName</tt> en ningÃƒÂºn direcotorio
	 *             definido por <tt>searchPath</tt>.
	 */
	public static String searchFileInPath(String searchPath, String fileName) {

		final String[] paths = searchPath.split(File.pathSeparator);
		for (int i = 0; i < paths.length; i++) {
			String filePath = paths[i];
			if (!filePath.endsWith(File.separator)) {
				filePath += File.separator;
			}
			filePath += fileName;
			if (log.isDebugEnabled())
				log.trace("Searching: " + filePath);

			final File propertiesFile = new File(filePath);
			if (propertiesFile.canRead()) {
				if (log.isDebugEnabled())
					log.trace("Found and is readable: " + filePath);
				return filePath;
			}
		}
		throw new MissingResourceException("Cannot find file '" + fileName + "' in path '" + searchPath + "', or cannot be read", FileUtils.class.getName(), fileName);
	}

	/**
	 * Devuelve un <tt>InputStream</tt> apuntando al <tt>resource</tt> que se ha
	 * localizado en el CLASSPATH.
	 * <p>
	 * Primero se busca en el CLASSPATH asociado al Thread, luego en el
	 * ClassLoader, y por Ãºltimo en el ClassLoader de esta clase.
	 * 
	 * @param resource
	 *            recurso que se quiere localizar en el CLASSPATH.
	 * @return un <tt>InputStream</tt> apuntando al <tt>resource</tt> que se ha
	 *         localizado en el CLASSPATH. <tt>null</tt> si no se ha encontrado.
	 */
	public static String searchInClasspath(String resource) {

		URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
		if (url == null) {
			url = ClassLoader.getSystemResource(resource);
			if (url == null) {
				url = FileUtils.class.getClassLoader().getResource(resource);
				if (url == null) {
					throw new MissingResourceException("Cannot find resource '" + resource + "' in none classpath", FileUtils.class.getName(), resource);
				}
			}
		}

		return url.getPath();
	}

	// public static void main(String[] args) {
	//
	// File[] files = searchDirectory("c:/temp", "*.log");
	// for (int i = 0; i < files.length; i++) {
	// System.out.println(files[i]);
	// }
	//
	// }

	/**
	 * Buscar ficheros en un directorio con un pattern (Ej.: *.log)
	 * 
	 * @param directory
	 * @param fileNameToSearch
	 * @param result
	 * @return
	 */
	public static File[] searchDirectory(String directory, String pattern) {

		File dir = new File(directory);
		FileFilter fileFilter = new org.apache.commons.io.filefilter.WildcardFileFilter(pattern);
		return dir.listFiles(fileFilter);
	}

	/**
	 * Obtiene el encoding de un fichero
	 * 
	 * @param file
	 * @return
	 */
	public String getEncoding(String file) {

		FileInputStream fis = null;
		InputStreamReader isr = null;
		String s;

		try {
			// new input stream reader is created
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);

			// the name of the character encoding returned
			s = isr.getEncoding();

			return s;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				// closes the stream and releases resources associated
				if (fis != null)
					fis.close();
				if (isr != null)
					isr.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Crear fichero en el directorio temporal del usuario
	 * 
	 * @param filename
	 * @param ext
	 * @param data
	 * @throws IOException
	 */
	public static File createTmpFile(String filename, String ext, byte[] data) throws IOException {

		File createTempFile = File.createTempFile(filename, ext);

		org.apache.commons.io.FileUtils.writeByteArrayToFile(createTempFile, data);

		return createTempFile;
		// FileUtils.deleteQuietly(createTempFile);
	}

	/**
	 * Copiar un fichero fuente en un fichero destino Si el fichero destino
	 * existe, se sobreescribe.
	 */
	static public void copyFileTo(String src, String dst) throws Exception {

		File fsrc = new File(src);
		File fdst = new File(dst);

		if (!fsrc.exists())
			throw new JavaLegoException("El fichero " + src + " no existe.", JavaLegoException.ERROR);

		internalCopyFileTo(fsrc, fdst, -1, -1);
	}

	/**
	 * Copiar un fichero fuente en un fichero destino Si el fichero destino
	 * existe, se sobreescribe.
	 */
	static public void copyFileTo(File src, File dst) throws Exception {
		internalCopyFileTo(src, dst, -1, -1);
	}

	/**
	 * Copiar un archivo
	 * 
	 * @param src
	 * @param dst
	 * @param progress
	 * @param content
	 * @param pos
	 * @throws Exception
	 */
	static private void internalCopyFileTo(File src, File dst, int content, int pos) throws IOException { // .,.

		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transferir bytes desde in a out.
		byte[] buf = new byte[1024];
		int len;
		int total = in.available();

		int i = 0;
		int initPos = 0;
		// saltar a la posición % especificada
		if (pos > -1) {
			initPos = total * pos / 100;
			in.skip(initPos);
		}

		while ((len = in.read(buf)) > 0) {
			++i;

			int posprogress = ((i * 1024) * 100) / (total - initPos);

			if (content > -1 && posprogress > content)
				break;

			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/**
	 * Grabar una cadena de caracteres en un archivo temporal
	 * 
	 * @param value
	 * @param extension
	 * @throws IOException
	 */
	static public String saveStringToTmpFile(String value, String extension) throws IOException {

		String filename = getFreeSecuencialFile(EnvironmentVariables.getUserTmp() + "tmp", extension);

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		out.write(value);
		out.close();
		return filename;
	}

	/**
	 * Grabar una cadena de caracteres en un archivo
	 * 
	 * @param value
	 * @param extension
	 * @throws IOException
	 */
	static public void saveStringToFile(String filename, String value) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		out.write(value);
		out.close();
	}

	/**
	 * Comprobar si un archivo es binario.
	 */
	static public boolean isFileBinary(String fileName) {
		FileReader file = null;
		try {
			file = new FileReader(fileName);
			BufferedReader b = new BufferedReader(file);
			char buf[] = new char[256];
			while ((b.read(buf, 0, buf.length)) > 0) {
				for (int i = 0; i < buf.length; i++) {
					if (buf[i] == '\0') {
						b.close();
						return true;
					}
				}
			}
			b.close();
		}
		catch (Exception ex) {

		}
		if (file != null)
			try {
				file.close();
			}
			catch (IOException e) {
			}
		return false;
	}

	/**
	 * Comprobar si un Stream de bytes es binario.
	 */
	static public boolean isStreamBinary(ByteArrayOutputStream stream) {
		byte[] data = stream.toByteArray();
		for (int i = 0; i < data.length; i++) {
			if (data[i] == '\0') {
				return true;
			}
		}
		return false;
	}

	/**
	 * Buscar un nombre de archivo que podamos crear o que no exista dentro de
	 * una secuencia de hasta 200 números.
	 * 
	 * @param fileName
	 * @param extension
	 * @return
	 */
	static public String getFreeSecuencialFile(String fileName, String extension) {
		return getFreeSecuencialFile(fileName, extension, true);
	}

	/**
	 * Buscar un nombre de archivo que podamos crear o que no exista dentro de
	 * una secuencia de hasta 200 números.
	 * 
	 * @param fileName
	 * @param extension
	 * @param delete
	 *            purgar fichero coincidentes para evitar crear archivos de
	 *            forma ilimitada.
	 * @return
	 */
	static public String getFreeSecuencialFile(String fileName, String extension, boolean delete) {

		// Validaciones
		if (extension == null)
			extension = "";
		else if (extension.indexOf(".") == -1)
			extension = "." + extension;

		for (int i = 1; i < 200; i++) {
			String name = fileName + i + extension;
			File file = new File(name);
			try {
				if (!file.exists() || (delete && file.delete())) {
					return name;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Buscar un nombre de archivo que podamos crear o que no exista dentro de
	 * una secuencia de hasta 2000 números. Además, el método intentará eliminar
	 * los archivos que difieran en algún día de la ejecución (purgar archivos
	 * temporales superiores a 1 día).
	 * 
	 * @return fileName
	 */
	static public String getFreeSecuencialFileDiffDay(String fileName, String extension) {
		Date date = new Date();
		for (int i = 1; i < 2000; i++) {
			String name = fileName + i + extension;
			File file = new File(name);
			try {
				if (!file.exists() || (DateUtils.differenceInDays(date, new Date(file.lastModified())) != 0 && file.delete())) {
					return name;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Grabar un array de bytes en un archivo enviado como parámetro.
	 * 
	 * @param byte[] data
	 * @param fileName
	 */
	static public void saveInputStreamToFile(byte[] data, String fileName) throws Exception {

		ByteArrayInputStream input = new ByteArrayInputStream(data);
		saveInputStreamToFile(input, fileName);
	}

	/**
	 * Grabar un texto a un fichero.
	 * 
	 * @param text
	 * @throws Exception
	 */
	static public void writeToFile(String text, String fileName) throws Exception {

		FileWriter file = new FileWriter(fileName);
		try {
			file.write(text);
		}
		finally {
			file.close();
		}
	}

	/**
	 * Grabar un inputStream en un archivo enviado como parámetro.
	 * 
	 * @param input
	 * @param fileName
	 */
	static public void saveInputStreamToFile(InputStream input, String fileName) throws Exception {

		DataInputStream dis = new DataInputStream(input);
		if (fileName != null && !fileName.equals("")) {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
			try {
				while (true) {
					out.writeByte(dis.readByte());
				}
			}
			// controlar el final del fichero.
			catch (EOFException ex) {
				if (dis != null)
					dis.close();
			}
			finally {
				out.flush();
				out.close();
				if (dis != null)
					dis.close();
			}
		}
	}

	/**
	 * Obtiene en un array de byte[] el contenido de cualquier archivo pasado
	 * como parámetro.
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	static public byte[] getStreamFile(String fileName) throws IOException, FileNotFoundException {

		byte[] datos = null;
		try {
			FileInputStream fichero = new FileInputStream(fileName);
			try {
				datos = new byte[fichero.available()];
				fichero.read(datos);
			}
			catch (IOException ex) {
			}
			finally {
				fichero.close();
			}
			return datos;
		}
		catch (IOException ex) {
			return datos;
		}
	}

	/**
	 * Comprobar si una URL es válida.
	 * 
	 * @param url
	 * @return
	 */
	static public boolean isValidURL(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.connect();
			return (conn.getContentLength() > 0);
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Obtener un array de bytes desde un fichero
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {

		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];
		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file " + file.getName());
		}
		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	/**
	 * Obtener un array de bytes desde un InputStream
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] getBytesFromStream(InputStream input) throws Exception {

		DataOutputStream out = null;
		DataInputStream dis = null;
		ByteArrayOutputStream fout = null;
		try {
			dis = new DataInputStream(input);
			fout = new ByteArrayOutputStream();
			out = new DataOutputStream(new BufferedOutputStream(fout));
			try {
				while (true) {
					out.writeByte(dis.readByte());
				}
			}
			// controlar el final del fichero.
			catch (EOFException ex) {
			}
			finally {
				out.flush();
				out.close();
			}
			return fout.toByteArray();
		}
		finally {
			if (dis != null)
				dis.close();
		}
	}

	/**
	 * Obtener la extensión de un archivo.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {

		if (fileName != null && fileName.lastIndexOf(".") > -1) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
		else
			return null;
	}

	/**
	 * Obtener un String con el contenido de un fichero de texto.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getStringFromFile(String fileName) throws Exception {
		return getStringFromFile(fileName, null);
	}

	/**
	 * Obtener un String con el contenido de un fichero de texto.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getStringFromFile(String fileName, String filter) throws Exception {

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String text = "";
		String str;
		while ((str = in.readLine()) != null) {
			if (filter != null && str.indexOf(filter) < 0)
				;
			else
				text += str + "\n";
		}
		in.close();
		return text;
	}

	/**
	 * Obtener el path de un fichero
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getPath(String fileName) {

		if (fileName == null)
			return null;
		else if (fileName.indexOf("/") > -1)
			return fileName.substring(0, fileName.lastIndexOf("/"));
		else
			return fileName.substring(0, fileName.lastIndexOf("\\"));
	}

	/**
	 * Crea los directorios necesarios para grabar el fichero en la ruta
	 * asociada la path del archivo.
	 * 
	 * @param fileName
	 */
	public static void mkdirs(String fileName) {

		new File(getPath(fileName)).mkdirs();

	}

	/**
	 * Eliminar archivo
	 * 
	 * @param fileName
	 */
	public static void deleteFile(String fileName) {

		new File(fileName).delete();

	}
}
