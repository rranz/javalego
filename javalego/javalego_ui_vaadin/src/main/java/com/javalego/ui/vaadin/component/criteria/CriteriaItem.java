package com.javalego.ui.vaadin.component.criteria;

import java.text.SimpleDateFormat;

import com.javalego.ui.vaadin.component.criteria.CriteriaField.ClassField;

public class CriteriaItem {

	final static int VALID_RECORD = 0;
	final static int ERR_NA_FIELD = -1;
	final static int ERR_NA_ELEMENT_CONDITION = -2;
	final static int ERR_NA_GROUP_CONDITION = -3;
	final static int ERR_NA_VALUE = -4;
	final static int ERR_INVALID_VALUE = -5;
	final static int ERR_INVALID_ELEMENT_CONDITION = -6;

	/**
	 * Create element
	 * 
	 * @param parent
	 * @param field
	 * @param flagNot
	 * @param elementCondition
	 * @param value
	 */
	public CriteriaItem(CriteriaItem parent, CriteriaField field, boolean flagNot, CriteriaCondition elementCondition, String value, boolean ignoreCase) {
		super();
		this.parent = parent;
		this.field = field;
		this.flagNot = flagNot;
		this.elementCondition = elementCondition;
		this.value = value;
		this.flagGroup = false;
		this.ignoreCase = ignoreCase;
	}

	/**
	 * Create group
	 * 
	 * @param parent
	 * @param flagNot
	 * @param groupCondition
	 */
	public CriteriaItem(CriteriaItem parent, boolean flagNot, CriteriaCondition groupCondition) {
		super();
		this.parent = parent;
		this.flagNot = flagNot;
		this.groupCondition = groupCondition;
		this.flagGroup = true;
	}

	public CriteriaItem() {
		super();
	}

	private CriteriaItem parent;
	private boolean flagGroup;
	private CriteriaField field;
	private boolean flagNot;
	private CriteriaCondition elementCondition;
	private CriteriaCondition groupCondition;
	private String value;
	private boolean ignoreCase;

	public CriteriaItem getParent() {
		return parent;
	}

	public void setParent(CriteriaItem parent) {
		this.parent = parent;
	}

	public boolean isFlagGroup() {
		return flagGroup;
	}

	public void setFlagGroup(boolean flagGroup) {
		this.flagGroup = flagGroup;
	}

	public CriteriaField getField() {
		return field;
	}

	public void setField(CriteriaField field) {
		this.field = field;
	}

	public boolean isFlagNot() {
		return flagNot;
	}

	public void setFlagNot(boolean flagNot) {
		this.flagNot = flagNot;
	}

	public CriteriaCondition getElementCondition() {
		return elementCondition;
	}

	public void setElementCondition(CriteriaCondition elementCondition) {
		this.elementCondition = elementCondition;
	}

	public CriteriaCondition getGroupCondition() {
		return groupCondition;
	}

	public void setGroupCondition(CriteriaCondition groupCondition) {
		this.groupCondition = groupCondition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public int validate() {
		if (flagGroup) {
			if (groupCondition == null)
				return ERR_NA_GROUP_CONDITION;
		}
		else {
			if (field == null)
				return ERR_NA_FIELD;
			if (elementCondition == null)
				return ERR_NA_ELEMENT_CONDITION;
			String elementConditionName = elementCondition.getName();
			if (value == null && !elementConditionName.equals("isNull"))
				return ERR_NA_VALUE;
			ClassField classField = field.getClassField();
			if (classField != ClassField.STRING && (elementConditionName.equals("startingWith") || elementConditionName.equals("containing") || elementConditionName.equals("like")))
				return ERR_INVALID_ELEMENT_CONDITION;
			try {
				switch (classField) {
				case INTEGER:
					Integer.parseInt(value);
					break;
				case FLOAT:
					Float.parseFloat(value);
					break;
				case DATE:
					SimpleDateFormat.getDateInstance().parse(value);
					break;
				default:
					break;
				}
			}
			catch (Exception E) {
				return ERR_INVALID_VALUE;
			}
		}
		return VALID_RECORD;
	}

}
