package com.javalego.erp.model.editors;

import java.util.Collection;

import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Departamento;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.mvp.beans.view.list.adapter.ListBeansViewAdapter;

/**
 * Formulario de mantenimiento de Departamentos
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorDepartamento extends BaseEditor<Departamento> {

	private static final long serialVersionUID = -7961233976875739670L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorDepartamento() {

		setTitle(Texts.DEPARTAMENTOS);
		setIcon(Icons.TEAMWORK);
		setEditionTitle(Texts.DEPARTAMENTO);

		// Campos
		addFieldModel(new TextFieldModel(NOMBRE, Texts.NOMBRE, true).setMaxSize(100).setColumns(20).setRequired(true));
		addFieldModel(new TextFieldModel(ANOTACIONES, Texts.DESCRIPCION).setMaxSize(200).setColumns(50));

		// Detalle
		addBeanDetail(new EditorEmpleados());

		// Vistas de datos del bean
		addFormatView(new ListBeansViewAdapter<Departamento>(UIContext.getText(Texts.NOMBRE)) {
			@Override
			public String toHtml(Departamento bean) {
				return "<h2>" + bean.getNombre() + "</h2>" + (bean.getAnotaciones() == null ? "" : "<i>" + bean.getAnotaciones() + "</i>");
			}
		});

	}

	@Override
	public Collection<Departamento> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getDepartamentos(filter, order);
	}

	@Override
	public Class<Departamento> getBeanClass() {
		return Departamento.class;
	}

}
