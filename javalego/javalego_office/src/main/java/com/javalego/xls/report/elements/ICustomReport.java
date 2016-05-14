package com.javalego.xls.report.elements;

import com.javalego.xls.report.ReportVisualStyle;

/**
 * Interface para personalizar el estilo de una celda de un campo de un objeto o registro.
 * @author ROBERTO RANZ
 */
public interface ICustomReport {

	/**
	 * Cambiar el estilo de la celda
	 * @param styleCell
	 * @param record
	 * @param fieldName
	 * @param value
	 */
	void changeStyleCell(ReportVisualStyle styleCell, Object record, String fieldName, Object value);

}
