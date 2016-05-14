package com.javalego.ui.field.impl;

import java.util.ArrayList;

/**
 * Interface utilizada en la clase CustomEnumProperty para recoger los elementos del enumerado
 * mediante una clase que implemente esta interface dentro de su método getItems().
 * @author ROBERTO RANZ
 */
public interface IEnumFieldModelItems {

	public ArrayList<EnumItem> getItems();
	
}
