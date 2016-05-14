package com.javalego.ui.patterns;

import com.javalego.exception.LocalizedException;

/**
 * Presenter pattern MVP.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IPresenter {
	
	void load() throws LocalizedException;
	
	IView getView();
	
}
