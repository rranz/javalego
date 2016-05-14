package com.javalego.poi.report.object.elements;

import com.javalego.poi.report.ReportVisualStyle;
import com.javalego.util.StringUtils;

/**
 * Clase que establece propiedades de la clase ReportVisualStyle si 
 * se cumple una condición.
 * @author ROBERTO RANZ
 */
public class VisualRule {

	private static VisualRuleExpression visualRuleExpression = new VisualRuleExpression();
	
	/**
	 * Utilizamos la misma clase de estilo incluida en los elementos de un informe
	 * para poder aplicar los cambios definidos en la regla de visualización.
	 */
	private ReportVisualStyle configStyle = new ReportVisualStyle(null);
	
	private String expression;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * Ejecutar las reglas de validación si se cumple la condición basándose
	 * en las propiedades del objeto pasado como referencia.
	 * @param object
	 */
	public void execute(Object object, ReportVisualStyle style, String expresssion) throws Exception {
		
		if (!StringUtils.isEmpty(expression)) {
			
			visualRuleExpression.setObject(object);
//			visualRuleExpression.setExpression(expression);
//			if (visualRuleExpression.getBooleanValue()) {
//			
//				// Asignar Propiedades a style
//				if (configStyle.getForeColor() > -1) {
//					style.setForeColor(configStyle.getForeColor());
//				}
//				if (configStyle.getBackgroundColor() > -1) {
//					style.setBackgroundColor(configStyle.getBackgroundColor());
//				}
//				if (configStyle.isFontBold()) {
//					style.setFontBold(true);
//				}
//				if (configStyle.isFontItalic()) {
//					style.setFontItalic(true);
//				}
//				if (configStyle.getFontSize() > -1) {
//					style.setFontSize(configStyle.getFontSize());
//				}
//				
//			}
			
		}
		
	}

	public ReportVisualStyle getConfigStyle() {
		return configStyle;
	}

	public void setConfigStyle(ReportVisualStyle configStyle) {
		this.configStyle = configStyle;
	}

}
