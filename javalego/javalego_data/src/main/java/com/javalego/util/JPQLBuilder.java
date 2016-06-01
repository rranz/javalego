package com.javalego.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

/**
 * Constructor de sentencias JPQL.
 * <p>
 * En base a los alias crea joins del tipo "left outer join" que evita la
 * pérdida de registros cuando relacionamos varias tablas ya que el uso directo
 * de los campos de otras tablas desde una consulta simple = (ej.) select
 * pais.nombre from divisas provoca que si existen registros sin un país
 * asociado no se recuperan estos registros.
 * <p>
 * Dentro de este generador de sentencias Sql se definen los campos (path
 * incluido pais.nombre) que deseamos recuperar, la tabla principal, la
 * ordenación, la condición y la agrupación, típicas de una sentencia sql. Las
 * tablas relacionadas son se incluyen ya que este generador las extrae en base
 * al path de los campos incluidos y se encarga de sustituir el path de los
 * campos incluidos en las campos, ordenación, agrupación o condición por su
 * alias asociado.
 * <p>
 * Nota: actualmente no es usada en la arquitectura.
 * 
 * @author ROBERTO RANZ
 */
public class JPQLBuilder {

	private static final Logger logger = Logger.getLogger(JPQLBuilder.class);

	/**
	 * Caracteres que debe incluir una expresión de cálculo de un campo que no
	 * esté basado en sql para que este clase realice el cálculo una vez
	 * recuperada la información de la base de datos y aplique la expresión
	 * sobre los valores de los campos de los registros recuperados.
	 */
	public static final String NOT_SQL_EXPRESSION_FIELDS = "@@";

	/**
	 * Carácter que debe incluir una expresión de cálculo de un campo que basado
	 * en sql.
	 */
	public static final String SQL_EXPRESSION_FIELDS = "@";

	/**
	 * Evaluador de expresiones.
	 */
	// static private ObjectEditorExpression expression;

	private String fields[];

	private String order;

	private String where;

	private String groupby;

	private String className;

	private boolean distinct;

	/**
	 * Lista de alias de tablas relacionados que intervienen en la sentencia
	 * JPQL.
	 */
	private final ArrayList<Alias> alias = new ArrayList<Alias>();

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Nombre simple de la referencia a la clase principal.
	 * 
	 * @return
	 */
	private String getClassSimpleName() {

		if (className != null) {
			if (className.lastIndexOf(".") > -1)
				return className.substring(className.lastIndexOf(".") + 1);
			else
				return className;
		}
		else
			return null;
	}

	/**
	 * Obtener sentencia sql.
	 * 
	 * @return
	 */
	public String getStatement() throws LocalizedException {

		loadAlias();

		String tables = getTablesStatement();

		String fields = getFieldsStatement();

		// Campos
		String statement = !StringUtils.isEmpty(fields) ? "select " : "";
		statement += distinct ? "DISTINCT " : "";
		statement += (!StringUtils.isEmpty(fields) ? fields + " " : "") + "from " + tables;

		// Condición
		if (!StringUtils.isEmpty(where)) {
			statement += " where " + getWhereStatement();
		}

		// Ordenación
		if (order != null) {
			statement += " order by " + getOrderStatement();
		}

		// Agrupación
		else if (groupby != null) {
			statement += " group by " + getGroupByStatement();
		}

		return statement;
	}

	/**
	 * Obtener sentencia sql para las tablas
	 * 
	 * @return
	 */
	private String getTablesStatement() {

		String statement = getClassName() + " " + getClassSimpleName();

		if (alias == null)
			return statement;

		for (int i = 0; i < alias.size(); i++) {
			statement += " left outer join " + alias.get(i).relationPath + " " + alias.get(i).name;
		}
		return statement;
	}

	/**
	 * Obtener sentencia sql con los campos definidos.
	 * 
	 * @return
	 */
	private String getFieldsStatement() throws LocalizedException {

		String statement = "";

		if (fields == null)
			return "";

		for (int i = 0; i < fields.length; i++) {

			String field = fields[i];

			// Campos calculados basados en una expresión que no es una
			// sentencia sql sino una expresión de cálculo basada en la
			// información de los campos del registro recuperado en la setencia
			// sql ejecutada. La clase EntityHQLFactory
			// se encargará de realizar la transformación.
			if (field.length() > 2 && field.substring(0, 2).equals(NOT_SQL_EXPRESSION_FIELDS)) {

				statement += (statement.equals("") ? "" : ", ") + "0";

			}
			// Campos calculados definidos en DataTableQueryView para ignorar la
			// asociación de alias.
			else if (field.substring(0, 1).equals(SQL_EXPRESSION_FIELDS)) {

				String value = field.substring(1).replaceAll(Entity.THIS_FULL, getClassSimpleName() + ".");

				// // Se ha incluido una expresión de cálculo.
				// if (value.indexOf("$") > -1) {
				// BasicGanaExpression ge = new EntityEditorExpression();
				// value = ge.translateMessage(value);
				// }

				statement += (statement.equals("") ? "" : ", ") + value;

			}
			else if (field.lastIndexOf(".") > -1) {

				String aliasField = field.substring(0, field.lastIndexOf("."));

				for (int k = 0; k < alias.size(); k++) {

					if (alias.get(k).path.equals(aliasField)) {

						statement += (statement.equals("") ? "" : ", ") + alias.get(k).name + field.substring(field.lastIndexOf("."));
						break;
					}
				}
			}
			// Campos sum() o count() no se incluye el alias.
			else if (field.indexOf("sum(") > -1 || field.indexOf("count(") > -1)

				statement += (statement.equals("") ? "" : ", ") + field;

			// Campos vacíos que deseamos incluir en la sentencia sql.
			else if (field.equals("") || field.equals("''") || field.equals("0") || field.equals("0.0") || field.equals("false") || field.equals("true"))

				statement += (statement.equals("") ? "" : ", ") + (field.equals("") ? "''" : field);

			else
				statement += (statement.equals("") ? "" : ", ") + getClassSimpleName() + "." + field;

		}
		return statement;
	}

	/**
	 * Obtener sentencia sql con la condición definida.
	 * 
	 * @return
	 */
	private String getWhereStatement() {
		return translateStatement(where);
	}

	/**
	 * Obtener sentencia sql con la ordenación definida.
	 * 
	 * @return
	 */
	private String getOrderStatement() {
		return translateStatement(order);
	}

	/**
	 * Obtener sentencia sql con la agrupación definida.
	 * 
	 * @return
	 */
	private String getGroupByStatement() {
		return translateStatement(groupby);
	}

	/**
	 * Traducir los nombres de los campos incluidos en la sentencia sql por los
	 * alias de las tablas relacionadas.
	 * 
	 * @return
	 */
	private String translateStatement(String statement) {

		// Sustituir la palabra clave this por el nombre de la clase.
		if (statement.indexOf(Entity.THIS_FULL) > -1)
			statement = statement.replaceAll(Entity.THIS_FULL, getClassSimpleName() + ".");

		// Hay que buscar los alias de forma descendente para evitar que persona
		// se busque antes que persona.pais ya que cambiaría el alias adecuado.
		for (int i = alias.size() - 1; i > -1; i--) {

			if (alias.get(i).path.indexOf(".") > -1) {

				// Si es una propiedad de un objeto de la clase.
				String find = alias.get(i).path + ".";

				int pos = -1;
				while ((pos = statement.indexOf(find)) > -1) {

					if (pos > 0) {
						// Comprobar que el anterior caracter sea un espacio,
						// coma, paréntesis o paid.
						char c = statement.charAt(pos - 1);
						if (!Character.isLetterOrDigit(c) && c != '_' && c != '-')
							statement = statement.substring(0, statement.indexOf(find)) + alias.get(i).name + statement.substring(statement.indexOf(find) + find.length() - 1);
					}
					else
						statement = alias.get(i).name + statement.substring(0 + (find.length() - 1));
				}
			}
		}

		// Incluye expresiones que debemos evaluar previo a la ejecución de la
		// sentencia sql.
		statement = transformExpression(statement);

		return statement;
	}

	/**
	 * Transformar una expresión en una sentencia sql válida.
	 * 
	 * @param expression
	 * @return
	 */
	public static String transformExpression(String statement) {

		// Incluye expresiones que debemos evaluar.
		if (statement != null && statement.indexOf("{") > -1) {

			// if (expression == null) {
			// expression = new ObjectEditorExpression();
			// expression.setSql(true);
			// }
			//
			// try {
			// expression.setExpression(statement);
			// statement = expression.getValue().toString();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		}
		return statement;
	}

	/**
	 * Cargar los alias.
	 */
	private void loadAlias() {

		if (fields == null)
			return;

		for (int i = 0; i < fields.length; i++) {

			String field = fields[i];

			if (field == null) {
				logger.error("HQLFACTORY: ERROR PORQUE EL CAMPO INDICE = " + i + " ES NULO.");
				continue;
			}

			// Si es una propiedad de un objeto de la clase. (omitir los campos
			// calculados que se incluye el primer carácter igual a @)
			if (field.lastIndexOf(".") > -1 && !field.substring(0, 1).equals(SQL_EXPRESSION_FIELDS)) {

				String aliasField = field.substring(0, field.lastIndexOf("."));

				// Generar todos los alias existentes en el nombre del campo ya
				// que si tiene 2 referencias a dos tablas ej:
				// formador.formador.persona hay
				// que crear el alias para formador, formador y persona. En el
				// caso de existir ya formador solamente habría que crear
				// formador y persona.
				String[] items = aliasField.split("\\.");

				for (int k = 0; k < items.length; k++) {

					// String aliasName = items[k];

					// Buscar y añadir alias sino se localiza.
					boolean find = false;
					String fullAlias = "";

					// Crear el path completo hasta el item actual del for (i =
					// ..)
					for (int h = 0; h <= k; h++)
						fullAlias += (fullAlias.equals("") ? "" : ".") + items[h];

					// Buscar este path en los alias actualmente cargados.
					for (int j = 0; j < alias.size(); j++) {
						if (alias.get(j).path.equals(fullAlias)) {
							find = true;
							break;
						}
					}

					// Añadir alias sino se encuentra.
					if (!find) {
						alias.add(new Alias(fullAlias, fullAlias.replaceAll("\\.", "\\_")));
					}
				}

			}
		}
		if (alias.size() > 0)
			StringUtils.sortList(alias, new String[] { "path" });

		setRelationPath();

	}

	/**
	 * Establecer el nombre que hay que utilizar para establecer la relación
	 * entre las tablas existentes.
	 * 
	 * @return
	 */
	private void setRelationPath() {

		if (alias == null)
			return;

		for (int i = 0; i < alias.size(); i++) {

			String name = alias.get(i).path;

			if (name.lastIndexOf(".") > -1) {

				// Buscar el alias de la tabla relacionada para establecer el
				// path de relación de tablas.
				String relationName = name.substring(0, name.lastIndexOf("."));
				for (int k = 0; k < alias.size(); k++) {
					if (alias.get(k).path.equals(relationName)) {
						alias.get(i).relationPath = alias.get(k).path + name.substring(name.lastIndexOf("."));
						break;
					}
				}
			}
			else
				alias.get(i).relationPath = getClassSimpleName() + "." + alias.get(i).name;
		}
	}

	/**
	 * Alias que contiene el nombre y el path. Ej.: Pais pais. o Persona_Pais
	 * persona.pais.
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class Alias {

		/**
		 * Path de la tabla. Ej.: persona.provincia con nombre =
		 * persona_provincia y relationPath = persona.provincia
		 */
		protected String path;
		/**
		 * Nombre del alias.
		 */
		protected String name;
		/**
		 * Path utilizado para establecer la relación con la tabla master a la
		 * que está relacionada.
		 */
		protected String relationPath;

		public Alias(String path, String name) {
			this.path = path;
			this.name = name;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRelationPath() {
			return relationPath;
		}

		public void setRelationPath(String relationPath) {
			this.relationPath = relationPath;
		}
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

}
