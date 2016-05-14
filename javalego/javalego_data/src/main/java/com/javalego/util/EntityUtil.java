package com.javalego.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.javalego.entity.Entity;
import com.javalego.entity.IdEntity;
import com.javalego.entity.impl.AbstractCompositeEntity;

/**
 * Métodos de utilidades de objetos de entidad JPA para obtener información de campos clave dependiendo de su tipología (numérica, object o composite).
 * 
 * @author ROBERTO RANZ
 */
public class EntityUtil {

	/**
	 * Clase que nos permite la gestión de reflexion en java (commons-bean.jar).
	 */
	static public final PropertyUtilsBean bean = new PropertyUtilsBean();

	/**
	 * Constructor privado para que se pueda instanciar objetos de esta clase.
	 */
	private EntityUtil() {
	};

	/**
	 * Obtener los campos de la clave de la entidad JPA.
	 * 
	 * @param object
	 * @return
	 */
	public static List<String> getCompositeKeys(Entity object) {

		List<String> fieldnames = new ArrayList<String>();

		// Buscar EmbeddedId que contiene la lista de campos de la clave
		for (Field field : getDeclaredFields(object.getClass())) {

			// Id simple
			if (field.getAnnotation(Id.class) != null) {
				fieldnames.add(field.getName());
				break;
			}
			// Id compuesto
			else if (field.getAnnotation(EmbeddedId.class) != null) {

				for (Field pkfield : getDeclaredFields(field.getType())) {
					fieldnames.add(pkfield.getName());
				}
				break;
			}
		}

		return fieldnames.size() == 0 ? null : fieldnames;
	}

	/**
	 * Obtener la lista de campos declarados del objeto y sus superclases hasta Object.
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public Field[] getDeclaredFields(Class<?> _class) {

		ArrayList list = new ArrayList();

		while (_class != Object.class) {

			Field[] f = _class.getDeclaredFields();

			for (int i = 0; i < f.length; i++)
				list.add(f[i]);

			if (_class.getSuperclass() != null)
				_class = _class.getSuperclass();
		}
		return (Field[]) list.toArray(new Field[list.size()]);
	}

	/**
	 * Buscar el campo de la clase que contiene EnmbeddedId.class.
	 * 
	 * @param object
	 * @return
	 */
	public static Field getCompositeKeysField(Entity object) {

		// Buscar EmbeddedId que contiene la lista de campos de la clave
		for (Field field : getDeclaredFields(object.getClass())) {

			if (field.getAnnotation(EmbeddedId.class) != null) {
				return field;
			}
		}

		return null;
	}

	/**
	 * Buscar el campo de la clase que contiene EnmbeddedId.class.
	 * 
	 * @param object
	 * @return
	 */
	public static Field getCompositeKeysField(Class<?> _class) {

		// Buscar EmbeddedId que contiene la lista de campos de la clave
		for (Field field : getDeclaredFields(_class)) {

			if (field.getAnnotation(EmbeddedId.class) != null) {
				return field;
			}
		}

		return null;
	}

	/**
	 * Obtener la sentencia Sql para buscar por la clave de entidad (simple o compuesta).
	 * 
	 * @param alias
	 * @return
	 */
	public static String getWhereStatementWithAlias(Entity object) throws Exception {

		return getWhereStatement(object, object.getClass().getSimpleName());
	}

	/**
	 * Obtener la sentencia Sql para buscar por la clave de la entidad (simple o compuesta).
	 * 
	 * @param alias
	 * @return
	 */
	public static String getWhereStatement(Entity object) throws Exception {

		return getWhereStatement(object, null);
	}

	/**
	 * Obtener la sentencia Sql para buscar por la clave de la entidad (simple o compuesta).
	 * 
	 * @param alias
	 * @return
	 */
	public static String getWhereStatement(Entity object, String alias) throws Exception {

		return getWhereStatement(object, alias, getCompositeKeys(object), getIdValues(object, getCompositeKeys(object)));
	}

	/**
	 * Obtener sentencia Where de la clave de la entidad (simple o compuesta).
	 * 
	 * @param Entity
	 * @param alias
	 * @param idValues
	 * @return
	 */
	public static String getWhereStatement(Entity baseObject, String alias, List<String> fieldnames, List<?> idValues) throws Exception {

		if (fieldnames != null) {

			String where = "";

			int index = 0;

			// Campo clave que contiene la clave compuesta de la propiedad de entidad relacionada.
			String fieldKey = getCompositeKeysField(baseObject) != null ? getCompositeKeysField(baseObject).getName() : "";

			for (String fieldname : fieldnames) {

				Object value = idValues != null ? idValues.get(index) : bean.getProperty(baseObject, "".equals(fieldKey) ? fieldname : fieldKey + "." + fieldname);

				// Si es clave compuesta crear condición clave_fieldname para que la clase HQLFactory no transforme Alias.clave.nombre en Alias.clave_nombre y genere error. TODO
				String name = ("".equals(where) ? "" : " and ") + ("".equals(fieldKey) ? (alias != null ? alias + "." : "") + fieldname : (alias != null ? alias + "." : "") + fieldKey + "." + fieldname);

				where += name + (value == null ? " is null" : " = " + getValue(value, baseObject));
				// Obtener la clave de la entidad para localizar el registro.

				++index;

			}
			return where;
		}
		return null;
	}

	/**
	 * Obtener el valor de un atributo en el formato JQL dependiendo del tipo de valor.
	 * 
	 * @param fieldname
	 * @param baseObject
	 * @return
	 */
	private static String getValue(Object value, Entity baseObject) throws Exception {

		if (value instanceof String)
			return "'" + value.toString() + "'";

		else if (value instanceof Date)
			return "'" + new SimpleDateFormat("yyyyMMdd").format((Date) value) + "'";

		else if (value != null)
			return value.toString();

		else
			return null;
	}

	/**
	 * Obtener los valores clave del objeto de entidad.
	 * 
	 * @param baseObject
	 * @return
	 */
	private static List<?> getIdValues(Entity baseObject, List<String> fieldnames) throws Exception {

		List<Object> values = new ArrayList<Object>();

		boolean exist = false;

		if (fieldnames != null) {

			for (String fieldname : fieldnames) {

				Object value = bean.getProperty(baseObject, fieldname);

				if (value != null)
					exist = true;

				values.add(value);
			}
		}

		return exist ? values : null;
	}

	/**
	 * Poner a null los campos clave del objeto de entidad.
	 * 
	 * @param baseObject
	 */
	public static void setNullId(Entity baseObject) throws Exception {

		// Id
		if (baseObject instanceof IdEntity) {
			bean.setProperty(baseObject, ((IdEntity) baseObject).getIdName(), null);
		}
		// Clave compuesta
		else if (baseObject instanceof AbstractCompositeEntity) {

			for (String fieldname : ((AbstractCompositeEntity) baseObject).getCompositeKeys()) {
				bean.setProperty(baseObject, fieldname, null);
			}
		}

	}

	/**
	 * Obtener el texto de los valores clave del objeto de entidad para ser mostrados en la aplicación y tener una referencia del código compuesto o id del registro asociado. NOTA: incluye el nombre de
	 * los campos
	 * 
	 * @return
	 */
	public static String getTextId(Entity baseObject) throws Exception {

		String text = "";
		// Id
		if (baseObject instanceof IdEntity) {
			text = "ID: " + bean.getProperty(baseObject, ((IdEntity) baseObject).getIdName()).toString();
		}
		// Clave compuesta
		else if (baseObject instanceof AbstractCompositeEntity) {

			for (String fieldname : ((AbstractCompositeEntity) baseObject).getCompositeKeys()) {
				text += ("".equals(text) ? "" : ", ") + fieldname + ": " + bean.getProperty(baseObject, fieldname);
			}
		}
		return text;
	}

	/**
	 * Obtener el texto de los valores clave del objeto de entidad para ser mostrados en la aplicación y tener una referencia del código compuesto o id del registro asociado. NOTA: NO incluye el nombre
	 * de los campos
	 * 
	 * @return
	 */
	public static String getStringId(Entity baseObject) throws Exception {

		String text = "";
		// Id
		if (baseObject instanceof IdEntity) {
			text = bean.getProperty(baseObject, ((IdEntity) baseObject).getIdName()).toString();
		}
		// Clave compuesta
		else if (baseObject instanceof AbstractCompositeEntity) {

			for (String fieldname : ((AbstractCompositeEntity) baseObject).getCompositeKeys()) {
				text += bean.getProperty(baseObject, fieldname);
			}
		}
		return text;
	}

	/**
	 * Obtener el texto de los nombre clave del objeto de entidad separados por el carácter ,. Servirá para mostrar una referencia del código compuesto o id de entidad.
	 * 
	 * @return
	 */
	public static String getFieldNamesId(Entity baseObject) throws Exception {

		String text = "";
		// Id
		if (baseObject instanceof IdEntity) {
			text = ((IdEntity) baseObject).getIdName();
		}
		// Clave compuesta
		else if (baseObject instanceof AbstractCompositeEntity) {

			for (String fieldname : ((AbstractCompositeEntity) baseObject).getCompositeKeys()) {
				text += ("".equals(text) ? "" : ", ") + fieldname;
			}
		}
		return text;
	}

	/**
	 * Token This (nombre entidad para JQL) y nombre del campo clave de la entidad obtenido a partir de la clase de la entidad. Si la entidad es de clave compuesta el método devolverá "".
	 * 
	 * @return
	 */
	public static String getThisIdName(Class<?> _class) {

		return Entity.THIS + "." + getIdName(_class);
	}

	/**
	 * Nombre del campo clave de la entidad obtenido a partir de la clase de la entidad. Si la entidad es de clave compuesta el método devolverá "".
	 * 
	 * @return
	 */
	public static String getIdName(Class<?> _class) {

		for (Field field : getDeclaredFields(_class)) {

			// Anotación @Id
			Id id = field.getAnnotation(Id.class);

			if (id != null) {

				// Anotación @Column para obtener su nombre.
				Column column = field.getAnnotation(Column.class);

				if (column != null && !("".equals(column.name()))) {
					return column.name();
				}
				else {
					break;
				}
			}
		}
		return IdEntity.DEFAULT_ID;
	}

	/**
	 * Nombre del campo clave de la entidad obtenido a partir de la clase de la entidad. Si la entidad es de clave compuesta el método devolverá "".
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static String getIdName(String className) throws ClassNotFoundException {

		return getIdName(Class.forName(className));
	}

	/**
	 * Obtener un objeto en base a la clase Java
	 * 
	 * @param _class
	 * @return
	 */
	public static Entity getObject(Class<?> _class) throws Exception {

		return (Entity) Class.forName(_class.getCanonicalName()).newInstance();
	}

	/**
	 * Comprobar que la clase de la entidad tenga clave compuesta.
	 * 
	 * @param classRef
	 * @return
	 */
	public static boolean hasCompositeKeys(Class<?> _class) {

		for (Field field : getDeclaredFields(_class)) {
			if (field.getAnnotation(EmbeddedId.class) != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si dos objetos tiene el mismo valor de sus Id o clave.
	 * 
	 * @param object
	 * @param object2
	 * @return
	 * @throws Exception
	 */
	public static boolean equalsObjects(Entity object, Entity object2) throws Exception {

		return getStringId(object).equals(getStringId(object2));
	}

}
