package com.javalego.ui.vaadin.component;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.Reindeer;

/**
 * Extensión de TabSheet
 * @author roberto
 *
 */
public class TabSheetExt extends TabSheet {

	private static final long serialVersionUID = 1L;

	public TabSheetExt() {
	}

	/**
	 * Constructor
	 * @param minimal Style minimal tabs
	 */
	public TabSheetExt(boolean minimal) {
		if (minimal) {
			setMinimal();
		}
	}

	public void setSmall() {
		setStyleName(Reindeer.TABSHEET_SMALL);
	}

	public void setBorderless() {
		setStyleName(Reindeer.TABSHEET_BORDERLESS);
	}

	public void setMinimal() {
		setStyleName(Reindeer.TABSHEET_MINIMAL);
	}
}
