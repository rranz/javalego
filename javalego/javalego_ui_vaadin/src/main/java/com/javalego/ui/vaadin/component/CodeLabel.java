package com.javalego.ui.vaadin.component;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

/**
 * Ver texto preformateado.
 * @author ROBERTO RANZ
 */
public class CodeLabel extends Label {

	private static final long serialVersionUID = -6543157130722450587L;

	public CodeLabel() {
        setContentMode(ContentMode.PREFORMATTED);
    }

    public CodeLabel(String content) {
        super(content, ContentMode.PREFORMATTED);
    }

}
