package com.javalego.ui.vaadin.component;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Componente que incluye un AbstractField y una lista de componentes alineados
 * a su derecha que representan las acciones posibles asociadas al valor editado
 * Ej.: campos de relaciones con otras entidades, editor de expresiones, ...
 * 
 * Especialmente concebido para utilizar campos de edición que tienen una serie
 * de opciones asociadas que se pueden ejecutar mediante buttons u otro tipo de
 * componente de disparador de acciones (ej.: link, checks, ...)
 * 
 * @author ROBERTO RANZ
 * 
 */
public class AbstractFieldActions extends CssLayout {

	private static final long serialVersionUID = 1L;

	private AbstractField<?> textField;

	private boolean hideActions = true;

	/**
	 * Constructor Crear un TextField y añade los componentes de acciones
	 * pasados como parámetro.
	 */
	public AbstractFieldActions() {

		textField = new TextFieldExt();
		generate(null);
	}

	/**
	 * Constructor Crear un TextField y añade los componentes de acciones
	 * pasados como parámetro.
	 */
	public AbstractFieldActions(boolean hasTextField) {

		if (hasTextField) {
			textField = new TextFieldExt();
		}
		generate(null);
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 *            Campo de edición
	 */
	public AbstractFieldActions(AbstractField<?> text) {

		this.textField = text;
		generate(null);
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 *            Campo de edición
	 * @param component
	 *            Componente de acción
	 */
	public AbstractFieldActions(AbstractField<?> text, Component component) {

		this.textField = text;
		generate(new Component[] { component });
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 *            Campo de edición
	 * @param actionComponents
	 *            Lista de componentes de acciones
	 */
	public AbstractFieldActions(AbstractField<?> text, Component[] actionComponents) {

		this.textField = text;
		generate(actionComponents);
	}

	@Override
	public boolean isReadOnly() {
		return textField != null && textField.isReadOnly() ? true : false;
	}

	/**
	 * Generar componentes
	 * 
	 * @param components
	 */
	public void generate(Component[] components) {

		addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		if (textField != null) {
			addComponent(textField);
		}

		if (components != null) {
			for (Component action : components) {
				addAction(action);
			}
		}
	}

	/**
	 * Añadir acción.
	 * 
	 * @param btn
	 */
	public void addAction(Component component) {
		addComponent(component);
	}

	/**
	 * Obtiene el campo de edición
	 * 
	 * @return
	 */
	public AbstractField<?> getField() {
		return textField;
	}

	/**
	 * Obtiene el campo de edición
	 * 
	 * @return
	 */
	public TextField getTextField() {
		if (textField instanceof TextField)
			return (TextField) textField;
		else
			return null;
	}

	/**
	 * Establece el campo de edición
	 * 
	 * @param text
	 */
	public void setField(AbstractField<?> text) {
		this.textField = text;
	}

	/**
	 * Obtiene el componente de edición.
	 * 
	 * @return
	 */
	public AbstractComponent getComponent() {
		return textField;
	}

	/**
	 * Obtiene el número de acciones definidos.
	 * 
	 * @return
	 */
	public int getActionsCount() {
		return getComponentCount() - 1;
	}

	/**
	 * Required
	 * 
	 * @param required
	 */
	public void setRequired(boolean required) {

		if (textField != null) {
			textField.setRequired(required);
		}
	}

	/**
	 * Establece a readOnly el campo de edición y oculta las acciones si es
	 * readOnly y la propiedad hideActions = true.
	 */
	@Override
	public void setReadOnly(boolean readOnly) {

		if (textField != null) {
			textField.setReadOnly(readOnly);
		}

		if (isHideActions()) {
			for (int i = 1; i < getComponentCount(); i++) {
				getComponent(i).setVisible(!readOnly);
			}
		}
	}

	/**
	 * Cuando se establece readOnly en el componente si hideActions = true se
	 * ocultan los componentes de acciones.
	 * 
	 * @return
	 */
	public boolean isHideActions() {
		return hideActions;
	}

	/**
	 * Cuando se establece readOnly en el componente si hideActions = true se
	 * ocultan los componentes de acciones.
	 * 
	 * @param hideActions
	 */
	public void setHideActions(boolean hideActions) {
		this.hideActions = hideActions;
	}

}
