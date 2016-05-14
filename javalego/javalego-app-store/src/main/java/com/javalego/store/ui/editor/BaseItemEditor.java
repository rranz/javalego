package com.javalego.store.ui.editor;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.impl.Category;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorObserver;
import com.javalego.ui.vaadin.UIContextVaadin;
import com.javalego.ui.vaadin.component.backbutton.BackButton;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Editor básico de Item de la plataforma
 * 
 * @author ROBERTO RANZ
 * 
 */
public abstract class BaseItemEditor<T> extends CssLayout implements IItemEditor<T> {

	private static final Logger logger = Logger.getLogger(BaseItemEditor.class);

	private static final long serialVersionUID = -6078895634284885616L;

	protected BeanEditorObserver<T> observer;

	protected boolean readOnly;

	protected boolean remove;

	private Label title;

	private Component icon;

	private BackButton backButton;

	private HorizontalLayout titleLayout;

	@Override
	protected String getCss(Component c) {
		return "margin: 0px; padding: 2px; vertical-align: top; display: inline-block;";
	}

	public BeanEditorObserver<T> getObserver() {
		return observer;
	}

	@Override
	public void setObserver(BeanEditorObserver<T> observer) {
		this.observer = observer;
	}

	/**
	 * Botón de retorno
	 * 
	 * @return
	 */
	protected BackButton getBackButton() {

		BackButton btn = new BackButton(MenuIcons2.LEFTROW);
		btn.setHeight("80px");
		btn.setWidth("40px");
		btn.addStyleName(CssVaadin.getRounded());
		return btn;
	}

	@SuppressWarnings("serial")
	@Override
	public void load() throws LocalizedException {

		titleLayout = new HorizontalLayout();
		titleLayout.setWidth("100%");
		titleLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		// titleLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		titleLayout.setSpacing(true);
		addComponent(titleLayout);

		backButton = getBackButton();
		backButton.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (observer != null) {
					observer.discard(getBean());
				}
			}
		});
		titleLayout.addComponent(backButton);

		if (getBeanIcon() != null) {
			try {
				icon = ResourceIconsVaadin.getCurrent().getComponent(getBeanIcon());
				titleLayout.addComponent(icon);
			}
			catch (Exception e1) {
				logger.error("ERROR LOAD ICON '" + getBeanIcon().name() + "'", e1);
			}
		}
		addStyleName(CssVaadin.getShadow());

		titleLayout.addComponent(loadTitle(getBeanTitle()));
		titleLayout.setExpandRatio(title, 1);
	}

	/**
	 * Título del bean
	 * 
	 * @return
	 */
	protected abstract String getBeanTitle();

	/**
	 * Bean editado
	 * 
	 * @return
	 */
	protected abstract T getBean();

	/**
	 * Icono representativo del bean
	 * 
	 * @return
	 */
	protected abstract Icon getBeanIcon();

	/**
	 * Actualizar icono en base a la categoría.
	 * 
	 * @param value
	 * @throws LocalizedException
	 */
	protected void updateIcon(Object value) throws LocalizedException {

		if (icon != null) {
			titleLayout.removeComponent(icon);
		}
		if (value instanceof Category) {
			icon = UIContextVaadin.getComponent(((Category) value).getIcon());
			titleLayout.addComponent(icon, 1);
		}
	}

	/**
	 * Título del producto
	 * 
	 * @param text
	 * @return
	 */
	protected Label loadTitle(String text) {

		String caption = (text != null ? "<font size=+1>" + text + "</font>" : "");

		if (title == null) {
			title = new Label(caption, ContentMode.HTML);
		}
		else {
			title.setValue(caption);
		}

		return title;
	}

}
