package com.javalego.xls.report.elements.calcfields;

import com.javalego.xls.report.BasicReport;

/**
 * Campos calculados para informes de tipo Objects.
 * Ej.: $F{name_property}
 * @author ROBERTO RANZ
 */
public class ReportCalcFieldObjects extends BasicReportCalcField {

	public ReportCalcFieldObjects(BasicReport report, String fieldName, String title, String expression) {
		super(report, fieldName, title, expression);
	}

	@Override
	public Object getValue() throws Exception {
		ReportObjectsExpression e = new ReportObjectsExpression(this);
		e.setExpression(expression);
		return e.getValue(true);
	}
	
}
