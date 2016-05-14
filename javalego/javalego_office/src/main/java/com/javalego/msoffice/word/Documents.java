package com.javalego.msoffice.word;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.BasicElementApp;

/**
 * Documentos del archivo MS-Word
 * @author ROBERTO RANZ
 */
public class Documents extends BasicElementApp {

	/**
	 * Aplicación MS-Word asociada.
	 */
	private WordApplication app;
	
	/**
	 * Constructor donde le indicamos la aplicación asociada a los documentos.
	 * @param app
	 */
	public Documents(WordApplication app) {
		this.app = app;
	}
	
	@Override
	protected String getInternalName() {
		return "Documents";
	}
	
	/**
	 * Abrir un documento dentro de la aplicación MS-Word.
	 * @param fileName
	 */
	public Document openDocument(String fileName) throws Exception {
		Document document = new Document(this);
		document.open(fileName);
		return document;
	}
	
	@Override
	protected void setPointer() throws Exception {
		
		pointer = Dispatch.get(app.getPointer(),"Documents").toDispatch();
	}

}
