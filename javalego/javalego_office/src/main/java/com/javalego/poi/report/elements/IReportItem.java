package com.javalego.poi.report.elements;

/**
 * Métodos que deben de implementar los diferentes elementos del informe y definición de constantes utilizadas.
 * @author ROBERTO RANZ
 *
 */
public interface IReportItem {

	/**
	 * Generar el contenido en el archivo del informe.
	 * @throws Exception
	 */
	public void generate() throws Exception;
	
}
