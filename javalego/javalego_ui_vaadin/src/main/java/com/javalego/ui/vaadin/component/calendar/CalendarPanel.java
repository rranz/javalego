package com.javalego.ui.vaadin.component.calendar;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.javalego.util.DateUtils;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;

/**
 * Clase que representa un calendario donde se pueden gestionar de forma
 * individualizada sus días (colores, ...)
 * Ejemplo:
 
			final CalendarPanel c2 = new CalendarPanel();
			main.addComponent(c2.getComponent(2012, 4, true, new Date[] {DateUtils.getDate(2012, 4, 2), DateUtils.getDate(2012, 4, 12)}));
			Button b = new Button("Ver");
			b.addListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					System.out.println(c2.getSelectedDates() + "   " + c2.getSelectedDates().size());
					System.out.println(c2.getRemoveDates() +  "  " + c2.getRemoveDates().size());
				}
			});
 * 
 * @author ROBERTO RANZ
 */
public class CalendarPanel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date = new Date();

	private GridLayout grid = null;

	/**
	 * No permite marcar días.
	 */
	private boolean readOnly;

	/**
	 * Lista de componentes visuales de los días del mes generados para
	 * representar el mes.
	 */
	private List<DayButton> buttons = new ArrayList<DayButton>();

	private boolean holidays;

	/**
	 * Días seleccionados en la creación del panel de fechas del mes.
	 */
	private Date[] initSelectedDates;

	/**
	 * Constructor
	 * @param holidays
	 */
	public CalendarPanel(boolean holidays) {
		this.holidays = holidays;
	}

	/**
	 * Constructor
	 * @param holidays
	 * @param dates
	 */
	public CalendarPanel(boolean holidays, Date[] dates) {
		this.holidays = holidays;
		this.initSelectedDates = dates;
	}

	public CalendarPanel() {
	}

	/**
	 * Obtener componente visual con la fecha pasada como parámetro.
	 * 
	 * @param year
	 * @param month
	 * @param holidays
	 */
	public Layout getComponent(int year, int month, boolean holidays) {

		this.date = DateUtils.getDate(year, month, 1);
		this.holidays = holidays;

		return getComponent();

	}

	/**
	 * Obtener componente visual con la fecha pasada como parámetro y seleccionar
	 * los días pasados como parámetro.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param dates
	 * @return
	 */
	public Component getComponent(int year, int month, boolean holidays, Date[] dates) {

		this.date = DateUtils.getDate(year, month, 1);

		this.holidays = holidays;

		this.initSelectedDates = dates;

		return getComponent();
	}

	/**
	 * Obtener componente visual con la fecha pasada como parámetro.
	 * 
	 * @param date
	 * @return
	 */
	public Layout getComponent(int year, int month) {

		this.date = DateUtils.getDate(year, month, 1);

		return getComponent();

	}

	/**
	 * Obtener componente visual con la fecha pasada como parámetro.
	 * 
	 * @param date
	 * @return
	 */
	public Layout getComponent(Date date) {

		this.date = date;

		return getComponent();
	}

	/**
	 * Obtener componente visual
	 * 
	 * @return
	 */
	public Layout getComponent() {

		if (this.date == null)
			return null;

		grid = new GridLayout(7, 8);
		grid.setSizeUndefined();
		grid.addStyleName("gradient_blue");
		grid.setMargin(true);

		drawCalendar();

		return grid;
	}

	/**
	 * Dibujar las cabeceras de los días y los días del mes.
	 */
	private void drawCalendar() {

		int year = getYear();
		int month = getMonth();

		drawHeaders();

		drawDays(year, month);
	}

	/**
	 * Pintar los días del mes.
	 * 
	 * @param year
	 * @param month
	 */
	private void drawDays(int year, int month) {

		java.util.Calendar c = java.util.Calendar.getInstance();
		c.set(java.util.Calendar.YEAR, year);
		c.set(java.util.Calendar.MONTH, month);
		c.set(java.util.Calendar.DAY_OF_MONTH, 1);
		c.getTime();

		int firstDay = c.get(java.util.Calendar.DAY_OF_WEEK) == 1 ? 7 : c.get(java.util.Calendar.DAY_OF_WEEK) - 1;

		int numOfDays = getDaysInMonth(year, month);

		int j = 0;
		for (int i = 1; i < 7; i++) {

			for (int k = 0; k < 7; k++, j++) {

				int displayNum = ((j + 1) - firstDay);

				if (j < (firstDay - 1) || displayNum >= numOfDays) {
					Label l = new Label();
					l.setSizeUndefined();
					grid.addComponent(l, k, i);
				} else {

					final DayButton btn = new DayButton(displayNum + 1, month, year);
					btn.setSizeUndefined();
					btn.setSelected(initSelectedDates == null ? false : isInitDates(btn.getDay()), holidays);

					buttons.add(btn);

					// Seleccionar días.
					btn.getLayout().addLayoutClickListener(new LayoutClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void layoutClick(LayoutClickEvent event) {
							if (!readOnly && !event.isDoubleClick())
								btn.setSelected(!(btn.isSelected()), holidays);
						}
					});

					grid.addComponent(btn, k, i);
					grid.setSpacing(true);
					//grid.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
				}
			}
		}
	}

	/**
	 * Comprueba si la fecha está comprendida en las fechas iniciales
	 * seleccionadas.
	 * 
	 * @param day
	 * @return
	 */
	private boolean isInitDates(Date day) {

		if (initSelectedDates == null)
			return false;

		for (Date date : initSelectedDates) {
			if (date.getTime() == day.getTime())
				return true;
		}

		return false;
	}

	/**
	 * Obtener la lista de fechas seleccionadas.
	 * 
	 * @return
	 */
	public List<Date> getSelectedDates() {

		List<Date> dates = new ArrayList<Date>();

		for (DayButton button : buttons) {
			if (button.isSelected())
				dates.add(button.getDay());
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

		for (DayButton button : buttons) {
			if (!button.isSelected() && existInitSelecteDates(button.getDay()))
				dates.add(button.getDay());
		}

		return dates;
	}

	/**
	 * Comprobar si una fecha está dentro de las fechas inicialmente
	 * seleccionadas.
	 * 
	 * @param date
	 * @return
	 */
	public boolean existInitSelecteDates(Date date) {
		
		for (Date _date : initSelectedDates) {
			if (_date.getTime() == date.getTime()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Pintar cabeceras de los días de la semana
	 */
	private void drawHeaders() {

		// TODO: Generar los días de la semana en la localización del navegador.
		
		Locale usersLocale = Locale.getDefault();

    DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
    String weekdays[] = dfs.getWeekdays();

    java.util.Calendar cal = java.util.Calendar.getInstance(usersLocale);

    int firstDayOfWeek = cal.getFirstDayOfWeek();
    int dayOfWeek;

    int pos = 0;
    for (dayOfWeek = firstDayOfWeek; dayOfWeek < weekdays.length; dayOfWeek++) {
			generateLabel(weekdays, dayOfWeek, pos++);
		}
    
    for (dayOfWeek = 1; dayOfWeek < firstDayOfWeek; dayOfWeek++) {
			generateLabel(weekdays, dayOfWeek, pos++);
    }
    
	}

	/**
	 * Generar etiqueta del día.
	 * @param weekdays
	 * @param dayOfWeek
	 * @param pos
	 */
	private void generateLabel(String[] weekdays, int dayOfWeek, int pos) {
		
		Label label = new Label(weekdays[dayOfWeek].substring(0, 3));
		label.setSizeUndefined();
		label.setWidth("30px");
		label.addStyleName("calendar_header");

		//grid.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

		grid.addComponent(label, pos, 0);
	}

	/**
	 * Obtener los días del mes.
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDaysInMonth(int year, int month) {
		switch (month) {
		case 1:
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				return 29; // leap year
			else
				return 28;
		case 3:
			return 30;
		case 5:
			return 30;
		case 8:
			return 30;
		case 10:
			return 30;
		default:
			return 31;
		}
	}

	// public void prevMonth() {
	// int month = getMonth() - 1;
	// if (month < 0) {
	// setDate(getYear() - 1, 11, getDay());
	// } else {
	// setMonth(month);
	// }
	// drawCalendar();
	// }

	// public void nextMonth() {
	// int month = getMonth() + 1;
	// if (month > 11) {
	// setDate(getYear() + 1, 0, getDay());
	// } else {
	// setMonth(month);
	// }
	// drawCalendar();
	// }
	//
	// public void prevYear() {
	// setYear(getYear() - 1);
	// drawCalendar();
	// }
	//
	// public void nextYear() {
	// setYear(getYear() + 1);
	// drawCalendar();
	// }
	//
	// private void setDate(int year, int month, int day) {
	// date = new Date(year - 1900, month, day);
	// }
	//
	// private void setYear(int year) {
	// date.setYear(year - 1900);
	// }
	//
	// private void setMonth(int month) {
	// date.setMonth(month);
	// }

	@SuppressWarnings("deprecation")
	public int getYear() {
		return 1900 + date.getYear();
	}

	@SuppressWarnings("deprecation")
	public int getMonth() {
		return date.getMonth();
	}

	@SuppressWarnings("deprecation")
	public int getDay() {
		return date.getDate();
	}

	public Date getDate() {
		return date;
	}

	public boolean isHolidays() {
		return holidays;
	}

	public void setHolidays(boolean holidays) {
		this.holidays = holidays;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

}
