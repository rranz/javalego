package com.javalego.msoffice.word;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.BasicElementApp;
import com.javalego.msoffice.word.table.Tables;

/**
 * Documento MS-Office
 * @author ROBERTO RANZ
 */
public class Document extends BasicElementApp {

	/**
	 * Bookmarks del documento.
	 */
	private Bookmarks bookmarks;

	/**
	 * Campos de formulario
	 */
	private FormFields formFields;
	
	/**
	 * Tablas
	 */
	private Tables tables;
	
	private Documents documents;
	
	private String fileName;
	
	private boolean create;
	
	public Document(Documents documents) {
		this.documents = documents;
	}
	
	/**
	 * Documento existente
	 * @return
	 */
	public boolean isOpen() {
		return create == false;
	}
	
	/**
	 * Nuevo documento.
	 * @return
	 */
	public boolean isNew() {
		return create == true;
	}
	
	/**
	 * Abrir un documento existente
	 * @param fileName
	 * @throws Exception
	 */
	public void open(String fileName) throws Exception {
		this.fileName = fileName;
		create = false;
		setPointer();
	}
	
	/**
	 * Cerrar documento
	 */
	public void close() throws Exception {

		boolean tSaveOnExit = false; 
		Dispatch.call(getPointer(), "Close", tSaveOnExit); 
	}
	
	/**
	 * Añadir un documento
	 * @param fileName
	 * @throws Exception
	 */
	public void create(String fileName) throws Exception {
		this.fileName = fileName;
		create = true;
		setPointer();
	}
	
	@Override
	protected String getInternalName() {
		return "Document";
	}

	/**
	 * Bookmarks del documento.
	 * @return
	 */
	public Bookmarks getBookmarks() {
		if (bookmarks == null) {
			bookmarks = new Bookmarks(this);
		}
		return bookmarks;
	}

	/**
	 * Buscar un bookmark
	 * @return
	 */
	public Bookmark getBookmark(String name) throws Exception {
		return getBookmarks().getBookmark(name);
	}

	/**
	 * Buscar un checkBox
	 * @return
	 */
	public CheckBox getCheckBox(String name) throws Exception {
		return getFormFields().getCheckBox(name);
	}

	/**
	 * Tablas del documento.
	 * @return
	 */
	public Tables getTables() {
		if (tables == null) {
			tables = new Tables(this);
		}
		return tables;
	}

	@Override
	protected void setPointer() throws Exception {
		
		pointer = Dispatch.call(documents.getPointer(), create ? "New" : "Open", fileName).toDispatch(); 
	}

	/**
	 * Cambiar el path de ubicación del fichero de datasource definido en "Combinar correspondencia".
	 * @param path
	 */
	public void changePathMailMergeDataSource(String path) throws Exception {
		Dispatch p2 = Dispatch.get(pointer, "MailMerge").toDispatch();
		Dispatch.call(p2, "OpenDataSource", path);
	}
	
	/**
	 * Grabar el documento.
	 * @throws Exception
	 */
	public void save() throws Exception {
		Dispatch.call(getPointer(), "Save");
	}
	
	/**
	 * Grabar el documento con otro nombre.
	 * @throws Exception
	 */
	public void saveAs(String fileName) throws Exception {
		//"wdFormatText"
		Dispatch.call(getPointer(), "SaveAs", fileName, 0);
	}

	/**
	 * Grabar el documento con otro nombre.
	 * @throws Exception
	 */
	public void saveAsHtml(String fileName) throws Exception {
		//"wdFormatText"
		Dispatch.call(getPointer(), "SaveAs", fileName, 10);
	}

	public FormFields getFormFields() {
		if (formFields == null) {
			formFields = new FormFields(this);
		}
		return formFields;
	}

	public void setFormFields(FormFields formFields) {
		this.formFields = formFields;
	}

	/**
	 * Obtiene cualquier propiedad del documento.
	 * @param internalName
	 * @return
	 * @throws Exception
	 */
	public Dispatch getProperty(String internalName) throws Exception {
		return Dispatch.get(getPointer(), internalName).toDispatch();
	}
	
}
