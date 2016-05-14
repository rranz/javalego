package com.javalego.ui.mvp.editor.services;

import java.util.Map;

import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;
import com.javalego.ui.mvp.nestedbean.INestedBeanServices;
import com.javalego.ui.patterns.IService;

/**
 * Servicios del editor relacionados con la edición. Principalmente para gestionar los campos nested o relacionados que contiene el bean o entidad JPA (Ej.: campo nested empresa del bean contacto).
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IEditorServices<T> extends IService {
	
	/**
	 * Servicios para obtener y validar beans anidados.
	 * 
	 * Ej.: campo empresa (Empresa entidad) en Contactos entidad.
	 * 
	 * Nombre del campo (Ej.: empresa.nombre) que contiene la edición del campo nested.
	 * 
	 * Servicio de obtención y validación de beans.
	 * 
	 * @return
	 */
	Map<String, INestedBeanServices<T, ?>> getNestedFieldModel();
	
	/**
	 * Definir un evento de validación de datos. Se ejecuta cuando se modifica el valor de cualquiera campo definido en el modelo.
	 * @return
	 */
	ValueChangeListener getValueChangeListener();

	/**
	 * Gestión de Eventos del editor para controlar el ciclo de vida de la edición de datos.
	 * @return
	 */
	IBeanEditorEvents<T> getEditorEvents();
}
