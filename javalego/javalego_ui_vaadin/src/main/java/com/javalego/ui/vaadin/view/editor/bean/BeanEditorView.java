package com.javalego.ui.vaadin.view.editor.bean;

import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.javalego.ui.mvp.editor.bean.IBeanEditorView;
import com.javalego.ui.vaadin.view.editor.BaseEditorView;

/**
 * Edición de los datos completos del modelo de forma secuencial.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class BeanEditorView<T> extends BaseEditorView implements IBeanEditorView<T> {

	private static final long serialVersionUID = -1487031828376557316L;

	/**
	 * Eventos a implementar en el presenter que serán invocados desde la vista.
	 */
	protected BeanEditorViewListener<T> listener;

	public BeanEditorView() {
	}
	
	/**
	 * Establecer 100% width el componente
	 * 
	 * @return
	 */
	@Override
	public BeanEditorView<T> fullWidth() {
		super.fullWidth();
		return this;
	}

	/**
	 * Establecer estilo Light para mostrar AbstractField sin contornos de
	 * edición.
	 * 
	 * @return
	 */
	@Override
	public BeanEditorView<T> light() {
		super.light();
		return this;
	}

	/**
	 * Incluir o no incluir la barra de herramientas
	 * 
	 * @param toolbar
	 * @return
	 */
	@Override
	public BeanEditorView<T> setToolbar(boolean toolbar) {
		super.setToolbar(toolbar);
		return this;
	}
	
	@Override
	public void setListener(BeanEditorViewListener<T> listener) {
		this.listener = listener;
	}

	@Override
	public IEditorViewListener getListener() {
		return listener;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void setListener(EditorViewListener listener) {
		if (listener instanceof BeanEditorViewListener) {
			this.listener = (BeanEditorViewListener<T>)listener;
		}
	}
}
