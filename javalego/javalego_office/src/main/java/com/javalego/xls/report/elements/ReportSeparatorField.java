package com.javalego.xls.report.elements;

import com.javalego.xls.report.BasicReport;

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
