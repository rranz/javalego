package com.javalego.word.report.elements;

import java.util.Date;

import com.javalego.util.DateUtils;
import com.javalego.word.report.WordDocument;

/**
 * Campo utilizado para realizar una combinación de correspondencia en un documento MS-Word.
 * @author ROBERTO RANZ
 */
public class MergeField extends BasicElementWordDocument {

	private WordDocument wordDocument;
	
	public MergeField(WordDocument wordDocument) {
		this.wordDocument = wordDocument;
	}

	public WordDocument getWordDocument() {
		return wordDocument;
	}

	public void setWordDocument(WordDocument wordDocument) {
		this.wordDocument = wordDocument;
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
	
}
