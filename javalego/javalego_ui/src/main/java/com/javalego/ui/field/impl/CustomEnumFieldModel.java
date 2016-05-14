package com.javalego.ui.field.impl;

import com.javalego.util.ReflectionUtils;

/**
 * Propiedad de tipo enumerado cuyos valores los obtene de una interface derivada de IEnumPropertyItems que debe implementar el método getItems() (valores y etiquetas).
 * @author ROBERTO RANZ
 */
public class CustomEnumFieldModel extends EnumFieldModel {

	private static final long serialVersionUID = 9119201136255982086L;

	/**
	 * Nombre de la clase que implementa IEnumPropertyItems y que se instanciará sólo una vez
	 * y sustituirá al método de getItems(). de EnumProperty.
	 */
	private String className;
	
	private IEnumFieldModelItems classItems;
	
	private boolean loaded;
	
	/**
	 * Carga única. Si define este valor a false, cada vez que se utilice en un comboBox de selección, esta información se cargará de nuevo.
	 */
	private boolean singleLoad = true;
	
	public CustomEnumFieldModel() {
		
	}

	/**
	 * Cargar los elementos mediante la clase definida.
	 * @throws Exception
	 */
	public void loadItems() throws Exception {
		
		if (loaded == true)
			return;
		else
			loadItems(true);
	}
	
	
	/**
	 * Cargar los elementos mediante la clase definida.
	 * @throws Exception
	 */
	public void loadItems(boolean load) throws Exception {
		
		if (!load && loaded)
			return;
		
		if (classItems == null) {
			try {
				classItems = (IEnumFieldModelItems)ReflectionUtils.createObject(className);
			}
			catch(Exception e) {
				throw new Exception(e);
			}
		}
		
		items = classItems.getItems();
		loaded = true;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public boolean isSingleLoad() {
		return singleLoad;
	}

	public void setSingleLoad(boolean singleLoad) {
		this.singleLoad = singleLoad;
	}

}
