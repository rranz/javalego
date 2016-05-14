package com.javalego.store.ui.components;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.ICode;
import com.javalego.store.items.IComment;
import com.javalego.store.items.IDemo;
import com.javalego.store.items.IMember;
import com.javalego.store.items.INews;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.items.IProvider;
import com.javalego.store.items.IScreenShot;
import com.javalego.store.items.ISocial;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.store.ui.editor.IItemEditor;
import com.javalego.store.ui.editor.impl.CommonEditor;
import com.javalego.store.ui.editor.impl.MemberEditor;
import com.javalego.store.ui.editor.impl.ProductEditor;
import com.javalego.store.ui.editor.impl.ProjectEditor;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

/**
 * Factoría de generación de componentes visuales
 * 
 * @author ROBERTO RANZ
 * 
 */
public class UIFactory {

	private UIFactory() {
	};

	/**
	 * Obtener el editor específico para el tipo de item.
	 * 
	 * @param item
	 * @param readOnly
	 *            Sólo lectura
	 * @param remove
	 *            Añadir opción de borrado
	 * @return
	 */
	public static IItemEditor<?> getEditor(IBaseItem item, boolean readOnly, boolean remove) {

		IItemEditor<?> detail = null;

		if (item instanceof IProduct) {
			detail = new ProductEditor((IProduct) item, readOnly, remove);
		}
		else if (item instanceof IProject) {
			detail = new ProjectEditor((IProject) item, readOnly, remove);
		}
		else if (item instanceof IScreenShot) {
			detail = new CommonEditor((IScreenShot) item, StoreAppContext.getCurrent().getModelService().getScreenShotFields(), readOnly, remove);
		}
		else if (item instanceof IMember) {
			detail = new MemberEditor((IMember) item, readOnly, remove);
		}
		else if (item instanceof IComment) {
			detail = new CommonEditor((IComment) item, StoreAppContext.getCurrent().getModelService().getCommentFields(), readOnly, remove);
		}
		else {
			detail = new CommonEditor(item, StoreAppContext.getCurrent().getModelService().getCommonFields(), readOnly, remove);
		}

		return detail;
	}

	/**
	 * Editor de productos
	 * 
	 * @return
	 */
	public static final ProductsUI getProducts() {
		return new ProductsUI();
	}

	/**
	 * Comentarios
	 * 
	 * @return
	 */
	public static final CommentsUI getComments() {
		return new CommentsUI();
	}

	/**
	 * Acceso a los diferentes recursos relacionados con redes sociales y el
	 * código.
	 * 
	 * @param social
	 * @return
	 */
	public static final ResourcesUI getResources(ISocial social, ICode code, IDemo demo) {

		return new ResourcesUI(social, code, demo);
	}

	/**
	 * Acceso a los diferentes proveedores.
	 * 
	 * @param social
	 * @return
	 */
	public static final ProvidersUI getProviders(Collection<IProvider> providers) {

		return new ProvidersUI(providers);
	}

	/**
	 * Obtiene el componente que lanza la url asociada la proveedor.
	 * 
	 * @param provider
	 * @return
	 * @throws LocalizedException
	 */
	public static final Component getProviderIcon(IProvider provider) throws LocalizedException {

		return ResourceIconsVaadin.getCurrent().getLink(provider.getUrl(), provider.getIcon());
	}

	/**
	 * Noticias.
	 * 
	 * @param news
	 * @return
	 */
	public static Component getNews(Collection<INews> news) {

		VerticalLayout newsLayout = new VerticalLayout();
		newsLayout.setMargin(true);
		newsLayout.setWidth("100%");

		for (INews item : news) {
			newsLayout.addComponent(new Label(item.toHtml(), ContentMode.HTML));
		}

		return newsLayout;
	}

	/**
	 * Editor de imágenes
	 * 
	 * @param screenShots
	 * @return
	 */
	public static Layout getScreenShots(Collection<IScreenShot> screenShots) {

		return new ScreenShotsUI<IScreenShot>(screenShots);
	}

}
