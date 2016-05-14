package com.javalego.ui.vaadin.component.util;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

/**
 * Clase que nos permite definir las dimensiones que queremos aplicar a un componente de forma específica o 
 * porcentual con respecto a la ventana principal o ventana definida.
 * @author ROBERTO RANZ
 */
public class DimensionUtil {
	
	private double percentHeight;
	
	private double percentWidth;
	
	private int height;
	
	private int width;

	/**
	 * Dimensión en porcentaje.
	 * @param percentHeight
	 * @param percentWidth
	 */
	public DimensionUtil(double percentHeight, double percentWidth) {
		this.percentHeight = percentHeight;
		this.percentWidth = percentWidth;
	}

	/**
	 * Dimensión en porcentaje
	 * @param percentHeight
	 * @param percentWidth
	 * @param height
	 * @param width
	 */
	public DimensionUtil(double percentHeight, double percentWidth, int height, int width) {
		this.percentHeight = percentHeight;
		this.percentWidth = percentWidth;
		this.height = height;
		this.width = width;
	}

	/**
	 * Dimensión en píxeles
	 * @param height
	 * @param width
	 */
	public DimensionUtil(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * Dimensión en porcentaje con respecto a la ventana principal.
	 * @param percentHeight
	 * @param width
	 */
	public DimensionUtil(double percentHeight, int width) {
		this.percentHeight = percentHeight;
		this.width = width;
	}

	/**
	 * Aplicar dimensiones a un componente con respecto a una ventana owner.
	 * @param window
	 * @param component
	 */
	public void apply(Component component) {
		
		// Height
		if (percentHeight > 0) {
			
			double height = UI.getCurrent().getHeight() < 0 ? BrowserUtil.getHeight() : UI.getCurrent().getHeight();
			
			// TODO: si abrimos un diálogo sin incluir componentes en la ventana principal height = -1 o 0
			if (height < 1)
				height = 740;
			
			component.setHeight((int)((height * percentHeight) / 100), Unit.PERCENTAGE);
		}
		else if (height > 0) {
			component.setHeight(height, Unit.PIXELS);
		}
		
		// Width
		if (percentWidth > 0) {

			double width = UI.getCurrent().getWidth() < 0 ? BrowserUtil.getWidth() : UI.getCurrent().getWidth();
			
			// TODO: si abrimos un diálogo sin incluir componentes en la ventana principal width = -1 o 0
			if (width < 1)
				width = 1024;

			component.setWidth((int)((width * percentWidth) / 100), Unit.PERCENTAGE);
		}
		else if (width > 0) {
			component.setWidth(width, Unit.PIXELS);
		}
	}
	
	public double getPercentHeight() {
		return percentHeight;
	}

	public void setPercentHeight(double percentHeight) {
		this.percentHeight = percentHeight;
	}

	public double getPercentWidth() {
		return percentWidth;
	}

	public void setPercentWidth(double percentWidth) {
		this.percentWidth = percentWidth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
