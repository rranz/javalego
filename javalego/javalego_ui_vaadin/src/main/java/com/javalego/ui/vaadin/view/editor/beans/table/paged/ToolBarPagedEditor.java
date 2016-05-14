package com.javalego.ui.vaadin.view.editor.beans.table.paged;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.UIContext;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.mvp.beans.view.paged.IPagedBeansView.PagedBeansViewListener;
import com.javalego.ui.vaadin.component.util.Html;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Barra de opciones para el editor de objetos.
 * 
 * @author ROBERTO RANZ
 */
public class ToolBarPagedEditor<T> extends CssLayout {

	private static final long serialVersionUID = 1L;

	private PagedBeansViewListener<T> editor;

	private Label labelPages;

	private TextField textPage;

	private boolean small;

	/**
	 * Constructor
	 * 
	 * @param editor
	 * @throws LocalizedException
	 */
	public ToolBarPagedEditor(PagedBeansViewListener<T> editor) throws LocalizedException {
		this(editor, false);
	}

	/**
	 * Constructor
	 * 
	 * @param editor
	 * @throws LocalizedException
	 */
	public ToolBarPagedEditor(PagedBeansViewListener<T> editor, boolean small) throws LocalizedException {
		this.editor = editor;
		this.small = small;
		init();
	}

	/**
	 * Añadir opciones asociadas al editor.
	 * 
	 * @throws LocalizedException
	 */
	private void init() throws LocalizedException {

		addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		// addStyleName(CssVaadin.getBorderRounded());

		addFirst();

		addPrior();

		if (!small) {
			addGoPage();
		}

		addNext();

		addLast();

		addRefresh();

		if (!small) {
			addStatus();
		}
	}

	/**
	 * Actualizar datos del Toolbar en base a los objetos actuales cargados en
	 * el editor.
	 * 
	 * @throws LocalizedException
	 */
	public void refresh() throws LocalizedException {

		if (small)
			return;

		textPage.setValue(new Integer(editor.getCurrentPage()).toString());

		labelPages.setValue(getLabel());
	}

	private String getLabel() throws LocalizedException {
		return Html.getSpace(2) + UIContext.getText(LocaleEditor.PAGES) + ": " + editor.getCountPages() + " " + UIContext.getText(LocaleEditor.RECORDS) + ": " + editor.getCountBeans();
	}

	/**
	 * Texto que muestra el número de páginas y número de registros.
	 * 
	 * @throws LocalizedException
	 */
	private void addStatus() throws LocalizedException {

		labelPages = new Label(getLabel(), ContentMode.HTML);
		labelPages.setSizeUndefined();
		labelPages.addStyleName(ValoTheme.LABEL_TINY);
		addComponent(labelPages);
	}

	/**
	 * Ir a página
	 * 
	 * @throws LocalizedException
	 */
	private void addGoPage() throws LocalizedException {

		textPage = new TextField();
		textPage.addStyleName(ValoTheme.TEXTAREA_LARGE);
		textPage.setImmediate(true);
		textPage.setEnabled(false);
		textPage.setDescription("Número de página actual. Si desea cambiar este número introduzca el número de página y pulse la tecla ENTER");
		textPage.selectAll();
		textPage.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = -2553185358832899236L;

			@Override
			public void focus(FocusEvent event) {
				((TextField) event.getComponent()).selectAll();
			}
		});
		textPage.setColumns(3);

		textPage.addShortcutListener(new ShortcutListener("paged_number", ShortcutAction.KeyCode.ENTER, null) {
			private static final long serialVersionUID = -2772444127491453945L;

			@Override
			public void handleAction(Object sender, Object target) {
				try {
					if (target == textPage && textPage.getValue() != null) {
						int number = textPage.getConvertedValue() instanceof Integer ? (Integer) textPage.getConvertedValue() : Integer.parseInt((String) textPage.getValue());
						try {
							editor.loadPage(number);
						}
						catch (LocalizedException e) {
							MessagesUtil.error(e);
						}
					}
				}
				catch (NumberFormatException e) {
				}
			}
		});
		addComponent(textPage);
	}

	protected int getIndexPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Primera página
	 * @throws LocalizedException 
	 */
	private void addFirst() throws LocalizedException {

		Button btn = getButton(IconEditor.FIRST, LocaleEditor.FIRST_PAGE);
		btn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 436895037947429585L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					editor.firstPage();
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});
		addComponent(btn);
	}

	/**
	 * Página anterior
	 * @throws LocalizedException 
	 */
	private void addPrior() throws LocalizedException {

		Button btn = getButton(IconEditor.PREVIOUS, LocaleEditor.PRIOR_PAGE);
		btn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -3781909383365771060L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					editor.priorPage();
					if (!small) {
						refresh();
					}
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});
		addComponent(btn);
	}

	/**
	 * Siguiente página
	 * @throws LocalizedException 
	 */
	private void addNext() throws LocalizedException {

		Button btn = getButton(IconEditor.NEXT, LocaleEditor.NEXT_PAGE);
		btn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6228301487041186446L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					editor.nextPage();
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});
		addComponent(btn);
	}

	/**
	 * Última página
	 * @throws LocalizedException 
	 */
	private void addLast() throws LocalizedException {

		Button btn = getButton(IconEditor.LAST, LocaleEditor.LAST_PAGE);
		btn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 45904083748651322L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					editor.lastPage();
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});
		addComponent(btn);
	}

	/**
	 * Refresh
	 * @throws LocalizedException 
	 */
	private void addRefresh() throws LocalizedException {

		Button btn = getButton(IconEditor.REFRESH, LocaleEditor.REFRESH);
		btn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -651644256246889183L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					editor.refreshPage();
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});
		addComponent(btn);
	}

	/**
	 * Obtener el componente de la acción en base a un icono y descripción.
	 * @param icon
	 * @param description
	 * @return
	 * @throws LocalizedException
	 */
	private Button getButton(Icon icon, Key description) throws LocalizedException {

		Button btn = new Button();
		
		btn.setDescription(UIContext.getText(description));
		
		btn.setIcon(ResourceIconsVaadin.getCurrent().getResource(icon));
		
		btn.setStyleName(ValoTheme.BUTTON_LARGE);

		return btn;
	}

	/**
	 * No incluye número página y status
	 * @return
	 */
	public boolean isSmall() {
		return small;
	}

	/**
	 * No incluye número página y status
	 * @return
	 */
	public void setSmall(boolean small) {
		this.small = small;
	}

}
