package com.javalego.ui.vaadin.view.editor;

import com.javalego.ui.mvp.editor.IEditorViewListener;

/**
 * Edición de los datos completos del modelo de forma secuencial.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class EditorView extends BaseEditorView {

	private static final long serialVersionUID = -1487031828376557316L;

	/**
	 * Eventos a implementar en el presenter que serán invocados desde la vista.
	 */
	protected EditorViewListener listener;

	public EditorView() {
	}
	

	/**
	 * Establecer 100% width el componente
	 * 
	 * @return
	 */
	@Override
	public EditorView fullWidth() {
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
	public EditorView light() {
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
	public EditorView setToolbar(boolean toolbar) {
		super.setToolbar(toolbar);
		return this;
	}
	
	@Override
	public void setListener(EditorViewListener listener) {
		this.listener = listener;
	}

	@Override
	public IEditorViewListener getListener() {
		return listener;
	}
}
