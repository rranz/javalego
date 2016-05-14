package com.javalego.erp.model.editors;

import java.util.ArrayList;
import java.util.Collection;

import com.javalego.erp.model.Texts;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.CountryFieldModel;
import com.javalego.ui.field.impl.EmailFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.PhoneNumberFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;

/**
 * Modelo de datos de empresas aplicado a Clientes y Proveedores. Reutilización.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ModeloContacto implements ModeloCampos {

	private static Collection<FieldModel> modelFields;

	/**
	 * Campos
	 * 
	 * @return
	 */
	public synchronized static Collection<FieldModel> getFieldModels() {

		if (modelFields != null) {
			return modelFields;
		}

		modelFields = new ArrayList<FieldModel>();

		modelFields.add(new TextFieldModel(EMPRESA_NOMBRE, Texts.EMPRESA, true).setColumns(30).setRequired(true));
		modelFields.add(new TextFieldModel(NOMBRE, Texts.NOMBRE, true).setMaxSize(100).setColumns(30).setRequired(true));
		modelFields.add(new PhoneNumberFieldModel(TELEFONO, Texts.TELEFONO).setMaxSize(15));
		modelFields.add(new EmailFieldModel(EMAIL, Texts.EMAIL).setMaxSize(130).setColumns(40));
		modelFields.add(new CountryFieldModel(PAIS, Texts.PAIS).setRequired(true));
		modelFields.add(new TextFieldModel(PROVINCIA, Texts.PROVINCIA).setMaxSize(100));
		modelFields.add(new TextFieldModel(LOCALIDAD, Texts.LOCALIDAD).setMaxSize(100));
		modelFields.add(new TextFieldModel(CODIGO_POSTAL, Texts.CODIGOPOSTAL).setMaxSize(10));
		modelFields.add(new TextFieldModel(DOMICILIO, Texts.DIRECCION));
		modelFields.add(new ImageFieldModel(FOTO, Texts.FOTO));

		return modelFields;
	}

}
