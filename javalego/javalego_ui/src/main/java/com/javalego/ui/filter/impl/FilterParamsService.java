package com.javalego.ui.filter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.condition.Condition;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.editor.data.IDataFieldModel;
import com.javalego.ui.editor.data.impl.ValueDataEditor;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.filter.IFilterParam;
import com.javalego.ui.filter.IFilterParamsService;

/**
 * Servicio de generación de un filtro basado en parámetros.
 * 
 * @author ROBERTO RANZ
 *
 */
public class FilterParamsService implements IFilterParamsService {

	private List<IFilterParam> params = new ArrayList<IFilterParam>();

	protected String statement;

	protected String naturalStatement;

	private Key title;

	private Key description;

	private String name;

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param params
	 *            lista de filtros basados en parámetros.
	 */
	public FilterParamsService(Key title, IFilterParam... params) {
		this.title = title;
		addParams(params);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param params
	 *            lista de filtros basados en parámetros.
	 */
	public FilterParamsService(String name, Key title, IFilterParam... params) {
		this(title, params);
		this.name = name;
	}

	/**
	 * Añadir parámetros
	 * 
	 * @param params
	 */
	public void addParams(IFilterParam... params) {

		if (params != null) {

			for (IFilterParam item : params) {
				this.params.add(item);
			}
		}
	}

	/**
	 * Construir la sentencia en base a los valores de cada unos de los
	 * parámetros definidos. Esta información normalmente la aporta el presenter
	 * del editor del filtro obteniendo los datos de la vista que es la que
	 * editar los parámetros.
	 * 
	 * @param values
	 */
	@Override
	public void build(Map<String, ?> values) throws LocalizedException {

		StringBuffer text = new StringBuffer();

		int index = 0;
		for (Entry<String, ?> value : values.entrySet()) {

			IFilterParam param = params.get(index);

			// Buscar de gana7 condition y composición de filtros.
			FieldModel model = null;

			if (param.getDataEditor() instanceof IDataFieldModel) {
				model = ((IDataFieldModel) param).getFieldModel();
			}
			else {
				model = new TextFieldModel(param.getDataEditor().getName(), title);
			}

			text.append((text.length() > 0 ? " and " : "") + Condition.getSQLValue(param.getCondition(), value.getValue(), model));

			++index;
		}

		statement = text.toString();
	}

	@Override
	public String getStatement() {
		return statement;
	}

	@Override
	public String getNaturalStatement() {
		return naturalStatement;
	}

	@Override
	public Collection<IFilterParam> getParams() {
		return params;
	}

	@Override
	public Key getTitle() {
		return title;
	}

	public void setTitle(Key title) {
		this.title = title;
	}

	@Override
	public Key getDescription() {
		return description;
	}

	public void setDescription(Key description) {
		this.description = description;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Añadir parámetro básico (valor + condición)
	 * 
	 * @param name
	 * @param title
	 * @param value
	 * @param condition
	 */
	public void addParam(String name, Key title, Object value, ConditionType condition) {

		params.add(new FilterParam(new ValueDataEditor(name, title, value), condition));
	}

	/**
	 * Añadir parámetro básico (valor por defecto = new String() + condición)
	 * 
	 * @param name
	 * @param title
	 * @param condition
	 */
	public void addParam(String name, Key title, ConditionType condition) {

		addParam(name, title, new String(), condition);
	}
}
