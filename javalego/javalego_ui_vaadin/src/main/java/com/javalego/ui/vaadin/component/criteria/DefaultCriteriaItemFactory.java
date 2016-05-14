package com.javalego.ui.vaadin.component.criteria;

public class DefaultCriteriaItemFactory implements CriteriaItemFactory {

	@Override
	public CriteriaItem makeItem(CriteriaItem parent, CriteriaField field, boolean flagNot, CriteriaCondition elementCondition, String value, boolean ignoreCase) {
		return new CriteriaItem((CriteriaItem) parent, field, flagNot, elementCondition, value, ignoreCase);
	}

	@Override
	public CriteriaItem makeGroup(CriteriaItem parent, boolean flagNot, CriteriaCondition groupCondition) {
		return new CriteriaItem((CriteriaItem) parent, flagNot, groupCondition);
	}

}
