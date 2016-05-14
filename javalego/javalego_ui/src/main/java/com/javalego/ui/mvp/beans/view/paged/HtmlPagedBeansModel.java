package com.javalego.ui.mvp.beans.view.paged;

import java.util.List;

/**
 * Modelo que muestra datos del bean en formato Html
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public abstract class HtmlPagedBeansModel<T> extends AbstractPagedBeansModel<T> {

	public HtmlPagedBeansModel(List<T> beans, Class<T> beanClass) {
		super(beans, beanClass);
	}

	/**
	 * Obtiene el valor del bean en formato Html para mostrarse en una lista.
	 * @return
	 */
	abstract public String toHtml(T bean);
}
