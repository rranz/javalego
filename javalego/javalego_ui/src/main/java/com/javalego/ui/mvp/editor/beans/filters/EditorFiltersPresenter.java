package com.javalego.ui.mvp.editor.beans.filters;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.mvp.editor.beans.filters.IEditorFiltersView.IEditorFiltersViewListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter de edición de filtros
 * 
 * @author ROBERTO RANZ
 *
 */
public class EditorFiltersPresenter implements IEditorFiltersViewListener, IPresenter {

	private IEditorFiltersModel model;

	private IEditorFiltersView view;

	/**
	 * Observer que permite gestionar las acciones realizadas en los filtros para su ejecución o cancelación.
	 */
	private EditorFilterObserver observer;

	public EditorFiltersPresenter(IEditorFiltersModel model, IEditorFiltersView view) {
		this.model = model;
		this.view = view;
		view.setListener(this);
	}

	@Override
	public Collection<IFilterService> getFilters() {
		return model.getFilters();
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public IEditorFiltersView getView() {
		return view;
	}

	@Override
	public void execute(String statement, String naturalStatement) throws LocalizedException {

		if (observer != null) {
			observer.execute(statement, naturalStatement);
		}

	}

	public EditorFilterObserver getListener() {
		return observer;
	}

	/**
	 * Observer que permite gestionar las acciones realizadas en los filtros para su ejecución o cancelación.
	 * @param observer
	 */
	public void setObserver(EditorFilterObserver observer) {
		this.observer = observer;
	}

	/**
	 * Observer para la ejecución de un filtro de selección
	 * 
	 * @author ROBERTO RANZ
	 */
	public interface EditorFilterObserver {

		/**
		 * Ejecutar filtro utilizando la sentencia pasada como parámetro.
		 * 
		 * @param statement
		 * @param naturalStatement
		 * @throws LocalizedException
		 */
		void execute(String statement, String naturalStatement) throws LocalizedException;

		/**
		 * Eliminar filtro actual
		 * 
		 * @throws LocalizedException
		 */
		void removeCurrentFilter() throws LocalizedException;
	}

	@Override
	public void removeCurrentFilter() throws LocalizedException {

		if (observer != null) {
			observer.removeCurrentFilter();
		}
	}

	/**
	 * Observer que permite gestionar las acciones realizadas en los filtros para su ejecución o cancelación.
	 * @return
	 */
	public EditorFilterObserver getObserver() {
		return observer;
	}
}
