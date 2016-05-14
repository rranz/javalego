package com.javalego.erp.model.editors;

import java.util.Collection;

import com.javalego.data.DataContext;
import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Proveedor;
import com.javalego.erp.model.entity.ProveedorContacto;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.mvp.beans.view.list.adapter.ListBeansViewAdapter;
import com.javalego.ui.mvp.editor.services.IBeanEditorEvents;

/**
 * Formulario de mantenimiento de Proveedores
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorProveedores extends BaseEditor<Proveedor> {

	private static final long serialVersionUID = -1340632691365317111L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorProveedores() {

		setTitle(Texts.PROVEEDORES);
		setIcon(Icons.COMPANIES);
		setEditionTitle(Texts.PROVEEDOR);

		// Detalle
		addBeanDetail(new EditorContactosProveedores());

		// Servicios del editor para beans nested relacionados.
		setEditorServices(new EditorServices<Proveedor>(new IBeanEditorEvents<Proveedor>() {
			@Override
			public void beforeSaveEvent(Proveedor bean) throws LocalizedException {
			}

			@SuppressWarnings("unchecked")
			@Override
			public void beforeDeleteEvent(Proveedor bean) throws LocalizedException {
				Collection<ProveedorContacto> list = (Collection<ProveedorContacto>) DataContext.getProvider().getList(ProveedorContacto.class, "empresa_id = " + bean.getId(), null);
				if (list != null && list.size() > 0) {
					throw new LocalizedException("EXISTEN CONTACTOS DE LA EMPRESA. Eliminación cancelada.");
				}
			}
		}));

		// Vista de datos del bean
		addFormatView(new ListBeansViewAdapter<Proveedor>(UIContext.getText(Texts.NOMBRE) + " + Cif + " + UIContext.getText(Texts.LOCALIDAD), LOGO) {
			@Override
			public String toHtml(Proveedor bean) {
				return "<h2>" + bean.getNombre() + "</h2>Id: <b>" + bean.getId() + "</b><br><i>Cif: <b>" + bean.getCif() + "</b></i>"
						+ (bean.getLocalidad() != null ? "<br>" + bean.getLocalidad() : "");
			}
		});

		addFormatView(new ListBeansViewAdapter<Proveedor>(UIContext.getText(Texts.RAZONSOCIAL) + " + " + UIContext.getText(Texts.NOMBRE), LOGO) {
			@Override
			public String toHtml(Proveedor bean) {
				return (bean.getRazon_social() == null ? "" : "<h2>" + bean.getRazon_social() + "</h2>") + bean.getNombre();
			}
		});
	}

	@Override
	public Collection<Proveedor> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getProveedores(filter, order);
	}

	@Override
	public Class<Proveedor> getBeanClass() {
		return Proveedor.class;
	}

	@Override
	public Collection<FieldModel> getFieldModels() {
		return ModeloEmpresa.getFieldModels();
	}

	@Override
	public Collection<IFilterService> getFilters() {
		return ModeloEmpresa.getFilters();
	}

	@Override
	public Proveedor getNewBean() {

		Proveedor e = new Proveedor();
		// País por defecto obtenido de la localización de la sesión de usuario.
		e.setPais(UIContext.getCurrent().getCountry().toUpperCase());
		return e;
	}

}
