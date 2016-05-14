package com.javalego.poi.report.word.elements;

import java.util.Date;

import com.javalego.poi.report.word.WordDocument;
import com.javalego.util.DateUtils;

/**
 * Incluir un valor en la posición de un Bookmark de un documento de MS-Word
 * @author ROBERTO RANZ
 */
public class CheckBox extends BasicElementWordDocument {

	/**
	 * Nombre de la propiedad de donde obtenemos la información. Utilizar sólo cuando se utilicen las propiedades de un objeto para configurar el informe.
	 */
	private String propertyName;
	
	/**
	 * Expresión de cálculo aplicada sobre el registro actualmente procesado desde donde obtenemos la información. Ej: $F{propiedad} + $F{propiedad}.
	 */
	private String expression;
	
	/**
	 * Expresión de cálculo utilizando expresión natural para fechas y valores lógico (Verdades/Falso).
	 */
	private boolean natural;
	
	/**
	 * Formato aplicado al valor recuperado.
	 */
	private String displayFormat;
	
	private WordDocument wordDocument;
	
	public CheckBox(WordDocument wordDocument) {
		this.wordDocument = wordDocument;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public WordDocument getWordDocument() {
		return wordDocument;
	}

	public void setWordDocument(WordDocument wordDocument) {
		this.wordDocument = wordDocument;
	}
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public boolean isNatural() {
		return natural;
	}

	public void setNatural(boolean natural) {
		this.natural = natural;
	}

	/**
	 * Obtener el String de un Objeto cualquiera.
	 * @param object
	 * @return
	 */
	public String getStringValue(Object object) {
		
		if (object != null) {
		
			if (object instanceof Date)
				return DateUtils.getDateToString((Date)object);
			else
				return object.toString();
		}
		else
			return "";
	}

	public String getDisplayFormat() {
		return displayFormat;
	}

	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}

}
