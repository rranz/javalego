package com.javalego.ui.vaadin.component.criteria;

import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;

public class CriteriaConditionContainer {

	private ResourceBundle bundle;
	private HashMap<String, CriteriaCondition> groupConditionMap;
	private HashMap<String, CriteriaCondition> elementConditionMap;
	private Container elementConditionContainer;
	private Container elementConditionWoStringContainer;

	public CriteriaConditionContainer() {
		super();
		bundle = ResourceBundle.getBundle("com/javalego/ui/vaadin/component/criteria/criteriabuilder");
		createGroupCondition();
		createElementCondition();
	}

	public Collection<CriteriaCondition> getGroupConditionCollection() {
		return groupConditionMap.values();
	}

	public Collection<CriteriaCondition> getElementConditionCollection() {
		return elementConditionMap.values();
	}

	public CriteriaCondition getElementCondition(String key) {
		return elementConditionMap.get(key);
	}

	public CriteriaCondition getGroupCondition(String key) {
		return groupConditionMap.get(key);
	}

	private void createGroupCondition() {
		groupConditionMap = new HashMap<String, CriteriaCondition>();
		putGroupCondition("and");
		putGroupCondition("or");
	}

	private void createElementCondition() {
		elementConditionMap = new HashMap<String, CriteriaCondition>();
		putElementCondition("equal");
		putElementCondition("notEqual");
		putElementCondition("greater");
		putElementCondition("greaterOrEqual");
		putElementCondition("less");
		putElementCondition("lessOrEqual");
		putElementCondition("isNull");
		elementConditionWoStringContainer = new IndexedContainer(elementConditionMap.values());
		putElementCondition("startingWith");
		putElementCondition("containing");
		putElementCondition("like");
		elementConditionContainer = new IndexedContainer(elementConditionMap.values());
	}

	private void addToSet(HashMap<String, CriteriaCondition> set, String key) {
		set.put(key, new CriteriaCondition(key, bundle.getString(key)));
	}

	private void putGroupCondition(String key) {
		addToSet(groupConditionMap, key);
	}

	private void putElementCondition(String key) {
		addToSet(elementConditionMap, key);
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public Container getElementConditionWoStringContainer() {
		return elementConditionWoStringContainer;
	}

	public Container getElementConditionContainer() {
		return elementConditionContainer;
	}

}
