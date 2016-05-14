package com.javalego.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;

/**
 * Clase que utiliza la librería de lectura y escritura de documentos pdf
 * (itext) para crea métodos sencillos que nos permitan crear documentos pdf de
 * una forma más sencilla y productiva.
 * 
 * @author ROBERTO
 */
public abstract class BasicPdfWapper {

	// Documento pdf
	protected Document document = new Document();

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setLandscape() {
		document.setPageSize(PageSize.A4.rotate());
	}

}
