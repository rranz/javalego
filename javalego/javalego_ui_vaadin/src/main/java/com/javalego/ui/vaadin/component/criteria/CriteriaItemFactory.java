package com.javalego.ui.vaadin.component.criteria;

public interface CriteriaItemFactory {
	public CriteriaItem makeItem(CriteriaItem parent, CriteriaField field, boolean flagNot, CriteriaCondition elementCondition, String value, boolean ignoreCase);

	public CriteriaItem makeGroup(CriteriaItem parent, boolean flagNot, CriteriaCondition groupCondition);
}
