package com.javalego.excel.report;

import java.util.Collection;
import java.util.Locale;

import com.javalego.exception.LocalizedException;
import com.javalego.model.BaseModel;

/**
 * Interface de definición de un informe de beans basado en un modelo de datos.
 * 
 * @author ROBERTO RANZ
 */
public interface ReportModelBeans<T> extends ReportModel {

	/**
	 * Lista de Beans utilizados para generar el informe
	 * @return
	 * @throws LocalizedException
	 */
	abstract Collection<T> getBeans() throws LocalizedException;
	
	/**
	 * Modelo de la lista de campos del informe.
	 * @return
	 */
	abstract Collection<? extends BaseModel> getModel();

	/**
	 * Idioma utilizado para generar el informe y así poder traducir los valores enumerados del modelo de campos.
	 * @return
	 */
	abstract Locale getLocale();
	
}
