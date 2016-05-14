package com.javalego.xls.report.object.elements;

import java.util.Date;

import com.javalego.util.ReflectionUtils;
import com.javalego.util.StringUtils;

/**
 * Evaluador de expresiones asociado a las propiedades de un objeto
 * @author ROBERTO RANZ
 */
public class VisualRuleExpression {

	private static final String TOKEN_FIELD = "F";
	private Object object;
	
	public VisualRuleExpression(){
		addTokens();
	}
	
	public VisualRuleExpression(Object object){
		this.object = object;
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
		if (token.equals(TOKEN_FIELD) && object != null){
		
			Object object = ReflectionUtils.getPropertyValue(this.object, value);
			
			if (object instanceof String)
				return StringUtils.getSingleQuotedStr(object.toString());
			else if (object instanceof Date)
				return new Long(((Date)object).getTime()).toString(); //. Functions.getSingleQuotedStr(new SimpleDateFormat("yyyyMMdd").format(object));
			else if (object != null)
				return object.toString();
		}
		return null;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
