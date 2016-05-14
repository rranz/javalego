package com.javalego.ui.mvp.editor.impl;

import java.util.List;

import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.IEditorModel;
import com.javalego.ui.mvp.editor.IEditorView;
import com.javalego.ui.mvp.editor.IEditorView.EditorViewListener;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorModel;
import com.javalego.ui.mvp.editor.services.IEditorServices;

/**
 * Presenter Editor de Datos del modelo
 * 
 * @author ROBERTO RANZ
 */
public class EditorPresenter extends BaseEditorPresenter implements EditorViewListener {

	private static final long serialVersionUID = -4682328076782006740L;

	/**
	 * Modelo
	 */
	protected IEditorModel model;

	/**
	 * Vista
	 */
	protected IEditorView view;

	/**
	 * Constructor
	 * @param model
	 * @param services
	 * @param view
	 */
	public EditorPresenter(IEditorModel model, IEditorServices<?> services, IEditorView view) {

		this.model = model;
		this.services = services;
		this.view = view;
		view.setListener(this);
		if (services != null) {
			addValueChangeListener(services.getValueChangeListener());
		}
	}

	/**
	 * Constructor
	 * @param model
	 * @param view
	 */
	public EditorPresenter(IEditorModel model, IEditorView view) {

		this.model = model;
		this.view = view;
		view.setListener(this);
		if (services != null) {
			addValueChangeListener(services.getValueChangeListener());
		}
	}

	/**
	 * Constructor
	 * @param model
	 * @param services
	 * @param view
	 * @param readOnly
	 * @param remove
	 */
	public EditorPresenter(IEditorModel model, IEditorServices<?> services, IEditorView view, boolean readOnly, boolean remove) {
		this(model, services, view);
		this.readOnly = readOnly;
		this.remove = remove;
	}

	/**
	 * Constructor
	 * @param model
	 * @param view
	 * @param readOnly
	 * @param remove
	 */
	public EditorPresenter(IEditorModel model, IEditorView view, boolean readOnly, boolean remove) {
		this(model, null, view);
		this.readOnly = readOnly;
		this.remove = remove;
	}

	/**
	 * Constructor
	 * @param model
	 * @param services
	 * @param view
	 * @param layoutModel
	 */
	public EditorPresenter(IEditorModel model, IEditorServices<?> services, IEditorView view, ILayoutEditorModel layoutModel) {
		this(model, services, view);
		this.layoutEditorModel = layoutModel;
	}

	/**
	 * Constructor
	 * @param model
	 * @param view
	 * @param layoutModel
	 */
	public EditorPresenter(IEditorModel model, IEditorView view, ILayoutEditorModel layoutModel) {
		this(model, null, view);
		this.layoutEditorModel = layoutModel;
	}
	
	/**
	 * Constructor
	 * @param model
	 * @param services
	 * @param view
	 * @param layoutModel
	 * @param readOnly
	 * @param remove
	 */
	public EditorPresenter(IEditorModel model, IEditorServices<?> services, IEditorView view, ILayoutEditorModel layoutModel, boolean readOnly, boolean remove) {
		this(model, services, view, layoutModel);
		this.readOnly = readOnly;
		this.remove = remove;
	}

	@Override
	public IEditorModel getModel() {
		return model;
	}
	
	@Override
	public IEditorView getView() {
		return view;
	}

	@Override
	public List<IItemEditor> getData() {
		return model.getData();
	}
}
