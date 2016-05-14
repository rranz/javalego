package com.javalego.ui.mvp.editor.beans.filters;

import java.util.Collection;

import com.javalego.ui.filter.IFilterService;

public interface IEditorFiltersModel {

	Collection<IFilterService> getFilters();
	
	Class<?> getBeanClass();

}
