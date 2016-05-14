package com.javalego.store.ui.demos;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.editor.beans.BeansEditorPresenter;
import com.javalego.ui.patterns.IView;
import com.javalego.ui.vaadin.component.util.Html.H1;
import com.javalego.ui.vaadin.view.editor.beans.BeansEditorView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import entities.Empresa;

/**
 * Formulario de mantenimiento de Empresas
 * 
 * @author ROBERTO RANZ
 */
public class FormEmpresas extends CustomComponent implements View, IView {

	private static final long serialVersionUID = 2309076083044608810L;

	public static final String NAME = "FORMDEMO";

	private BeansEditorPresenter<Empresa> presenter;
	
	/**
	 * Constructor
	 * 
	 * @param navigator
	 */
	public FormEmpresas() throws LocalizedException {

		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		presenter = new BeansEditorPresenter<Empresa>(new EditorEmpresas(), new BeansEditorView<Empresa>());
		
		layout.addComponent(new H1(UIContext.getText(presenter.getTitle())));
		
		layout.addComponent((Component) presenter.getView());

		setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	@Override
	public void load() throws LocalizedException {
		presenter.load();
	}

}
