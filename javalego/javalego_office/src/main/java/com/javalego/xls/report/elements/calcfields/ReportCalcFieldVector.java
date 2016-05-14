package com.javalego.xls.report.elements.calcfields;

import com.javalego.xls.report.BasicReport;

/**
 * Campos calculados para informes de tipo Vector.
 * Ej.: $F{fieldName}
 * @author ROBERTO RANZ
 */
public class ReportCalcFieldVector extends BasicReportCalcField {

	public ReportCalcFieldVector(BasicReport report, String fieldName, String title, String expression) {
		super(report, fieldName, title, expression);
	}

	@Override
	public Object getValue() throws Exception {
		ReportVectorExpression e = new ReportVectorExpression(this);
		e.setExpression(expression);
		return e.getValue(true);
	}
	
}
