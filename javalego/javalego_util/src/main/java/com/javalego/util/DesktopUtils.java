package com.javalego.util;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.swing.JFileChooser;

import com.javalego.config.EnvironmentVariables;
import com.javalego.exception.JavaLegoException;

/**
 * Clase dedicada a implementar utilidades relacionadas con el Desktop (java
 * 1.6+)
 * 
 * @author ROBERTO RANZ
 */
public class DesktopUtils {

	// Ventana activa. Variable estática.
	private static Window activeWindow = null;

	// SISTEMA OPERATIVO DONDE ESTÁ CORRIENDO LA APLICACIÓN.
	/**
	 * Comprueba si el sistema operativo es Linux
	 * 
	 * @return
	 */
	static public boolean isLinux() {
		return System.getProperty("os.name").toLowerCase().indexOf("linux") > -1;
	}

	/**
	 * Comprueba si el sistema operativo es Unix
	 * 
	 * @return
	 */
	static public boolean isUnix() {
		return System.getProperty("os.name").toLowerCase().indexOf("unix") > -1;
	}

	/**
	 * Comprueba si el sistema operativo es Mac
	 * 
	 * @return
	 */
	static public boolean isMac() {
		return System.getProperty("os.name").toLowerCase().indexOf("mac") > -1;
	}

	/**
	 * Comprueba si el sistema operativo es Windows
	 * 
	 * @return
	 */
	static public boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().indexOf("windows") > -1;
	}

	/**
	 * Comprueba si un archivo está asociado al Office. (.xls, .doc, .ppt, .rtf).
	 * 
	 * @param fileName
	 * @return
	 */
	static public boolean isOfficeFile(String fileName) {

		if (fileName != null && fileName.lastIndexOf(".") > -1) {

			String ext = fileName.substring(fileName.lastIndexOf((".")));

			if (ext.toLowerCase().equals(".xls") || ext.toLowerCase().equals(".doc") || ext.toLowerCase().equals(".docx") || ext.toLowerCase().equals(".ppt") || ext.toLowerCase().equals(".pptx") || ext.toLowerCase().equals(".rtf")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Ejecutar office.
	 * 
	 * @param fileName
	 */
	static public void runOffice(String fileName) throws Exception {
		SystemUtils.CmdExec("ooffice -o " + fileName);
	}

	/**
	 * Abrir un archivo con la aplicación asociada.
	 * 
	 * @param fileName
	 */
	static public void openFile(File file) throws Exception {
		if (file != null)
			openFile(file.getAbsolutePath());
	}

	/**
	 * Ver un archivo desde un recurso
	 * 
	 * @param resourceName
	 * @throws Exception
	 */
	static public void openFileFromResource(String resourceName) throws Exception {

		String ext = FileUtils.getFileExtension(resourceName);
		String fileName = FileUtils.getFreeSecuencialFile(EnvironmentVariables.getUserTmp() + "/tmp", ext);
		SystemUtils.saveResourceToFile(resourceName, fileName);
		openFile(fileName);
	}

	/**
	 * Ver un archivo desde un recurso
	 * @param resourceName
	 * @param fileTmp utilizar un fichero temporal grabado en el directorio temporal del usuario en su sistema operativo o mostrar el fichero original
	 * del recurso.
	 * @throws Exception
	 */
	static public void openFileFromResource(String resourceName, boolean fileTmp) throws Exception {
		if (fileTmp)
			openFileFromResource(resourceName);
		else {
			String fileName = SystemUtils.getPathResource(resourceName);
			if (fileName.indexOf("%20") > -1)
				fileName = fileName.replaceAll("%20", " ");
			openFile(fileName);
		}
	}

	/**
	 * Abrir un archivo con la aplicación asociada.
	 * 
	 * @param fileName
	 */
	static public void openFile(String fileName) throws Exception {

		// Dejar sólo un /
		if (fileName != null && fileName.indexOf("//") > -1)
			fileName = fileName.replaceAll("//", "/");

		if (fileName == null || fileName.equals("") || !(new File(fileName).exists()))
			throw new Exception(fileName + "NOT FOUND");

		fileName = fileName.replaceAll("\\|", "/");

		File file = new File(fileName);

		if (file.exists()) {

			// Sistemas operativos Linux o Unix
			if (isOfficeFile(fileName) && (isLinux() || isUnix())) {
				try {
					runOffice(fileName);
				}
				catch(Exception e) {
					throw e;
				}
			}
			// Windows
			else {
				try {
					Desktop.getDesktop().open(file);
				}
				catch(Exception e) {
					throw e;
				}
			}
		} else
			throw new JavaLegoException("El archivo '" + file.getAbsolutePath() + "' no existe.", JavaLegoException.WARNING);
	}

	/**
	 * Editar un archivo con la aplicación asociada.
	 * 
	 * @param fileName
	 */
	static public void editFile(String fileName) throws Exception {

		if (fileName == null || fileName.equals("") || !(new File(fileName).exists()))
			throw new Exception(fileName + " NOT FOUND");

		File file = new File(fileName);

		if (file.exists()) {

			if (isOfficeFile(fileName) && (isLinux() || isUnix()))
				runOffice(fileName);
			else
				Desktop.getDesktop().edit(file);
		} else
			throw new JavaLegoException("El archivo '" + file.getAbsolutePath() + "' no existe.", JavaLegoException.WARNING);
	}

	/**
	 * Imprimir un archivo con la aplicación asociada. Esta aplicación debe
	 * implementar la posibilidad de impresión. Ej. Excel o Word.
	 * 
	 * @param fileName
	 */
	static public void printFile(String fileName) throws Exception {

		if (fileName == null || fileName.equals("") || !(new File(fileName).exists()))
			throw new Exception(fileName + " NOT FOUND");

		try {
			Desktop.getDesktop().print(new File(fileName));
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Abrir el navegador con una url
	 * 
	 * @param url
	 */
	static public void browser(String url) throws Exception {
		URI uri = new URI(url);
		Desktop.getDesktop().browse(uri);
	}

	/**
	 * Abrir la aplicación de correo electrónico y enviar un texto mailto: Ej:
	 * mailto:duke@sun.com?SUBJECT=Happy New Year!&BODY=Happy New Year, Duke!
	 * 
	 * @param text
	 */
	static public void sendEmail(String text) throws Exception {

		String mailTo = text;
		URI uriMailTo = null;
		if (mailTo.length() > 0) {
			uriMailTo = new URI("mailto", mailTo, null);
			Desktop.getDesktop().mail(uriMailTo);
		} else {
			Desktop.getDesktop().mail();
		}
	}

	/**
	 * Obtener un path mediante JFileChooser
	 * 
	 * @param defaultPath
	 * @return
	 */
	static public File getPath(File defaultPath, Component owner) {

		JFileChooser chooser = new JFileChooser();

		chooser.setDialogTitle("Seleccione un directorio");

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		chooser.setAcceptAllFileFilterUsed(false);

		if (defaultPath != null)
			chooser.setCurrentDirectory(defaultPath);

		int returnVal = chooser.showOpenDialog(owner);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (chooser.getSelectedFile().isDirectory())
				return chooser.getSelectedFile().getAbsoluteFile();
		}
		return null;
	}

	/**
	 * Obtener un path mediante JFileChooser
	 * 
	 * @param defaultPath
	 * @return
	 */
	static public File getFile(File defaultPath, Component owner) {
		return getFile(defaultPath, owner, null);
	}

	/**
	 * Obtener un path mediante JFileChooser
	 * 
	 * @param defaultPath
	 * @return
	 */
	static public File getFile(File defaultPath, Component owner, String[] filters) {

		JFileChooser chooser = new JFileChooser();

		if (filters != null) {
			FileFilters filter = new FileFilters(filters);
			chooser.setFileFilter(filter);
		}

		chooser.setDialogTitle("Seleccione un archivo");

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		chooser.setAcceptAllFileFilterUsed(false);

		if (defaultPath != null)
			chooser.setCurrentDirectory(defaultPath);

		int returnVal = chooser.showOpenDialog(owner);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (chooser.getSelectedFile().isFile())
				return chooser.getSelectedFile().getAbsoluteFile();
		}
		return null;
	}

	/**
	 * Grabar un fichero mediante JFileChooser
	 * 
	 * @param defaultPath
	 * @return
	 */
	static public File saveFile(File defaultPath, Component owner) {

		JFileChooser chooser = new JFileChooser();

		chooser.setDialogTitle("Guardar un archivo");

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		chooser.setAcceptAllFileFilterUsed(false);

		if (defaultPath != null)
			chooser.setCurrentDirectory(defaultPath);

		int returnVal = chooser.showSaveDialog(owner);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (chooser.getSelectedFile() != null)
				return new File(chooser.getSelectedFile().getAbsolutePath());
		}
		return null;
	}

	static public void main(String args[]) {
		
		//File f = setFile(null, null);
		//f.exists();
		// browser("www.eclipse.org");
		 try {
			sendEmail("mailto:duke@sun.com?SUBJECT=Happy New Year!&BODY=Happy New Year, Duke!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la ventana actualmente activa.
	 * 
	 * @return
	 */
	public static Window getActiveWindow() {
		getSelectedWindow(Frame.getWindows());
		return activeWindow;
	}

	/**
	 * Obtener la ventana activa mediante recursividad.
	 * 
	 * @param ownedWindows
	 * @return
	 */
	private static void getSelectedWindow(Window[] ownedWindows) {
		
		for (Window window : ownedWindows) {
			if (window.isActive()) {
				activeWindow = window;
				return;
			} else {
				Window[] owned = window.getOwnedWindows();
				if (ownedWindows != null && ownedWindows.length > 0) {
					getSelectedWindow(owned);
				}
			}
		}
	}

}
