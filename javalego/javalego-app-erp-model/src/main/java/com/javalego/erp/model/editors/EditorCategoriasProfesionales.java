package com.javalego.erp.model.editors;

import java.util.Collection;

import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.CategoriaProfesional;
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
public class EditorCategoriasProfesionales extends BaseEditor<CategoriaProfesional> {

	private static final long serialVersionUID = -8261761191179939832L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorCategoriasProfesionales() {

		setTitle(Texts.CATEGORIAS_PROFESIONALES);
		setIcon(Icons.CATEGORIES);
		setEditionTitle(Texts.CATEGORIA_PROFESIONAL);

		// Campos
		addFieldModel(new TextFieldModel(NOMBRE, Texts.NOMBRE, true).setMaxSize(100).setColumns(20).setRequired(true));
		addFieldModel(new TextFieldModel(ANOTACIONES, Texts.DESCRIPCION).setMaxSize(200).setColumns(50));

		// Vista de datos del bean
		addFormatView(new ListBeansViewAdapter<CategoriaProfesional>(UIContext.getText(Texts.NOMBRE)) {
			@Override
			public String toHtml(CategoriaProfesional bean) {
				return "<h2>" + bean.getNombre() + "</h2>" + (bean.getAnotaciones() == null ? "" : "<i>" + bean.getAnotaciones() + "</i>");
			}
		});

	}

	@Override
	public Collection<CategoriaProfesional> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getCategoriasProfesionales(filter, order);
	}

	@Override
	public Class<CategoriaProfesional> getBeanClass() {

		return CategoriaProfesional.class;
	}

}
