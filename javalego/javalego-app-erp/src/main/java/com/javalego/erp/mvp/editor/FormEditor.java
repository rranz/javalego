package com.javalego.erp.mvp.editor;

import com.javalego.erp.model.Icons;
import com.javalego.erp.mvp.ErpDemoPresenter;
import com.javalego.exception.LocalizedException;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.editor.beans.BeansEditorPresenter;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.patterns.IView;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.view.editor.beans.BeansEditorView;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Formulario del editor de beans
 * 
 * @author ROBERTO RANZ
 */
public class FormEditor extends VerticalLayout implements View, IView {

	public static final String NAME = "FORMDEMO";

	private BeansEditorPresenter<?> presenter;

	/**
	 * Constructor
	 * 
	 * @param navigator
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FormEditor(IBeansEditorModel<?> model) throws LocalizedException {

		// config
		setMargin(true);
		setSpacing(true);
		
		BeansEditorView view = new BeansEditorView();
        
		// presenter
		presenter = new BeansEditorPresenter(model, view);

		// título
		Label section = new Label("<strong>" + UIContext.getText(presenter.getTitle()) + "</strong>", ContentMode.HTML);
        section.addStyleName("h2");
        section.addStyleName("colored");		
		
		HorizontalLayout layout = new HorizontalLayout();
		
		layout.addComponent(section);
		CssButton btn = new CssButton(Icons.HOME);
		btn.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(ErpDemoPresenter.HOMEVIEW);
			}
		});
		
		layout.addComponent(btn);
		layout.setWidth("100%");
		layout.setExpandRatio(section, 1);		
		
		addComponent(layout);
		
		// vista
		addComponent((Component) presenter.getView());
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	@Override
	public void load() throws LocalizedException {
		presenter.load();
	}

}
