package com.javalego.ui.vaadin.view.editor.beans.filters;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.filter.IFilterParam;
import com.javalego.ui.mvp.editor.filter.params.IEditorFilterParamsView;
import com.javalego.ui.mvp.editor.impl.EditorModel;
import com.javalego.ui.mvp.editor.impl.EditorPresenter;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.tiles.TilesLayout;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.view.editor.EditorView;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

/**
 * Edición de un filtro que contiene parámetros de edición de valores que debe
 * complimentar el usuario.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class EditorFilterParamsView extends TilesLayout implements IEditorFilterParamsView {

	private static final long serialVersionUID = -6410506134696946038L;

	private EditorFilterParamsViewListener listener;

	@Override
	public void load() throws LocalizedException {

		if (getComponentCount() > 0)
			return;

		// Crear el model de datos del filtro a editar.
		EditorModel model = new EditorModel();
		for (IFilterParam param : listener.getParams()) {
			model.add(param.getDataEditor());
		}

		// Generar MVP
		final EditorPresenter p = new EditorPresenter(model, new EditorView().setToolbar(false));
		p.load();

		addComponent((Component) p.getView());

		// Ejecutar filtro
		ButtonExt b = new ButtonExt(LocaleEditor.FILTER).blue();
		b.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -502515039495911219L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					listener.execute(p.getFieldValues());
				} catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});

		addComponent(b);
	}

	@Override
	public void setListener(EditorFilterParamsViewListener listener) {
		this.listener = listener;
	}

}
