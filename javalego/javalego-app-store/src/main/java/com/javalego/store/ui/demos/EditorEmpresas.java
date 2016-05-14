package com.javalego.store.ui.demos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.data.DataContext;
import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.office.export.ExportBeans;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.actions.IActionBeanEditor;
import com.javalego.ui.actions.IActionBeansEditor;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.DateFieldModel;
import com.javalego.ui.field.impl.EnumFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.filter.impl.FilterParam;
import com.javalego.ui.filter.impl.FilterParamsService;
import com.javalego.ui.filter.impl.FilterService;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;
import com.javalego.ui.mvp.editor.services.IEditorServices;
import com.javalego.ui.vaadin.view.actions.ExportServices;
import com.javalego.ui.vaadin.view.actions.ExportServices.ExportFormat;

import entities.Empresa;

/**
 * Editor de mantenimiento de Empresas
 * 
 * @author ROBERTO RANZ
 *
 */
public class EditorEmpresas implements IBeansEditorModel<Empresa> {

	private static final long serialVersionUID = -2670951549024358151L;
	private ArrayList<FieldModel> modelFields;
	private ArrayList<IFilterService> filters;

	@Override
	public Key getDescription() {
		return null;
	}
	
	@Override
	public Key getTitle() {
		return ModelLocaleEmpresas.EMPRESAS;
	}

	@Override
	public Key getEditionTitle() {
		return ModelLocaleEmpresas.EMPRESA;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Empresa> getBeans(String filter, String order) throws LocalizedException {
		return getDataProvider() != null ? (Collection<Empresa>) getDataProvider().getList(Empresa.class) : null;
	}

	@Override
	public Class<Empresa> getBeanClass() {
		return Empresa.class;
	}

	@Override
	public Collection<FieldModel> getFieldModels() {
		
		if (modelFields != null) {
			return modelFields;
		}
		
		modelFields = new ArrayList<FieldModel>();
		
		modelFields.add(new TextFieldModel("nombre", LocaleStore.NAME, true).setMaxSize(30).setColumns(20).setRequired(true));
		modelFields.add(new TextFieldModel("cif", Textos.CIF, true).setMaxSize(14).setColumns(10).setRequired(true));
		modelFields.add(new DateFieldModel("fecha_creacion", Textos.FECHACREACION));
		modelFields.add(new TextFieldModel("razon_social", Textos.RAZONSOCIAL, true).setMaxSize(30).setRequired(true));
		modelFields.add(new EnumFieldModel("localidad", Textos.LOCALIDAD, new String[] { "MADRID", "BARCELONA", "SEVILLA", "MALLORCA", "VALENCIA", "TOLEDO", "AVILA"} ));
		modelFields.add(new TextFieldModel("representante", Textos.REPRESENTANTE).setMaxSize(30).setColumns(20));
		
		return modelFields;
	}

	@Override
	public Collection<IFilterService> getFilters() {
		
		if (filters != null) {
			return filters;
		}

		filters = new ArrayList<IFilterService>();
		
		// Filtro basado en una sentencia sql
		filters.add(new FilterService(Textos.FILTRAR_MADRID).setStatement("localidad = 'MADRID'").setNaturalStatement("Localidad de Madrid"));

		// Filtro basado en campos del editor. Se crea el presenter para la edición de los campos que constituyen los parámetros del filtro
		filters.add(new FilterParamsService(Textos.LOCALIDAD, new FilterParam("localidad", Textos.LOCALIDAD, ConditionType.STARTWITH)));
		
		return filters;
	}

	@Override
	public Empresa getNewBean() {
		return new Empresa();
	}

	@Override
	public Collection<IActionBeansEditor> getListActions() {
		
		List<IActionBeansEditor> actions = new ArrayList<IActionBeansEditor>();
		
		actions.add(new ExportServices(new ExportBeans(), ExportFormat.EXCEL));
		
		return actions;
	}

	@Override
	public Collection<IFormatBeansView<Empresa>> getFormatViews() {
		return null;
	}

	@Override
	public Collection<IBeansEditorModel<?>> getBeanDetail() {
		return null;
	}

	@Override
	public DataProvider<Entity> getDataProvider() {
		return DataContext.getProvider();
	}

	@Override
	public IEditorServices<?> getEditorServices() {
		return null;
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IActionBeanEditor> getEditingActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LayoutEditorModel getLayoutEditorModel(IBeanEditorModel<Empresa> model) {
		LayoutEditorModel em = new LayoutEditorModel(model);
		em.addChildLayout(Textos.DATOS_GENERALES, Colors.ORANGE, "nombre", "razon_social", "cif");
		em.addChildLayout(Textos.DATOS_ADICIONALES, Colors.INDIGO, "fecha_creacion", "localidad", "representante");
		return em;
	}

	@Override
	public ICustomLayoutEditor<Empresa> getCustomLayoutEditor(IBeanEditorModel<Empresa> model) {
		// TODO Auto-generated method stub
		return null;
	}

}
