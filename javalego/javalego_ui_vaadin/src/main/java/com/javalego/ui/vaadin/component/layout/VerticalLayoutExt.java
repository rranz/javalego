package com.javalego.ui.vaadin.component.layout;

import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**

setContent(new MVerticalLayout().withFullHeight().with(a, b,
        new MHorizontalLayout(c, d).withFullWidth().withMargin(false)));
 *
 */
public class VerticalLayoutExt extends VerticalLayout {

	private static final long serialVersionUID = 6688250637072506676L;

	public VerticalLayoutExt() {
        super.setSpacing(true);
        super.setMargin(true);
    }

    public VerticalLayoutExt(Component... components) {
        this();
        addComponents(components);
    }
    
    public VerticalLayoutExt with(Component... components) {
        addComponents(components);
        return this;
    }


    public VerticalLayoutExt withSpacing(boolean spacing) {
        setSpacing(spacing);
        return this;
    }

    public VerticalLayoutExt withMargin(boolean marging) {
        if (!marging) {
        	setMargin(marging);
        }
        else {
        	addStyleName(CssVaadin.getMargin4());
        }
        return this;
    }

    public VerticalLayoutExt withWidth(String width) {
        setWidth(width);
        return this;
    }

    public VerticalLayoutExt withFullWidth() {
        setWidth("100%");
        return this;
    }

    public VerticalLayoutExt withHeight(String height) {
        setHeight(height);
        return this;
    }

    public VerticalLayoutExt withFullHeight() {
        setHeight("100%");
        return this;
    }

}
