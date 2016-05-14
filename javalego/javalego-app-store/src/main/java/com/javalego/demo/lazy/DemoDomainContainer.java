package com.javalego.demo.lazy;

import com.javalego.ui.vaadin.view.editor.beans.list.lazy.LazyPagedContainer;

public class DemoDomainContainer extends LazyPagedContainer<DemoDomain> {

	private static final long serialVersionUID = 3018567394091518443L;

	public DemoDomainContainer() {
		super(DemoDomain.class);
	}

}
