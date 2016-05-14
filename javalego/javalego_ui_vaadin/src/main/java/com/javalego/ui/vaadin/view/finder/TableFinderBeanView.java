package com.javalego.ui.vaadin.view.finder;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleWarnings;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.finder.IFinderView;
import com.javalego.ui.vaadin.component.buttonbar.ButtonBar;
import com.javalego.ui.vaadin.component.layout.VerticalLayoutExt;
import com.javalego.ui.vaadin.component.table.TableExt;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 * Seleccionar un bean utilizando el componente Table UI de Vaadin.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class TableFinderBeanView<T> extends CustomComponent implements IFinderView<T> {

	private static final long serialVersionUID = -1001637272291821035L;

	private IFinderViewListener<T> listener;

	private TableExt<T> table;

	private Window window;

	@Override
	public void load() throws LocalizedException {

		VerticalLayout layout = new VerticalLayoutExt().withSpacing(true).withMargin(true);

		table = new TableExt<T>(listener.getObjects()).withProperties(listener.getProperties()).withColumnHeaders(listener.getHeaders());
		// t.addMValueChangeListener(new MValueChangeListener<T>() {
		// @Override
		// public void valueChange(MValueChangeEvent<T> event) {
		// System.out.println(event.getValue());
		// }
		// });

		// if (t.getContainerDataSource().getItemIds().size() <
		// t.getPageLength()) {
		// t.setPageLength(t.getContainerDataSource().getItemIds().size());
		// }

		ButtonBar buttons = new ButtonBar();
		buttons.addOKOption().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 3787510734425035145L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (table.getValue() != null) {
					try {
						listener.setValue(table.getValue());
						if (window != null) {
							window.close();
						}
					} catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				} else {
					MessagesUtil.warning(UIContext.getText(LocaleWarnings.SELECT_RECORD));
				}
			}
		});

		buttons.addCancelOption().addClickListener(new ClickListener() {
			private static final long serialVersionUID = -2211798208338732393L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (window != null) {
					window.close();
				}
			}
		});

		table.setPageLength(10);
		table.setCacheRate(0.1);
		table.setSizeFull();
		setSizeFull();

		layout.setSizeFull();
		layout.addComponent(table);
		layout.addComponent(buttons);
		layout.setExpandRatio(table, 1.0f);

		setCompositionRoot(layout);
	}

	@Override
	public void setListener(IFinderViewListener<T> listener) {
		this.listener = listener;
	}

	/**
	 * Mostrar el componente de búsqueda en una ventana.
	 */
	@Override
	public void show() {
		show(null, null);
	}

	/**
	 * Mostrar el componente de búsqueda en una ventana.
	 */
	public void show(String height, String width) {

		window = new Window(UIContext.getText(listener.getTitle()));
		window.setStyleName(Reindeer.WINDOW_LIGHT);
		window.center();
		window.setSizeUndefined();
		window.setHeight(height == null ? "60%" : height);
		window.setWidth(width == null ? "60%" : width);
		window.setContent(this);
		window.getContent().setSizeFull();
		UI.getCurrent().addWindow(window);
	}

}
