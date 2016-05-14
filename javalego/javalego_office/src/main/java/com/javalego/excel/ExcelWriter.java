package com.javalego.excel;

import com.javalego.exception.LocalizedException;


/**
 * Escribir ficheros de Excel
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ExcelWriter {

	/**
	 * Poner el valor de una celda.
	 * @param row
	 * @param column
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public abstract void setValue(int row, int column, Object value) throws LocalizedException;

	/**
	 * Activar sheet
	 * @param index
	 */
	public abstract void setActiveSheet(int index);

	/**
	 * Escribir archivo
	 * @param fileName
	 * @throws Exception 
	 */
	public abstract void write() throws LocalizedException;
	
	/**
	 * Bold de una celda
	 * @param row
	 * @param column
	 * @param value
	 */
	public abstract void setValueBold(int row, int column, Object value) throws LocalizedException;
	
	/**
	 * Bold de una celda
	 * @param row
	 * @param column
	 * @param value
	 */
	public abstract void setValueBold(int row, String column, Object value) throws LocalizedException;

	/**
	 * Autosize columnas de la hoja
	 */
	public abstract void sheetAutoFitColumns();

	/**
	 * Comprueba si existe un workbook instanciado para poder aplicar cambios.
	 * Ej.: si no hemos definido un fichero de escritura, el workbook será igual a null y 
	 * generará un error al intentar modificar un documento inexistente.
	 * @return
	 */
	public abstract boolean hasWoorkbook();

}
