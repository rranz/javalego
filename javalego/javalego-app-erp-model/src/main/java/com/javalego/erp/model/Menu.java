package com.javalego.erp.model;

import java.util.ArrayList;
import java.util.List;

import com.javalego.erp.model.editors.EditorCategorias;
import com.javalego.erp.model.editors.EditorCategoriasProfesionales;
import com.javalego.erp.model.editors.EditorClientes;
import com.javalego.erp.model.editors.EditorContactosClientes;
import com.javalego.erp.model.editors.EditorContactosProveedores;
import com.javalego.erp.model.editors.EditorDepartamento;
import com.javalego.erp.model.editors.EditorEmpleados;
import com.javalego.erp.model.editors.EditorEmpresas;
import com.javalego.erp.model.editors.EditorProductos;
import com.javalego.erp.model.editors.EditorProductosTarifas;
import com.javalego.erp.model.editors.EditorProveedores;
import com.javalego.model.keys.Colors;
import com.javalego.security.SecurityContext;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.menu.MenuItem;

/**
 * Modelo de datos del menú que dan acceso a los módulos de la aplicación.
 * 
 * @author ROBERTO RANZ
 *
 */
public class Menu implements IMenuModel {

	private List<IMenuItem> items;
	
	@Override
	public IMenuItem getHeader() {
		return new MenuItem(MenuKey.HOME, Texts.MODULOS, Colors.INDIGO);
	}
	
	@Override
	public List<IMenuItem> getMainItems() {
		
		if (items != null) {
			return items;
		}
		
		items = new ArrayList<IMenuItem>();
		
		// Compras
		MenuItem m = new MenuItem().setName(MenuKey.BUSINESS).setTitle(Texts.VENTAS).setColor(Colors.ORANGE).setIcon(Icons.TRUCK);
		
		m.addSubItem(getModule(EditorClientes.class, MenuKey.CUSTOMERS, Texts.CLIENTES, Icons.COMPANIES));
		m.addSubItem(getModule(EditorContactosClientes.class, MenuKey.CUSTOMERS_CONTACTS, Texts.CONTACTOS, Icons.CONTACTS));
		items.add(m);
		
		// Ventas
		m = new MenuItem().setName(MenuKey.BUSINESS).setTitle(Texts.COMPRAS).setColor(Colors.BLUE).setIcon(Icons.SHOPPING);
		
		m.addSubItem(getModule(EditorProveedores.class, MenuKey.PROVIDERS,Texts.PROVEEDORES,Icons.COMPANIES));
		m.addSubItem(getModule(EditorContactosProveedores.class, MenuKey.PROVIDERS_CONTACTS,Texts.CONTACTOS,Icons.CONTACTS));
		m.addSubItem(getModule(EditorCategorias.class, MenuKey.CATEGORIES,Texts.CATEGORIAS,Icons.COMPONENTS));
		m.addSubItem(getModule(EditorProductos.class, MenuKey.PRODUCTS,Texts.PRODUCTOS,Icons.PRODUCTS));
		m.addSubItem(getModule(EditorProductosTarifas.class, MenuKey.PRODUCTS_RATES,Texts.PRODUCTOS_TARIFAS,Icons.MONEY));
		items.add(m);
		
		// RRHH
		m = new MenuItem().setName(MenuKey.RRHH).setTitle(Texts.RRHH).setColor(Colors.GREEN).setIcon(Icons.COMMUNITY);
		
		m.addSubItem(getModule(EditorEmpresas.class, MenuKey.COMPANIES, Texts.EMPRESAS, Icons.COMPANIES));
		m.addSubItem(getModule(EditorDepartamento.class, MenuKey.DEPARTAMENTS,Texts.DEPARTAMENTOS,Icons.TEAMWORK));
		m.addSubItem(getModule(EditorCategoriasProfesionales.class, MenuKey.STAFF_CATEGORIES,Texts.CATEGORIAS_PROFESIONALES,Icons.CATEGORIES));
		m.addSubItem(getModule(EditorEmpleados.class, MenuKey.STAFF,Texts.PERSONAL,Icons.PEOPLE));
		items.add(m);
		
		return items;
	}
	
	/**
	 * Instanciar el módulo si el usuario tiene acceso.
	 * @param module
	 * @param name
	 * @param title
	 * @param icon
	 * @return
	 */
	private MenuItem getModule(Class<?> module, MenuKey name, Texts title, Icons icon) {

		if (SecurityContext.getCurrent().getServices() == null || SecurityContext.getCurrent().getServices().isPermitted(module)) {
			return new MenuItemModule(name, title, icon, module);
		}
		else {
			return null;
		}
		
	}

	@Override
	public List<IMenuItem> getSubItems(IMenuItem menuItem) {

		return menuItem.getSubItems();
	}

}
