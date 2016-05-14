package com.javalego.ui.options;

import com.javalego.model.BaseModel;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;

/**
 * Opción que podemos utilizar en diferentes contextos de la aplicación para reutilizar sus datos.
 * 
 * Ej.: opciones CRUD como añadir, borrar, editar, ... pueden ser definidas como opciones estándar y
 * reutilizarse en acciones o etiquetas dentro de los diferentes contextos de la aplicación, reduciendo
 * los tiempos de desarrollo, refactorización y estandarización UI.
 * 
 * @author ROBERTO RANZ
 */
public interface IOption extends BaseModel {

	Icon getIcon();

	Colors getColor();
	
}
