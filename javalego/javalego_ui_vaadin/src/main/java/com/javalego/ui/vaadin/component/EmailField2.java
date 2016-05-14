package com.javalego.ui.vaadin.component;

import java.util.Collection;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Email
 * 
 * @author ROBERTO RANZ
 */
public class EmailField2 extends AbstractFieldActions implements Field<String> {

	private static final long serialVersionUID = -3060440120670335857L;

	@SuppressWarnings("rawtypes")
	private Property datasource;
	
	private boolean required;

	private String requiredMessage;

	public EmailField2() {
		super();
		init();
	}
	
	@Override
	public void focus() {
		super.focus();
	}	
	
	private void init() {
		Button button = new Button(FontAwesome.MAIL_FORWARD);
		button.addStyleName(ValoTheme.BUTTON_LINK);
		addComponent(button);
	}

	@Override
	public boolean isInvalidCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInvalidCommitted(boolean isCommitted) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void commit() throws SourceException, InvalidValueException {
		datasource.setValue(getValue());
	}

	@Override
	public void discard() throws SourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBuffered(boolean buffered) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isBuffered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void addValidator(Validator validator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeValidator(Validator validator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAllValidators() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Validator> getValidators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate() throws InvalidValueException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInvalidAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValue() {
		return getTextField().getValue().trim().equals("") ? null : getTextField().getValue();
	}

	@Override
	public void setValue(String newValue) throws com.vaadin.data.Property.ReadOnlyException {
		getTextField().setValue("".equals(newValue) ? null : newValue);
	}

	@Override
	public Class<? extends String> getType() {
		return String.class;
	}

	@Override
	public void addValueChangeListener(com.vaadin.data.Property.ValueChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(com.vaadin.data.Property.ValueChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeValueChangeListener(com.vaadin.data.Property.ValueChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(com.vaadin.data.Property.ValueChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setPropertyDataSource(Property newDataSource) {
		datasource = newDataSource;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Property getPropertyDataSource() {
		return datasource;
	}

	@Override
	public int getTabIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTabIndex(int tabIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
	}	
	
	@Override
	public void setRequiredError(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	@Override
	public String getRequiredError() {
		return requiredMessage;
	}
	
}
