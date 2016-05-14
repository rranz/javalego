package com.javalego.ui.vaadin.component;

import com.javalego.ui.vaadin.component.util.DimensionUtil;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Diálogo con opciones de confirmación dependiendo del parámetro readOnly
 * pasado al constructor. ReadOnly = true : sólo OK = Salir y readOnly = false :
 * OK y Cancel.
 * 
 * @author ROBERTO RANZ
 */
public class Dialog extends Window {

	private static final long serialVersionUID = 1L;

	private Component component;

	private String title;

	private boolean margin = true;

	private DimensionUtil dimension;

	private Component panelTitle;

	/**
	 * Constructor
	 * 
	 * @param ui
	 * @param component
	 * @param title
	 * @param confirm
	 * @param autosize
	 *          Ajustar ventana al tamaño del componente pasado como parámetro. El
	 *          caso contrario, se realizaría un scroll y se quedaría siempre
	 *          visible los botones de confirmación.
	 */
	public Dialog(UI ui, Component component, String title, boolean modal, boolean confirm, boolean autosize, ConfirmedListener confirmListener) {
		
		this(component, title, modal, confirm, autosize, confirmListener, true);
	}
	
	/**
	 * Constructor
	 * 
	 * @param owner
	 * @param component
	 * @param title
	 * @param confirm
	 * @param autosize
	 *          Ajustar ventana al tamaño del componente pasado como parámetro. El
	 *          caso contrario, se realizaría un scroll y se quedaría siempre
	 *          visible los botones de confirmación.
	 */
	public Dialog(Component component, String title, boolean modal, boolean confirm, boolean autosize, ConfirmedListener confirmListener) {
		
		this(component, title, modal, confirm, autosize, confirmListener, true);
	}
	
	/**
	 * Constructor
	 * 
	 * @param owner
	 * @param component
	 * @param title
	 * @param confirm
	 * @param autosize
	 *          Ajustar ventana al tamaño del componente pasado como parámetro. El
	 *          caso contrario, se realizaría un scroll y se quedaría siempre
	 *          visible los botones de confirmación.
	 */
	public Dialog(Component component, String title, boolean modal, boolean confirm, boolean autosize, ConfirmedListener confirmListener, boolean margin) {

		this.component = component;
		this.title = title;
		this.margin = margin;
		
		setModal(modal);

		if (autosize)
			generateAutoSize();
		else
			generateScroll();
	}

	/**
	 * Constructor
	 * @param owner
	 * @param component
	 * @param title
	 * @param modal
	 * @param confirm
	 * @param dimension define una clase aplicando una configuración de dimensiones aplicadas a un componente.
	 * @param confirmListener
	 */
	public Dialog(Component component, String title, boolean modal, boolean confirm, DimensionUtil dimension, ConfirmedListener confirmListener) {
		new Dialog(component, title, modal, confirm, dimension, confirmListener, null, true);
	}
	
	/**
	 * Constructor
	 * @param owner
	 * @param component
	 * @param title
	 * @param modal
	 * @param confirm
	 * @param dimension define una clase aplicando una configuración de dimensiones aplicadas a un componente.
	 * @param confirmListener
	 */
	public Dialog(Component component, String title, boolean modal, boolean confirm, DimensionUtil dimension, ConfirmedListener confirmListener, Component panelTitle, boolean margin) {

		this.component = component;
		this.title = title;
		this.panelTitle = panelTitle;
		this.dimension = dimension;
		this.margin = margin;
		//this.scrollComponent = scrollComponent;

		setModal(modal);

		generateScroll();
	}

	
	/**
	 * Generar controles visuales y botones de confirmación incluyendo un scroll
	 * para el componente incluido y dejando siempre visiblas las opciones de
	 * confirmación.
	 */
	private void generateScroll() {

		setCaption(title);
		setSizeUndefined();
		
		VerticalLayout c = new VerticalLayout();
		setSizeUndefined();
		c.setSpacing(false);
		if (margin) {
			c.setMargin(new MarginInfo(true, true, false, true));
		}
		setContent(c);
		
		// Panel del título
		if (panelTitle != null) {
			c.addComponent(panelTitle);
		}

		// Incluir un panel con scrollable = true para que se visualice un scroll en el componente
		c.addComponent(component);
		c.setExpandRatio(component, 1);
		
		//addToolBarAndWindow(c);

		if (dimension != null) {
			dimension.apply(this);
		}
	}

	/**
	 * Generar controles visuales y botones de confirmación con ventana autosize
	 * al tamaño máximo del componente incluido.
	 */
	private void generateAutoSize() {

		setCaption(title);

		VerticalLayout c = new VerticalLayout();
		c.setSizeUndefined();
		c.setSpacing(false);
		if (margin) {
			c.setMargin(new MarginInfo(true, true, false, true));
		}
		setContent(c);
		
		if (!margin)
			c.setMargin(false);

		if (component != null)
			c.addComponent(component);
	}

	/**
	 * Listener for dialog close events. Implement and register an instance of
	 * this interface to dialog to receive close events.
	 * 
	 * @author Sami Ekblad
	 * 
	 */
	public interface ConfirmedListener {
		boolean onClose(boolean confirmed) throws Exception;
	}	
}
