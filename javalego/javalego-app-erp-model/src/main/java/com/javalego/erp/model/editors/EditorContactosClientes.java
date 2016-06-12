package com.javalego.erp.model.editors;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.javalego.data.DataContext;
import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Cliente;
import com.javalego.erp.model.entity.ClienteContacto;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.editor.data.impl.ValueDataEditor;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.filter.impl.FilterParam;
import com.javalego.ui.filter.impl.FilterParamsService;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.nestedbean.imp.NestedBeanListValues;

/**
 * Formulario de mantenimiento de Contactos
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorContactosClientes extends BaseEditor<ClienteContacto> {

	private static final long serialVersionUID = -4732990898853569772L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorContactosClientes() {

		setTitle(Texts.CONTACTOS);
		setEditionTitle(Texts.CONTACTO);
		setIcon(Icons.CONTACTS);

		// Filtros

		// Filtro por localidad
		FilterParamsService filter = new FilterParamsService(Texts.LOCALIDAD);
		filter.addParam(LOCALIDAD, Texts.LOCALIDAD, ConditionType.STARTWITH);
		addFilterService(filter);

		// Filtro por email
		filter = new FilterParamsService(Texts.EMAIL);
		filter.addParam(EMAIL, Texts.EMAIL, ConditionType.CONTAINS);
		addFilterService(filter);

		// Filtro por empresa
		filter = new FilterParamsService(Texts.EMPRESA) {
			// Personalizamos la construcción de la sentencia sql en el caso de
			// que estemos trabajando sobre una base de datos SQLite NO JPA.
			@Override
			public void build(Map<String, ?> fieldValues) throws LocalizedException {

				if (getDataProvider() != null && DataContext.getCurrent().isSQLite()) {

					Object value = fieldValues.values().iterator().next();

					if (value != null) {

						Collection<Cliente> list = (Collection<Cliente>) getDataProvider().findAll(Cliente.class, "nombre like '" + value + "%'", null);

						if (list != null) {
							String where = "";
							for (Cliente e : list) {
								where += ("".equals(where) ? "" : ",") + e.getId();
							}
							statement = "empresa_id in(" + where + ")";
						}
						else {
							statement = "empresa_id = -1";
						}
					}
				}
				else {
					super.build(fieldValues);
				}

			}
		};
		filter.getParams().add(new FilterParam(new ValueDataEditor(EMPRESA_NOMBRE, Texts.EMPRESA, new String()), ConditionType.STARTWITH));
		addFilterService(filter);

		// Servicios del editor para beans nested relacionados.
		EditorServices<ClienteContacto> services = new EditorServices<ClienteContacto>();
		setEditorServices(services);

		// Nested bean Cliente
		services.addNestedFieldModel(EMPRESA_NOMBRE, new NestedBeanListValues<ClienteContacto, Cliente>() {
			@Override
			public void valueChangeEvent(ValueChangeEvent event) throws LocalizedException {

				Cliente e = null;

				if (getDataProvider() != null) {
					e = (Cliente) getDataProvider().find(Cliente.class, "nombre = '" + event.getValue() + "'");
				}
				else if (event.getValue() != null) {
					e = new Cliente();
					e.setNombre((String)event.getValue());
					e.setId(1L);
				}

				if (e != null) {
					event.getEditorRules().setValue(EMPRESA_ID, e.getId());
				}
				else {
					event.getEditorRules().setValue(EMPRESA_ID, null);
					event.getEditorRules().setValue(EMPRESA_NOMBRE, null);
				}

			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public List getKeys(String constraint) throws LocalizedException {

				return getDataProvider().propertyValues(Cliente.class, NOMBRE, constraint != null ? "nombre like'" + constraint + "%'" : null, NOMBRE);
			}

			@Override
			public boolean isAutoComplete() {
				return true;
			}
		});
	}

	@Override
	public Collection<ClienteContacto> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getClienteContactos(filter, order);
	}

	@Override
	public Collection<FieldModel> getFieldModels() {

		return ModeloContacto.getFieldModels();
	}

	@Override
	public ClienteContacto getNewBean() {

		ClienteContacto c = new ClienteContacto();
		// País por defecto obtenido de la localización de la sesión de usuario.
		c.setPais(UIContext.getCurrent().getCountry().toUpperCase());
		return c;
	}

	@Override
	public Class<ClienteContacto> getBeanClass() {
		return ClienteContacto.class;
	}

}
