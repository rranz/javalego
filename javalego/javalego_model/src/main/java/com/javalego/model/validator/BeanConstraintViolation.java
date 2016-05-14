package com.javalego.model.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import com.javalego.model.validator.Validator.InvalidValueException;

public class BeanConstraintViolation<T> implements ConstraintViolation<T> {

	private String errorMessage;
	
	//private String propertyName;
	
	private String title;
	
	public BeanConstraintViolation(InvalidValueException exception, String propertyName, String title) {
		this.errorMessage = exception != null ? exception.getLocalizedMessage() : null;
		//this.propertyName = propertyName;
		this.title = title;
	}

	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getExecutableParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getExecutableReturnValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getInvalidValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLeafBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage() {
		return (title != null ? title + ": " : "") + errorMessage;
		//return (title != null ? title + ": " : propertyName != null ? propertyName + ": " : "") + errorMessage;
	}

	@Override
	public String getMessageTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path getPropertyPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getRootBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<T> getRootBeanClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> U unwrap(Class<U> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
