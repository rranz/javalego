package com.javalego.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.javalego.exception.JavaLegoException;

/**
 * Funciones de útiles para fechas.
 * @author ROBERTO RANZ
 *
 */
public class DateUtils {

  private static Calendar CALENDAR = Calendar.getInstance();

  /**
   * Zonas horarias.
   */
  private static String[] zoneIds = null;

  /**
   * Obtener zonas horarias.
   * @return
   */
  static public String[] getTimeZoneIds() {
  	
    if (zoneIds == null)
      zoneIds = TimeZone.getAvailableIDs();
    return zoneIds;
  }

  /**
   * Obtiene la fecha en una determinada zona horaria.
   * @param date
   * @param timeZone
   * @return
   */
  static public Date getDateTimeZone(Date date, String timeZone) throws Exception {

    if (!existTimeZone(timeZone)) throw new JavaLegoException("NO EXIST ZONE: " + timeZone, JavaLegoException.WARNING);

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.setTimeZone(TimeZone.getTimeZone(timeZone));

    GregorianCalendar d = new GregorianCalendar();
    d.set(cal.get(GregorianCalendar.YEAR), cal.get(GregorianCalendar.MONTH), cal.get(GregorianCalendar.DATE), cal.get(GregorianCalendar.HOUR_OF_DAY), cal.get(GregorianCalendar.MINUTE), cal.get(GregorianCalendar.SECOND));
    return d.getTime();

  }

  /**
   * Imprimir TimeZones por consola.
   */
  static public void printTimeZones() {
  	
    String[] zones = getTimeZoneIds();
    for(int i = 0; i < zones.length; i++)
      System.out.println(zones[i]);
  }

  /**
   * Comprueba si una zona horaria existe en la relación de zonas internacionales.
   * @param timeZone
   * @return
   */
  static public boolean existTimeZone(String timeZone) {
  	
    String[] zoneIds = getTimeZoneIds();
    return StringUtils.existInArrayString(zoneIds, timeZone);
  }

  /**
   * <p>
   * Construye y devuelve un <code>java.util.Calendar</code> a partir del
   * valor <code>java.util.Date</code> almacenado en el campo.
   * </p>
   *
   * @return Calendar
   */
  static public Calendar getCalendar(java.util.Date date) {
  	
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    return cal;
  }

  /**
   * Obtiene una fecha con año, mes y día.
   * @return
   */
  static public Date getDate(int year, int month, int day) {
  	
    GregorianCalendar cal = new GregorianCalendar();
    cal.setLenient(false);
    cal.set(year, month, day, 0, 0, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * Obtiene una fecha con el año y el día del año.
   * @return
   */
  static public Date getDate(int year, int day_year) {
  	
    Calendar cal = new GregorianCalendar();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.DAY_OF_YEAR, day_year);
    return cal.getTime();
  }

  /**
   * Obtiene el día de la fecha actual.
   * @return
   */
  static public Date getDate(long time) {
  	
    Calendar cal = new GregorianCalendar();
    cal.setTimeInMillis(time);
    return cal.getTime();
  }

  /**
   * Obtiene el día de la fecha actual incluyendo el mes natural 1 enero 2 febrero en lugar de 0 enero, 1 febrero.
   * @return
   */
  static public Date getDateNatural(int year, int month, int day) {
  	
    Calendar cal = new GregorianCalendar();
    cal.set(year, month-1, day, 0, 0, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.setLenient(false);
    return cal.getTime();
  }

  /**
   * Obtiene el día de la fecha actual.
   * @return
   */
  static public int getDayToday() {
  	
    return getCalendar(new Date()).get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Obtiene el día de una fecha.
   * @return
   */
  static public int getDayToday(Date date){
    
  	return getCalendar(date).get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Obtiene el día de una fecha.
   * @return
   */
  static public int getWeekDay(Date date){
    
  	return getCalendar(date).get(Calendar.DAY_OF_WEEK);
  }

  /**
   * Comprueba si es un día laborable (de lunes a viernes).
   * @param date
   * @return
   */
  static public boolean isWorkDay(Date date) {
  	
  	return !isSaturday(date) && !isSunday(date);
  }
  
  /**
   * Comprueba si el día de la fechas es sábado.
   * @return
   */
  static public boolean isSaturday(Date date){
    
  	return getCalendar(date).get(Calendar.DAY_OF_WEEK) == 7;
  }

  /**
   * Comprueba si el día de la fechas es lunes.
   * @return
   */
  static public boolean isMonday(Date date){
    
  	return getCalendar(date).get(Calendar.DAY_OF_WEEK) == 2;
  }

  /**
   * Comprueba si el día de la fechas es martes.
   * @return
   */
  static public boolean isTuesday(Date date){
    
  	return getCalendar(date).get(Calendar.DAY_OF_WEEK) == 3;
  }

  /**
   * Comprueba si el día de la fechas es miércoles.
   * @return
   */
  static public boolean isWednesday(Date date) {
    
  	return getCalendar(date).get(Calendar.DAY_OF_WEEK) == 4;
  }

  /**
   * Comprueba si el día de la fechas es jueves.
   * @return
   */
  static public boolean isThursday(Date date) {
    
  	return getCalendar(date).get(Calendar.DAY_OF_WEEK) == 5;
  }

  /**
   * Comprueba si el día de la fechas es viernes.
   * @return
   */
  static public boolean isFriday(Date date) {
    
  	return getCalendar(date).get(Calendar.DAY_OF_WEEK) == 6;
  }

  /**
   * Comprueba si el día de la fechas es sábado.
   * @return
   */
  static public boolean isSaturday(int day, int month, int year){
    
  	return getCalendar(getDate(year, month, day)).get(Calendar.DAY_OF_WEEK) == 7;
  }
  
  /**
   * Comprueba si el día de la fechas es sábado con el mes natural 1 enero en lugar de 0 enero.
   * @return
   */
  static public boolean isSaturdayNatural(int day, int month, int year){
    
  	return getCalendar(getDate(year, month-1, day)).get(Calendar.DAY_OF_WEEK) == 7;
  }
  
  /**
   * Comprueba si el día de una fecha es domingo.
   * @return
   */
  static public boolean isSunday(Date date){
    
  	return getCalendar(date).get(Calendar.DAY_OF_WEEK) == 1;
  }

  /**
   * Comprueba si el día de la fechas es domingo.
   * @return
   */
  static public boolean isSunday(int day, int month, int year){
    
  	return getCalendar(getDate(year, month, day)).get(Calendar.DAY_OF_WEEK) == 1;
  }
  
  /**
   * Comprueba si el día de la fechas es domingo con el mes natural 1 enero en lugar de 0 enero.
   * @return
   */
  static public boolean isSundayNatural(int day, int month, int year){
    
  	return getCalendar(getDate(year, month-1, day)).get(Calendar.DAY_OF_WEEK) == 1;
  }
  
  /**
   * Obtiene el mes de la fecha actual.
   * @return
   */
  static public int getMonthToday(){
    
  	return getCalendar(new Date()).get(Calendar.MONTH)+1;
  }

  /**
   * Obtiene la letra del día (L,M,X,J,V,S,D).
   * @param day
   * @param month
   * @param year
   * @return
   */
  static public String getDayLetter(int day, int month, int year) {
  	
  	Calendar cal = new GregorianCalendar(year, month-1, day);
  	
  	switch (cal.get(Calendar.DAY_OF_WEEK)) {
	    case 1:
	      return "D"; 
	    case 2:
	      return "L";
	    case 3:
	      return "M";
	    case 4:
	      return "X";
	    case 5:
	      return "J";
	    case 6:
	      return "V";
	    case 7:
	      return "S";
	    default :
	    	return "";
  	}
  }
  
  /**
   * Número de días de un mes.
   * @return
   */
  static public int getDaysMonth(int month, int year) {
  	
  	Calendar cal = new GregorianCalendar(year, month-1, 1);
  	
  	// Get the number of days in that month
  	return cal.getActualMaximum(Calendar.DAY_OF_MONTH);   
  }
  
  /**
   * Número de días de un mes actual.
   * @return
   */
  static public int getDaysMonth() {
  	
  	Calendar cal = new GregorianCalendar();
  	cal.setTime(new Date());
  	
  	// Get the number of days in that month
  	return cal.getActualMaximum(Calendar.DAY_OF_MONTH);   
  }
  
  /**
   * Número de días del año actual.
   * @return
   */
  static public int getDaysYear() {
  	
  	Calendar cal = new GregorianCalendar();
  	cal.setTime(new Date());
  	
  	// Get the number of days in that month
  	return cal.getActualMaximum(Calendar.DAY_OF_YEAR);   
  }
  
  /**
   * Número de días del año actual.
   * @return
   */
  static public int getDaysYear(Date date) {
  	
  	Calendar cal = new GregorianCalendar();
  	cal.setTime(date);
  	
  	// Get the number of days in that month
  	return cal.getActualMaximum(Calendar.DAY_OF_YEAR);   
  }
  
  /**
   * Número de días del año actual.
   * @return
   */
  static public int getDaysYear(int year) {
  	
  	Calendar cal = new GregorianCalendar();
  	cal.setTime(DateUtils.getDate(year, 1, 1));
  	
  	// Get the number of days in that month
  	return cal.getActualMaximum(Calendar.DAY_OF_YEAR);   
  }
  
  /**
   * Obtiene la hora de la fecha actual.
   * @return
   */
  static public int getHourToday(){
    return getCalendar(new Date()).get(Calendar.HOUR_OF_DAY);
  }

  /**
   * DateFormatSymbols returns an extra, empty value at the
   * end of the array of months.  Remove it.
   */
  static public String[] getMonthStrings() {
  	
  	String[] months = new java.text.DateFormatSymbols().getMonths();
  	
  	int lastIndex = months.length - 1;

  	if (months[lastIndex] == null || months[lastIndex].length() <= 0) { //last item empty
  	
  		String[] monthStrings = new String[lastIndex];
  		System.arraycopy(months, 0, monthStrings, 0, lastIndex);
  		return monthStrings;
  	
  	} else { //last item not empty
  		return months;
  	}
  }  
  
  /**
   * Obtiene el valor texto de un mes.
   * @return
   */
  static public String getMonthText(int month){
    
  	Date date;
    try {
      date = new SimpleDateFormat("ddMMyyyy").parse("01" + (month > 9 ? month : "0" + month) + DateUtils.getYearToday()); 
      return new SimpleDateFormat("MMMM").format(date); 
    } catch (ParseException e) {
      return ""; 
    }
  }

  /**
   * Obtiene el valor texto de un mes.
   * @return
   */
  static public String getMonthText(Date date){
  	
  	return getMonthText(getMonthToday(date));
  }

  /**
   * Obtiene el mes de una fecha.
   * @return
   */
  static public int getMonthToday(Date date){
    
  	return getCalendar(date).get(Calendar.MONTH)+1;
  }

  /**
   * Obtiene el año de la fecha actual.
   * @return
   */
  static public int getYearToday(){
    
  	return getCalendar(new Date()).get(Calendar.YEAR);
  }

  /**
   * Obtiene el año de una fecha.
   * @return
   */
  static public int getYearToday(Date date){
    
  	return getCalendar(date).get(Calendar.YEAR);
  }

  /**
   * <p>
   * Añade una cantidad unidades de tiempo a la fecha almacenada en el campo.
   * <br>
   * El tipo de unidad a añadir es una de las constantes de
   * <code>java.util.Calendar</code>. Por ejemplo,
   * <code>Calendar.MONTH, Calendar.SECOND,</code> etc.
   * </p>
   *
   * @param quantity
   *          int - Cantidad de unidades de tiempo a añadir
   * @param type -
   *          Tipo de unidad de tiempo (p.e. <code>Calendar.MONTH</code>)
   */
  static Date add(Date date, int quantity, int type) {
    
  	Calendar cal = getCalendar(date);
    
  	if (cal == null)
  		return date; 
  	
    cal.add(type, quantity);
    
    return cal.getTime();
  }

  /**
   * Obtener el día actual eliminando la hora de la fecha.
   * @param date
   * @return
   */
  static public Date getToday(Date date) throws Exception {
  	
  	if (date == null)
  		return null;
    
  	String today = new SimpleDateFormat("yyyyMMdd").format(date);
    
  	return new Date(new SimpleDateFormat("yyyyMMdd").parse(today).getTime());
  }

  /**
   * <p>
   * Añade una determinada cantidad de días a la fecha almacenada en el campo.
   * <br>
   * Si la cantidad a aladir es negativa, la resta.
   * </p>
   *
   * @param quantity
   *          int - Cantidad a añadir
   */
  static public Date addDays(Date date, int quantity) {
    return add(date, quantity, Calendar.DAY_OF_MONTH);
  }

  /**
   * Añadir semanas
   * @param date
   * @param quantity
   * @return
   */
  static public Date addWeekdays(Date date, int quantity) {
    return add(date, quantity, Calendar.DAY_OF_WEEK_IN_MONTH);
  }

  /**
   * Añadir días laborales
   * @param date
   * @param quantity
   * @param endWorkHourFriday hora de fin de jornada para el viernes. Implica que el sistema buscará el lunes como día de jornada habitual
   * @return
   */
  static public Date addWorkDays(Date date, int quantity, String endWorkHourFriday) {
  	
		if (date == null)	return date;
		
  	Date tmpDate = date;

  	// Añadir un nuevo día si es viernes y la hora es superior a las 15:00
  	// TODO: este caso tiene que ser parametrizable ya que actualmente sólo se aplica a empresas cuya jornada habitual del viernes termine a esta hora.
  	if (endWorkHourFriday != null && DateUtils.isFriday(date)) {
  		
  		String hour = new SimpleDateFormat("HH:mm").format(date); 
  		
  		if (DateUtils.getHoursAndMinutes(hour) > DateUtils.getHoursAndMinutes(endWorkHourFriday))
  			tmpDate = DateUtils.addDays(date, 1);
  	}
  	
  	date = addWorkDays(tmpDate, quantity);

  	// Añadir un nuevo día si es viernes y la hora es superior a las 15:00
  	// TODO: este caso tiene que ser parametrizable ya que actualmente sólo se aplica a empresas cuya jornada habitual del viernes termine a esta hora.
  	if (endWorkHourFriday != null && DateUtils.isFriday(date)) {
  		
  		String hour = new SimpleDateFormat("HH:mm").format(date); 
  		
  		if (DateUtils.getHoursAndMinutes(hour) > DateUtils.getHoursAndMinutes(endWorkHourFriday))
  			date = DateUtils.addWorkDays(date, 1);
  	}
  	
  	return date;
  }
  
  /**
   * Añadir días laborales
   * @param date
   * @param quantity
   * @return
   */
  public static Date addWorkDays(Date date, int quantity) {
  	
		if (date == null)	return date;
		
  	int count = 0;
  	Date tmpDate = date;

  	while(count < quantity) {
    	
  		tmpDate = addDays(tmpDate, 1);
  		
  		if (!isSaturday(tmpDate) && !isSunday(tmpDate)) {
  			date = tmpDate;
  			++count;
  		}
  	}
  	
  	return tmpDate;
  }

  public static void main(String args[]) {
  	
  	System.out.println(getDiffDates(getStringToDate("20100328"), new Date()));
  	//System.out.println(DateUtils.getDateTimeLocaleString(addNaturalDays(new Date(), 6, "15:00")));
  }

  /**
   * Día de la semana en formato completo.
   * @param date
   * @return
   */
  static public String getStringDay(Date date) {
  	return new SimpleDateFormat("EEEE").format(date);
  }
  
  /**
   * Día de la semana en formato completo.
   * @param year
   * @param month
   * @param day
   * @return
   */
  static public String getStringDay(int year, int month, int day) {
  	return new SimpleDateFormat("EEEE").format(DateUtils.getDate(year, month, day));
  }
  
  /**
   * Añadir días naturales teniendo en cuenta que el último día no sea laboral o festivo
   * @param date
   * @param days
   * @return
   */
	static public Date addNaturalDays(Date date, int quantity, String endWorkHourFriday) {

		if (date == null)	return date;
		
  	Date tmpDate = addDays(date, quantity - 1);

  	tmpDate = addWorkDays(tmpDate, 1, endWorkHourFriday);
  	
  	return tmpDate;
	}

  /**
   * <p>
   * Añade una determinada cantidad de semanas a la fecha almacenada en el
   * campo. <br>
   * Si la cantidad a aladir es negativa, la resta.
   * </p>
   *
   * @param quantity
   *          int - Cantidad a añadir
   */
  static public Date addWeeks(Date date, int quantity) {
    
  	return add(date, quantity, Calendar.WEEK_OF_MONTH);
  }

  /**
   * <p>
   * Añade una determinada cantidad de meses a la fecha almacenada en el campo.
   * <br>
   * Si la cantidad a aladir es negativa, la resta.
   * </p>
   *
   * @param quantity
   *          int - Cantidad a añadir
   */
  static public Date addMonths(Date date, int quantity) {
    
  	return add(date, quantity, Calendar.MONTH);
  }

  /**
   * <p>
   * Añade una determinada cantidad de años a la fecha almacenada en el campo.
   * <br>
   * Si la cantidad a aladir es negativa, la resta.
   * </p>
   *
   * @param quantity
   *          int - Cantidad a añadir
   */
  static public Date addYears(Date date, int quantity) {
    
  	return add(date, quantity, Calendar.YEAR);
  }

  /**
   * <p>
   * Añade una determinada cantidad de horas a la fecha almacenada en el campo.
   * <br>
   * Si la cantidad a aladir es negativa, la resta.
   * </p>
   *
   * @param quantity
   *          int - Cantidad a añadir
   */
  static public Date addHours(Date date, int quantity) {
    
  	return add(date, quantity, Calendar.HOUR);
  }

  /**
   * <p>
   * Añade una determinada cantidad de minutos a la fecha almacenada en el
   * campo. <br>
   * Si la cantidad a aladir es negativa, la resta.
   * </p>
   *
   * @param quantity
   *          int - Cantidad a añadir
   */
  static public Date addMinutes(Date date, int quantity) {
    
  	return add(date, quantity, Calendar.MINUTE);
  }

  /**
   * <p>
   * Añade una determinada cantidad de segundos a la fecha almacenada en el
   * campo. <br>
   * Si la cantidad a aladir es negativa, la resta.
   * </p>
   *
   * @param quantity
   *          int - Cantidad a añadir
   */
  static public Date addSeconds(Date date, int quantity) {
    
  	return add(date, quantity, Calendar.SECOND);
  }

   /**
    * Devuelve la fecha en formato dd/MM/yyyy.
    * @return String
    */
   static public String getDateToString() {
     
  	 return new SimpleDateFormat().format(new Date());
   }

   /**
    * Formate la fecha con la localización definida en el cliente (día y hora).
    * @return
    */
   static public String getDateTimeLocaleString() {
     
  	 return new SimpleDateFormat().format(new Date());
   }

   /**
    * Formate la fecha con la localización definida en el cliente (día y hora).
    * @return
    */
   static public String getDateTimeLocaleString(Date date) {
     
  	 return new SimpleDateFormat().format(date);
   }

   /**
    * Devuelve una fecha pasando un string con la fecha y un formato de fecha específico.
    * @return String
    */
   static public Date getStringToDate(String value, String pattern) {
     try {
      return new SimpleDateFormat(pattern).parse(value);
    } catch (ParseException e) {
      return null;
    }
   }

   /**
    * Devuelve una fecha pasando un string con la fecha en formato yyyyMMdd.
    * @return String
    */
   static public Date getStringToDate(String value) {
     try {
      SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
      f.setLenient(false);
      return f.parse(value);
    } catch (ParseException e) {
      return null;
    }
   }

   /**
    * Devuelve la fecha actual en el formato definido
    * @param El patrón de conversión
    * @return String
    */
   static public String getDateToString(String pattern) {
     
  	 SimpleDateFormat f = new SimpleDateFormat(pattern);
     f.setLenient(false);
     return f.format(new java.util.Date());
   }

   /**
    * Devuelve la fecha en formato pattern definido
    * @return String
    */
   static public String getDateToString(java.util.Date date, String pattern) {
     
  	 SimpleDateFormat f = new SimpleDateFormat(pattern);
     f.setLenient(false);
     return f.format(date);
   }

   /**
    * Devuelve la fecha en formato dd/MM/yyyy.
    * @return String
    */
   static public String getDateToString(Date date) {
  	 
  	 if (date == null)
  		 return "";
  	 else {
	     SimpleDateFormat f = new SimpleDateFormat();
	     f.setLenient(false);
	     return f.format(date);
  	 }
   }

   /**
    * Devuelve la fecha en formato yyyyMMdd.
    * @return String
    */
   static public String getDateToYYYYMMDD(Date date) {
     
  	 if (date == null)
  		 return null;
  	 
  	 SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd"); 
     f.setLenient(false);
     return f.format(date);
   }

   /**
    * Devuelve la fecha y hora en formato dd/MM/yyyy HH:mm:ss.
    * @return String
    */
   static public String getDateTimeToString() {
     
  	 SimpleDateFormat f = new SimpleDateFormat();
     f.setLenient(false);
     return f.format(new java.util.Date());
   }

   /**
    * Devuelve la fecha en formato dd MMMM yyyy HH:mm:ss.
    * @return String
    */
  	public static String getFullDateTimeToString() {
      
  		SimpleDateFormat f = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
      f.setLenient(false);
      return f.format(new java.util.Date());
  	}
   
   /**
    * Devuelve la fecha actual en formato yyyyMMdd
    * @return String
    */
   static public String getFileDateToFormat(String pattern) {
     
  	 SimpleDateFormat f = new SimpleDateFormat(pattern);
     f.setLenient(false);
     return f.format(new java.util.Date());
   }

   /**
    * Devuelve la fecha en formato yyyyMMdd
    * @return String
    */
   static public String getFileDateToFormat(java.util.Date date, String pattern) {
     
  	 SimpleDateFormat f = new SimpleDateFormat(pattern);
     f.setLenient(false);
     return f.format(date);
   }

   /**
    * Devuelve la fecha en formato yyyyMMdd_hhmmss.
    * @return String
    */
   static public String getFileDateTimeToString(){
     
  	 return new SimpleDateFormat("yyyyMMdd_hhmmss").format(new java.util.Date()); 
   }

   /**
    * Devuelve la fecha en formato yyyyMMdd_hhmmss.
    * @return String
    */
   static public String getFileDateTime24hToString(){
     
  	 return new SimpleDateFormat("yyyyMMdd_kkmmss").format(new java.util.Date()); 
   }

   /**
    * Devuelve una fecha eliminando sus segundos.
    * @param date
    * @return Date
    */
   static public Date getDateWithoutSeconds(Date date){
     
  	 Calendar calendar = new GregorianCalendar();
     calendar.setTime(date);
     calendar.set(Calendar.SECOND, 0);
     //new SimpleDateFormat("yyyy MM dd hh mm ss").format(calendar.getTime())
     return calendar.getTime();
   }

   /**
    * Devuelve una fecha eliminando la hora.
    * @param date
    * @return Date
    */
   static public Date getUntimedDate(Date date){
     
  	 Calendar calendar = new GregorianCalendar();
     calendar.setTime(date);
     calendar.set(Calendar.SECOND, 0);
     calendar.set(Calendar.HOUR, 0);
     calendar.set(Calendar.HOUR_OF_DAY, 0);
     calendar.set(Calendar.MINUTE, 0);
     calendar.set(Calendar.MILLISECOND, 0);
     return calendar.getTime();
   }

   /**
    * Devuelve el último día del mes de la fecha pasada como parámetro.
    * @param date
    * @return Date
    */
   static public int getLastDayMonth(Date date){
     
  	 Calendar calendar = new GregorianCalendar();
     calendar.setTime(date);
     calendar.set(Calendar.SECOND, 0);
     return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
   }
   
   /**
    * Devuelve la fecha del último día del mes de la fecha pasada como parámetro.
    * @param date
    * @return
    */
   static public Date getDateLastDayMonth(Date date) {
     
  	 Calendar calendar = new GregorianCalendar();
     calendar.setTime(date);
     calendar.set(Calendar.SECOND, 0);
     int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
     calendar.set(Calendar.DAY_OF_MONTH, max);
     return calendar.getTime();
   }

   /**
    * Comparar dos fechas utilizando SimpleDateFormat yyyy MM dd hh:mm:ss.
    * @param date1
    * @param date2
    * @return
    */
   static public boolean equalsDates(Date date1, Date date2){
     
  	 String inStr = new SimpleDateFormat("yyyy MM dd hh:mm:ss").format(date1); 
     String outStr = new SimpleDateFormat("yyyy MM dd hh:mm:ss").format(date2); 
     return inStr.equals(outStr);
   }

   /**
    * Comparar dos fechas utilizando SimpleDateFormat yyyy MM dd.
    * @param date1
    * @param date2
    * @return
    */
   static public boolean equalsShortDates(Date date1, Date date2){
     
  	 String inStr = new SimpleDateFormat("yyyy MM dd").format(date1); 
     String outStr = new SimpleDateFormat("yyyy MM dd").format(date2); 
     return inStr.equals(outStr);
   }

   /**
    * Eliminar la hora en una fecha.
    * @param date1
    * @param date2
    * @return
    */
   static public Date getShortDate(Date date) {
     if (date != null)
    	 return getStringToDate(new SimpleDateFormat("yyyyMMdd").format(date));
     else
    	 return date;
   }

   /**
    * Número de días de diferencia entre dos fechas.
    * @param date1
    * @param date2
    * @return
    */
   static public long differenceInDays(java.util.Date date1, java.util.Date date2) {

     final long msPerDay = 1000 * 60 * 60 * 24;

     final long date1Milliseconds = date1.getTime();
     final long date2Milliseconds = date2.getTime();
     final long result = (date1Milliseconds - date2Milliseconds) / msPerDay;

     return result;
   }

   /**
    * Validar una fecha.
    * @param year
    * @param month
    * @param day
    * @return
    */
   public static boolean isValid(int year, int month, int day) {
     
  	 boolean result = true;
      try	{
         // Want to force USA date formatting so that the month, day, and year
         // are not interpreted differently if this software is run in a different
        // locale!
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        df.setLenient(false);

        final String dateString = month + "/" + day + "/" + year; 
        df.parse(dateString);
      }
      catch (Exception e)	{
        result = false;
      }
      return result;
   }

   /**
    * Validar una fecha.
    * @param year
    * @param month
    * @param day
    * @return
    */
   public static boolean isValid(String dateString, Locale locale) {
     
  	 boolean result = true;
      try	{
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        df.setLenient(false);
        df.parse(dateString);
      }
      catch (Exception e)	{
        result = false;
      }
      return result;
   }

   /**
    * Validar una fecha.
    */
   public static boolean isValid(String dateString) {
     
  	 boolean result = true;
      try	{
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        df.setLenient(false);
        df.parse(dateString);
      }
      catch (Exception e)	{
        result = false;
      }
      return result;
   }

   /**
    * Validar una fecha.
    */
   public static boolean isValid(String dateString, int dateformat) {
     
  	 boolean result = true;
      try	{
        DateFormat df = DateFormat.getDateInstance(dateformat);
        df.setLenient(false);
        df.parse(dateString);
      }
      catch (Exception e)	{
        result = false;
      }
      return result;
   }

   /**
    * Validar una fecha mediante un formato específico.
    */
   public static boolean isValid(String dateString, String format) {
     
  	 boolean result = true;
     try	{
        SimpleDateFormat f = new SimpleDateFormat(format);
        f.setLenient(false);
        f.parse(dateString);
      }
      catch (Exception e)	{
        result = false;
      }
      return result;
   }

   /**
    * Validar una hora con formato HH:mm o HH:mm:ss.
    */
   public static boolean isValidTime(String timeString) {
     
  	 if (StringUtils.isEmpty(timeString) || (timeString.length() != 5 && timeString.length() != 8)) return false;
      try	{
      	//if (timeString.equals("24:00")) 
      		//return true;
        String format = timeString.length() == 5 ? "HH:mm" : "HH:mm:ss";
        SimpleDateFormat f = new SimpleDateFormat(format);
        f.setLenient(false);
        f.parse(timeString);
        return true;
      }
      catch (Exception e)	{
        return false;
      }
   }

   /**
    * Obtener el número de minutos de un time en formato string HH:mm.
    */
   public static int getMinutes(String timeString) {
     
  	 if (isValidTime(timeString))
       return new Integer(timeString.substring(3,5)).intValue();
     else
       return 0;
   }

   /**
    * Comparar si una hora es inferior a otra.
    * @param init
    * @param end
    * @return
    */
   public static boolean isMinorTime(String value, String compare) throws Exception {

     if (isValidTime(value) && isValidTime(compare)) {

       int hi = getHours(value);
       int mi = getMinutes(value);

       int he = getHours(compare);
       int me = getMinutes(compare);

       return hi > he || (he == hi && mi > me);

     }
     else
       return false; //throw new GanaException(Messages.getString("DateUtils.8"), GanaException.WARNING);
   }

   /**
    * Horas de diferencia entre dos.
    * @param init
    * @param end
    * @return
    */
   public static double getDiffTimesHours(String init, String end) {
  	 
  	 return getHoursAndMinutes(getDiffTimes(init, end));
   }

   /**
    * Diferencia de horas entre dos fechas.
    * @param init
    * @param end
    * @return
    */
   public static long getDiffHoursDates(Date init, Date end) {
  	 
  	 Calendar cal1 = Calendar.getInstance();
  	 Calendar cal2 = Calendar.getInstance();
  	 // Set the date for both of the calendar instance
  	 cal1.setTime(init);
  	 cal2.setTime(end);
  	 // Get the represented date in milliseconds
  	 long milis1 = cal1.getTimeInMillis();
  	 long milis2 = cal2.getTimeInMillis();
  	 // Calculate difference in milliseconds
  	 long diff = milis2 - milis1;
  	 // Calculate difference in hours
  	 return diff / (60 * 60 * 1000);
   }
   
   /**
    * Diferencia entre dos fechas.
    * @param init
    * @param end
    * @return
    */
   public static String getDiffDates(Date init, Date end) {
  	 
  // Creates two calendars instances
  	 Calendar cal1 = Calendar.getInstance();
  	 Calendar cal2 = Calendar.getInstance();
  	 // Set the date for both of the calendar instance
  	 cal1.setTime(init);
  	 cal2.setTime(end);
  	 // Get the represented date in milliseconds
  	 long milis1 = cal1.getTimeInMillis();
  	 long milis2 = cal2.getTimeInMillis();
  	 // Calculate difference in milliseconds
  	 long diff = milis2 - milis1;
  	 // Calculate difference in seconds
  	 //long diffSeconds = diff / 1000;
  	 // Calculate difference in minutes
  	 long diffMinutes = diff / (60 * 1000);
  	 // Calculate difference in hours
  	 long diffHours = diff / (60 * 60 * 1000);
  	 // Calculate difference in days
  	 long diffDays = diff / (24 * 60 * 60 * 1000);
//  	 System.out.println("In milliseconds: " + diff + " milliseconds.");
//  	 System.out.println("In seconds: " + diffSeconds + " seconds.");
//  	 System.out.println("In minutes: " + diffMinutes + " minutes.");
//  	 System.out.println("In hours: " + diffHours + " hours.");
//  	 System.out.println("In days: " + diffDays + " days.");

  	 String text = "";
  	
  	 if (diffDays > 0)
  		 text += diffDays + " día" + (diffDays > 1 ? "s" : "");
  	 
  	 if ((diffHours % 24) > 0)
  		 text += (text.equals("") ? "" : " ") + (diffHours % 24) + " hora" + ((diffHours % 24) > 1 ? "s" : "");
  	 
  	 if ((diffMinutes % 60) > 0)
  		 text += (text.equals("") ? "" : " ") + (diffMinutes % 60) + " minuto" + ((diffMinutes % 60) > 1 ? "s" : "");
  	 
  	 return text;
   }
   
   /**
    * Diferencia en minutos entre dos fechas.
    * @param init
    * @param end
    * @return
    */
   public static long getDiffMinutesDates(Date init, Date end) {
  	 
  	 Calendar cal1 = Calendar.getInstance();
  	 Calendar cal2 = Calendar.getInstance();
  	 // Set the date for both of the calendar instance
  	 cal1.setTime(init);
  	 cal2.setTime(end);
  	 // Get the represented date in milliseconds
  	 long milis1 = cal1.getTimeInMillis();
  	 long milis2 = cal2.getTimeInMillis();
  	 long diff = milis2 - milis1;
  	 return diff / (60 * 1000);
   }   
   
   /**
    * Horas y minutos de diferencia entre dos horas.
    * @param init
    * @param end
    * @return
    */
   public static String getDiffTimes(String init, String end) {

     if (isValidTime(init) && isValidTime(end) && getHoursAndMinutes(end) >= getHoursAndMinutes(init)) {

       int hi = getHours(init);
       int mi = getMinutes(init);

       int he = getHours(end);
       int me = getMinutes(end);

       int th = 0;
       int tm = 0;

       // Horas
       if (mi == 0)
         th = he - hi;
       else if (hi != he)
         th = he - hi;

       // Minutos
       if (mi == 0)
         tm = me;
       else if (mi > me)
         tm = (60 - mi) + me;
       else
      	 tm = me - mi;

       if (tm == 60) {
      	 tm = 0;
       }

       if (mi > me)
      	 --th;


       return StringUtils.fillCharacterLeft(new Integer(th).toString(), '0', 2) + ":" + StringUtils.fillCharacterLeft(new Integer(tm).toString(), '0', 2); 
     }
     else
       return null;
   }

   /**
    * Obtener el número de horas de un time en formato string HH:mm
    */
   public static int getHours(String timeString) {
     
  	 if (isValidTime(timeString))
       return new Integer(timeString.substring(0,2)).intValue();
     else
       return 0;
   }

   /**
    * Obtener el número de horas y minutos (valor double donde la parte entera son las horas y la decimal los minutos)
    * de un time en formato string HH:mm
    */
   public static double getHoursAndMinutes(String timeString) {
     
  	 if (isValidTime(timeString)) {
    	 Double value = new Double(timeString.substring(3,5));
    	 value = value * 100 / 60;
    	 // Ej.: .3 o 30 minutos da 50 o .5 horas.
       return new Double(timeString.substring(0,2) + "." + value.toString().substring(0, value.toString().indexOf("."))).doubleValue();
     }
     else
       return 0.0;
   }

   /**
    * Returns the day of the week.
    * @param date date
    * @return day of week.
    */
   public static int getDayOfWeek(long date) {
  	 Calendar calendar = CALENDAR;
  	 synchronized(calendar) {
  		 calendar.setTimeInMillis(date);
  		 return (calendar.get(Calendar.DAY_OF_WEEK));
  	 }
   }

   /**
    * Returns the last millisecond of the specified date.
    *
    * @param date Date to calculate end of day from
    * @return Last millisecond of <code>date</code>
    */
   public static Date endOfDay(Date date) {
  	 Calendar calendar = CALENDAR;
  	 synchronized(calendar) {
  		 calendar.setTime(date);
  		 calendar.set(Calendar.HOUR_OF_DAY, 23);
  		 calendar.set(Calendar.MILLISECOND, 999);
  		 calendar.set(Calendar.SECOND, 59);
  		 calendar.set(Calendar.MINUTE, 59);
  		 return calendar.getTime();
  	 }
   }

   /**
    * Returns a new Date with the hours, milliseconds, seconds and minutes
    * set to 0.
    *
    * @param date Date used in calculating start of day
    * @return Start of <code>date</code>
    */
   public static Date startOfDay(Date date) {
       Calendar calendar = CALENDAR;
       synchronized(calendar) {
           calendar.setTime(date);
           calendar.set(Calendar.HOUR_OF_DAY, 0);
           calendar.set(Calendar.MILLISECOND, 0);
           calendar.set(Calendar.SECOND, 0);
           calendar.set(Calendar.MINUTE, 0);
           return calendar.getTime();
       }
   }

   /**
    * Returns day in millis with the hours, milliseconds, seconds and minutes
    * set to 0.
    *
    * @param date long used in calculating start of day
    * @return Start of <code>date</code>
    */
   public static long startOfDayInMillis(long date) {
       Calendar calendar = CALENDAR;
       synchronized(calendar) {
           calendar.setTimeInMillis(date);
           calendar.set(Calendar.HOUR_OF_DAY, 0);
           calendar.set(Calendar.MILLISECOND, 0);
           calendar.set(Calendar.SECOND, 0);
           calendar.set(Calendar.MINUTE, 0);
           return calendar.getTimeInMillis();
       }
   }

   /**
    * Returns the last millisecond of the specified date.
    *
    * @param date long to calculate end of day from
    * @return Last millisecond of <code>date</code>
    */
   public static long endOfDayInMillis(long date) {
       Calendar calendar = CALENDAR;
       synchronized(calendar) {
           calendar.setTimeInMillis(date);
           calendar.set(Calendar.HOUR_OF_DAY, 23);
           calendar.set(Calendar.MILLISECOND, 999);
           calendar.set(Calendar.SECOND, 59);
           calendar.set(Calendar.MINUTE, 59);
           return calendar.getTimeInMillis();
       }
   }

   /**
    * Returns the number of days difference between <code>t1</code> and
    * <code>t2</code>.
    *
    * @param t1 Time 1
    * @param t2 Time 2
    * @param checkOverflow indicates whether to check for overflow
    * @return Number of days between <code>start</code> and <code>end</code>
    */
   public static int getDaysDiff(long t1, long t2, boolean checkOverflow) {
       if (t1 > t2) {
           long tmp = t1;
           t1 = t2;
           t2 = tmp;
       }
       Calendar calendar = CALENDAR;
       synchronized(calendar) {
           calendar.setTimeInMillis(t1);
           int delta = 0;
           while (calendar.getTimeInMillis() < t2) {
               calendar.add(Calendar.DAY_OF_MONTH, 1);
               delta++;
           }
           if (checkOverflow && (calendar.getTimeInMillis() > t2)) {
               delta--;
           }
           return delta;
       }
   }

   /**
    * Días de diferencia entre dos fechas.
    * @param init
    * @param end
    * @return
    */
   public static int getDaysDiff(Date init, Date end) {
  	 return getDaysDiff(init.getTime(), end.getTime());
   }
   
   /**
    * Returns the number of days difference between <code>t1</code> and
    * <code>t2</code>.
    *
    * @param t1 Time 1
    * @param t2 Time 2
    * @return Number of days between <code>start</code> and <code>end</code>
    */
   public static int getDaysDiff(long t1, long t2) {
      
  	 return getDaysDiff(t1, t2, true);
   }

   /*
    * This methods returns true if the date passed in is the
    * first day of the year.
    */
   public static boolean isFirstOfYear(long date) {
  	 
  	 boolean ret = false;
  	 Calendar calendar = CALENDAR;
  	 synchronized(calendar) {
           calendar.setTimeInMillis(date);
           int currentYear = calendar.get(Calendar.YEAR);
           // Check yesterday
           calendar.add(Calendar.DATE,-1);
           int yesterdayYear = calendar.get(Calendar.YEAR);
           ret = (currentYear != yesterdayYear);
  	 }
  	 return ret;
   }

   /*
    * This methods returns true if the date passed in is the
    * first day of the month.
    */
   public static boolean isFirstOfMonth(long date) {
       boolean ret = false;
       Calendar calendar = CALENDAR;
       synchronized(calendar) {
         calendar.setTimeInMillis(date);
         int currentMonth = calendar.get(Calendar.MONTH);
         // Check yesterday
         calendar.add(Calendar.DATE,-1);
         int yesterdayMonth = calendar.get(Calendar.MONTH);
         ret =  (currentMonth != yesterdayMonth);
       }
       return ret;
   }

   /**
    * Convertir un valor TimeStamp a java.util.Date
    * @param time
    * @return
    */
   static public Date converTimestampToDate(Timestamp time) throws Exception {
      if (time != null){
       SimpleDateFormat f = new SimpleDateFormat();
        String value = f.format(time);
        return f.parse(value);
      }
      else
        return null;
   }

  /**
   * Convierte String con separadores - y / en formato YYYYMMDD.
   */
  static public String getDateYYYYMMDD(String dateValue) throws Exception {

    if (dateValue.indexOf("/") > -1) { 
      Date dateTmp = new SimpleDateFormat().parse(dateValue); 
      return new SimpleDateFormat("yyyyMMdd").format(dateTmp); 
    }
    else if (dateValue.indexOf("-") > -1) { 
      Date dateTmp = new SimpleDateFormat().parse(dateValue); 
      return new SimpleDateFormat("yyyyMMdd").format(dateTmp); 
    }
    else
      return dateValue.replaceAll("-", ""); 
  }

  /**
   * Obtener el día de una fecha.
   * @param time
   * @param day
   * @return
   */
  public static Date getDay(Date date, int day) {
    return getDate(getYearToday(date), getMonthToday(date)-1, day);
  }

  /**
   * Obtener un número de horas desde una lista de valores donde la parte decimal representa los minutos de una hora.
   * @param values
   * @return
   */
  static public double sumHours(double[] values) throws Exception {

  	double value = 0.0;

  	int entera = 0;
  	int decimal = 0;

  	for(int i = 0; i < values.length; i++) {

  		if (StringUtils.getNumberDecimals(values[i]) > 59)
  			throw new JavaLegoException("ERROR VALUE: " + values[i], JavaLegoException.WARNING);
  		else {
  			entera += new Double(values[i]).intValue();
  			long d = StringUtils.getNumberDecimals(values[i]);
  			if (d < 10)
  				d = d * 10;
  			decimal += d;
  		}
  	}
  	if (values.length > 0) {

  		if (decimal >= 60) {

  			int r = (decimal % 60);

  			if (r < 10)
  				r *= 10;

  			value = new Double(entera + new Integer(decimal / 60).intValue() + "." + r);
  		}
  		else
  			value = new Double(entera + "." + decimal).doubleValue();
  	}
  	return value;
  }

  /**
   * Validar que los tramos horarios no coincidan en el tiempo.
   * @param horarios
   * @return
   */
	public static boolean validateTimes(List<String[]> horarios) {

		if (horarios == null || horarios.size() == 0) return true;

		for(int i = 0; i < horarios.size(); i++) {

			for(int k = 0; k < horarios.size(); k++) {

				if (k != i) {

					// Comprobar que el nuevo horario no se encuentre comprendido en el horario comparado. El nuevo horario es initn y endn
					double init = getHoursAndMinutes(horarios.get(i)[0]);
					double end = getHoursAndMinutes(horarios.get(i)[1]);
					double initn = getHoursAndMinutes(horarios.get(k)[0]);
					double endn = getHoursAndMinutes(horarios.get(k)[1]);

					if ((initn >= init && initn <= end) || (endn >= init && endn <= end) || (initn <= init && endn >= end))
						return false;
				}
			}
		}
		return true;
	}

  /**
   * Validar que un horario no se encuentra comprendido en otro.
   * @return
   */
	public static boolean validateRangeTime(double init, double end, double init_range, double end_range) {

		if ((init_range >= init && init_range <= end) || (end_range >= init && end_range <= end) || (init_range <= init && end_range >= end))
			return false;
		else
			return true;
	}

	/**
	 * Comprueba si una fecha está comprendida entre un rango de fechas.
	 * @param date
	 * @param init
	 * @param end
	 * @return
	 */
	public static boolean inDates(Date date, Date init, Date end) {
		
		return date.getTime() >= init.getTime() && date.getTime() <= end.getTime();
	}
	
	/**
	 * Comprueba si un rango de fechas se encuentra comprendido en otro
	 * @param date
	 * @param init
	 * @param end
	 * @return
	 */
	public static boolean inDates(Date init, Date end, Date check_init, Date check_end) {
		
		return (init.getTime() <= check_init.getTime() && end.getTime() >= check_end.getTime()) || inDates(init, check_init, check_end) || inDates(end, check_init, check_end);
	}
	
	/**
	 * Comprueba si un rango de fechas se encuentra comprendido en otro (formato SQL).
	 * @param date
	 * @param init
	 * @param end
	 * @return
	 */
	public static String inDatesSQL(String init, String end, String check_init, String check_end) {
		
		String statement = "(";
		
		// 1. Comprobar si init se encuentra entre check_init y check_end
		statement += "(" + init + " >= " + check_init + " and " + init + " <= " + check_end + ")";
		// 2. Comprobar si init es menor que _check_init pero que end se encuentra entre check_init y check_end
		statement += " or (" + init + " < " + check_init + " and " + end + " >= " + check_init + ")";
		
		statement += ")";
		
		return statement;
	}
	
	/**
	 * Obtiene la letra del día pasado como parámetro. L = lunes M = martes, etc.
	 * @param date
	 * @return
	 */
	public static String getLetterDay(Date date) {
		
		if (date != null) {
			
			if (isMonday(date)) return "L";
			else if (isTuesday(date)) return "M";
			else if (isWednesday(date)) return "X"; 
			else if (isThursday(date)) return "J"; 
			else if (isFriday(date)) return "V"; 
			else if (isSaturday(date)) return "S"; 
			else if (isSunday(date)) return "D"; 
			else
				return "";
		}
		else 
			return "";
		
	}

	/**
	 * Obtiene la posición de un fecha dentro de una lista de valores de tipo fecha.
	 * @param festivos
	 * @param date
	 * @return
	 */
	public static int indexOf(Date[] holidays, Date date) {
		
		if (holidays != null && date != null) {
			for(int i = 0; i < holidays.length; i++)
				if (holidays[i].getTime() == date.getTime())
					return i;
		}
		
		return -1;
	}

	/**
	 * Obtener la lista de horas comprendidas dentro de un rango horario.
	 * @param init
	 * @param end
	 * @return
	 */
	public static ArrayList<String> getHours(String init, String end) {
		
		if (init == null || end == null)
			return null;
		
		ArrayList<String> hours = new ArrayList<String>();

		String _init = init;
		
		while (getHours(_init) < getHours(end)) {
			hours.add(_init);
			_init = addHours(_init, 1);
		}
		
		return hours;
	}
	
	/**
	 * Añadir un número de horas a una hora
	 * @param time
	 * @param hours
	 * @return
	 */
	public static String addHours(String time, int hours) {
		
		if (time == null)
			return time;
		
		int hour = getHours(time);
		if (hour + hours < 24 && hour + hours > -1)
      return StringUtils.fillCharacterLeft(Integer.toString(hour + hours), '0', 2) + time.substring(2); 
		else
			return time;
	}

	/**
	 * Método que obtiene en número de días de vacaciones
	 * @param alta
	 * @param baja
	 * @param dias_anuales
	 * @return
	 */
	public static int getDiasVacaciones(Date alta, Date baja, double dias_anuales) {

		double dias_vacaciones = DateUtils.getDaysYear(baja) / dias_anuales;
		
		int meses = new Long(Math.round((DateUtils.getDaysDiff(alta, baja)+1) / dias_vacaciones)).intValue();
		
		return meses < 0 ? 0 : meses;
	}
	
}
