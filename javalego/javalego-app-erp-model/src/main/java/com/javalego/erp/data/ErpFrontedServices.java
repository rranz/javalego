package com.javalego.erp.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.data.BusinessService;
import com.javalego.erp.model.editors.ModeloCampos;
import com.javalego.erp.model.editors.ModeloContacto;
import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.factory.FieldModelFactory;
import com.javalego.ui.field.frontend.FieldFrontEnd;

/**
 * Servicios Frontend
 * <p>
 * Proporciona servicios al fronted del modelo de datos.
 * <p>
 * Ej.: lista del modelo de campos de una entidad en un determinado idioma.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ErpFrontedServices implements BusinessService, ModeloCampos {

	/**
	 * Campos de un contacto
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	public Collection<FieldFrontEnd> getContactoFields() throws LocalizedException {

		List<FieldFrontEnd> list = new ArrayList<FieldFrontEnd>();
		for (FieldModel item : ModeloContacto.getFieldModels()) {
			list.add(FieldModelFactory.getFieldFrontEnd(item));
		}
		return list;
	}

}
