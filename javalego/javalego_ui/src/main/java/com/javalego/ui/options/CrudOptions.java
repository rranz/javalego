package com.javalego.ui.options;

import com.javalego.model.keys.Colors;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.icons.enums.IconEditor;

/**
 * Opciones mantenimientos CRUD
 * 
 * @author ROBERTO RANZ
 *
 */
public interface CrudOptions {

	public static IOption FILTER = new Option(LocaleEditor.FILTER, Colors.TEAL, IconEditor.INFO);
	
	public static IOption CLEAR_FILTER = new Option(LocaleEditor.REMOVE_FILTER, Colors.BLUE, IconEditor.CANCEL);

	public static IOption DELETE = new Option(LocaleEditor.DELETE, Colors.RED, IconEditor.CANCEL);
	
	public static IOption DOC = new Option(LocaleEditor.DOC, Colors.GREEN, IconEditor.INFO);
	
	public static IOption EDIT = new Option(LocaleEditor.EDIT, Colors.ORANGE, IconEditor.EDIT);

	public static IOption ADD = new Option(LocaleEditor.INSERT, Colors.GREEN, IconEditor.ADD);
	
	public static IOption REPORT = new Option(LocaleEditor.REPORT, Colors.GRAY, IconEditor.REPORT);
	
	public static IOption PROPERTIES = new Option(LocaleEditor.PROPERTIES, Colors.BLACK, IconEditor.INFO);
	
	public static IOption REFRESH = new Option(LocaleEditor.REFRESH, Colors.INDIGO, IconEditor.REFRESH);
	
}
