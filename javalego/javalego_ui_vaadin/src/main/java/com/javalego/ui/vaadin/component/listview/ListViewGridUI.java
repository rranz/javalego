package com.javalego.ui.vaadin.component.listview;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Lista de items basada en un GridLayout.
 * 
 * El texto se adapta al ancho de la columna del campo texto (wrap).
 * 
 * @author ROBERTO RANZ
 */
public class ListViewGridUI extends BaseListViewUI {

	private static final long serialVersionUID = -4152835600373306141L;

	/**
	 * Tipologías de definición del gridLayout
	 */
	public static final String STYLE_BORDERS = "borders", STYLE_ROWS = "row", STYLE_SEPARATOR = "separator";

	/**
	 * Layout
	 */
	private GridLayout grid;

	private boolean icons;

	private int count;

	private boolean actions;

	private String style = STYLE_BORDERS;

	/**
	 * Constructor
	 * 
	 * @param checked
	 *            Incluye un check de selección de un objeto de la lista
	 * @param icons
	 *            Los items contienen iconos.
	 * @param actions
	 *            Los items contienen acciones.
	 * @param style
	 *            Estilo aplicado (ver propiedad STYLE_BORDERS de este clase).
	 */
	public ListViewGridUI(boolean checked, boolean icons, boolean actions, String style) {
		this.checked = checked;
		this.icons = icons;
		this.actions = actions;
		this.style = style;
		initialize();
	}

	/**
	 * Constructor
	 * 
	 * @param checked
	 *            Incluye un check de selección de un objeto de la lista
	 * @param icons
	 *            Los items contienen iconos.
	 * @param actions
	 *            Los items contienen acciones.
	 * @param style
	 *            Estilo aplicado (ver propiedad STYLE_BORDERS de este clase).
	 * @param option_group
	 *            Cuando queremos que sólo se pueda seleccion una opción del
	 *            grupo de opciones disponibles.
	 */
	public ListViewGridUI(boolean checked, boolean icons, boolean actions, String style, boolean option_group) {
		this(checked, icons, actions, style);
		this.option_group = option_group;
		initialize();
	}

	/**
	 * Inicializar componente en base a la configuración definida.
	 */
	private void initialize() {

		count = 1 + (icons ? 1 : 0) + (checked ? 1 : 0) + (actions ? 1 : 0);

		grid = new GridLayout(count, 1);
		grid.setSpacing(true);

		if (STYLE_BORDERS.equals(style)) {
			grid.addStyleName("gana_borders");
		}
		else if (STYLE_ROWS.equals(style)) {
			grid.addStyleName("gana_rows");
		}

		grid.setColumnExpandRatio(count - (1 + (actions ? 1 : 0)), 1);

		grid.setSizeUndefined();

		setCompositionRoot(grid);
	}

	/**
	 * Añadir elemento a la lista
	 * 
	 * @param item
	 * @throws LocalizedException
	 */
	@Override
	public void addItem(IItemListView item, boolean select) throws LocalizedException {

		boolean separator = STYLE_SEPARATOR.equals(style);

		items.add(item);

		grid.setRows(separator ? items.size() * 2 : items.size());

		int row = grid.getRows() - (separator ? 2 : 1);

		int col = 0;

		boolean pijama = !separator; // && (row % 2 != 0);

		String css = "pijama_off";

		// Check
		if (checked) {
			Component chk = newItem(item);
			if (pijama) {
				chk.addStyleName(css);
			}
			if (select && chk instanceof CheckBox) {
				((CheckBox) chk).setValue(true);
			}
			grid.addComponent(chk, col++, row);
			grid.setComponentAlignment(chk, Alignment.MIDDLE_CENTER);
		}

		// Imagen
		if (icons) {
			if (item.getIcon() != null) {
				Component icon = ResourceIconsVaadin.getCurrent().getComponent(item.getIcon());

				if (pijama) {
					icon.addStyleName(css);
				}

				grid.addComponent(icon, col++, row);
				grid.setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
			}
			else {
				col++;
			}
		}

		// Texto
		Label label = new Label(item.getHtml(), ContentMode.HTML);
		// como ListViewUI
		// label.setWidth("100%");

		grid.addComponent(label, col++, row);
		grid.setComponentAlignment(label, Alignment.MIDDLE_LEFT);

		if (pijama) {
			label.addStyleName(css);
		}

		// Acciones
		if (actions && item.getComponents() != null) {

			HorizontalLayout layout = new HorizontalLayout();
			layout.setSizeUndefined();
			layout.setSpacing(true);
			layout.addStyleName(CssVaadin.getMargin4());

			if (pijama) {
				layout.addStyleName(css);
			}
			loadComponents(item, layout);

			grid.addComponent(layout, col++, row);
			grid.setComponentAlignment(layout, Alignment.MIDDLE_LEFT);
		}

		if (separator) {
			Label hr = new Label("<hr class='gana_small'>", ContentMode.HTML);
			grid.addComponent(hr, 0, row + 1, col - 1, row + 1);
		}

	}

	@Override
	protected void setVisibleItem(IItemListView item, boolean visible) {
		// TODO no implementado. Hay que analizar cómo eliminar un row de un
		// grid.
	}

	@Override
	public boolean isFilter() {
		return true;
	}
}
