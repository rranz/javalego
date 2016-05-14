package com.javalego.poi.report.word;

import com.javalego.word.report.WordDocument;

/**
 * Evaluador de expresiones basado en los elementos de un documento de Word que
 * utiliza bookmarks y campos de formulario como campos de información del
 * documento.
 * 
 * @author ROBERTO RANZ
 */
public class WordDocumentExpression {

	/**
	 * Token que representa un Bookmark y obtiene el valor del objeto que lo
	 * representa (propiedad o índice de un array de valores).
	 */
	protected static final String TOKEN_BOOKMARK = "B";

	private WordDocument wordDocument;

	/**
	 * Formato aplicado al valor recuperado dependiendo del tipo de valor
	 * (fecha, etc.).
	 */
	// private String displayFormat;

	private boolean natural;

	public WordDocumentExpression(WordDocument wordDocument) {
		this.wordDocument = wordDocument;
		addTokens();
	}

	/**
	 * Añadir tokens comunes.
	 */
	protected void addTokens() {
		// addToken(TOKEN_BOOKMARK);
		// setIgnoreError(false);
	}

	protected String translateToken(String token, String value) throws Exception {

		// Datos del ado.
		if ((token.equals(TOKEN_BOOKMARK) && wordDocument != null)) {
			String _value = wordDocument.getBookmarkValue(value);
			if (_value != null)
				return "'" + _value + "'";
			else
				return "''";
		}
		return null;
	}

	public boolean isNatural() {
		return natural;
	}

	public void setNatural(boolean natural) {
		this.natural = natural;
	}

	public WordDocument getWordDocument() {
		return wordDocument;
	}

	public void setWordDocument(WordDocument wordDocument) {
		this.wordDocument = wordDocument;
	}

}
