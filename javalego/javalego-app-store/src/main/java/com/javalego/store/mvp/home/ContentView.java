package com.javalego.store.mvp.home;

import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityContext;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.mvp.detail.DetailItemPresenter;
import com.javalego.store.mvp.detail.DetailItemView;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

/**
 * Vista que muestra el contenido de las opciones de menú (body HomeLayout).
 * 
 * @author ROBERTO RANZ
 *
 */
public class ContentView extends CustomComponent implements View {

	private static final long serialVersionUID = 7252295042013773202L;
	
	private DetailItemPresenter<IBaseItem> detailPresenter;

	private DetailItemView<IBaseItem> detailView;
	
	public ContentView() {

		setWidth("100%");
		try {
			setCompositionRoot(getDetailView());
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}
	
	private DetailItemView<IBaseItem> getDetailView() throws LocalizedException {
		
		if (detailView != null) {
			return detailView;
		}
		
		detailView = new DetailItemView<IBaseItem>(!SecurityContext.getCurrent().getServices().isAuthenticated());
		
		detailPresenter = new DetailItemPresenter<IBaseItem>(StoreAppContext.getCurrent().getDataServices(), detailView);
		detailPresenter.load();
		detailPresenter.load(null);
		return detailView;
	}
	
	/**
	 * Cargar componente asociado al item de menú.
	 * @param menuItem
	 * @throws LocalizedException
	 */
	public boolean load(IMenuItem menuItem) throws LocalizedException {
		
		return getDetailView().load(menuItem);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
	
}
