package com.javalego.ui.vaadin.component.criteria;

import java.util.Collection;

import com.vaadin.ui.Table;

public class CriteriaField {

	public CriteriaField(String name, String displayName, ClassField classField) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.classField = classField;
	}

	public CriteriaField(String name, String displayName) {
		this(name, displayName, ClassField.STRING);
	}

	private String name;
	private String displayName;
	private ClassField classField;

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public ClassField getClassField() {
		return classField;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClassField(ClassField classField) {
		this.classField = classField;
	}

	@Override
	public String toString() {
		return displayName;
	}

	public static enum ClassField {
		INTEGER, FLOAT, DATE, STRING
	};

	public static void getCriteriaField(Table table, Collection<CriteriaField> fields) {
		for (Object col : table.getVisibleColumns()) {
			fields.add(new CriteriaField(col.toString(), table.getColumnHeader(col)));
		}
	}
}
