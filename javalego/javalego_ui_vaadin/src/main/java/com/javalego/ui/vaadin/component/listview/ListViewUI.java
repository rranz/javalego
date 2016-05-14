package com.javalego.ui.vaadin.component.listview;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Lista que permite configurar items con imagen, check, texto y componentes
 * adicionales.
 * 
 * .pijama_on { background-color: orange; padding: 2px; } .pijama_off { padding:
 * 2px; }
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ListViewUI extends BaseListViewUI {

	private static final long serialVersionUID = 4041481264847165399L;

	/**
	 * Layout
	 */
	private VerticalLayout layout;

	/**
	 * Constructor
	 * 
	 * @param checked
	 *          Incluye un check de selección de un objeto de la lista
	 */
	public ListViewUI(boolean checked) {
		this.checked = checked;
		initialize();
	}

	/**
	 * Constructor
	 * 
	 * @param checked
	 *          Incluye un check de selección de un objeto de la lista
	 * @param option_group
	 *          Cuando queremos que sólo se pueda seleccion una opción del grupo
	 *          de opciones disponibles.
	 */
	public ListViewUI(boolean checked, boolean option_group) {
		this.checked = checked;
		this.option_group = option_group;
		initialize();
	}

	private void initialize() {

		layout = new VerticalLayout();
		layout.setSizeUndefined();
		layout.setWidth("100%");

		setCompositionRoot(layout);
	}

	/**
	 * Añadir elemento a la lista
	 * 
	 * @param item
	 * @throws LocalizedException 
	 */
	@Override
	public void addItem(IItemListView item, boolean select) throws LocalizedException {

		items.add(item);

		HorizontalLayout itemLayout = new HorizontalLayout();
		itemLayout.setData(item);
		itemLayout.setSpacing(true);
		itemLayout.setSizeUndefined();
		itemLayout.setWidth("100%");

		itemLayout.addStyleName("pijama_" + (items.size() % 2 == 0 ? "on" : "off"));

		// Check
		if (checked) {
			Component chk = newItem(item);
			itemLayout.addComponent(chk);
			itemLayout.setComponentAlignment(chk, Alignment.MIDDLE_LEFT);
			if (select && chk instanceof CheckBox) {
				((CheckBox)chk).setValue(true);
			}
		}

		// Imagen
		if (item.getIcon() != null) {
			Component icon = ResourceIconsVaadin.getCurrent().getComponent(item.getIcon());
			itemLayout.addComponent(icon);
			itemLayout.setComponentAlignment(icon, Alignment.MIDDLE_LEFT);
		}
		// col++;

		// Texto
		Label label = new Label(item.getHtml(), ContentMode.HTML);
		label.setSizeUndefined();
		label.setWidth("100%");
		itemLayout.addComponent(label);
		itemLayout.setComponentAlignment(label, Alignment.MIDDLE_LEFT);

		// Crear un layout para todos los componentes asociados al item.
		if (item.getComponents() != null) {

			HorizontalLayout layoutComponents = new HorizontalLayout();
			layoutComponents.setSizeUndefined();
			layoutComponents.setSpacing(true);
			// layout.setMargin(false, true, false, true);
			layoutComponents.addStyleName(CssVaadin.getMargin4());

			loadComponents(item, layoutComponents);

			itemLayout.addComponent(layoutComponents);
			itemLayout.setComponentAlignment(layoutComponents, Alignment.MIDDLE_LEFT);

			itemLayout.setExpandRatio(layoutComponents, 1);
		}
		else {
			itemLayout.setExpandRatio(label, 1);
		}

		// Añadir layout del item al layout principal de la lista de items.
		layout.addComponent(itemLayout);

	}

	@Override
	protected void setVisibleItem(IItemListView item, boolean visible) {

		for(int i = 0; i < layout.getComponentCount(); i++) {
			
			if ( ((AbstractLayout)layout.getComponent(i)).getData() == item) {
				
				layout.getComponent(i).setVisible(visible);
				break;
			}
		}
		
	}

	@Override
	public boolean isFilter() {
		return true;
	}

}
