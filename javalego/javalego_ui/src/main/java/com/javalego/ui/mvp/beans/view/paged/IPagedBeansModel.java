package com.javalego.ui.mvp.beans.view.paged;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.list.IListBeansViewModel;

/**
 * Modelo de beans paginados. Ej.: ver su contenido en formato Html para incluir en vistas UI.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IPagedBeansModel<T> extends IListBeansViewModel<T> {
	
	/**
	 * Número de beans por página
	 */
	public static final int SIZE_PAGE_BEANS = 10;
	
	/**
	 * Obtener los beans de la siguiente página
	 * @throws Exception 
	 */
	public Collection<T> getNextPageBeans() throws LocalizedException;
	
	/**
	 * Obtener los beans de la anterior página
	 */
	public Collection<T> getPriorPageBeans() throws LocalizedException;
	
	/**
	 * Obtener los beans de la primera página
	 */
	public Collection<T> getFirstPageBeans() throws LocalizedException;
	
	/**
	 * Obtener los beans de la última página
	 */
	public Collection<T> getLastPageBeans() throws LocalizedException;
	
	/**
	 * Obtener los beans de una página 
	 * @param index
	 */
	public Collection<T> getPageBeans(int index) throws LocalizedException;
	
	/**
	 * Número de páginas que se obtienen del total de objetos a paginar
	 * @return
	 * @throws LocalizedException 
	 */
	public int getCountPages() throws LocalizedException;
	
	/**
	 * Número de página actual
	 * @return
	 */
	public int getCurrentPage();
	
	/**
	 * Número total de beans a paginar
	 * @return
	 * @throws LocalizedException 
	 */
	public long getCountBeans() throws LocalizedException;
	
	/**
	 * Número de beans por página
	 * @return
	 */
	public int getSizePageBeans();
	
	/**
	 * Recargar la página actual
	 */
	public Collection<T> getBeansCurrentPage() throws LocalizedException;
	
		
}
