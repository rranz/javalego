package com.javalego.poi.report.elements;

import com.javalego.poi.report.BasicReport;
import com.javalego.poi.report.elements.ReportField;

/**
 * Separador de campos que añade una celda
 * @author ROBERTO RANZ
 */
public class ReportSeparatorField extends ReportField {

	public ReportSeparatorField(BasicReport report, String fieldName) {
		super(report, fieldName);
		setMaxWidth(10);
	}

	@Override
	public void generate() throws Exception {
	}

}
