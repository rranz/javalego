package com.javalego.store.model;

import java.util.ArrayList;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.store.items.ICategory;
import com.javalego.store.items.Type;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.store.ui.icons.MenuIcons;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.menu.MenuItem;

/**
 * Modelo de datos del menú principal.
 * 
 * @author ROBERTO RANZ
 *
 */
public class MenuModel implements IMenuModel {

	@Override
	public IMenuItem getHeader() {
		return new MenuItem(LocaleStore.HOME, LocaleStore.STORE, Colors.BLUE);
	}

	@Override
	public List<IMenuItem> getMainItems() throws LocalizedException {
		
		List<IMenuItem> items = new ArrayList<IMenuItem>();
		
		// Productos de negocio
		MenuItem m = new MenuItem(LocaleStore.BUSINESS, LocaleStore.BUSINESS_PRODUCTS, MenuIcons.BUSINESS, Colors.RED);
		for(ICategory item : StoreAppContext.getCurrent().getDataServices().getCategories(Type.BUSINESS)) {
			m.addSubItem(new MenuItem(item.getCode(), item.getIcon()));
		}
		items.add(m);

		// Productos de arquitectura
		m = new MenuItem(LocaleStore.ARCHITECTURE, LocaleStore.ARCHITECTURE_PRODUCTS, MenuIcons.BRIEFCASE, Colors.BLUE);
		for(ICategory item : StoreAppContext.getCurrent().getDataServices().getCategories(Type.ARCHITECTURE)) {
			m.addSubItem(new MenuItem(item.getCode(), item.getIcon()));
		}
		items.add(m);
		
		// Proyectos
		MenuItem projects = new MenuItem(LocaleStore.PROJECTS, MenuIcons.PROJECTS, Colors.INDIGO);
		items.add(projects);
		projects.addSubItem(new MenuItem(LocaleStore.ARCHITECTURE_PROJECTS, LocaleStore.ARCHITECTURE, MenuIcons2.BRIEFCASE));
		projects.addSubItem(new MenuItem(LocaleStore.BUSINESS_PROJECTS, LocaleStore.BUSINESS, MenuIcons2.BUSINESS));

		// Comunidad
		MenuItem community = new MenuItem(LocaleStore.COMMUNITY, MenuIcons.COMMUNITY, Colors.ORANGE);
		items.add(community);
		community.addSubItem(new MenuItem(LocaleStore.DEVELOPERS, MenuIcons2.MEMBER));
		community.addSubItem(new MenuItem(LocaleStore.COMPANIES, MenuIcons2.MEMBER));

		// Cloud
		//items.add(new MenuItem(LocaleStore.CLOUD, LocaleStore.CLOUD_SERVICES, MenuIcons.CLOUD, Colors.GRAY));
		
		// Datos generales
		MenuItem cruds = new MenuItem(LocaleStore.CRUDS, MenuIcons.APPS, Colors.GRAY);
		items.add(cruds);
		cruds.addSubItem(new MenuItem(LocaleStore.PROVIDERS, MenuIcons2.TOOLS));
		cruds.addSubItem(new MenuItem(LocaleStore.LICENSES, MenuIcons2.LICENSE));
		cruds.addSubItem(new MenuItem(LocaleStore.NEWS, MenuIcons2.BLOG));
		cruds.addSubItem(new MenuItem(LocaleStore.REPOSITORIES, MenuIcons2.GITHUB));
		
		// Ayuda
		MenuItem help = new MenuItem(LocaleStore.HELP, MenuIcons.HELP, Colors.GREEN);
		items.add(help);
		help.addSubItem(new MenuItem(LocaleStore.TOUR, MenuIcons2.MONITOR));
		help.addSubItem(new MenuItem(LocaleStore.DOCUMENTATION, MenuIcons2.BOOK));
		help.addSubItem(new MenuItem(LocaleStore.GUIDES, MenuIcons2.GUIDE));
		help.addSubItem(new MenuItem(LocaleStore.SUPPORT, MenuIcons2.SUPPORT));
		help.addSubItem(new MenuItem(LocaleStore.FORUM, MenuIcons2.FORUM));
		
		return items;
	}
	
	/**
	 * Opciones de inicio.
	 * @return
	 */
	public static final List<IMenuItem> getHomeOptions() {
		
		List<IMenuItem> items = new ArrayList<IMenuItem>();
		items.add(new MenuItem(LocaleStore.NEWS));
		items.add(new MenuItem(LocaleStore.FIND));
		//items.add(new MenuItem(LocaleStore.FAVORITES));
		return items;
	}
	
	@Override
	public List<IMenuItem> getSubItems(IMenuItem menuItem) {

		return menuItem.getSubItems();
	}

}
