package com.javalego.ui.filter;

import java.util.Collection;
import java.util.Map;

import com.javalego.exception.LocalizedException;

/**
 * Filtro basado en una lista de parámetros.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IFilterParamsService extends IFilterService {

	/**
	 * Lista de parámetros que establecen el filtro de selección.
	 * @return
	 */
	Collection<IFilterParam> getParams();
	
	/**
	 * Genear sentencia sql en base al valor de los parámetros.
	 * @param fieldValues Lista de nombre de campos y sus valores
	 * @throws LocalizedException
	 */
	void build(Map<String, ?> fieldValues) throws LocalizedException;
	
}
