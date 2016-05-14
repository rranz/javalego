package com.javalego.erp.model.editors;

import java.util.Collection;

import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Categoria;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.mvp.beans.view.list.adapter.ListBeansViewAdapter;

/**
 * Formulario de mantenimiento de Categorias
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorCategorias extends BaseEditor<Categoria> {

	private static final long serialVersionUID = -4577919263523525808L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorCategorias() {

		addBeanDetail(new EditorProductos());

		setTitle(Texts.CATEGORIAS);
		setIcon(Icons.COMPONENTS);
		setEditionTitle(Texts.CATEGORIA);

		// Campos
		addFieldModel(new TextFieldModel(NOMBRE, Texts.NOMBRE, true).setMaxSize(100).setColumns(20).setRequired(true));
		addFieldModel(new TextFieldModel(ANOTACIONES, Texts.DESCRIPCION).setMaxSize(200).setColumns(50));

		// Vistas Html de los datos del bean (default grid).
		addFormatView(new ListBeansViewAdapter<Categoria>(UIContext.getText(Texts.NOMBRE)) {
			@Override
			public String toHtml(Categoria bean) {
				return "<h2>" + bean.getNombre() + "</h2>" + (bean.getAnotaciones() == null ? "" : "<i>" + bean.getAnotaciones() + "</i>");
			}
		});
	}

	@Override
	public Collection<Categoria> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getCategorias(filter, order);
	}

	@Override
	public Class<Categoria> getBeanClass() {
		return Categoria.class;
	}

}
