package com.javalego.ui.vaadin.component.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.javalego.util.DateUtils;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Butón que representa un día. Permite su personalización por colores y si está o no seleccionado 
 * @see CalendarPanel
 * @author ROBERTO RANZ
 */
public class DayButton extends CustomComponent {
	
	private static final long serialVersionUID = 1L;

	private VerticalLayout layout;
	
	private Label label;
	
	/**
	 * Fecha que representa el componente.
	 */
	private Date day;

	/**
	 * Día seleccionado.
	 */
	private boolean selected;

	private String color;
	
	/**
	 * Constructor
	 * @param day
	 */
	public DayButton(Date day) {
		this.day = day;
		init();
	}	

	/**
	 * Constructor
	 * @param day
	 * @param month
	 * @param year
	 */
	public DayButton(int day, int month, int year) {
		
//		java.util.Calendar c = java.util.Calendar.getInstance();
//		c.set(java.util.Calendar.YEAR, year);
//		c.set(java.util.Calendar.MONTH, month);
//		c.set(java.util.Calendar.DAY_OF_MONTH, day);
		
		this.day = DateUtils.getDate(year, month, day);		
		init();
	}

	@Override
	public void addStyleName(String style) {
		layout.addStyleName(style);
	}

	@Override
	public void removeStyleName(String style) {
		layout.removeStyleName(style);
	}	
	
	@Override
	public void setCaption(String caption) {
		label.setCaption(caption);
		
	}

	/**
	 * Inicializar componente
	 */
	private void init() {
		
		layout = new VerticalLayout();
		
		layout.setSizeUndefined();
		setCompositionRoot(layout);
		label = new Label(new SimpleDateFormat("d").format(day.getTime()));
		label.setWidth("-1px");
		
		layout.setWidth("30px");
		
		label.addStyleName("rounded-gana-button-label");
		
		layout.addStyleName("rounded-gana-button");
		
		layout.addComponent(label);
		
		layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}

	/**
	 * Layout donde se instancia la etiqueta.
	 * Podremos añadir un evento de tipo LayoutClickListener() para gestionar el click del usuario al pulsar sobre la etiqueta.
	 * @return
	 */
	public VerticalLayout getLayout() {
		return layout;
	}

	/**
	 * Label que representa el botón
	 * @return
	 */
	public Label getLabel() {
		return label;
	}
	
	public void setRedColor() {
		setColor("red");
	}
	
	public void setAquaColor() {
		setColor("aqua");
	}
	
	public void setGreenColor() {
		setColor("green");
	}
	
	public void setBlueColor() {
		setColor("blue");
	}
	
	public void setSilverColor() {
		setColor("silver");
	}
	
	public void setWhiteColor() {
		setColor("white");
	}
	
	/**
	 * Establecer el color del día
	 * @param color
	 */
	public void setColor(String color) {
		
		//System.out.println(color);
		
		if (this.color != null)
			removeStyleName("button_" + this.color);
		
		addStyleName("button_" + color);
		
		this.color = color;
	}

	/**
	 * Día seleccionado
	 * @return
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Seleccionar el día.
	 * @param selected
	 */
	public void setSelected(boolean selected, boolean holidays) {
		
		this.selected = selected;
		if (selected)
			setCheckColor();
		else
			setUnCheckColor(holidays);
	}

	/**
	 * Marcar el día
	 */
	private void setUnCheckColor(boolean holidays) {
	
		if (holidays && (DateUtils.isSaturday(day) || DateUtils.isSunday(day)) ) 
			setAquaColor();
		else
			setWhiteColor();
	}

	/**
	 * Desmarcar el día
	 */
	private void setCheckColor() {
		setGreenColor();
		
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

}
