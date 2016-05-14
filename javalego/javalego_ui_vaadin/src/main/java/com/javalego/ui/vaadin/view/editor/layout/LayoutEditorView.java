package com.javalego.ui.vaadin.view.editor.layout;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.UIContext;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorView;
import com.javalego.ui.vaadin.component.util.Html;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.view.editor.DataEditorManager;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;

/**
 * Implementación del layout del editor basado en una anidación de layouts.
 * 
 * @author ROBERTO RANZ
 *
 */
public class LayoutEditorView extends FormLayout implements ILayoutEditorView {

	private static final long serialVersionUID = 5231904799949868443L;

	/**
	 * Administrador del bindado de campos y propiedades de beans y datos
	 * primitivos. Servirá para obtener información o modificar datos o
	 * componentes.
	 */
	protected DataEditorManager dataEditorManager = null;

	/**
	 * Lista de components generados para el layout
	 */
	private List<Component> components;

	/**
	 * Alineación
	 * 
	 * @author ROBERTO RANZ
	 *
	 */
	public static enum Align {
		HORIZONTAL, VERTICAL;
	}

	/**
	 * Alineación
	 */
	private Align align = Align.VERTICAL;

	private LayoutEditorViewListener listener;

	@Override
	public void load() throws LocalizedException {
	}

	@Override
	public void setListener(LayoutEditorViewListener listener) {
		this.listener = listener;
	}

	/**
	 * Alineación. Por defecto vertical.
	 * 
	 * @return
	 */
	public Align getAlign() {
		return align;
	}

	/**
	 * Alineación. Por defecto vertical.
	 * 
	 * @param align
	 */
	public void setAlign(Align align) {
		this.align = align;
	}

	@Override
	public List<IItemEditor> getDataEditor() {
		return listener.getDataEditor();
	}

	@Override
	public void loadFields(IEditorViewListener editorListener) throws LocalizedException {

		if (dataEditorManager == null) {
			dataEditorManager = new DataEditorManager(editorListener);
		}

		components = dataEditorManager.getComponents(listener.getDataEditor());

		// Layout principal
		fillComponents(components, this);

		addStyleName(CssVaadin.getScrollable());

		// Hijos: Cargar recursivamente sus layouts hijos.
		loadChildren(listener.getChildren(), editorListener);
	}

	/**
	 * Lista de componentes generados en el layout a partir del modelo de
	 * campos.
	 * 
	 * @return
	 */
	public List<Component> getComponents() {
		return components;
	}

	/**
	 * Cargar los layout hijos recursivamente e incluir sus componentes UI en
	 * este layout principal.
	 * 
	 * @param children
	 * @param editorListener
	 * @throws LocalizedException
	 */
	private void loadChildren(List<ILayoutEditorModel> children, IEditorViewListener editorListener) throws LocalizedException {

		// Recorrer todos los layouts hijos.
		if (children != null && children.size() > 0) {

			for (ILayoutEditorModel lm : children) {

				addComponent(new Html.H3(Html.getSpace(2) + UIContext.getText(lm.getTitle())).colored());

				List<Component> components = dataEditorManager.getComponents(lm.getData());

				if (components != null) {
					this.components.addAll(components);
				}

				// Incluir los campos definidos en este layout hijo.
				fillComponents(components, this);
			}
		}
	}

	/**
	 * Cargar componentes UI en el layout.
	 * 
	 * @param components
	 * @param layout
	 */
	private void fillComponents(List<Component> components, Layout layout) {

		// Añadir todos los componentes generados.
		if (components != null) {
			for (Component component : components) {

				if (component instanceof CheckBox) {
					CssLayout wrap = new CssLayout() {
						private static final long serialVersionUID = -8377823445460639581L;

						@Override
						protected String getCss(Component c) {
							return "margin-top: 6px;";
						}
					};
					wrap.setCaption(component.getCaption());
					component.setCaption(null);
					wrap.addComponent(component);
					layout.addComponent(wrap);
				}
				else {
					layout.addComponent(component);
				}
			}
		}
	}

	public DataEditorManager getDataEditorManager() {
		return dataEditorManager;
	}

	public void setDataEditorManager(DataEditorManager dataEditorManager) {
		this.dataEditorManager = dataEditorManager;
	}

}
