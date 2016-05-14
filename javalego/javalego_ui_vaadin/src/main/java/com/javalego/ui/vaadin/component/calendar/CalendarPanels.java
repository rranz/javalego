package com.javalego.ui.vaadin.component.calendar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.javalego.util.DateUtils;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * Paneles de calendario configurable por unidades.
 * Ejemplo:
 
			final CalendarPanels c2 = new CalendarPanels(2012, 1, 6, 3, true, new Date[] {DateUtils.getDate(2012, 3, 2), DateUtils.getDate(2012, 3, 12)});
			main.addComponent(c2.getLayout());
			Button b = new Button("Ver");
			b.addListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					System.out.println(c2.getSelectedDates() + "   " + c2.getSelectedDates().size());
					System.out.println(c2.getRemoveDates() +  "  " + c2.getRemoveDates().size());
				}
			});
 
 * @author ROBERTO RANZ
 */
public class CalendarPanels implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int year;
	private int initMonth;
	private int endMonth;
	private Component component;
	private int step;
	private boolean tabs;
	private boolean holidays = true;

	/**
	 * Paneles de calendarios por cada mes incluido 
	 */
	private List<CalendarPanel> panels = new ArrayList<CalendarPanel>();
	
	/**
	 * Días seleccionados en la creación del panel de fechas del mes.
	 */
	private Date[] initSelectedDates;

	/**
	 * Constructor
	 * @param year
	 * @param initMonth
	 * @param endMonth
	 * @param step Crea un grid con un número de meses por fila
	 */
	public CalendarPanels(int year, int initMonth, int endMonth, int step, boolean holidays) {
		this(year, initMonth, endMonth, step, holidays, null, false);
	}
	
	/**
	 * Constructor
	 * @param year
	 * @param initMonth
	 * @param endMonth
	 * @param step Crea un grid con un número de meses por fila
	 */
	public CalendarPanels(int year, int initMonth, int endMonth, int step, boolean holidays, Date[] dates) {
		this(year, initMonth, endMonth, step, holidays, dates, false);
	}
	
	/**
	 * Constructor
	 * @param year
	 * @param initMonth
	 * @param endMonth
	 * @param step Crea un grid con un número de meses por fila
	 */
	public CalendarPanels(int year, int initMonth, int endMonth, int step, boolean holidays, Date[] dates, boolean tabs) {
		
		this.year = year;
		this.initMonth = initMonth;
		this.endMonth = endMonth;
		this.step = step;
		this.holidays = holidays;
		this.initSelectedDates = dates;
		this.tabs = tabs;
	}

	/**
	 * Obtener layout con los paneles de los meses definidos
	 * @return
	 */
	public Component getComponent() throws Exception {
		
		validate();
		
		component = step > 0 ? getInternalComponent() : getVerticalLayout();
		
		return component;
	}
	
	/**
	 * Validar steps y meses definidos.
	 * @throws Exception
	 */
	private void validate() throws Exception {
	
		if (step > 12)
			throw new Exception("Número de distribución de paneles superior a 12. No válido.");
		
		else if (endMonth > 12)
			throw new Exception("Número mes final del año superior a 12. No válido.");
		
		else if (initMonth < 1)
			throw new Exception("Número mes inicial del año inferior a 1. No válido.");
		
		else if (initMonth > 12)
			throw new Exception("Número mes inicial del año superior a 12. No válido.");
		
		else if (endMonth < initMonth)
			throw new Exception("Número mes final de mes inferior al inicial. No válido.");
		
	}

	/**
	 * Generar calendarios mensuales con los meses definidos cuando hay steps definidos.
	 * @return
	 */
	private Component getInternalComponent() {
		
		GridLayout layout = new GridLayout(step, ((endMonth - initMonth) / step) + 1);
		layout.setSizeUndefined();
		layout.setSpacing(true);
		int row = 0;
		int init_month = 1;
		
		TabSheet tabs = null;
		if (this.tabs) {
			tabs = new TabSheet();
		}
		
		for(int i = initMonth; i < endMonth+1; i += step) {
			
			for(int k = 0; k < step; k++) {
				
				java.util.Calendar c = getDate(year, i + k);			
				
				VerticalLayout v = new VerticalLayout();
				v.setSizeUndefined();
				v.setMargin(true);
				v.setSpacing(true);
				
				Label label = new Label("<h3>" + new SimpleDateFormat("MMMM yyyy").format(c.getTime()) + "</h3>", ContentMode.HTML);
				label.setSizeUndefined();
				label.addStyleName("rounded-corners-weekdays");
				label.setWidth("100%");
				
				v.addComponent(label);
				v.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
				
				CalendarPanel calendarPanel = new CalendarPanel(holidays, initSelectedDates);
				panels.add(calendarPanel);
				
				v.addComponent(calendarPanel.getComponent(c.getTime()));
				
				layout.addComponent(v, k, row);
			}
			
			if (tabs != null) {
				tabs.addTab(layout, "Meses de " + getStringMonth(year, init_month) + " a " + getStringMonth(year, (i+step-1)));
				layout = new GridLayout(step, ((endMonth - initMonth) / step) + 1);
				row = 0;
				init_month = (i+step);
			}
			else {
				++row;
			}
		}
		
		this.component = layout;
		
		return tabs != null ? tabs : layout;
	}

	/**
	 * Texto del mes
	 * @param year
	 * @param month
	 * @return
	 */
	private String getStringMonth(int year, int month) {
		
		return new SimpleDateFormat("MMMM yyyy").format(DateUtils.getDate(year, month, 1));		
	}
	
	/**
	 * Generar calendarios en línea vertical cuando no hay steps definidos
	 * @return
	 */
	private VerticalLayout getVerticalLayout() {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		
		for(int i = initMonth; i < endMonth+1; i++) {
			
			java.util.Calendar c = getDate(year, i);
			Label l = new Label(new SimpleDateFormat("MMMM   yyyy").format(c.getTime()));
			l.setSizeUndefined();
			layout.addComponent(l);
			layout.addComponent(new CalendarPanel(holidays).getComponent(c.getTime()));
		}
		
		this.component = layout;
		
		return layout;
	}

	private java.util.Calendar getDate(int year, int month) {
		
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.set(java.util.Calendar.YEAR, year);
		c.set(java.util.Calendar.MONTH, month-1);
		c.set(java.util.Calendar.DAY_OF_MONTH, 1);
		c.getTime();
		return c;
	}

	public boolean isHolidays() {
		return holidays;
	}

	public void setHolidays(boolean holidays) {
		this.holidays = holidays;
	}
	
	/**
	 * Obtener la lista de fechas seleccionadas.
	 * 
	 * @return
	 */
	public List<Date> getSelectedDates() {

		List<Date> dates = new ArrayList<Date>();

		for (CalendarPanel panel : panels) {
			dates.addAll(panel.getSelectedDates());
		}

		return dates;
	}

	/**
	 * Obtener las fechas eliminadas con respecto a las fechas seleccionadas al
	 * inicio de la edición.
	 * 
	 * @return
	 */
	public List<Date> getRemoveDates() {

		List<Date> dates = new ArrayList<Date>();

		for (CalendarPanel panel : panels) {
			dates.addAll(panel.getRemoveDates());
		}

		return dates;
	}
	
}
