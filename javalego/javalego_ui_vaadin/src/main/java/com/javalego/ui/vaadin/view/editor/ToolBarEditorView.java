package com.javalego.ui.vaadin.view.editor;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.model.locales.LocaleWarnings;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.editor.ICrudListener;
import com.javalego.ui.vaadin.component.buttonbar.ButtonBar;
import com.javalego.ui.vaadin.component.util.DialogUtil;
import com.javalego.ui.vaadin.component.util.MessageBox.ButtonType;
import com.javalego.ui.vaadin.component.util.MessageBox.EventListener;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Barra de opciones del editor.
 * 
 * @author q74749x
 *
 */
public class ToolBarEditorView extends ButtonBar {

	private static final long serialVersionUID = 2795817441294319973L;

	/**
	 * Listener para generar eventos básicos de un mantenimiento (CRUD)
	 */
	protected ICrudListener listener;

	/**
	 * Confirmar al pulsar la opción de borrado.
	 */
	private boolean remove_confirm = true;

	public ToolBarEditorView(ICrudListener listener) {
		this.listener = listener;
		init();
	}

	/**
	 * Inicializar componentes.
	 */
	private void init() {

		Button btn = addButtonOption(UIContext.getText(listener.isReadOnly() ? LocaleEditor.EXIT : LocaleEditor.OK));

		btn.addStyleName(ValoTheme.BUTTON_PRIMARY);

		btn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -8728680540245396098L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					if (listener.isReadOnly()) {
						listener.discard();
					}
					else {
						listener.commit();
					}
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});

		if (!listener.isReadOnly()) {

			addButtonOption(UIContext.getText(LocaleEditor.CANCEL)).addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1040183162346366383L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						listener.discard();
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}
			});
		}

		if (!listener.isReadOnly() && listener.hasRemove()) {

			addButtonOption(UIContext.getText(LocaleEditor.DELETE)).addClickListener(new ClickListener() {
				private static final long serialVersionUID = 968236342201973317L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						if (remove_confirm) {

							DialogUtil.getConfirm(UIContext.getText(LocaleWarnings.DELETE), new EventListener() {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean buttonClicked(ButtonType buttonType) throws LocalizedException {
									if (buttonType == ButtonType.YES) {
										listener.remove();
									}
									return true;
								}
							});
						}
						else {
							listener.remove();
						}
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}
			});

		}

	}

	public boolean isRemove_confirm() {
		return remove_confirm;
	}

	public void setRemove_confirm(boolean remove_confirm) {
		this.remove_confirm = remove_confirm;
	}
}
