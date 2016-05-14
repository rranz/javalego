package com.javalego.poi.report.word.elements;

/**
 * Elemento o acción básica que necesitamos realizar sobre un documento de Word existente o no. Ej.: bookmarks, tables, etc.
 * @author ROBERTO RANZ
 */
public abstract class BasicElementWordDocument {

	/**
	 * Nombre del bookmark en el documento Word
	 */
	private String name;
	
	private String title;
	
	private String description;

	/**
	 * Número de índice del array de valores de donde obtendremos la información. Utilizar sólo cuando se utilice un array de valores (Object []).
	 */
	private int index = -1;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
