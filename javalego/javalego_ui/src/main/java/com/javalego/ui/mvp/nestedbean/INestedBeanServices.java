package com.javalego.ui.mvp.nestedbean;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;

/**
 * Servicios de validación y de selección de beans nested válidos.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T> Owner Bean (Ej.: Contacto bean que contiene el campo anidado empresa del contacto)
 * @param <U> Nested Bean (Ej.: Contacto.empresa = Empresa)
 */
public interface INestedBeanServices<T, U> {

	/**
	 * Validación del bean
	 * @throws Exception
	 */
	public abstract void validate(U bean) throws LocalizedException;

	/**
	 * Establecer el nested bean en el bean propietario.
	 * @param bean
	 * @param ownerBean
	 * @throws Exception
	 */
	public abstract void setBean(U bean, T ownerBean) throws LocalizedException;
	
	/**
	 * Obtiene el nested bean a partir del bean que lo contiene.
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public abstract U getBean(T ownerBean) throws LocalizedException;

	/**
	 * Obtener la lista de beans válidos que pueden ser asignados al bean owner.
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException 
	 */
	public abstract Collection<U> getBeans(String filter) throws LocalizedException;
	
	/**
	 * Vista utilizada para formatear los datos mostrados al usuario para seleccionar y localizar el nestedbean.
	 * @return
	 */
	public abstract IFormatBeansView<T> getFormatView();	
	
	/**
	 * Obtener el evento que permite actualizar el valor de los campos del bean anidado en el editor. 
	 * @return
	 */
	public abstract ValueChangeListener getValueChangeListener();
	
	/**
	 * Busca el nombre clave del bean filtrando datos según se introduce su
	 * valor (recomendado para muchos valores). Si es falso, se mostrará la
	 * lista completa de sus posibles valores (recomentado para poca
	 * información).
	 * 
	 * @return
	 */
	public abstract boolean isAutoComplete();	
}
