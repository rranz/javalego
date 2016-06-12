package com.javalego.erp.model.editors;

import java.util.Collection;

import com.javalego.data.DataContext;
import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Categoria;
import com.javalego.erp.model.entity.Producto;
import com.javalego.erp.model.entity.ProductoTarifa;
import com.javalego.erp.model.entity.Proveedor;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.field.impl.BooleanFieldModel;
import com.javalego.ui.field.impl.EnumFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.filter.impl.FilterParam;
import com.javalego.ui.filter.impl.FilterParamsService;
import com.javalego.ui.filter.impl.FilterService;
import com.javalego.ui.mvp.beans.view.list.adapter.ListBeansViewAdapter;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.editor.services.IBeanEditorEvents;
import com.javalego.ui.mvp.nestedbean.imp.NestedBeanListValues;

/**
 * Formulario de mantenimiento de Productos
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorProductos extends BaseEditor<Producto> {

	private static final long serialVersionUID = -2774768138705772641L;

	private LayoutEditorModel lp;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorProductos() {

		setTitle(Texts.PRODUCTOS);
		setIcon(Icons.CONTACTS);
		setEditionTitle(Texts.PRODUCTO);

		// Campos
		addFieldModel(new TextFieldModel(PROVEEDOR_NOMBRE, Texts.PROVEEDOR, true).setColumns(30).setRequired(true));
		addFieldModel(new TextFieldModel(CATEGORIA_NOMBRE, Texts.CATEGORIA, true).setColumns(30).setRequired(true));
		addFieldModel(new TextFieldModel(IDENTIFICADOR, Texts.IDENTIFICADOR, true).setMaxSize(100).setColumns(20).setRequired(true));
		addFieldModel(new TextFieldModel(NOMBRE, Texts.NOMBRE, true).setMaxSize(20).setRequired(true));
		addFieldModel(new EnumFieldModel(TIPO, Texts.TIPO, ARTICULO, SERVICIO, COSTE).setRequired(true));
		addFieldModel(new BooleanFieldModel(HOMOLOGADO, Texts.HOMOLOGADO));
		addFieldModel(new BooleanFieldModel(ACTIVO, Texts.ACTIVO));
		addFieldModel(new BooleanFieldModel(COMPRA, Texts.COMPRA));
		addFieldModel(new BooleanFieldModel(VENTA, Texts.VENTA));
		addFieldModel(new BooleanFieldModel(ALMACENADO, Texts.ALMACENADO));
		addFieldModel(new ImageFieldModel(IMAGEN, Texts.IMAGEN));

		// Filtros basado en campos del editor. Se crea el presenter para la
		// edición de los campos que constituyen los parámetros del filtro
		addFilterService(new FilterParamsService(Texts.NOMBRE, new FilterParam(NOMBRE, Texts.NOMBRE, ConditionType.CONTAINS)));
		addFilterService(new FilterParamsService(Texts.IDENTIFICADOR, new FilterParam(IDENTIFICADOR, Texts.DIRECCION, ConditionType.CONTAINS)));
		addFilterService(new FilterParamsService(Texts.TIPO, new FilterParam(TIPO, Texts.TIPO, ConditionType.CONTAINS)));

		// Filtro basado en una sentencia sql
		addFilterService(new FilterService(Texts.ACTIVO).setStatement(ACTIVO + " = 1"));
		addFilterService(new FilterService(Texts.ALMACENADO).setStatement(ALMACENADO + " = 1"));

		// Detalle
		addBeanDetail(new EditorProductosTarifas());

		// Servicios del editor para beans nested relacionados.
		EditorServices<Producto> services = new EditorServices<Producto>(new IBeanEditorEvents<Producto>() {
			@Override
			public void beforeSaveEvent(Producto bean) throws LocalizedException {
			}

			@Override
			public void beforeDeleteEvent(Producto bean) throws LocalizedException {

				if (getDataProvider() != null) {
					Collection<ProductoTarifa> list = (Collection<ProductoTarifa>) getDataProvider().findAll(ProductoTarifa.class, "producto_id = " + bean.getId(), null);
					if (list != null && list.size() > 0) {
						throw new LocalizedException("EXISTEN TARIFAS PARA ESTE PRODUCTO. Eliminación cancelada.");
					}
				}
			}
		});
		setEditorServices(services);

		// Relaciones con proveedores y categorías
		services.addNestedFieldModel(PROVEEDOR_NOMBRE, new NestedBeanListValues<Producto, Proveedor>() {
			@Override
			public void valueChangeEvent(ValueChangeEvent event) throws LocalizedException {

				Proveedor e = (Proveedor) DataContext.getProvider().find(Proveedor.class, "nombre = '" + event.getValue() + "'");
				if (e != null) {
					event.getEditorRules().setValue(PROVEEDOR_ID, e.getId());
				} else {
					event.getEditorRules().setValue(PROVEEDOR_ID, null);
					event.getEditorRules().setValue(PROVEEDOR_NOMBRE, null);
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public Collection<String> getKeys(String constraint) throws LocalizedException {
				return getDataProvider() == null ? null : (Collection<String>) getDataProvider().propertyValues(Proveedor.class, NOMBRE,
						constraint != null ? "nombre like'" + constraint + "%'" : null, NOMBRE);
			}

			@Override
			public boolean isAutoComplete() {
				return true;
			}
		});

		services.addNestedFieldModel(CATEGORIA_NOMBRE, new NestedBeanListValues<Producto, Categoria>() {
			@Override
			public void valueChangeEvent(ValueChangeEvent event) throws LocalizedException {

				if (getDataProvider() != null) {

					Categoria e = (Categoria) getDataProvider().find(Categoria.class, "nombre = '" + event.getValue() + "'");
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

				return getDataProvider() == null ? null : (Collection<String>) getDataProvider().propertyValues(Categoria.class, NOMBRE,
						constraint != null ? "nombre like'" + constraint + "%'" : null, NOMBRE);
			}

			@Override
			public boolean isAutoComplete() {
				return false;
			}
		});

		// Vista de datos del bean
		addFormatView(new ListBeansViewAdapter<Producto>(UIContext.getText(Texts.NOMBRE) + " + " + UIContext.getText(Texts.IDENTIFICADOR), IMAGEN) {
			@Override
			public String toHtml(Producto bean) {
				return "<h2>" + bean.getNombre() + " - " + (bean.getProveedor() != null ? bean.getProveedor().getNombre() : "") + "</h2>Id: <b>" + bean.getIdentificador();
			}
		});

	}

	@Override
	public Collection<Producto> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getProductos(filter, order);
	}

	@Override
	public Class<Producto> getBeanClass() {
		return Producto.class;
	}

	@Override
	public Producto getNewBean() {

		Producto e = new Producto();
		e.setTipo(ARTICULO);
		return e;
	}

	@Override
	public LayoutEditorModel getLayoutEditorModel(IBeanEditorModel<Producto> model) {

		if (lp == null) {
			lp = new LayoutEditorModel(model, IMAGEN, PROVEEDOR_NOMBRE, CATEGORIA_NOMBRE, IDENTIFICADOR, NOMBRE, TIPO);
			lp.addChildLayout(Texts.CARACTERISTICAS, HOMOLOGADO, ACTIVO, COMPRA, VENTA, ALMACENADO);
		} else {
			// Actualizar los campos con bean a editar. El modelo es diferente
			// en cada edición de registro y su bean también.
			lp.updateBean(model);
		}
		return lp;
	}

}
