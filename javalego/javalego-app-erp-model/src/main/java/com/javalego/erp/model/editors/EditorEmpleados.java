package com.javalego.erp.model.editors;

import java.util.Collection;
import java.util.List;

import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.CategoriaProfesional;
import com.javalego.erp.model.entity.Departamento;
import com.javalego.erp.model.entity.Empleado;
import com.javalego.erp.model.entity.Empresa;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.field.impl.DateFieldModel;
import com.javalego.ui.field.impl.EmailFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.PhoneNumberFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.filter.impl.FilterParam;
import com.javalego.ui.filter.impl.FilterParamsService;
import com.javalego.ui.mvp.beans.view.list.adapter.ListBeansViewAdapter;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.nestedbean.imp.NestedBeanListValues;
import com.javalego.util.StringUtils;

/**
 * Formulario de mantenimiento de Tarifas de Productos
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorEmpleados extends BaseEditor<Empleado> {

	private static final long serialVersionUID = -8571636754447755369L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorEmpleados() {

		setTitle(Texts.EMPLEADOS);
		setIcon(Icons.PEOPLE);
		setEditionTitle(Texts.EMPLEADO);

		// Campos
		addFieldModel(new TextFieldModel(EMPRESA_NOMBRE, Texts.EMPRESA, true).setColumns(30).setRequired(true));
		addFieldModel(new TextFieldModel(DEPARTAMENTO_NOMBRE, Texts.DEPARTAMENTO, true).setColumns(30).setRequired(true));
		addFieldModel(new TextFieldModel(CATEGORIA_NOMBRE, Texts.CATEGORIA, true).setColumns(30).setRequired(true));

		addFieldModel(new TextFieldModel(NOMBRE, Texts.NOMBRE).setColumns(30).setRequired(true));
		addFieldModel(new TextFieldModel(NIF, Texts.NIF).setMaxSize(20));
		addFieldModel(new DateFieldModel(ALTA, Texts.FECHA_ALTA).setReadOnly(true));
		addFieldModel(new PhoneNumberFieldModel(TELEFONO, Texts.TELEFONO).setMaxSize(15));
		addFieldModel(new PhoneNumberFieldModel(MOVIL, Texts.MOVIL).setMaxSize(15));
		addFieldModel(new EmailFieldModel(EMAIL, Texts.EMAIL).setMaxSize(130).setColumns(40));
		addFieldModel(new TextFieldModel(DOMICILIO, Texts.DIRECCION));
		addFieldModel(new ImageFieldModel(FOTO, Texts.FOTO));

		// Filtros basado en campos del editor. Se crea el presenter para la
		// edición de los campos que constituyen los parámetros del filtro
		addFilterService(new FilterParamsService(Texts.NOMBRE, new FilterParam(NOMBRE, Texts.NOMBRE, ConditionType.CONTAINS)));
		addFilterService(new FilterParamsService(Texts.DIRECCION, new FilterParam(DOMICILIO, Texts.DIRECCION, ConditionType.CONTAINS)));
		addFilterService(new FilterParamsService(Texts.NIF, new FilterParam(NIF, Texts.NIF, ConditionType.CONTAINS)));
		addFilterService(new FilterParamsService(Texts.EMAIL, new FilterParam(EMAIL, Texts.EMAIL, ConditionType.CONTAINS)));

		// Servicios del editor para beans nested relacionados.
		EditorServices<Empleado> services = new EditorServices<Empleado>();
		setEditorServices(services);
		
		services.addNestedFieldModel(DEPARTAMENTO_NOMBRE, new NestedBeanListValues<Empleado, Departamento>() {
			@Override
			public void valueChangeEvent(ValueChangeEvent event) throws LocalizedException {

				if (getDataProvider() != null) {
					Departamento e = (Departamento) getDataProvider().find(Departamento.class, "nombre = '" + event.getValue() + "'");
					if (e != null) {
						event.getEditorRules().setValue(DEPARTAMENTO_ID, e.getId());
					} else {
						event.getEditorRules().setValue(DEPARTAMENTO_ID, null);
						event.getEditorRules().setValue(DEPARTAMENTO_NOMBRE, null);
					}
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public Collection<String> getKeys(String constraint) throws LocalizedException {
				return getDataProvider() == null ? null : (Collection<String>) getDataProvider().propertyValues(Departamento.class, NOMBRE,
						constraint != null ? "nombre like'" + constraint + "%'" : null, NOMBRE);
			}

			@Override
			public boolean isAutoComplete() {
				return false;
			}
		});

		services.addNestedFieldModel(CATEGORIA_NOMBRE, new NestedBeanListValues<Empleado, CategoriaProfesional>() {
			@Override
			public void valueChangeEvent(ValueChangeEvent event) throws LocalizedException {

				if (getDataProvider() != null) {

					CategoriaProfesional e = (CategoriaProfesional) getDataProvider().find(CategoriaProfesional.class, "nombre = '" + event.getValue() + "'");

					if (e != null) {
						event.getEditorRules().setValue(CATEGORIA_ID, e.getId());
					} else {
						event.getEditorRules().setValue(CATEGORIA_ID, null);
						event.getEditorRules().setValue(CATEGORIA_NOMBRE, null);
					}
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public Collection<String> getKeys(String constraint) throws LocalizedException {

				return getDataProvider() == null ? null : (Collection<String>) getDataProvider().propertyValues(CategoriaProfesional.class, NOMBRE,
						constraint != null ? "nombre like'" + constraint + "%'" : null, NOMBRE);
			}

			@Override
			public boolean isAutoComplete() {
				return false;
			}
		});

		services.addNestedFieldModel(EMPRESA_NOMBRE, new NestedBeanListValues<Empleado, Empresa>() {
			@Override
			public void valueChangeEvent(ValueChangeEvent event) throws LocalizedException {

				if (getDataProvider() != null) {

					Empresa e = (Empresa) getDataProvider().find(Empresa.class, "nombre = '" + event.getValue() + "'");

					if (e != null) {
						event.getEditorRules().setValue(EMPRESA_ID, e.getId());
					} else {
						event.getEditorRules().setValue(EMPRESA_ID, null);
						event.getEditorRules().setValue(EMPRESA_NOMBRE, null);
					}
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public Collection<String> getKeys(String constraint) throws LocalizedException {

				return getDataProvider() == null ? null : (Collection<String>) getDataProvider().propertyValues(Empresa.class, NOMBRE, constraint != null ? "nombre like'" + constraint + "%'" : null,
						NOMBRE);
			}

			@Override
			public boolean isAutoComplete() {
				return false;
			}
		});

		// Vistas de datos del bean
		addFormatView(new ListBeansViewAdapter<Empleado>(UIContext.getText(Texts.NOMBRE) + " + " + UIContext.getText(Texts.DEPARTAMENTO), FOTO) {
			@Override
			public String toHtml(Empleado bean) {
				return "<h2>" + bean.getNombre() + "</h2>" + (bean.getDepartamento() != null ? bean.getDepartamento().getNombre() : "") + "<br>" + (bean.getEmpresa() != null ? bean.getEmpresa().getNombre() : "");
			}
		});

	}

	@Override
	public Collection<Empleado> getBeans(String filter, String order) throws LocalizedException {

		Collection<Empleado> list = getDataServices().getEmpleados(filter, order);

		if (order != null && list != null && list.size() > 1) {
			StringUtils.sortList((List<?>) list, NOMBRE);
		}
		return list;
	}

	@Override
	public Class<Empleado> getBeanClass() {
		return Empleado.class;
	}

}
