package com.javalego.xls.report.elements.calcfields;

import com.javalego.util.StringUtils;


/**
 * Evaluador de expresiones asociado a la clase ReportCalcFieldObject de
 * donde obtiene la información contextual relativa a los siguientes
 * TOKENS disponibles:
 * $F{KEY} campo de la vista.
 *
 * El evaluador sustituye los tokens por la información de la emisión actualmente en edición
 * y ejecuta la expresión devolviendo un valor o un valor lógico utilizado en las validaciones.
 *
 * @author ROBERTO RANZ
 */
public class ReportObjectsExpression  {

	private static final String TOKEN_FIELD = "F";
	
	private ReportCalcFieldObjects field;
	
	public ReportObjectsExpression(){
		addTokens();
	}
	
	public ReportObjectsExpression(ReportCalcFieldObjects field){
		this.field = field;
		addTokens();
	}
	
	private void addTokens(){
//		addToken(TOKEN_FIELD);
//		setIgnoreError(false);
	}
	
	/**
	 * Traducir el token con la información contenida en el adoEditor.
	 */
	public String translateToken(String token, String value) throws Exception {
		
		// Datos del ado.
		if (token.equals(TOKEN_FIELD) && field != null){
		
			Object object = field.getReport().getFieldValue(field.getReport().getActualObject(), value);
			if (object instanceof String)
				return StringUtils.getSingleQuotedStr(object.toString());
			else if (object != null)
				return object.toString();
			else
				return "";
		}
		return null;
	}

	public ReportCalcFieldObjects getField() {
		return field;
	}

	public void setField(ReportCalcFieldObjects field) {
		this.field = field;
	}

	public void setExpression(String expression) {
		// TODO Auto-generated method stub
		
	}

	public Object getValue(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

}
