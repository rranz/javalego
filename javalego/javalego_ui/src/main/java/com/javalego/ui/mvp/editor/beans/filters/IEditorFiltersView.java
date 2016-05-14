package com.javalego.ui.mvp.editor.beans.filters;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.patterns.IView;


public interface IEditorFiltersView extends IView {
	
	public interface IEditorFiltersViewListener {

		Collection<IFilterService> getFilters();

		void execute(String statement, String naturalStatement) throws LocalizedException;

		void removeCurrentFilter() throws LocalizedException;
	}
	

	void setListener(IEditorFiltersViewListener listener);
	
}
