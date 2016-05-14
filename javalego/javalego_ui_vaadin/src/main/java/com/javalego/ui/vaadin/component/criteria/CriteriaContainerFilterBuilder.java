package com.javalego.ui.vaadin.component.criteria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.javalego.ui.vaadin.component.criteria.CriteriaField.ClassField;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.ContainerHierarchicalWrapper;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.IsNull;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Not;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;

public class CriteriaContainerFilterBuilder {

	public static Filter getFilter(CriteriaBuilder criteriaBuilder) {
		return getFilter(criteriaBuilder.getItemContainerWrapper());
	}

	public static Filter getFilter(ContainerHierarchicalWrapper itemContainerWrapper) {
		return getGroupFilter(itemContainerWrapper, (CriteriaItem) itemContainerWrapper.rootItemIds().iterator().next());
	}

	@SuppressWarnings("unchecked")
	public static Filter getGroupFilter(ContainerHierarchicalWrapper itemContainerWrapper, CriteriaItem item) {
		Collection<CriteriaItem> children = (Collection<CriteriaItem>) itemContainerWrapper.getChildren(item);
		if (children == null)
			return null;
		Collection<Filter> filters = new ArrayList<Filter>();
		for (CriteriaItem childItem : children) {
			if (childItem.isFlagGroup())
				addNotNullFilter(filters, getGroupFilter(itemContainerWrapper, childItem));
			else
				addNotNullFilter(filters, getElementFilter(itemContainerWrapper, childItem));
		}
		Filter[] filterArray = filters.toArray(new Filter[filters.size()]);
		if (item.getGroupCondition().getName().equalsIgnoreCase("and"))
			return new And(filterArray);
		else
			return new Or(filterArray);

	}

	@SuppressWarnings("incomplete-switch")
	public static Filter getElementFilter(ContainerHierarchicalWrapper itemContainerWrapper, CriteriaItem item) {
		if (item.getField() == null || item.getElementCondition() == null)
			return null;
		Filter filter = null;
		String field = item.getField().getName();
		String stringValue = item.getValue().toString();
		String oper = item.getElementCondition().getName();
		ClassField fieldClass = item.getField().getClassField();
		if (oper.equals("isNull")) {
			filter = new IsNull(field);
		}
		else if (fieldClass == ClassField.STRING) {
			if (oper.equals("like")) {
				filter = new Like(field, stringValue, !item.isIgnoreCase());
			}
			else if (oper.equals("startingWith")) {
				filter = new SimpleStringFilter(field, stringValue, item.isIgnoreCase(), true);
			}
			else if (oper.equals("containing"))
				filter = new SimpleStringFilter(field, stringValue, item.isIgnoreCase(), false);
			else if (oper.equals("equal"))
				filter = new CompareString.Equal(field, stringValue, item.isIgnoreCase());
			else if (oper.equals("greater"))
				filter = new CompareString.Greater(field, stringValue, item.isIgnoreCase());
			else if (oper.equals("less"))
				filter = new CompareString.Less(field, stringValue, item.isIgnoreCase());
			else if (oper.equals("greaterOrEqual"))
				filter = new CompareString.GreaterOrEqual(field, stringValue, item.isIgnoreCase());
			else if (oper.equals("lessOrEqual"))
				filter = new CompareString.LessOrEqual(field, stringValue, item.isIgnoreCase());
			else if (oper.equals("notEqual"))
				filter = new CompareString.NotEqual(field, stringValue, item.isIgnoreCase());
		}
		else {
			Object value = stringValue;
			switch (fieldClass) {
			case INTEGER:
				value = new Integer(stringValue);
				break;
			case FLOAT:
				value = new Double(stringValue);
				break;
			case DATE:
				try {
					value = SimpleDateFormat.getDateInstance().parse(stringValue);
				}
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			if (oper.equals("equal"))
				filter = new Compare.Equal(field, value);
			else if (oper.equals("greater"))
				filter = new Compare.Greater(field, value);
			else if (oper.equals("less"))
				filter = new Compare.Less(field, value);
			else if (oper.equals("greaterOrEqual"))
				filter = new Compare.GreaterOrEqual(field, value);
			else if (oper.equals("lessOrEqual"))
				filter = new Compare.LessOrEqual(field, value);
			else if (oper.equals("notEqual"))
				filter = new Not(new Compare.Equal(field, value));
		}

		if (filter != null && item.isFlagNot())
			filter = new Not(filter);

		return filter;

	}

	private static void addNotNullFilter(Collection<Filter> filters, Filter filter) {
		if (filter != null)
			filters.add(filter);
	}

}
