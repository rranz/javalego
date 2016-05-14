package com.javalego.xls.report.elements;

/**
 * Métodos que deben de implementar los diferentes elementos del informe y definición de constantes utilizadas.
 * @author ROBERTO RANZ
 *
 */
public interface IReportConstans {

	/**
	 * Tipología de fórmulas aplicables.
	 */
	static public final String 
		FORMULA_SUM = "sum", 
		FORMULA_AVG = "avg",
		FORMULA_MAX = "max",
		FORMULA_MIN = "min",
		FORMULA_PERCENT = "percent";
	
	/**
	 * Tipologías de fórmulas aplicables al totalizar los valores numéricos.
	 * @return
	 */
	static public String[] formulaTypes = new String[] { FORMULA_SUM, FORMULA_AVG, FORMULA_MAX, FORMULA_MIN, FORMULA_PERCENT };
	
}
