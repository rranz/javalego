package com.javalego.excel.jxl;

/**
 * Funciones de Excel
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ExcelUtil {

	private static final char[] A2Z = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * Obtener el código (letras) de referencia de una columna (Ej.: A, AE, ...)
	 * 
	 * @param column
	 *            número de columna
	 * @return Código de referencia de la columna
	 */
	public static String getReferenceColumn(int column) {

		StringBuffer retval = new StringBuffer();
		int tempcellnum = column;
		do {
			retval.insert(0, A2Z[tempcellnum % A2Z.length]);
			tempcellnum = (tempcellnum / A2Z.length) - 1;
		} while (tempcellnum >= 0);

		return retval.toString();
	}

	/**
	 * Obtener la posición de la columna pasada en formato A1, E4.
	 * 
	 * @param column
	 * @return
	 */
	public static int getIndexColumn(String column) {

		for (int i = 0; i < 1000; i++) {
			if (column.equals(getReferenceColumn(i))) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String args[]) {
		System.out.println(getIndexColumn("E"));
	}
}
