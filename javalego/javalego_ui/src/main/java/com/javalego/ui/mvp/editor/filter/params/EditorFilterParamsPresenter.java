package com.javalego.ui.mvp.editor.filter.params;

import java.util.Collection;
import java.util.Map;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.filter.IFilterParam;
import com.javalego.ui.filter.IFilterParamsService;
import com.javalego.ui.mvp.editor.beans.filters.EditorFiltersPresenter.EditorFilterObserver;
import com.javalego.ui.mvp.editor.filter.params.IEditorFilterParamsView.EditorFilterParamsViewListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Editor de filtro basado en parámetros para su edición en un componente UI.
 * 
 * @author ROBERTO RANZ
 *
 */
public class EditorFilterParamsPresenter implements IPresenter, EditorFilterParamsViewListener {

	/**
	 * Servicios de gestión de los parámetros del filtro
	 */
	private IFilterParamsService services;

	/**
	 * Vista para la edición de los parámetros del filtro.
	 */
	private IEditorFilterParamsView view;

	/**
	 * Establecer observer para las acciones de activar o desactivar el filtro.
	 */
	private EditorFilterObserver observer;

	/**
	 * Constructor
	 * 
	 * @param services
	 * @param view
	 */
	public EditorFilterParamsPresenter(IFilterParamsService services, IEditorFilterParamsView view) {

		this.services = services;
		this.view = view;

		view.setListener(this);
	}

	/**
	 * Constructor que genera una vista por defecto a partir de la interface
	 * IEditorFilterParamsView.
	 * 
	 * @param services
	 */
	public EditorFilterParamsPresenter(IFilterParamsService services) {

		this.services = services;

		this.view = new IEditorFilterParamsView() {
			@SuppressWarnings("unused")
			private EditorFilterParamsPresenter listener;

			@Override
			public void load() throws LocalizedException {
			}

			@Override
			public void setListener(EditorFilterParamsViewListener listener) {
				listener = EditorFilterParamsPresenter.this;
			}
		};
		view.setListener(this);
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public String getStatement() {
		return services.getStatement();
	}

	@Override
	public String getNaturalStatement() {
		return services.getNaturalStatement();
	}

	@Override
	public Collection<IFilterParam> getParams() {
		return services.getParams();
	}

	@Override
	public void execute(Map<String, ?> fieldValues) throws LocalizedException {

		services.build(fieldValues);

		if (observer != null) {
			observer.execute(services.getStatement(), services.getNaturalStatement());
		}
	}

	/**
	 * Establecer observer para las acciones de activar o desactivar el filtro.
	 * 
	 * @param listener
	 */
	public void setObserver(EditorFilterObserver listener) {
		this.observer = listener;
	}

	@Override
	public Key getTitle() {
		return services.getTitle();
	}

	@Override
	public IEditorFilterParamsView getView() {
		return view;
	}

	@Override
	public Key getDescription() {
		return services.getDescription();
	}

	@Override
	public String getName() {
		return services.getName();
	}

	public IFilterParamsService getServices() {
		return services;
	}

	/**
	 * Establecer observer para las acciones de activar o desactivar el filtro.
	 * @return
	 */
	public EditorFilterObserver getObserver() {
		return observer;
	}
}
