package com.javalego.util;

import com.javalego.config.EnvironmentVariables;
import com.javalego.util.url.UrlFile;

/**
 * Clase encargada de la gestión de documentos.
 * @author ROBERTO RANZ
 */
public class DocUtils {

	/**
	 * Captura un fichero del directorio web dedicado a la documentación y lo graba en el directorio temporal del usuario.
	 * @param fileName
	 * @return
	 */
	static public String getFileNameDocument(String fileName) throws Exception {

		UrlFile url = new UrlFile();
		url.setUrl(fileName);
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String outFileName = EnvironmentVariables.getUserTmp() + FileUtils.getFreeSecuencialFile("tmp", ext);
		url.setFileName(outFileName);
		url.execute();
		return outFileName;
	}

	/**
	 * Captura un fichero del directorio web dedicado a la documentación y lo graba con el nombre del fichero definido en el parámetro del método (debe incluir el path completo).
	 * @param fileName
	 * @return
	 */
	static public String getFileNameDocument(String inFileName, String outFileName) throws Exception {

		UrlFile url = new UrlFile();
		url.setUrl(inFileName);
		url.setFileName(outFileName);
		url.execute();
		return outFileName;
	}

	/**
	 * Mostrar un documento existente en el directorio hlp de ayuda de cada entorno.
	 * @param fileName
	 */
	static public void showDocument(String fileName) throws Exception {
		String outFileName = getFileNameDocument(fileName);
		DesktopUtils.openFile(outFileName);
	}

	/**
	 * Test
	 * @param args
	 */
	static public void main(String args[]) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
