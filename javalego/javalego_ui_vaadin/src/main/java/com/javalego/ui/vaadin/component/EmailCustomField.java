package com.javalego.ui.vaadin.component;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

public class EmailCustomField extends CustomField<EmailField2> {

	private static final long serialVersionUID = 3271721135834792537L;

	@Override
	protected Component initContent() {
		return new EmailField2();
	}

	@Override
	public Class<? extends EmailField2> getType() {
		return EmailField2.class;
	}

}
