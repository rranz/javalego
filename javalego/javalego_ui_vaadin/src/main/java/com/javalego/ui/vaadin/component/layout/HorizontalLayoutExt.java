package com.javalego.ui.vaadin.component.layout;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 setContent(new MVerticalLayout().withFullHeight().with(a, b,
        new MHorizontalLayout(c, d).withFullWidth().withMargin(false)));
 *
 */
public class HorizontalLayoutExt extends HorizontalLayout {

	private static final long serialVersionUID = -8668945058885079914L;

	public HorizontalLayoutExt() {
        super.setSpacing(true);
        super.setMargin(true);
    }

    public HorizontalLayoutExt(Component... components) {
        this();
        addComponents(components);
    }

    public HorizontalLayoutExt with(Component... components) {
        addComponents(components);
        return this;
    }

    public HorizontalLayoutExt withSpacing(boolean spacing) {
        setSpacing(spacing);
        return this;
    }

    public HorizontalLayoutExt withMargin(boolean marging) {
        setMargin(marging);
        return this;
    }

    public HorizontalLayoutExt withWidth(String width) {
        setWidth(width);
        return this;
    }

    public HorizontalLayoutExt withFullWidth() {
        setWidth("100%");
        return this;
    }

    public HorizontalLayoutExt withHeight(String height) {
        setHeight(height);
        return this;
    }

    public HorizontalLayoutExt withFullHeight() {
        setHeight("100%");
        return this;
    }

}
