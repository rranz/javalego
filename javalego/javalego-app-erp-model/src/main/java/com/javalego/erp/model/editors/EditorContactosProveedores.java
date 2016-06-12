package com.javalego.erp.model.editors;

import java.util.Collection;
import java.util.Map;

import com.javalego.data.DataContext;
import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Proveedor;
import com.javalego.erp.model.entity.ProveedorContacto;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.filter.impl.FilterParamsService;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.nestedbean.imp.NestedBeanListValues;

/**
 * Formulario de mantenimiento de Contactos de Proveedores
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorContactosProveedores extends BaseEditor<ProveedorContacto> {

	private static final long serialVersionUID = -841645223077422987L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorContactosProveedores() {

		setTitle(Texts.CONTACTOS);
		setEditionTitle(Texts.CONTACTO);
		setIcon(Icons.CONTACTS);

		// Servicios del editor para beans nested relacionados.
		EditorServices<ProveedorContacto> services = new EditorServices<ProveedorContacto>();
		setEditorServices(services);

		services.addNestedFieldModel(EMPRESA_NOMBRE, new NestedBeanListValues<ProveedorContacto, Proveedor>() {
			@Override
			public void valueChangeEvent(ValueChangeEvent event) throws LocalizedException {
				
				if (getDataProvider() != null) {
					
					Proveedor e = (Proveedor) getDataProvider().find(Proveedor.class, "nombre = '" + event.getValue() + "'");
					
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
				
				return DataContext.getProvider() == null ? null : (Collection<String>) DataContext.getProvider().propertyValues(Proveedor.class, NOMBRE,
						constraint != null ? "nombre like'" + constraint + "%'" : null, NOMBRE);
			}

			@Override
			public boolean isAutoComplete() {
				return true;
			}
		});

		// Filtros

		// Filtro por empresa
		FilterParamsService filter = new FilterParamsService(Texts.EMPRESA) {
			// Personalizamos la construcción de la sentencia sql en el caso de
			// que estemos trabajando sobre una base de datos SQLite NO JPA.
			@Override
			public void build(Map<String, ?> fieldValues) throws LocalizedException {

				if (getDataProvider() != null && DataContext.getCurrent().isSQLite()) {

					Object value = fieldValues.values().iterator().next();

					if (value != null) {

						Collection<Proveedor> list = (Collection<Proveedor>) DataContext.getProvider().findAll(Proveedor.class, "nombre like '" + value + "%'", null);

						if (list != null) {
							String where = "";
							for (Proveedor e : list) {
								where += ("".equals(where) ? "" : ",") + e.getId();
							}
							statement = "empresa_id in(" + where + ")";
						} else {
							statement = "empresa_id = -1";
						}
					}
				} else {
					super.build(fieldValues);
				}

			}
		};

		filter.addParam(EMPRESA_NOMBRE, Texts.EMPRESA, new String(), ConditionType.STARTWITH);
		addFilterService(filter);

		// Filtro por localidad
		filter = new FilterParamsService(Texts.LOCALIDAD);
		filter.addParam(LOCALIDAD, Texts.LOCALIDAD, new String(), ConditionType.STARTWITH);
		addFilterService(filter);

		// Filtro por email
		filter = new FilterParamsService(Texts.EMAIL);
		filter.addParam(EMAIL, Texts.EMAIL, new String(), ConditionType.CONTAINS);
		addFilterService(filter);
	}

	@Override
	public Collection<ProveedorContacto> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getProveedorContactos(filter, order);
	}

	@Override
	public Collection<FieldModel> getFieldModels() {

		return ModeloContacto.getFieldModels();
	}

	@Override
	public ProveedorContacto getNewBean() {

		ProveedorContacto c = new ProveedorContacto();
		// País por defecto obtenido de la localización de la sesión de usuario.
		c.setPais(UIContext.getCurrent().getCountry().toUpperCase());
		return c;
	}

	@Override
	public Class<ProveedorContacto> getBeanClass() {
		return ProveedorContacto.class;
	}

}
