package com.javalego.xls.report.elements.calcfields;

import com.javalego.xls.report.BasicReport;
import com.javalego.xls.report.elements.ReportField;

/**
 * Campo calculado donde el usuario del mismo tiene que establecer su valor en el evento beforeGenerate().
 * @author ROBERTO
 */
public abstract class BasicReportCalcField extends ReportField {

	/**
	 * Expresión de cálculo
	 */
	protected String expression; 
	
	public BasicReportCalcField(BasicReport report, String fieldName, String expression) {
		super(report, fieldName);
		this.expression = expression;
	}

	public BasicReportCalcField(BasicReport report, String fieldName, String title, String expression) {
		super(report, fieldName, title);
		this.expression = expression;
	}

	/**
	 * Polimorfismo de este método para devolver el valor calculado del campo.
	 * @return
	 */
	@Override
	public abstract Object getValue() throws Exception;
	
	
}
