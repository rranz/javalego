package com.javalego.ui.vaadin.component.criteria;

import java.util.Collection;

import com.javalego.ui.vaadin.component.criteria.CriteriaField.ClassField;
import com.vaadin.data.util.ContainerHierarchicalWrapper;

public class CriteriaSqlTextBuilder {

	public static String getSql(ContainerHierarchicalWrapper itemContainerWrapper, CriteriaItem root) {
		return getGroupSql(itemContainerWrapper, root);
	}

	public static String getSql(ContainerHierarchicalWrapper itemContainerWrapper) {
		return getGroupSql(itemContainerWrapper, (CriteriaItem) itemContainerWrapper.rootItemIds().iterator().next());
	}

	public static String getSql(CriteriaBuilder criteriaBuilder) {
		return getSql(criteriaBuilder.getItemContainerWrapper());
	}

	@SuppressWarnings("unchecked")
	protected static String getGroupSql(ContainerHierarchicalWrapper itemContainerWrapper, CriteriaItem item) {
		String str = null;
		String strChild;
		String oper = item.getGroupCondition().getName();
		for (CriteriaItem childItem : (Collection<CriteriaItem>) (itemContainerWrapper.getChildren(item))) {
			if (childItem.isFlagGroup())
				strChild = getGroupSql(itemContainerWrapper, childItem);
			else
				strChild = getElementSql(itemContainerWrapper, childItem);
			if (strChild != null) {
				if (str == null)
					str = strChild;
				else
					str = str + " " + oper + " " + strChild;
			}
		}
		if (str != null) {
			str = "(" + str + ")";
			if (item.isFlagNot())
				str = " NOT " + str;
		}
		return str;
	}

	protected static String getElementSql(ContainerHierarchicalWrapper itemContainerWrapper, CriteriaItem item) {
		if (item.getField() == null || item.getElementCondition() == null)
			return null;
		String field = item.getField().getName();
		String value = item.getValue().toString();
		String str;
		String oper = item.getElementCondition().getName();
		if (oper.equals("isNull")) {
			if (item.isFlagNot())
				return field + " is not null";
			else
				return field + " is null";
		}

		ClassField fieldClass = item.getField().getClassField();
		if (item.isIgnoreCase() && fieldClass == ClassField.STRING) {
			field = "UPPER(" + field + ")";
			value = value.toUpperCase();
		}

		if (fieldClass == ClassField.DATE || fieldClass == ClassField.STRING) {
			if (oper.equals("startingWith"))
				value = "'" + prepareStringtoLikeOper(value) + "%'";
			else if (oper.equals("containing"))
				value = "'%" + prepareStringtoLikeOper(value) + "%'";
			else
				value = "'" + value + "'";
		}

		if (oper.equals("equal"))
			str = field + "=" + value;
		else if (oper.equals("greater"))
			str = field + ">" + value;
		else if (oper.equals("less"))
			str = field + "<" + value;
		else if (oper.equals("greaterOrEqual"))
			str = field + ">=" + value;
		else if (oper.equals("lessOrEqual"))
			str = field + "<=" + value;
		else if (oper.equals("notEqual"))
			str = field + "!=" + value;
		else if (oper.equals("startingWith") || oper.equals("like") || oper.equals("containing"))
			str = field + " like " + value;
		else
			str = null;

		if (str != null && item.isFlagNot())
			str = " NOT " + str;

		return str;
	}

	protected static String prepareStringtoLikeOper(String s) {
		return s.replace("\\", "\\\\").replace("_", "\\_").replace("%", "\\%");
	}

}
