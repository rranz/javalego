package com.javalego.erp.model.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.erp.model.Texts;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.BooleanFieldModel;
import com.javalego.ui.field.impl.CountryFieldModel;
import com.javalego.ui.field.impl.DateFieldModel;
import com.javalego.ui.field.impl.EmailFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.PhoneNumberFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.field.impl.UrlFieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.filter.impl.FilterParam;
import com.javalego.ui.filter.impl.FilterParamsService;
import com.javalego.ui.filter.impl.FilterService;

/**
 * Modelo de datos de empresas aplicado a Clientes y Proveedores. Reutilización.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ModeloEmpresa implements ModeloCampos {

	private static Collection<FieldModel> modelFields;

	private static List<IFilterService> filters;

	/**
	 * Campos
	 * 
	 * @return
	 */
	public static synchronized Collection<FieldModel> getFieldModels() {

		if (modelFields != null) {
			return modelFields;
		}

		modelFields = new ArrayList<FieldModel>();

		modelFields.add(new TextFieldModel(NOMBRE, Texts.NOMBRE, true).setMaxSize(100).setColumns(20).setRequired(true));
		modelFields.add(new TextFieldModel(CIF, Texts.CIF, true).setMaxSize(20).setRequired(true));
		modelFields.add(new TextFieldModel(RAZON_SOCIAL, Texts.RAZONSOCIAL, true).setMaxSize(100).setRequired(true));
		modelFields.add(new CountryFieldModel(PAIS, Texts.PAIS).setRequired(true));
		modelFields.add(new TextFieldModel(PROVINCIA, Texts.PROVINCIA).setMaxSize(100));
		modelFields.add(new TextFieldModel(LOCALIDAD, Texts.LOCALIDAD).setMaxSize(100));
		modelFields.add(new TextFieldModel(CODIGO_POSTAL, Texts.CODIGOPOSTAL).setMaxSize(10));
		modelFields.add(new TextFieldModel(DOMICILIO, Texts.DIRECCION));
		modelFields.add(new TextFieldModel(REPRESENTANTE, Texts.REPRESENTANTE).setMaxSize(100).setColumns(30));
		modelFields.add(new DateFieldModel(FECHA_CREACION, Texts.FECHACREACION));
		modelFields.add(new BooleanFieldModel(PYME, Texts.PYME));
		modelFields.add(new UrlFieldModel(URL, Texts.URL).setMaxSize(200).setColumns(30));
		modelFields.add(new PhoneNumberFieldModel(TELEFONO, Texts.TELEFONO));
		modelFields.add(new EmailFieldModel(EMAIL, Texts.EMAIL));
		modelFields.add(new ImageFieldModel(LOGO, Texts.LOGO));

		return modelFields;
	}

	/**
	 * Filtros
	 * 
	 * @return
	 */
	public static Collection<IFilterService> getFilters() {

		if (filters != null) {
			return filters;
		}

		filters = new ArrayList<IFilterService>();

		// Filtros basado en campos del editor. Se crea el presenter para la
		// edición de los campos que constituyen los parámetros del filtro
		filters.add(new FilterParamsService(Texts.LOCALIDAD, new FilterParam(LOCALIDAD, Texts.LOCALIDAD, ConditionType.STARTWITH)));
		filters.add(new FilterParamsService(Texts.PROVINCIA, new FilterParam(PROVINCIA, Texts.PROVINCIA, ConditionType.CONTAINS)));
		filters.add(new FilterParamsService(Texts.CIF, new FilterParam(CIF, Texts.CIF, ConditionType.CONTAINS)));
		filters.add(new FilterParamsService(Texts.EMAIL, new FilterParam(EMAIL, Texts.CIF, ConditionType.CONTAINS)));

		// Filtro basado en una sentencia sql
		filters.add(new FilterService(Texts.FILTRAR_MADRID).setStatement("localidad = 'MADRID'"));
		filters.add(new FilterService(Texts.PYME).setStatement(PYME + " = 1"));

		return filters;
	}

}
