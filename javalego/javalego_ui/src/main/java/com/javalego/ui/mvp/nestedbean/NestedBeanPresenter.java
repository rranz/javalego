package com.javalego.ui.mvp.nestedbean;

import com.javalego.ui.mvp.nestedbean.INestedBeanView.INestedBeanListener;

/**
 * Presenter MVP para gestionar la selección de un nested bean válido gestionando la modificación de los componentes UI
 * 
 * @author ROBERTO RANZ
 */
public class NestedBeanPresenter<T, U> implements INestedBeanListener<T, U> {

	@SuppressWarnings("unused")
	private INestedBeanView<T, U> view;

	private INestedBeanServices<T, U> services;

	/**
	 * Constructor
	 * @param model
	 * @param view
	 */
	public NestedBeanPresenter(INestedBeanServices<T, U> services, INestedBeanView<T, U> view) {
		this.view = view;
		this.services = services;
	}

	@Override
	public void validate(U bean) throws Exception {
		services.validate(bean);
	}

	@Override
	public void setBean(U bean, T ownerBean) throws Exception {
		services.setBean(bean, ownerBean);
	}
	
	@Override
	public U getBean(T ownerBean) throws Exception {
		return services.getBean(ownerBean);
	}
	
}
