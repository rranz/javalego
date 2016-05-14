package com.javalego.entity;


/**
 * Interface básica para objectos de entidades JPA cuya clave es de tipo Object. Se utiliza para entidades cuya clave es distinta a number como son las de tipo String principalmente.
 * 
 * @author ROBERTO RANZ
 * 
 *         NOTA: Los objetos con claves Id no incrementales que se permita su edición, hay que tener en cuenta dejar en sólo lectura la edición de esta campo clave cuando se modifique porque si
 *         persistimos el objeto JPA no localizará la clave y se DUPLICARÁ el registro, teniendo ahora dos objetos con distinta clave.
 * 
 *         TODO: Falta implementar en GANA la generación de componentes de sólo lectura asociados a estos campos clave no incrementales y editables. También se tendría que implementar una
 *         funcionalidad independiente de la edición para cambiar el campo clave utilizando un formulario de edición para el nuevo valor y persistir la modificación mediante sentencia SQL update ...
 *         en lugar de persistir el objeto.
 * 
 */
public interface IdEntity extends Entity {

	/**
	 * Obtener el nombre del campo Id.
	 * 
	 * @return
	 */
	String getIdName();

	/**
	 * Campo Id para las búsquedas JPA que utiliza el token this que será convertido al nombre o alias de la clase sobre la que se realiza la consulta JQL.
	 */
	String getThisIdName();

}
