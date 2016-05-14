package com.javalego.ui.editor.data;

/**
 * Edición de datos de valores primitivos. String, Date, Long, Double, Integer, ...
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IValueDataEditor extends IItemEditor {

	/**
	 * Valor en edición.
	 * @return
	 */
	Object getValue();
	
}
