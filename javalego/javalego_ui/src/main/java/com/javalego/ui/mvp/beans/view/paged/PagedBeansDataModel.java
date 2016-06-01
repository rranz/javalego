package com.javalego.ui.mvp.beans.view.paged;

import java.util.Collection;
import java.util.List;

import com.javalego.data.DataContext;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.beans.view.paged.FieldsPagedBeansModel;

/**
 * Extensión de modelo de beans paginados para obtener los datos del proveedor de datos definido en la aplicación.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public class PagedBeansDataModel<T> extends FieldsPagedBeansModel<T> {

	/**
	 * Número total de beans de la consulta.
	 */
	private Long count = null;
	
	public PagedBeansDataModel(List<T> beans, Class<T> beanClass, Collection<FieldModel> fieldsModel) {
		super(beans, beanClass, fieldsModel);
	}

	/**
	 * Obtiene los beans de la página actual del proveedor de datos de la aplicación.
	 * @return
	 * @throws LocalizedException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> getPageBeans() throws LocalizedException {

		if (DataContext.getProvider() == null) {
			return null;
		}
		
		currentBeans = (List<T>) DataContext.getProvider().pagedList((Class<Entity<?>>)getBeanClass(), lastIndex, sizePageBeans, getFilter(), getOrder());
		
		count = null;
		
		getCountBeans();
		
		return currentBeans;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long getCountBeans() throws LocalizedException {
		
		if (count == null && DataContext.getProvider() != null) {
			count = DataContext.getProvider().count((Class<Entity<?>>) getBeanClass(), getFilter());			
		}
		
		return count == null ? 0 : count;
	}
	
	/**
	 * Ordenar datos.
	 * @param propertyId
	 * @param ascending
	 */
	@Override
	public void sort(Object[] propertyId, boolean[] ascending) {
		
		if (propertyId == null || propertyId.length == 0)
			return;
		
		order = propertyId[0] + " " + (ascending[0] ? "ASC" : "DESC");
	}
	
}
