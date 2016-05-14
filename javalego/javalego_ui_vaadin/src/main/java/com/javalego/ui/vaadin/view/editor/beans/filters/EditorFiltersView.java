package com.javalego.ui.vaadin.view.editor.beans.filters;

import java.util.ArrayList;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.UIContext;
import com.javalego.ui.filter.IFilterParamsService;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.listeners.ItemChangeEvent;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.menu.MenuItem;
import com.javalego.ui.menu.MenuPresenter;
import com.javalego.ui.menu.MenuPresenterListener;
import com.javalego.ui.mvp.editor.beans.filters.IEditorFiltersView;
import com.javalego.ui.mvp.editor.beans.filters.EditorFiltersPresenter.EditorFilterObserver;
import com.javalego.ui.mvp.editor.filter.params.EditorFilterParamsPresenter;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.view.menu.MenuPanels;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * Editor básico de visualización de una lista de beans en un componente de tipo
 * rejilla.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class EditorFiltersView extends VerticalLayout implements IEditorFiltersView {

	private static final long serialVersionUID = -5462261458724232750L;

	private IEditorFiltersViewListener listener;

	private MenuPresenter filtersPresenter;

	private ButtonExt filterButton;

	public EditorFiltersView() {
		setMargin(true);
		setSpacing(true);
	}

	@Override
	public void load() throws LocalizedException {

		if (filtersPresenter != null) {
			return;
		}

		filtersPresenter = new MenuPresenter(new IMenuModel() {

			private List<IMenuItem> items = new ArrayList<IMenuItem>();

			@Override
			public List<IMenuItem> getSubItems(IMenuItem menuItem) {
				return null;
			}

			@Override
			public List<IMenuItem> getMainItems() {

				if (items.size() == 0) {

					for (IFilterService filter : listener.getFilters()) {

						MenuItem m = new MenuItem(UIContext.getText(filter.getTitle()), UIContext.getText(filter.getDescription()), IconEditor.UNCHECK);

						// Crear una vista para la edición de los parámetros y
						// los eventos para su ejecución.
						if (filter instanceof IFilterParamsService) {
							m.setData(new EditorFilterParamsPresenter((IFilterParamsService) filter, new EditorFilterParamsView()));
						} else {
							m.setData(filter);
						}
						items.add(m);
					}
				}

				return items;
			}

			@Override
			public IMenuItem getHeader() {
				return null;
			}
		}, new MenuPanels(true, MenuPanels.Style.PANELS));

		filtersPresenter.addItemChangeListener(new MenuPresenterListener() {
			@Override
			public void propertyChange(ItemChangeEvent<IMenuItem> event) {
				try {
					changeItem(event.getItemChange());
				} catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});

		addComponent((Component) filtersPresenter.getView());

		// Eliminar filtro actual
		filterButton = new ButtonExt(LocaleEditor.REMOVE_FILTER).red();
		filterButton.setVisible(false);
		filterButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 7925403058995492211L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					listener.removeCurrentFilter();
					filtersPresenter.unselect();
					filterButton.setVisible(false);
				} catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});

		addComponent(filterButton);
	}

	/**
	 * Cambio de item de filtro
	 * 
	 * @param itemChange
	 * @throws LocalizedException
	 */
	protected void changeItem(final IMenuItem itemChange) throws LocalizedException {

		final Object data = itemChange.getData();

		// Incluir listener para ejecutar filtro en el editor
		if (data instanceof EditorFilterParamsPresenter) {

			final IFilterParamsService filter = ((EditorFilterParamsPresenter) data).getServices();

			((EditorFilterParamsPresenter) data).setObserver(new EditorFilterObserver() {
				@Override
				public void execute(String statement, String naturalStatement) throws LocalizedException {
					listener.execute(filter.getStatement(), filter.getNaturalStatement());
					filtersPresenter.select(itemChange);
					filterButton.setVisible(true);
				}

				@Override
				public void removeCurrentFilter() throws LocalizedException {
					listener.removeCurrentFilter();
					filtersPresenter.unselect();
					filterButton.setVisible(false);
				}
			});
		}
		// Ejecutar filtro directo sobre el editor.
		else if (data instanceof IFilterService) {
			IFilterService filter = (IFilterService) data;
			listener.execute(filter.getStatement(), filter.getNaturalStatement());
			filtersPresenter.select(itemChange);
			filterButton.setVisible(true);
		}
	}

	@Override
	public void setListener(IEditorFiltersViewListener listener) {
		this.listener = listener;
	}

}
