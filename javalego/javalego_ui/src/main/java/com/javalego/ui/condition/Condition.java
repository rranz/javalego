/*
 * Created on 16-abr-2005
 */
package com.javalego.ui.condition;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.BooleanFieldModel;
import com.javalego.ui.field.impl.NumberFieldModel;
import com.javalego.util.SystemUtils;
import com.javalego.util.StringUtils;

/**
 * Definición de una condición utilizada en filtros de selección como JTableExt.
 * (ver JComboCondition).
 * 
 * @author ROBERTO
 */
public class Condition {

	private Condition() {
	}

	/**
	 * Obtiene la expresión de la condición en formato SQL.
	 * 
	 * @param fieldValue
	 * @param isNumber
	 * @return String
	 */
	public static String getSQLValue(ConditionType conditionType, Object value, FieldModel field) {

		String fieldName = field.getName();

		String text = "";

		if (value != null) {

			if (value instanceof Number) {

				text = value.toString();
			}
			else if (value instanceof Date) {

				text = StringUtils.getSingleQuotedStr(new SimpleDateFormat().format(value));
			}
			else if (value instanceof Boolean) {
				text = value.toString();
			}
			else {
				text = StringUtils.getSingleQuotedStr(value.toString());
			}
		}
		else {
			if (field instanceof BooleanFieldModel) {
				text = "false";
			}
			else if (field instanceof NumberFieldModel) {
				text = "0";
			}
			else {
				text = "''";
			}
		}

		String condition = null;

		if (conditionType == ConditionType.EQUAL)
			condition = "= " + text;
		else if (conditionType == ConditionType.STARTWITH)
			condition = "LIKE '" + StringUtils.removeAll(text, '\'') + "%'";
		else if (conditionType == ConditionType.ENDWITH)
			condition = "LIKE '%" + StringUtils.removeAll(text, '\'') + "'";
		else if (conditionType == ConditionType.DISTINCT)
			condition = "<> " + text;
		else if (conditionType == ConditionType.MINOR)
			condition = "< " + text;
		else if (conditionType == ConditionType.MINOREQUAL)
			condition = "<= " + text;
		else if (conditionType == ConditionType.MAJOR)
			condition = "> " + text;
		else if (conditionType == ConditionType.MAJOREQUAL)
			condition = ">= " + text;
		else if (conditionType == ConditionType.CONTAINS)
			condition = "LIKE '%" + StringUtils.removeAll(text, '\'') + "%'";
		else if (conditionType == ConditionType.NOCONTAINS)
			condition = "NOT LIKE '%" + StringUtils.removeAll(text, '\'') + "%'";
		else if (conditionType == ConditionType.WITHVALUE)
			condition = "IS NOT NULL";
		else if (conditionType == ConditionType.NOTVALUE)
			condition = "IS NULL";
		else if (conditionType == ConditionType.EXISTLIST)
			condition = "IN (" + transformInValues(text, field) + ")";
		else if (conditionType == ConditionType.NOTEXISTLIST)
			condition = "NOT IN (" + transformInValues(text, field) + ")";
		else if (conditionType == ConditionType.BETWEEN)
			condition = transformBetween(field, text);
		else if (conditionType == ConditionType.NOTBETWEEN)
			condition = transformBetween(field, text);
		else if (conditionType == ConditionType.CONTAINS_WORDS)
			condition = transformContainsWords(fieldName, text, true);
		else if (conditionType == ConditionType.CONTAINS_ANY_WORDS)
			condition = transformContainsWords(fieldName, text, false);

		if (condition != null && condition.indexOf("LIKE '") > -1 && condition.indexOf("_") > -1) {
			condition = condition.replaceAll("_", "!_");
			condition += " escape '!'";
		}

		// Crear condición completa incluyendo en nombre del campo
		if (conditionType != ConditionType.CONTAINS_WORDS && conditionType != ConditionType.CONTAINS_ANY_WORDS) {

			if (conditionType == ConditionType.NOTBETWEEN)
				condition = "not " + fieldName + " " + condition;
			else
				condition = fieldName + " " + condition;
		}

		// Modificar la , por el punto para los campos numéricos ya que la base
		// de datos sólo buscar por . decimal y daría error.
		if (field instanceof NumberFieldModel && conditionType != ConditionType.EXISTLIST && conditionType != ConditionType.NOTEXISTLIST)
			condition = condition.replace(",", ".");

		return condition;
	}

	/**
	 * Crear condición Between.
	 * 
	 * @param field
	 * @param fieldValue
	 * @param b
	 * @return
	 */
	private static String transformBetween(FieldModel field, String fieldValue) {

		String value = "";

		if (!StringUtils.isEmpty(fieldValue)) {

			if (fieldValue.indexOf(",") > -1)
				fieldValue = fieldValue.replaceAll(",", " ");

			while (fieldValue.indexOf("  ") > -1) {
				fieldValue = fieldValue.replaceAll("  ", " ");
			}

			String[] items = fieldValue.split(" ");

			if (items.length != 2)
				return value;

			String value1 = items[0].trim();
			String value2 = items[1].trim();
			value = "between " + (field instanceof NumberFieldModel || field instanceof BooleanFieldModel ? value1 : "'" + value1 + "'") + " and "
					+ (field instanceof NumberFieldModel || field instanceof BooleanFieldModel ? value2 : "'" + value2 + "'");

			return value;
		}
		else
			return value;
	}

	/**
	 * Transformar una lista de palabras en una sentencia sql que busque estas
	 * palabras mediante la cláusula LIKE %xxx%.
	 * 
	 * @param text
	 * @param and
	 * @return
	 */
	private static String transformContainsWords(String fieldName, String text, boolean and) {

		String value = "";

		if (!StringUtils.isEmpty(text)) {

			// Para evitar que el split cree items en blanco.
			while (text.indexOf("  ") > -1) {
				text = text.replaceAll("  ", " ");
			}

			String[] items = text.split(" ");

			for (int i = 0; i < items.length; i++) {

				value += fieldName + " like '%" + items[i] + "%'";

				if (i < items.length - 1)
					value += and ? " and " : " or ";
			}

			if (!and && value != null && items.length > 1)
				value = "(" + value + ")";
		}
		return value;
	}

	/**
	 * Transformar el valor de búsqueda para ser utilizado por la función
	 * in(,,,) con el tipo de valor adecuado, usando comillas o no.
	 * 
	 * @param value
	 * @return
	 */
	private static String transformInValues(String value, FieldModel field) {

		if (!StringUtils.isEmpty(value)) {

			// Para evitar que el split cree items en blanco.
			while (value.indexOf("  ") > -1) {
				value = value.replaceAll("  ", " ");
			}

			// Transformar los espacios por comas.
			if (value.indexOf(",") < 0 && value.indexOf(" ") > -1) {
				value = value.replaceAll(" ", ",");
			}

			String[] items = value.split(",");
			String result = "";

			for (String item : items) {
				result += (!"".equals(result) ? ", " : "") + (field instanceof NumberFieldModel || field instanceof BooleanFieldModel ? item.trim() : "'" + item.trim() + "'");
			}

			return result;
		}
		else
			return "";
	}

	/**
	 * Obtiene la expresión de la condición de forma natural no sql.
	 * 
	 * @param fieldValue
	 * @return String
	 */
	public static String getNaturalValue(Object value, ConditionType conditionType, FieldModel field) {

		String text = "";
		if (value != null) {
			if (value instanceof Number || value instanceof Boolean)
				text = value.toString();
			else
				text = StringUtils.getSingleQuotedStr(value.toString());
		}

		String condition = null;

		if (conditionType == ConditionType.EQUAL)
			condition = "= " + text;
		else if (conditionType == ConditionType.STARTWITH)
			condition = ConditionType.STARTWITH + SystemUtils.removeAll(value.toString(), '\'') + "'";
		else if (conditionType == ConditionType.ENDWITH)
			condition = ConditionType.ENDWITH + SystemUtils.removeAll(value.toString(), '\'') + "'";
		else if (conditionType == ConditionType.DISTINCT)
			condition = "<> " + text;
		else if (conditionType == ConditionType.MINOR)
			condition = "< " + text;
		else if (conditionType == ConditionType.MINOREQUAL)
			condition = "<= " + text;
		else if (conditionType == ConditionType.MAJOR)
			condition = "> " + text;
		else if (conditionType == ConditionType.MAJOREQUAL)
			condition = ">= " + text;
		else if (conditionType == ConditionType.CONTAINS)
			condition = ConditionType.CONTAINS + SystemUtils.removeAll(value.toString(), '\'') + "'";
		else if (conditionType == ConditionType.NOCONTAINS)
			condition = ConditionType.NOCONTAINS + SystemUtils.removeAll(value.toString(), '\'') + "'";
		else if (conditionType == ConditionType.WITHVALUE)
			condition = ConditionType.WITHVALUE.name();
		else if (conditionType == ConditionType.NOTVALUE)
			condition = ConditionType.NOTVALUE.name();
		else if (conditionType == ConditionType.EXISTLIST)
			condition = ConditionType.EXISTLIST + value.toString() + ")";
		else if (conditionType == ConditionType.NOTEXISTLIST)
			condition = ConditionType.NOTEXISTLIST + value.toString() + ")";
		else if (conditionType == ConditionType.BETWEEN)
			condition = ConditionType.BETWEEN + value.toString() + ")";
		else if (conditionType == ConditionType.NOTBETWEEN)
			condition = ConditionType.NOTBETWEEN + value.toString() + ")";
		else if (conditionType == ConditionType.CONTAINS_WORDS)
			condition = ConditionType.CONTAINS_WORDS + " " + text;
		else if (conditionType == ConditionType.CONTAINS_ANY_WORDS)
			condition = ConditionType.CONTAINS_ANY_WORDS + " " + text;
		else
			condition = null;

		return (field != null ? field.getTitle() + " " : "") + condition;
	}

	// /**
	// * Obtiene el valor natural de la condición.
	// *
	// * @param condition
	// * @return String
	// */
	// static public String getNaturalCondition(String conditionType) {
	// String condition = "";
	// if (conditionType == ConditionType.EQUAL)
	// condition = ConditionType.EQUAL.name();
	// else if (conditionType == ConditionType.STARTWITH)
	// condition = ConditionType.STARTWITH.name();
	// else if (conditionType == ConditionType.ENDWITH)
	// condition = ConditionType.ENDWITH.name();
	// else if (conditionType == ConditionType.DISTINCT))
	// condition = DISTINCT;
	// else if (conditionType == ConditionType.MINOR))
	// condition = MINOR;
	// else if (conditionType == ConditionType.MINOREQUAL))
	// condition = MINOREQUAL;
	// else if (conditionType == ConditionType.MAJOR))
	// condition = MAJOR;
	// else if (conditionType == ConditionType.MAJOREQUAL))
	// condition = MAJOREQUAL;
	// else if (conditionType == ConditionType.VALUEINCLUDE))
	// condition = VALUEINCLUDE;
	// else if (conditionType == ConditionType.VALUENOTINCLUDE))
	// condition = VALUENOTINCLUDE;
	// else if (conditionType == ConditionType.WITHVALUE))
	// condition = WITHVALUE;
	// else if (conditionType == ConditionType.NOTVALUE))
	// condition = NOTVALUE;
	// else if (conditionType == ConditionType.EXISTLIST))
	// condition = EXISTLIST;
	// else if (conditionType == ConditionType.NOTEXISTLIST))
	// condition = NOTEXISTLIST;
	// else if (conditionType == ConditionType.BETWEEN))
	// condition = BETWEEN;
	// else if (conditionType == ConditionType.NOTBETWEEN))
	// condition = NOTBETWEEN;
	// else if (conditionType == ConditionType.CONTAINS_WORDS))
	// condition = CONTAINS_WORDS;
	// else if (conditionType == ConditionType.CONTAINS_ANY_WORDS))
	// condition = CONTAINS_ANY_WORDS;
	// else
	// condition = null;
	//
	// return condition;
	// }

	// /**
	// * Evalúa si la condición es válida con un valor pasado como parámetro.
	// *
	// * @param value
	// * @return boolean
	// */
	// public boolean evaluate(Object value, Object value2) {
	//
	// // CaseSensitive
	// if (value2 != null && !caseSensitive) {
	// value2 = value2.toString().toUpperCase();
	// }
	// if (value != null && !caseSensitive) {
	// value = value.toString().toUpperCase();
	// }
	//
	// // evaluar
	// if (conditionType == ConditionType.EQUAL) && value ==
	// ConditionType.value2))
	// return true;
	// else if (conditionType == ConditionType.STARTWITH) &&
	// value.toString().startsWith(value2.toString()))
	// return true;
	// else if (conditionType == ConditionType.ENDWITH) &&
	// value.toString().endsWith(value2.toString()))
	// return true;
	// else if (conditionType == ConditionType.DISTINCT) && !value ==
	// ConditionType.value2))
	// return true;
	// else if (conditionType == ConditionType.VALUEINCLUDE) &&
	// value.toString().indexOf(value2.toString()) > -1)
	// return true;
	// else if (conditionType == ConditionType.VALUENOTINCLUDE) &&
	// value.toString().indexOf(value2.toString()) < 0)
	// return true;
	// /*
	// * else if (conditionType == ConditionType.MINOR) &&
	// value.toString().hashCode() <
	// * value2.toString().hashCode()) return true; else if
	// * (conditionType == ConditionType.MINOREQUAL) &&
	// value.toString().hashCode() <=
	// * value2.toString().hashCode()) return true; else if
	// * (conditionType == ConditionType.MAJOR) && value.toString().hashCode() >
	// * value2.toString().hashCode()) return true; else if
	// * (conditionType == ConditionType.MAJOREQUAL) &&
	// value.toString().hashCode() >=
	// * value2.toString().hashCode()) return true;
	// */
	// else
	// return false;
	// }

}
