package com.javalego.ui.filter.impl;

import com.javalego.model.AbstractBaseModel;
import com.javalego.model.keys.Key;
import com.javalego.ui.filter.IFilterService;

/**
 * Implementación de un filtro de selección de registros
 * 
 * @author ROBERTO RANZ
 *
 */
public class FilterService extends AbstractBaseModel implements IFilterService {

	private static final long serialVersionUID = 8687115395978013267L;

	private String statement;
	private String naturalStatement;

	/**
	 * Constructor
	 * @param title
	 */
	public FilterService(Key title) {
		setTitle(title);
	}
	
	@Override
	public String getStatement() {
		return statement;
	}

	@Override
	public String getNaturalStatement() {
		return naturalStatement;
	}
	
	public FilterService setNaturalStatement(String statement) {
		this.naturalStatement = statement;
		return this;
	}

	public FilterService setStatement(String statement) {
		this.statement = statement;
		return this;
	}

}
