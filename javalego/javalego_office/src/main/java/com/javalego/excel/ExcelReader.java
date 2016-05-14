package com.javalego.excel;

/**
 * Lector de ficheros de Excel
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ExcelReader {

	/**
	 * Obtener el valor de una celda.
	 * @param row
	 * @param column
	 * @return
	 */
	public abstract Object getValue(int row, int column);

	/**
	 * Obtener el valor de una celda.
	 * @param row
	 * @param column
	 * @return
	 */
	public abstract Object getValue(int row, String column);

	/**
	 * Número de filas de la hoja actual
	 * @return
	 */
	public abstract int getRows();

	/**
	 * Número de columnas de la hoja actual
	 * @return
	 */
	public abstract int getColumns();

	/**
	 * Establecer la hoja activa
	 * @param index Número de hoja
	 */
	public abstract void setActiveSheet(int index);

	/**
	 * Obtener el índice de una columna buscando su título de cabecera en una fila del sheet. 
	 * @param row
	 * @param name
	 * @return
	 */
	public abstract int getIndexColumn(int row, String name);
	
	/**
	 * Comprueba si existe un workbook instanciado para poder aplicar cambios.
	 * Ej.: si no hemos definido un fichero de escritura, el workbook será igual a null y 
	 * generará un error al intentar modificar un documento inexistente.
	 * @return
	 */
	public abstract boolean hasWoorkbook();	
}
