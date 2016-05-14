package com.javalego.ui.vaadin.component.listview;

import java.util.List;

import com.vaadin.ui.Component;

/**
 * Item de una lista de componentes
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IItemListView {

	/**
	 * Texto del item en formato Html.
	 * @return
	 */
	String getHtml();

	/**
	 * Icono
	 * @return
	 */
	String getIcon();

	/**
	 * Lista de componentes adicionales
	 * @return
	 */
	List<Component> getComponents();

	/**
	 * Valor del item
	 * @return
	 */
	Object getValue();

}
