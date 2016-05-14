package com.javalego.store.ui.demos;

import java.util.ArrayList;
import java.util.List;

import com.javalego.model.keys.Colors;
import com.javalego.store.ui.icons.MenuIcons;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.menu.MenuItem;

/**
 * Modelo de datos del menú.
 * 
 * @author ROBERTO RANZ
 *
 */
public class MenuPanelsModel implements IMenuModel {

	@Override
	public IMenuItem getHeader() {
		return new MenuItem(LocaleStore.HOME, "ERP", "", null, Colors.INDIGO);
	}

	@Override
	public List<IMenuItem> getMainItems() {
		
		List<IMenuItem> items = new ArrayList<IMenuItem>();
		
		MenuItem m = new MenuItem().setName(LocaleStore.ARCHITECTURE).setTitle("RECURSOS HUMANOS").setIcon(MenuIcons.CLIENTS).setColor(Colors.RED);
		
		m.addSubItem(new MenuItem("Plantilla").setIcon(MenuIcons2.MEMBER));
		m.addSubItem(new MenuItem("Informes").setIcon(MenuIcons2.REPORT));
		m.addSubItem(new MenuItem("Organigrama").setIcon(MenuIcons2.PROCESS));
		
		items.add(m);
		
		m = new MenuItem().setName(LocaleStore.BUSINESS).setTitle("COMPRAS").setIcon(MenuIcons.BUSINESS).setColor(Colors.BLUE);
		
		m.addSubItem(new MenuItem("Proveedores").setIcon(MenuIcons2.SOLUTION));
		m.addSubItem(new MenuItem("Facturación").setIcon(MenuIcons2.BRIEFCASE));
		m.addSubItem(new MenuItem("Informes").setIcon(MenuIcons2.REPORT));
		
		items.add(m);
		
		MenuItem projects = new MenuItem(LocaleStore.PROJECTS, "PROYECTOS").setIcon(MenuIcons.PROJECTS).setColor(Colors.INDIGO);
		items.add(projects);
		projects.addSubItem(new MenuItem().setName(LocaleStore.ARCHITECTURE_PROJECTS).setTitle("Cartera de Proyectos").setIcon(MenuIcons2.BRIEFCASE));
		projects.addSubItem(new MenuItem(LocaleStore.BUSINESS_PROJECTS, "Informes").setIcon(MenuIcons2.REPORT));
		return items;
	}
	
	@Override
	public List<IMenuItem> getSubItems(IMenuItem menuItem) {

		return menuItem.getSubItems();
	}

}
