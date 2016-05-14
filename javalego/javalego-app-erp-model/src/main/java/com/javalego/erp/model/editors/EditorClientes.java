package com.javalego.erp.model.editors;

import java.util.Collection;

import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Cliente;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.mvp.beans.view.list.adapter.ListBeansViewAdapter;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;
import com.javalego.ui.mvp.editor.services.IBeanEditorEvents;

/**
 * Formulario de mantenimiento de Clientes
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorClientes extends BaseEditor<Cliente> {

	private static final long serialVersionUID = -1672085029864021830L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorClientes() {
		
		setTitle(Texts.CLIENTES);
		setIcon(Icons.COMPANIES);
		setEditionTitle(Texts.CLIENTE);

		// Detalle
		addBeanDetail(new EditorContactosClientes());

		// Servicio de validación previa al borrado.
		setEditorServices(new EditorServices<Cliente>(new IBeanEditorEvents<Cliente>() {
			@Override
			public void beforeSaveEvent(Cliente bean) throws LocalizedException {
			}

			@Override
			public void beforeDeleteEvent(Cliente bean) throws LocalizedException {
				
				if (getDataServices().hasClienteContactos(bean)) {
					throw new LocalizedException("EXISTEN CONTACTOS DE LA EMPRESA. Eliminación cancelada.");
				}
			}
		}));

		// Vistas de datos del bean
		addFormatView(new ListBeansViewAdapter<Cliente>(UIContext.getText(Texts.NOMBRE) + " + Cif + " + UIContext.getText(Texts.LOCALIDAD), LOGO) {
			@Override
			public String toHtml(Cliente bean) {
				return "<h2>" + bean.getNombre() + "</h2>Id: <b>" + bean.getId() + "</b><br><i>Cif: <b>" + bean.getCif() + "</b></i>"
						+ (bean.getLocalidad() != null ? "<br>" + bean.getLocalidad() : "");
			}
		});

		addFormatView(new ListBeansViewAdapter<Cliente>(UIContext.getText(Texts.RAZONSOCIAL) + " + " + UIContext.getText(Texts.NOMBRE), LOGO) {
			@Override
			public String toHtml(Cliente bean) {
				return (bean.getRazon_social() == null ? "" : "<h2>" + bean.getRazon_social() + "</h2>") + bean.getNombre();
			}
		});

	}

	@Override
	public Collection<Cliente> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getClientes(filter, order);
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
	public Cliente getNewBean() {

		Cliente e = new Cliente();
		// País por defecto obtenido de la localización de la sesión de usuario.
		e.setPais(UIContext.getCurrent().getCountry().toUpperCase());
		return e;
	}

	@Override
	public ICustomLayoutEditor<Cliente> getCustomLayoutEditor(IBeanEditorModel<Cliente> model) {
		return null;

		// return new ICustomLayoutEditorAndroid<LinearLayout>() {
		//
		// private View view;
		//
		// @Override
		// public ViewGroup getLayout(IEditorModel model, Context context) {
		//
		// Activity activity = (Activity)context;
		//
		// view =
		// activity.getLayoutInflater().inflate(com.javalego.apps.R.layout.activity_grid_layout,
		// null);
		//
		// return (ViewGroup) view;
		// }
		//
		// @Override
		// public View getView(IDataFieldModel field, Context context) {
		//
		// // Ejemplo de búsqueda directa
		// if (field.getName().equals("nombre")) {
		// return view.findViewById(com.javalego.apps.R.id.edittext1);
		// }
		//
		// // Ejemplo de búsqueda por coincidencia de tag o nombre de id.
		// // android:tag="nombre"
		// return view.findViewWithTag(field.getName());
		// }
		//
		// };
	}

	@Override
	public Class<Cliente> getBeanClass() {
		return Cliente.class;
	}

}
