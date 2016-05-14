/**
 * 
 */
package com.javalego.ui.vaadin.component.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.javalego.ui.UIContext;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.EnumFieldModel;
import com.javalego.ui.vaadin.factory.FieldsFactoryVaadin;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author ROBERTO RANZ
 */
public class InputBox extends CustomComponent implements IInputBox {
	
	private static final long serialVersionUID = -7334717811544827149L;

	private List<InputProperty> properties = new ArrayList<InputProperty>();
	
	private String title;

	private boolean margin = true;
	
	private boolean align_vertical;

	public InputBox() {
	}
	
	public InputBox(boolean align_vertical) {
		this.align_vertical = align_vertical;
	}
	
	/**
	 * Inicializar componentes visuales.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init() {
		
		AbstractOrderedLayout layout = null;
		
		if (!align_vertical) {
			layout = new HorizontalLayout();
			layout.setMargin(margin);
			layout.setSpacing(true);
		}
		else {
			layout = new VerticalLayout();
			layout.setSpacing(true);
			layout.addComponent(new Label(title, ContentMode.HTML));
		}

		if (title != null) {
			layout.setCaption(title);
		}
		
		boolean firstFocused = false;
		
		for(InputProperty property : properties) {
			try {
				Component c = FieldsFactoryVaadin.getCurrent().getUI(property.getProperty());
				
				if (c != null) {
				
					layout.addComponent(c);
					
					if (c instanceof AbstractField) {
					
						AbstractField field = (AbstractField)c;
						
						// Habilitar control de edición.
						field.setEnabled(true);
						field.setReadOnly(false);
						
						//field.setImmediate(true);
						field.setCaption(UIContext.getText(property.getProperty().getTitle()));
						
						if (property.getValue() != null) {
							ObjectProperty p =	new ObjectProperty(property.getValue());
							field.setPropertyDataSource(p);
							property.setObjectProperty(p);
						}
						if (!firstFocused) {
							field.focus();
							firstFocused = true;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		layout.setSizeUndefined();
		if (layout instanceof HorizontalLayout) {
			layout.setExpandRatio(layout.getComponent(layout.getComponentCount()-1), 1);
		}
		setSizeUndefined();
		
		setCompositionRoot(layout);
	}

	@Override
	public List<InputProperty> getProperties() {

		return properties;
	}

	@Override
	public void setProperties(List<InputProperty> properties) {
		
		this.properties = properties;
		
	}

	@Override
	public Component getComponent() {
		
		if (getCompositionRoot() == null)
			init();
		
		return getCompositionRoot();
	}

	@Override
	public void addProperty(InputProperty property) {
		
		properties.add(property);
	}

	@Override
	public InputProperty addProperty(FieldModel property, Object value, Collection<?> values) {
		
		InputProperty p = new InputProperty(property, value);
		
		// Incluir la lista de valores si es un enumerado.
		if (property instanceof EnumFieldModel) {
			for(Object item : (Collection<?>)values) {
				((EnumFieldModel)property).addItem(item.toString(), item);
			}
		}
		
		properties.add(p);
		
		return p;
	}
	
	@Override
	public InputProperty addProperty(FieldModel property, Object value) {
		
		InputProperty p = new InputProperty(property, value);
		
		properties.add(p);
		
		return p;
	}

	/**
	 * Cargar componentes
	 */
	public void load() {
		
		init();
	}

	/**
	 * @return the title
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param b
	 */
	
	@Override
	public void setMargin(boolean margin) {
		this.margin = margin;
	}

	/**
	 * Obtener el valor actual de un objeto en edición.
	 * @param filename
	 */
	public Object getValue(Object object) {

		for(InputProperty item : properties) {
			if (item.getOriginalValue() == object)
				return item.getObjectProperty().getValue();
		}
		return null;
	}

	/**
	 * Obtener el valor String actual de un objeto en edición.
	 * @param object
	 * @return
	 */
	public String getStringValue(Object object) {
		return (String)getValue(object);
	}
	
	/**
	 * Obtener el valor Date actual de un objeto en edición.
	 * @param object
	 * @return
	 */
	public Date getDateValue(Object object) {
		return (Date)getValue(object);
	}	

	/**
	 * Obtener el valor Integer actual de un objeto en edición.
	 * @param object
	 * @return
	 */
	public Integer getIntValue(Object object) {
		return (Integer)getValue(object);
	}	
	
	/**
	 * Obtener el valor Double actual de un objeto en edición.
	 * @param object
	 * @return
	 */
	public Double getDoubleValue(Object object) {
		return (Double)getValue(object);
	}	
	
	/**
	 * Obtener el valor Boolean actual de un objeto en edición.
	 * @param object
	 * @return
	 */
	public Boolean getBooleanValue(Object object) {
		return (Boolean)getValue(object);
	}
	
}
