package com.javalego.ui.vaadin.component.layout;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

public class GridLayoutExt extends GridLayout {

	private static final long serialVersionUID = 2796221628369230302L;

	public GridLayoutExt() {
        super.setSpacing(true);
        super.setMargin(true);
    }
    
    public GridLayoutExt(Component... components) {
        this();
        addComponents(components);
    }
    
    public GridLayoutExt with(Component... components) {
        addComponents(components);
        return this;
    }
    
    public GridLayoutExt withSpacing(boolean spacing) {
        setSpacing(spacing);
        return this;
    }

    public GridLayoutExt withMargin(boolean marging) {
        setMargin(marging);
        return this;
    }
    
    public GridLayoutExt withWidth(String width) {
        setWidth(width);
        return this;
    }

    public GridLayoutExt withFullWidth() {
        setWidth("100%");
        return this;
    }
    
    public GridLayoutExt withHeight(String height) {
        setHeight(height);
        return this;
    }
    
    public GridLayoutExt withFullHeight() {
        setHeight("100%");
        return this;
    }

}
