package com.javalego.ui.vaadin.component.util;

import java.io.Serializable;

import com.javalego.ui.vaadin.component.TreeExt;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.Resource;

/**
 * Utilidades de generación de componentes de tipo Tree Vaadin para simplificar la construcción de interfaces visuales y el código.
 * @author ROBERTO RANZ
 */
public class TreeUtil implements Serializable  {
	
	private static final long serialVersionUID = 3917244494141993017L;

	public static Integer HOME_ID = 0;
	
	private TreeUtil() {}

	public static TreeExt getTree() {

		TreeExt tree = new TreeExt();
		tree.setSizeUndefined();
		//tree.setContainerDataSource(getContainer());

		//tree.select(id);
		
		return tree;
	}	
	
	/**
	 * Configurar contenedor de datos jerárquicos
	 * @return 
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private static HierarchicalContainer getContainer() {
		
		HierarchicalContainer hierarchialContainer = new HierarchicalContainer();

		// Lista de propiedades del objeto que queremos incluir en el container
		// para mostrar y localizar los objetos cuando queremos editar un objeto
		// del árbol.
		hierarchialContainer.addContainerProperty("icon", Resource.class, null);
		hierarchialContainer.addContainerProperty("caption", String.class, null);
		hierarchialContainer.addContainerProperty("id", Integer.class, null);

		// Nodo de inicio
		//hierarchialContainer.getContainerProperty(HOME_ID, "icon").setValue(GANA_VAADIN.getIcon(ICollectionImages.FOLDER));
		hierarchialContainer.getContainerProperty(HOME_ID, "caption").setValue("Home");
		
		return hierarchialContainer;
	}	
}
