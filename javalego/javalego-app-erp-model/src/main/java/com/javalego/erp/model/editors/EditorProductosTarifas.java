package com.javalego.erp.model.editors;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;

import com.javalego.data.DataContext;
import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Producto;
import com.javalego.erp.model.entity.ProductoTarifa;
import com.javalego.erp.model.entity.Proveedor;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.field.impl.BooleanFieldModel;
import com.javalego.ui.field.impl.CurrencyFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
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
public class EditorProductosTarifas extends BaseEditor<ProductoTarifa> {

	private static final long serialVersionUID = 1773634690815308374L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorProductosTarifas() {

		setTitle(Texts.PRODUCTOS_TARIFAS);
		setIcon(Icons.PRODUCTS);
		setEditionTitle(Texts.PRODUCTOS_TARIFAS);

		// Campos
		addFieldModel(new TextFieldModel(PRODUCTO_NOMBRE, Texts.PRODUCTO, true).setColumns(30).setRequired(true));
		addFieldModel(new TextFieldModel(PRODUCTO_PROVEEDOR_NOMBRE, Texts.PROVEEDOR).setColumns(30).setReadOnly(true));
		addFieldModel(new TextFieldModel(NOMBRE, Texts.NOMBRE).setColumns(30).setRequired(true));
		addFieldModel(new BooleanFieldModel(ACTIVO, Texts.ACTIVO).setColumns(30).setRequired(true));
		addFieldModel(new CurrencyFieldModel(TARIFA, Texts.TARIFA));
		addFieldModel(new CurrencyFieldModel(ESTANDAR, Texts.TARIFA_ESTANDAR));
		addFieldModel(new CurrencyFieldModel(LIMITE, Texts.TARIFA_LIMITE));

		// Servicios del editor para beans nested relacionados.
		EditorServices<ProductoTarifa> services = new EditorServices<ProductoTarifa>();
		setEditorServices(services);

		services.addNestedFieldModel(PRODUCTO_NOMBRE, new NestedBeanListValues<ProductoTarifa, Producto>() {
			@Override
			public void valueChangeEvent(ValueChangeEvent event) throws LocalizedException {

				if (getDataProvider() != null) {

					Producto e = (Producto) getDataProvider().getObject(Producto.class, "nombre = '" + event.getValue() + "'");
					if (e != null) {
						event.getEditorRules().setValue(PRODUCTO_ID, e.getId());

						if (DataContext.getCurrent().isSQLite() && e.getProveedor() != null && e.getProveedor().getId() != null) {
							event.getEditorRules().setValue(PRODUCTO_PROVEEDOR_NOMBRE,
									DataContext.getProvider().getFieldValues(Proveedor.class, NOMBRE, "id = " + e.getProveedor().getId(), null).iterator().next());
						}

					}
					else {
						event.getEditorRules().setValue(PRODUCTO_ID, null);
						event.getEditorRules().setValue(PRODUCTO_NOMBRE, null);
					}
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public Collection<String> getKeys(String constraint) throws LocalizedException {

				return getDataProvider() == null ? null : (Collection<String>) getDataProvider().getFieldValues(Producto.class, NOMBRE, constraint != null ? "nombre like'" + constraint + "%'" : null,
						NOMBRE);
			}

			@Override
			public boolean isAutoComplete() {
				return true;
			}
		});

		// Vista de datos del bean
		addFormatView(new ListBeansViewAdapter<ProductoTarifa>(UIContext.getText(Texts.PRODUCTO) + " + " + UIContext.getText(Texts.PROVEEDOR) + " + " + UIContext.getText(Texts.NOMBRE)) {
			@Override
			public String toHtml(ProductoTarifa bean) {
				return "<h2>" + bean.getProducto().getNombre() + " - " + bean.getProducto().getProveedor().getNombre() + "</h2>" + bean.getNombre() + "<br>" + UIContext.getText(Texts.TARIFA) + ": "
						+ NumberFormat.getInstance().format(bean.getTarifa()) + " - " + NumberFormat.getInstance().format(bean.getEstandar());
			}
		});

	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ProductoTarifa> getBeans(String filter, String order) throws LocalizedException {

		if (getDataProvider() == null) {
			return null;
		}

		List<ProductoTarifa> list = (List<ProductoTarifa>) getDataServices().getProductoTarifas(filter, order);

		// Incluir el nombre del proveedor si estamos con base de datos sqllite
		if (list != null && list.size() > 0 && DataContext.getCurrent().isSQLite()) {
			String ids = "";
			for (ProductoTarifa item : list) {
				ids += ("".equals(ids) ? "" : ",") + item.getProducto().getId();
			}
			Collection<String> names = (Collection<String>) getDataProvider()
					.getFieldValues(Producto.class, "(select p.nombre from proveedor p where p.id = proveedor_id)", "id in(" + ids + ")", null);
			if (names != null && names.size() > 0) {
				int i = 0;
				for (String name : names) {
					list.get(i).getProducto().setProveedor(new Proveedor());
					list.get(i++).getProducto().getProveedor().setNombre(name);
				}
			}
		}

		if (order != null && list != null && list.size() > 1) {
			StringUtils.sortList((List<?>) list, PRODUCTO_NOMBRE);
		}
		return list;
	}

	@Override
	public Class<ProductoTarifa> getBeanClass() {
		return ProductoTarifa.class;
	}

}
