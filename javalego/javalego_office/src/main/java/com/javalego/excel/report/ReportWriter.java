package com.javalego.excel.report;

import com.javalego.exception.LocalizedException;

/**
 * Generador de informes
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ReportWriter {

	/**
	 * Escribir título y subtítulo
	 * @throws LocalizedException
	 */
	void writeTitle() throws LocalizedException;
	
	/**
	 * Escribir las cabeceras de los campos
	 * @throws LocalizedException
	 */
	void writeHeaders() throws LocalizedException;
	
	/**
	 * Escribir los registros (lista de campos)
	 * @throws LocalizedException
	 */
	void writeRecords() throws LocalizedException;
	
	/**
	 * Escribir pié de página
	 * @throws LocalizedException
	 */
	void writeFooter() throws LocalizedException;
	
	/**
	 * Ejecutar informe
	 * @throws LocalizedException
	 */
	void run() throws LocalizedException;
}
