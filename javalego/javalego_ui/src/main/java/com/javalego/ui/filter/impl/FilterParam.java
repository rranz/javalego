package com.javalego.ui.filter.impl;

import com.javalego.model.keys.Key;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.impl.ValueDataEditor;
import com.javalego.ui.filter.IFilterParam;

/**
 * Parámetro del filtro
 * 
 * @author ROBERTO RANZ
 *
 */
public class FilterParam implements IFilterParam {

	private IItemEditor editor;

	private ConditionType condition;

	/**
	 * Constructor
	 * 
	 * @param editor
	 * @param condition
	 */
	public FilterParam(IItemEditor editor, ConditionType condition) {
		this.editor = editor;
		this.condition = condition;
	}

	/**
	 * Parámtro de un filtro simple basado en un valor y condición.
	 * 
	 * @param name
	 * @param title
	 * @param value
	 *            Valor por defecto. No puede ser null (Ej.: new String(), new
	 *            Boolean(false), ...)
	 * @param condition
	 */
	public FilterParam(String name, Key title, Object value, ConditionType condition) {

		this.editor = new ValueDataEditor(name, title, value);
		this.condition = condition;
	}

	/**
	 * Parámtro de un filtro simple basado una condición y un valor por defecto
	 * = new String() que no se requiere como parámetro.
	 * 
	 * @param name
	 * @param title
	 * @param condition
	 */
	public FilterParam(String name, Key title, ConditionType condition) {

		this.editor = new ValueDataEditor(name, title, new String());
		this.condition = condition;
	}

	public IItemEditor getEditor() {
		return editor;
	}

	public void setEditor(IItemEditor editor) {
		this.editor = editor;
	}

	public void setCondition(ConditionType condition) {
		this.condition = condition;
	}

	@Override
	public IItemEditor getDataEditor() {
		return editor;
	}

	@Override
	public ConditionType getCondition() {
		return condition;
	}

}
