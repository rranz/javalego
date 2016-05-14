package com.javalego.excel;

import java.io.File;
import java.io.InputStream;

/**
 * Acceso a ficheros de excel.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ExcelWorkbookReader extends ExcelReader {

	/**
	 * Carga un fichero de Excel.
	 * @param fileName
	 * @return 
	 */
	public abstract ExcelWorkbookReader loadFile(String fileName) throws Exception;

	/**
	 * Carga un fichero de Excel.
	 * @param fileName
	 * @return 
	 */
	public abstract ExcelWorkbookReader loadFile(InputStream stream) throws Exception;

	/**
	 * Carga un fichero de Excel.
	 * @param fileName
	 * @return 
	 */
	public abstract ExcelWorkbookReader loadFile(File file) throws Exception;

}
