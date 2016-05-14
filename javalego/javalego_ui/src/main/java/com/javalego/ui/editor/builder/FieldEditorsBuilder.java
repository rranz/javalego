package com.javalego.ui.editor.builder;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.impl.DataBean;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.editor.IEditorModel;
import com.javalego.ui.mvp.editor.IEditorView;
import com.javalego.ui.mvp.editor.impl.BaseEditorPresenter.EditorListener;
import com.javalego.ui.mvp.editor.impl.EditorPresenter;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorModel;
import com.javalego.ui.mvp.editor.services.IEditorServices;

/**
 * Factoría de editores.
 * 
 * @author ROBERTO RANZ
 *
 */
public final class FieldEditorsBuilder {

	private FieldEditorsBuilder() {
	}

	/**
	 * Editor de un modelo de campos
	 * 
	 * @param model
	 * @param services
	 * @param bean
	 * @param list
	 * @param layoutEditorModel
	 * @param readOnly
	 * @param remove
	 * @param listener
	 * @param view
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static EditorPresenter getEditor(IEditorModel model, IEditorServices services, Object bean, Collection<FieldModel> list, ILayoutEditorModel layoutEditorModel, boolean readOnly,
			boolean remove, EditorListener listener, IEditorView view) {

		IDataBean databean = new DataBean(bean);

		if (list != null) {
			for (FieldModel field : list) {
				databean.addField(field);
			}
		}
		model.add(databean);

		// Creación del presenter con la configuración definida en el editor de
		// beans (readOnly y layout).
		EditorPresenter p = new EditorPresenter(model, services, view, layoutEditorModel, readOnly, remove);

		if (listener != null) {
			p.setEditorListener(listener);
		}

		return p;
	}

}
