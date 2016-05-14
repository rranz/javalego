package com.javalego.ui.editor.builder;

import java.util.Collection;

import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.editor.bean.IBeanEditorView;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorModel;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorPresenter;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;

/**
 * Factoría de editores de tipo beans.
 * 
 * @author ROBERTO RANZ
 *
 */
public final class BeanEditorsBuilder<T> {

	public BeanEditorsBuilder() {
	}

	/**
	 * Obtener el editor del bean a partir de la definición del model de editor
	 * de beans.
	 * 
	 * @param beansmodel
	 *            Modelo de editor de Beans.
	 * @param bean
	 *            Bean a editar
	 * @param readOnly
	 *            sólo lectura
	 * @param insert
	 *            estado inserción.
	 * @param view
	 *            Vista de editor utilizada para la edición de sus campos.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BeanEditorPresenter<T> getEditor(IBeansEditorModel<T> beansmodel, IBeanEditorView<T> view, T bean, boolean readOnly, boolean insert) {

		BeanEditorModel model = new BeanEditorModel(bean, beansmodel.getFieldModels());

		// Establecer el modelo de editores detalle que tiene asociados.
		model.setDetail(beansmodel.getBeanDetail());

		BeanEditorPresenter<T> p = null;

		// Obtener las diferentes posibles alternativas de edición: layout
		// basada en grupos de datos a editar o una vista personalizada.
		LayoutEditorModel layout = beansmodel.getLayoutEditorModel(model);
		
		// Layout personalizado (opcional)
		ICustomLayoutEditor<?> customLayout = beansmodel.getCustomLayoutEditor(model);

		if (customLayout != null) {
			p = new BeanEditorPresenter<T>(model, beansmodel.getEditorServices(), view, readOnly, !insert);
			p.setCustomLayout(customLayout);
		}
		else if (layout != null) {
			p = new BeanEditorPresenter<T>(model, beansmodel.getEditorServices(), view, layout, readOnly, !insert);
		}
		else {
			p = new BeanEditorPresenter<T>(model, beansmodel.getEditorServices(), view, readOnly, !insert);
		}
		return p;

	}

	/**
	 * Obtener un editor de bean (edición)
	 * 
	 * @param fields
	 * @param view
	 * @param bean
	 */
	public BeanEditorPresenter<T> getEditor(Collection<FieldModel> fields, IBeanEditorView<T> view, T bean) {
		
		return getEditor(fields, view, bean, false, false);
	}
	
	/**
	 * Obtener un editor de bean (edición)
	 * 
	 * @param fields
	 * @param view
	 * @param bean
	 * @param readOnly
	 */
	public BeanEditorPresenter<T> getEditor(Collection<FieldModel> fields, IBeanEditorView<T> view, T bean, boolean readOnly) {
		
		return getEditor(fields, view, bean, readOnly, false);
	}
	
	/**
	 * Obtener un editor de bean (edición)
	 * 
	 * @param fields
	 * @param view
	 * @param bean
	 * @param readOnly
	 * @param remove Incluir botón de eliminar
	 */
	public BeanEditorPresenter<T> getEditor(Collection<FieldModel> fields, IBeanEditorView<T> view, T bean, boolean readOnly, boolean remove) {
		
		BeanEditorModel<T> model = new BeanEditorModel<T>(bean, fields);
		
		return new BeanEditorPresenter<T>(model, null, view, readOnly, remove);
	}

	/**
	 * Obtener un editor de bean (edición)
	 * 
	 * @param fields
	 * @param view
	 * @param bean
	 * @param layout
	 * @param readOnly
	 * @param remove Incluir botón de eliminar
	 */
	public BeanEditorPresenter<T> getEditor(Collection<FieldModel> fields, IBeanEditorView<T> view, T bean, ILayoutEditorModel layout, boolean readOnly, boolean remove) {
		
		BeanEditorModel<T> model = new BeanEditorModel<T>(bean, fields);
		
		return new BeanEditorPresenter<T>(model, null, view, layout, readOnly, remove);
	}

}
