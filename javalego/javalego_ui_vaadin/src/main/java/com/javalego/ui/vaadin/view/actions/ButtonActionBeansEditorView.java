package com.javalego.ui.vaadin.view.actions;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.actions.IActionBeansEditor;
import com.javalego.ui.mvp.editor.beans.BeansEditorPresenter;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansEditorViewListener;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.MessagesUtil;

/**
 * Acción básica basada en un Button asociada a una acción y editor de beans.
 * 
 * @see BeansEditorPresenter
 * 
 * @author ROBERTO RANZ
 *
 */
public class ButtonActionBeansEditorView<T> extends ButtonExt {

	private static final long serialVersionUID = -2540309288958866075L;

	protected IActionBeansEditor action = null;
	
	private BeansEditorViewListener<T> listener = null;
	
	/**
	 * Constructor
	 * @param action
	 * @param listener
	 */
	public ButtonActionBeansEditorView(IActionBeansEditor action, BeansEditorViewListener<T> listener) {

		super(action.getTitle(), action.getDescription(), action.getIcon());
		
		bordeless();
		
		this.action = action;
		this.listener = listener;
		
		addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1870831557328865618L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					getAction().execute(getListener());
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});
	}

	public IActionBeansEditor getAction() {
		return action;
	}

	public BeansEditorViewListener<T> getListener() {
		return listener;
	}

}
