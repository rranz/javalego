package com.javalego.ui.vaadin.component.buttonbar;

import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.LayoutUtils;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;

/**
 * Barra de botones utilizada para incluir opciones de confirmación, acciones, ...
 * 
 * Este componente incluye la posibilidad de incluir las opciones comunes de OK, Cancel
 * 
 * @author ROBERTO
 *
 */
public class ButtonBar extends CustomComponent {
	
	private static final long serialVersionUID = -608776366718534763L;

	private CssLayout layout;
	
	public ButtonBar() {
		init();
	}

	private void init() {
		
		layout = LayoutUtils.getCssLayout(true, "margin: 8px; vertical-align: top;", false);

		setCompositionRoot(layout);
	}

	/**
	 * Añadir opción OK
	 * @return
	 */
	public ButtonExt addOKOption() {
		
		ButtonExt ok = new ButtonExt(LocaleEditor.OK).green();
		layout.addComponent(ok);
		
		return ok;
	}
	
	/**
	 * Añadir opción Cancel
	 * @return
	 */
	public ButtonExt addCancelOption() {
		
		ButtonExt ok = new ButtonExt(LocaleEditor.CANCEL);
		layout.addComponent(ok);
		
		return ok;
	}
	
	/**
	 * Añadir opción
	 * @return
	 */
	public ButtonExt addButtonOption(String text) {
		
		ButtonExt button = new ButtonExt(text);
		layout.addComponent(button);
		
		return button;
	}

	@Override
	public void setEnabled(boolean enabled) {
		
		super.setEnabled(enabled);
		
		for(int i = 0; i < layout.getComponentCount(); i++) {
			layout.getComponent(i).setEnabled(enabled);
		}
	}
}
