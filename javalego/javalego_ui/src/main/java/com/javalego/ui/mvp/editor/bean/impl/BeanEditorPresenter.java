package com.javalego.ui.mvp.editor.bean.impl;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.bean.IBeanEditorView;
import com.javalego.ui.mvp.editor.bean.IBeanEditorView.BeanEditorViewListener;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.impl.BaseEditorPresenter;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.services.IEditorServices;

/**
 * Presenter de un Editor de Bean
 * 
 * @author ROBERTO RANZ
 */
public class BeanEditorPresenter<T> extends BaseEditorPresenter implements BeanEditorViewListener<T> {

	private static final long serialVersionUID = -818182118041450674L;

	/**
	 * Listener de las acciones de edición del bean.
	 */
	protected BeanEditorObserver<T> observer;

	/**
	 * Modelo
	 */
	protected IBeanEditorModel<T> model;

	/**
	 * Vista
	 */
	protected IBeanEditorView<T> view;

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param services
	 * @param view
	 */
	public BeanEditorPresenter(final IBeanEditorModel<T> model, IEditorServices<?> services, IBeanEditorView<T> view) {

		this.model = model;
		this.services = services;
		this.view = view;

		view.setListener(this);

		// Establecer el listener del editor básico para poder reenviar el
		// evento al listener del editor de bean incluyendo el bean editado.
		setEditorListener(new EditorListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void remove() throws LocalizedException {
				if (observer != null && model != null) {
					observer.remove((T) model.getBean());
				}
			}

			@Override
			public void loadDetailBeanEditor(IBeansEditorModel<?> detail) {
			}

			@SuppressWarnings("unchecked")
			@Override
			public void discard() throws LocalizedException {
				if (observer != null && model != null) {
					observer.discard((T) model.getBean());
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public void commit() throws LocalizedException {
				if (observer != null && model != null) {
					observer.commit((T) model.getBean());
				}
			}
		});

		if (services != null) {
			addValueChangeListener(services.getValueChangeListener());
		}
	}

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param services
	 * @param view
	 * @param readOnly
	 */
	public BeanEditorPresenter(IBeanEditorModel<T> model, IEditorServices<?> services, IBeanEditorView<T> view, boolean readOnly, boolean remove) {
		this(model, services, view);
		this.readOnly = readOnly;
		this.remove = remove;
	}

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param services
	 * @param view
	 */
	public BeanEditorPresenter(IBeanEditorModel<T> model, IEditorServices<?> services, IBeanEditorView<T> view, ILayoutEditorModel layoutModel) {
		this(model, services, view);
		this.layoutEditorModel = layoutModel;
	}

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param services
	 * @param view
	 * @param readOnly
	 */
	public BeanEditorPresenter(IBeanEditorModel<T> model, IEditorServices<?> services, IBeanEditorView<T> view, ILayoutEditorModel layoutModel, boolean readOnly, boolean remove) {
		this(model, services, view, layoutModel);
		this.readOnly = readOnly;
		this.remove = remove;
	}

	@Override
	public IBeanEditorModel<T> getModel() {
		return model;
	}

	@Override
	public IBeanEditorView<T> getView() {
		return view;
	}

	@Override
	public List<IItemEditor> getData() {
		return model.getData();
	}

	public BeanEditorObserver<T> getObserver() {
		return observer;
	}

	public void setObserver(BeanEditorObserver<T> observer) {
		this.observer = observer;
	}

	/**
	 * Establecer el layout del editor para crear secciones o agrupaciones de campos
	 * @param layoutEditorModel
	 */
	public void setLayout(LayoutEditorModel layoutEditorModel) {
		this.layoutEditorModel = layoutEditorModel;
	}

}
