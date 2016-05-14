package com.javalego.ui.filter;

import com.javalego.model.keys.Key;
import com.javalego.ui.filter.impl.FilterParamsService;

/**
 * Servicio que obtiene un filtro de selección de beans o registros.
 * 
 * Los servicios podrán obtener statement de forma directa o implementando un presenter que obtenga los datos
 * mediante una diálogo de entrada de datos de usuario.
 * 
 * @see FilterParamsService
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IFilterService {

	/**
	 * Sentencia SQL empleada para aplicar el filtro.
	 * @return
	 */
	String getStatement();
	
	/**
	 * Sentencia SQL en formato natural para una fácil comprensión del usuario.
	 * @return
	 */
	String getNaturalStatement();

	/**
	 * Título mostrado al usuario para su selección.
	 * @return
	 */
	Key getTitle();

	/**
	 * Nombre de referencia (opcional)
	 */
	String getName();
	
	/**
	 * Descripción del filtro
	 * @return
	 */
	Key getDescription();
}
