package com.javalego.store.ui.editor;

import com.javalego.exception.LocalizedException;
import com.javalego.store.items.IBaseItem;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorObserver;
import com.vaadin.ui.Component;


/**
 * Interface básica del editor de items en sus diferentes tipologías.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IItemEditor<T> extends Component {

	/**
	 * Cargar item
	 * @throws LocalizedException
	 */
	void load() throws LocalizedException;

	/**
	 * Observer para notificar la edición de un item
	 * @param observer
	 */
	void setObserver(BeanEditorObserver<T> observer);
	
	/**
	 * Item editado
	 * @return
	 */
	IBaseItem getItem();
}
