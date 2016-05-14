package com.javalego.ui.vaadin.component;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 * Elemento incluido en la lista gestionada por el componente ListValuesLayout.
 * Este componente nos permitirá agrupar toda la información relativa a cada item de la lista:
 * 1. CheckBox
 * 2. Label
 * 3. Componente adicional
 * 4. Object data
 * ...
 * @author ROBERTO RANZ
 */
public class ListValuesLayoutItem {

	private Object data;
	
	private CheckBox check;
	
	private Label label;
	
	private Component additionalComponent;
	
	private Component component;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public CheckBox getCheck() {
		return check;
	}

	public void setCheck(CheckBox check) {
		this.check = check;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Component getAdditionalComponent() {
		return additionalComponent;
	}

	public void setAdditionalComponent(Component additionalComponent) {
		this.additionalComponent = additionalComponent;
	}

	public Component getComponent() {
		return component;
	}

	public void setEmbebed(Component component) {
		this.component = component;
	}
	
}
