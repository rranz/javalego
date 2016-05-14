package com.javalego.ui.vaadin.component.search;

import com.javalego.ui.vaadin.component.AbstractFieldActions;
import com.javalego.util.StringUtils;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;

/**
 * Buscador de objetos en una lista por un texto introducido por el usuario.
 * 
 * La búsqueda se realizará cuando el usuario pulse la tecla intro. 
 * 
 * El sistema buscará desde el elemento actualmente seleccionado en la lista o desde el principio si no hay ninguno.
 * 
 * @author ROBERTO RANZ
 *
 */
public class TextFieldFind extends AbstractFieldActions {

	private static final long serialVersionUID = -6938819177780234563L;

	/**
	 * Interface que deben implementar las clases que requieran un sistema de búsqueda o filtros de objetos.
	 */
	private ISearch search;

	/**
	 * Constructor
	 * @param search
	 */
	public TextFieldFind(ISearch search) {

		this.search = search;

		getTextField().setColumns(16);
		
		getTextField().focus();

		setImmediate(true);

		getTextField().addShortcutListener(new ShortcutListener("textfieldfind_find", ShortcutAction.KeyCode.ENTER, null) {
			private static final long serialVersionUID = -2524454189526671920L;

			@Override
			public void handleAction(Object sender, Object target) {
				if (target == getTextField()) {
					find();
				}
			}
		});
		
//		addAction(ToolBarUtil.getButton(ICollectionImages.TABCLOSE, "remove_text", new LayoutClickListener() {
//			@Override
//			public void layoutClick(LayoutClickEvent event) {
//				getTextField().setValue(null);
//				find();
//				getTextField().focus();
//			}
//		}));

		//addAction(MessagesUtil.getHelpButton("Pulse la tecla ENTER para localizar el texto definido dentro de la lista de elementos."));
		
		setCaption("Find");
	}

	/**
	 * Localizar texto en la lista de objetos.
	 */
	public void find() {
		
		if (search.isFilter() || !StringUtils.isEmpty(getTextField().getValue())) {
			search.find((String)getTextField().getValue());
		}
	}
}
