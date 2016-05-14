package com.javalego.erp.model.editors;

import java.util.HashMap;
import java.util.Map;

import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;
import com.javalego.ui.mvp.editor.services.IBeanEditorEvents;
import com.javalego.ui.mvp.editor.services.IEditorServices;
import com.javalego.ui.mvp.nestedbean.INestedBeanServices;
import com.javalego.ui.mvp.nestedbean.imp.NestedBeanListValues;

/**
 * Servicios del editor relacionados con la edición. Principalmente para gestionar los campos nested o relacionados que contiene el bean o entidad JPA (Ej.: campo nested empresa del bean contacto).
 * 
 * @author ROBERTO RANZ
 *
 */
public class EditorServices<T> implements IEditorServices<T> {

	/**
	 * Gestión de Eventos del editor para controlar el ciclo de vida de la edición de datos.
	 */
	private IBeanEditorEvents<T> events;
	
	/**
	 * Lista de servicios para obtener y validar beans anidados.
	 */
	private HashMap<String, INestedBeanServices<T, ?>> list;
	
	/**
	 * Constructor
	 * @param events
	 */
	public EditorServices(IBeanEditorEvents<T> events) {
		this.events = events;
	}

	/**
	 * Constructor
	 */
	public EditorServices() {
	}

	@Override
	public Map<String, INestedBeanServices<T, ?>> getNestedFieldModel() {
		return list;
	}

	@Override
	public ValueChangeListener getValueChangeListener() {
		return null;
	}

	@Override
	public IBeanEditorEvents<T> getEditorEvents() {
		return events;
	}

	/**
	 * Añadir un modelo de selección y validación de bean nested que contiene el bean del editor. Ej.: nested bean empresa existente en el bean de contacto. 
	 * @param fieldname
	 * @param nestedBeanListValues
	 */
	public void addNestedFieldModel(String fieldname, NestedBeanListValues<T, ?> nestedBeanListValues) {
		
		if (list == null) {
			list = new HashMap<String, INestedBeanServices<T, ?>>();
		}
		
		list.put(fieldname, nestedBeanListValues);
	}

}
