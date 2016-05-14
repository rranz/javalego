package com.javalego.store.ui.components.fields;

import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.component.rating.LocaleRating;
import com.javalego.ui.vaadin.component.rating.Rating;
import com.vaadin.data.Validator;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

/**
 * Campo Rating para Vaadin.
 * 
 * @author ROBERTO RANZ
 *
 */
public class RatingField extends CustomField<Integer> {

	private static final long serialVersionUID = 2513526579111090116L;

	private Rating rating;
	
	@Override
	protected Component initContent() {

		rating = new Rating(super.getInternalValue());
		
		setValidationVisible(false);
		
		return rating;
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

	@Override
	protected void setInternalValue(Integer value) {

		super.setInternalValue(value);
	}

    @Override
    public void validate() throws Validator.InvalidValueException {

        if (isRequired() && rating != null && rating.getValue() == 0) {
            throw new Validator.EmptyValueException(UIContext.getText(LocaleRating.RATING_ERROR));
        }
    }	
	
	@Override
	protected Integer getInternalValue() {

		return rating != null ? rating.getValue() : super.getInternalValue() == null ? 0 : super.getInternalValue();
	}

}
