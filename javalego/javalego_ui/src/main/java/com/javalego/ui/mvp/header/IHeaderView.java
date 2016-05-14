package com.javalego.ui.mvp.header;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.patterns.IView;

/**
 * Vista de la cabecera
 * 
 * @author ROBERTO RANZ
 */
public interface IHeaderView extends IView {
	
	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del Presenter y que son utilizados por la vista (mvp pattern).
	 * @author ROBERTO RANZ
	 */
	interface HeaderViewListener {

		IHeaderModel getModel();

		boolean hasToolBar();

		IView getToolBarView() throws LocalizedException;
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios proporcionados por el Presenter que son necesarios para la vista (mvp pattern).
	 * @param listener
	 */
	public void setListener(HeaderViewListener listener);

}
