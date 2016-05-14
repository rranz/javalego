package com.javalego.ui.mvp.beans.view;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.patterns.IView;

/**
 * Vista de visualización de una lista de beans.
 * 
 * Ver implementaciones de paginación o simple listas basadas en tablas o grid componentes.
 * 
 * El editor de Beans puede configurar una lista de vistas de cualquier tipo para visualizar los beans a editar.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBeansView<T> extends IView {

	/**
	 * Eliminar la selección actual 
	 */
	void removeSelectedBean();

	/**
	 * Bean seleccionado
	 * @return
	 */
	T getSelectedBean();
	
	/**
	 * Seleccionar una lista de beans
	 * @param beans
	 */
	void setSelectedBeans(Collection<T> beans);
	
	/**
	 * Lista de beans seleccionados
	 * @return
	 */
	Collection<T> getSelectedBeans();

	/**
	 * Eliminar bean
	 * @param bean
	 */
	void removeBean(T bean);

	/**
	 * Modificar bean editado.
	 * @param bean
	 * @throws LocalizedException 
	 */
	void update(T bean) throws LocalizedException;
	
	/**
	 * Insertar bean
	 * @param bean
	 * @throws LocalizedException 
	 */
	void insert(T bean) throws LocalizedException;

	/**
	 * Recargar los beans del editor.
	 * @param beans
	 * @throws LocalizedException 
	 */
	public void reloadBeans(Collection<T> beans) throws LocalizedException;

	/**
	 * Aplicar un filtro de selección
	 * @param statement
	 * @throws LocalizedException
	 */
	void applyFilter(String statement) throws LocalizedException;

	/**
	 * Eliminar el actual filtro de selección.
	 * 
	 * @throws LocalizedException
	 */
	void removeCurrentFilter() throws LocalizedException;	
	
	/**
	 * Obtener la lista de beans
	 * @return
	 */
	Collection<T> getBeans() throws LocalizedException;
}
