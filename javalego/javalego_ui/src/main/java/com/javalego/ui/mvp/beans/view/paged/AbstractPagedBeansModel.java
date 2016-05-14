package com.javalego.ui.mvp.beans.view.paged;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.util.StringUtils;

/**
 * Modelo de datos basado en beans paginados.
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public abstract class AbstractPagedBeansModel<T> implements IPagedBeansModel<T> {

	/**
	 * Beans
	 */
	protected List<T> beans;

	/**
	 * número de beans por página
	 */
	protected int sizePageBeans = SIZE_PAGE_BEANS;

	/**
	 * Beans actualmente cargados
	 */
	protected List<T> currentBeans;

	/**
	 * Última posición de la que obtener los siguientes registros.
	 */
	protected int lastIndex = 0;

	protected Class<T> beanClass;

	/**
	 * Ordenación actual
	 */
	protected String order;

	/**
	 * Filtro de selección de objetos o registros.
	 */
	private String filter;

	/**
	 * Constructor
	 * 
	 * @param beans
	 * @param beanClass
	 */
	public AbstractPagedBeansModel(List<T> beans, Class<T> beanClass) {
		this.beans = beans;
		this.beanClass = beanClass;
	}

	@Override
	public Class<T> getBeanClass() {
		return beanClass;
	}

	/**
	 * Constructor
	 * 
	 * @param beans
	 * @param pageBeans
	 *            número de beans por página
	 */
	public AbstractPagedBeansModel(List<T> beans, int pageBeans) {
		this.beans = beans;
		this.sizePageBeans = pageBeans;
	}

	@Override
	public Collection<T> getNextPageBeans() throws LocalizedException {

		if (lastIndex + sizePageBeans >= getCountBeans()) {
			return currentBeans;
		}
		else {
			lastIndex += sizePageBeans;
			return getPageBeans();
		}
	}

	@Override
	public Collection<T> getPriorPageBeans() throws LocalizedException {

		if (lastIndex - sizePageBeans < 0) {
			return currentBeans;
		}
		else {
			lastIndex -= sizePageBeans;
			return getPageBeans();
		}
	}

	@Override
	public Collection<T> getFirstPageBeans() throws LocalizedException {
		lastIndex = 0;
		return getPageBeans();
	}

	@Override
	public Collection<T> getLastPageBeans() throws LocalizedException {

		lastIndex = absPages() * sizePageBeans;

		long countBeans = getCountBeans();
		
		if (lastIndex == countBeans && absPages() > 1) {
			lastIndex -= sizePageBeans;
		}
		else if (lastIndex == countBeans) {
			return currentBeans;
		}
		return getPageBeans();
	}

	@Override
	public int getCountPages() throws LocalizedException {

		long count = getCountBeans();
		
		int mas = count % sizePageBeans > 0 ? 1 : 0;

		return sizePageBeans > 0 ? Integer.valueOf(Math.abs((int) count / sizePageBeans) + mas) : 0;
	}

	public int absPages() throws LocalizedException {
		return sizePageBeans > 0 ? Integer.valueOf(Math.abs((int) getCountBeans() / sizePageBeans)) : 0;
	}

	@Override
	public int getCurrentPage() {
		return sizePageBeans > 0 ? Integer.valueOf(Math.abs((lastIndex + sizePageBeans) / sizePageBeans)) : 0;
	}

	@Override
	public Collection<T> getPageBeans(int index) throws LocalizedException {

		if (index > getCountPages() || index < 1) {
			return currentBeans;
		}
		else {
			lastIndex = index == 0 ? 0 : (index - 1) * sizePageBeans;
			return getPageBeans();
		}
	}

	@Override
	public long getCountBeans() throws LocalizedException {
		return beans == null ? 0 : beans.size();
	}

	@Override
	public int getSizePageBeans() {
		return sizePageBeans;
	}

	@Override
	public Collection<T> getBeansCurrentPage() throws LocalizedException {
		return getPageBeans(getCurrentPage());
	}

	/**
	 * Ordenar datos.
	 * 
	 * @param propertyId
	 * @param ascending
	 */
	public void sort(Object[] propertyId, boolean[] ascending) {

		if (propertyId == null || propertyId.length == 0)
			return;

		if (beans != null) {
			StringUtils.sortList(beans, propertyId[0].toString(), ascending[0]);
		}

		order = propertyId[0] + " " + (ascending[0] ? "ASC" : "DESC");
	}

	/**
	 * Cargar beans de la página en base a los datos actuales de navegación.
	 * 
	 * Ver componentes que implementan esta clase para recuperar los beans de la
	 * página de bases de datos.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	public Collection<T> getPageBeans() throws LocalizedException {

		currentBeans = new ArrayList<T>();

		if (beans == null) {
			return currentBeans;
		}

		for (int i = lastIndex; i < lastIndex + sizePageBeans; i++) {

			if (beans.size() > i) {
				currentBeans.add(beans.get(i));
			}
			else {
				break;
			}
		}
		return currentBeans;
	}

	/**
	 * Ordenación actual
	 * 
	 * @return
	 */
	public String getOrder() {
		return order;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

}
