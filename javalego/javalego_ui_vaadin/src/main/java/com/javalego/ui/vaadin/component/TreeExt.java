package com.javalego.ui.vaadin.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.util.StringUtils;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Tree;

/**
 * Extensión del componente Tree para añadir funcionales necesarias para el CORE GANA.
 * 
 * @author ROBERTO RANZ
 */
public class TreeExt extends Tree {

	private static final long serialVersionUID = 1L;

	//private static final Logger logger = Logger.getLogger(TreeExt.class);

	public TreeExt() {

		initialize();
	}

	public TreeExt(HierarchicalContainer container) {
		super(null, container);
		initialize();
	}

	private void initialize() {

		setImmediate(true);

		setNullSelectionAllowed(false);

		// Show all leaf nodes as disabled
		setItemStyleGenerator(new ItemStyleGenerator() {
			private static final long serialVersionUID = -1621972918846333179L;

			@Override
			public String getStyle(Tree source, Object itemId) {
				if (!hasChildren(itemId))
					return "disabled";
				else
					return null;
			}
		});
		
	}

	/**
	 * Expandir todos los nodos
	 */
	public void expandAll() {

		for (Object itemId : getItemIds())
			expandItem(itemId);
	}

	/**
	 * Colapsar todos los nodos
	 */
	public void collapseAll() {

		for (Object itemId : getItemIds())
			collapseItem(itemId);
	}

	/**
	 * Obtener las lista de ID de los nodos hijos del nodo seleccionado.
	 * @return
	 */
	public List<?> getSelectedChildrenKeys(boolean withId) {

		Object id = getValue();

		List<?> ids = new ArrayList<Object>();

		if (withId) {
			fillChildren(ids, id);
		}
		else {
			fillChildren2(ids, id);
		}

		return ids;
	}

	/**
	 * Obtener las lista de ID de los nodos hijos del nodo seleccionado.
	 * @return
	 */
	public List<?> getSelectedChildrenKeys() {
		return getSelectedChildrenKeys(true);
	}

	/**
	 * Incluir los hijos de un Id
	 * @param ids
	 * @param id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fillChildren(List ids, Object id) {

		Collection<?> list = getChildren(id);

		ids.add(getItem(id).getItemProperty("id").getValue());

		if (list != null) {
			for (Object o : list) {
				ids.add(getItem(o).getItemProperty("id").getValue());

				if (hasChildren(o))
					fillChildren(ids, (Integer) o);
			}
		}
	}

	/**
	 * Incluir los hijos de un Id
	 * @param ids
	 * @param id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fillChildren2(List ids, Object id) {

		Collection<?> list = getChildren(id);

		ids.add(id);

		if (list != null) {
			for (Object o : list) {
				ids.add(o);

				if (hasChildren(o))
					fillChildren2(ids, o);
			}
		}
	}

	
	/**
	 * Obtiene el primer item seleccionado (en caso de multiselect = true) se obtendría el item = 0.
	 */
	public Object getFirstSelectedValue() {
		
		if (getValue() != null) {
			return isMultiSelect() ? ((Collection<?>) getValue()).iterator().next() : getValue();
		}
		else
			return null;
	}

	/**
	 * Removes the Item identified by given itemId and all its children from the given Container.
	 * 
	 * @param container
	 *          the container where the item is to be removed
	 * @param itemId
	 *          the identifier of the Item to be removed
	 * @return true if the operation succeeded
	 */
	public boolean removeItemRecursively(Object itemId) {

		boolean success = true;

		Collection<?> children2 = getChildren(itemId);

		if (children2 != null) {

			Object[] array = children2.toArray();

			for (int i = 0; i < array.length; i++) {

				boolean removeItemRecursively = removeItemRecursively(array[i]);

				if (!removeItemRecursively) {
					success = false;
				}
			}
		}
		// remove the root of subtree if children where succesfully removed
		if (success) {
			success = removeItem(itemId);
		}
		return success;

	}

//	/**
//	 * Añadir Drag an Drop simple de movimiento de nodos al componente.
//	 * 
//	 * @param dragToParent
//	 *          Permite arrastrar nodos hijos a otros nodos padre. Si es igual a false, sólo se moverán los nodos que pertenezcan al mismo padre.
//	 */
//	public void addHandlerDragAndDrop(final boolean dragToParent) {
//		addHandlerDragAndDrop(dragToParent, false, null);
//	}
//
//	/**
//	 * Añadir Drag an Drop simple de movimiento de nodos al componente.
//	 * 
//	 * @param dragToParent
//	 *          Permite arrastrar nodos hijos a otros nodos padre. Si es igual a false, sólo se moverán los nodos que pertenezcan al mismo padre.
//	 */
//	public void addHandlerDragAndDrop(final boolean dragToParent, final boolean confirm, final ConfirmTreeMoveEventListener confirmEvent) {
//
//		if (!(getContainerDataSource() instanceof HierarchicalContainer)) {
//			logger.warn("No puede incluir DragAndDrop a un container Tree que no sea HierarchicalContainer. El contenedor es '" + getContainerDataSource().getClass().getCanonicalName() + "'.");
//			return;
//		}
//
//		// Set the tree in drag source mode
//		setDragMode(TreeDragMode.NODE);
//
//		// Allow the tree to receive drag drops and handle them
//		setDropHandler(new DropHandler() {
//
//			@Override
//			public AcceptCriterion getAcceptCriterion() {
//				return AcceptAll.get();
//			}
//
//			@Override
//			public void drop(DragAndDropEvent event) {
//
//				// Wrapper for the object that is dragged
//				Transferable t = event.getTransferable();
//
//				// Make sure the drag source is the same tree
//				if (t.getSourceComponent() != TreeExt.this)
//					return;
//
//				final TreeTargetDetails target = (TreeTargetDetails) event.getTargetDetails();
//
//				// Get ids of the dragged item and the target item
//				final Object sourceItemId = t.getData("itemId");
//				final Object targetItemId = target.getItemIdOver();
//
//				if (confirm) {
//
//					MessagesUtil.getConfirmDialog("¿Desea mover el nodo '" + ((TreeExt) t.getSourceComponent()).getItemCaption(sourceItemId) + "' al nodo '" + ((TreeExt) t.getSourceComponent()).getItemCaption(targetItemId) + "' ?", new EventListener() {
//						@Override
//						public boolean buttonClicked(ButtonType buttonType) throws Exception {
//							if (buttonType == ButtonType.YES) {
//								if (confirmEvent != null) {
//									if (confirmEvent.confirmClicked(sourceItemId, targetItemId)) {
//										moveNode(dragToParent, target, sourceItemId, targetItemId);
//									}
//								}
//								else
//									moveNode(dragToParent, target, sourceItemId, targetItemId);
//							}
//							return true;
//						}
//					});						
//				}
//				else
//					moveNode(dragToParent, target, sourceItemId, targetItemId);
//			}
//
//		});
//	}

	/**
	 * Obtener las claves hijas de un nodo
	 * 
	 * @param item
	 * @return
	 */
	public List<Integer> getChildrenKeys(Object item) {

		Integer id = StringUtils.toInt(item);

		List<Integer> ids = new ArrayList<Integer>();

		fillChildren(ids, id);

		return ids;
	}

	/**
	 * Realizar el movimiento del nodo seleccionado al nodo destino.
	 * 
	 * @param dragToParent
	 * @param target
	 * @param sourceItemId
	 * @param targetItemId
	 */
//	private void moveNode(final boolean dragToParent, TreeTargetDetails target, Object sourceItemId, Object targetItemId) {
//
//		// Si los dos nodos son de distinto padre, cancelamos.
//		if (!dragToParent && getParent(sourceItemId) != getParent(targetItemId))
//			return;
//
//		// On which side of the target the item was dropped
//		VerticalDropLocation location = target.getDropLocation();
//
//		if (getContainerDataSource() instanceof HierarchicalContainer) {
//
//			HierarchicalContainer container = (HierarchicalContainer) getContainerDataSource();
//
//			// Drop right on an item -> make it a child
//			if (location == VerticalDropLocation.MIDDLE) {
//				if (dragToParent)
//					setParent(sourceItemId, targetItemId);
//			}
//			// Drop at the top of a subtree -> make it previous
//			else if (location == VerticalDropLocation.TOP) {
//				if (dragToParent) {
//					Object parentId = container.getParent(targetItemId);
//					container.setParent(sourceItemId, parentId);
//				}
//				container.moveAfterSibling(sourceItemId, targetItemId);
//				container.moveAfterSibling(targetItemId, sourceItemId);
//			}
//
//			// Drop below another item -> make it next
//			else if (location == VerticalDropLocation.BOTTOM) {
//				if (dragToParent) {
//					Object parentId = container.getParent(targetItemId);
//					container.setParent(sourceItemId, parentId);
//				}
//				container.moveAfterSibling(sourceItemId, targetItemId);
//			}
//
//		}
//		select(sourceItemId);
//	}

	/**
	 * Expandir los nodos del árbol hasta un nivel de profundidad.
	 * 
	 * @param level
	 */
	public void expandLevels(int level) {

		for (Object item : getItemIds()) {
			if (levelNode(item) < level) {
				expandItem(item);
			}
		}

	}

	private int levelNode(Object object) {
		int level = 0;
		while (getParent(object) != null) {
			++level;
			object = getParent(object);
		}
		return level;
	}

}
