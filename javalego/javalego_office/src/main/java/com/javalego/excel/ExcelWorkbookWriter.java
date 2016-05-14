package com.javalego.excel;

import java.io.File;
import java.io.OutputStream;


/**
 * Crear ficheros de excel.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ExcelWorkbookWriter extends ExcelWriter {

	/**
	 * Define el fichero de salida.
	 * @param fileName
	 * @return 
	 */
	public abstract ExcelWorkbookWriter setFile(String fileName) throws Exception;

	/**
	 * Define el fichero de salida.
	 * @param fileName
	 * @return 
	 */
	public abstract ExcelWorkbookWriter setFile(File file) throws Exception;

	/**
	 * Define el fichero de salida.
	 * @param fileName
	 * @return 
	 */
	public abstract ExcelWorkbookWriter setFile(OutputStream stream) throws Exception;

}
