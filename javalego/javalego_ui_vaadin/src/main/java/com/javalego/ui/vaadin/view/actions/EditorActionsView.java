package com.javalego.ui.vaadin.view.actions;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.UIContext;
import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.javalego.ui.mvp.editor.actions.IEditorActionsView;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * Genera el componente UI de la lista de acciones definidas en el modelo.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class EditorActionsView extends HorizontalLayout implements IEditorActionsView {

	private static final long serialVersionUID = 8670818917721304659L;

	private IEditorActionsListener listener;
	
	/**
	 * Listener del editor para obtener información o ejecutar procesos.
	 */
	private IEditorViewListener editorListener;

	/**
	 * Campo del editor al que está asociada la acción.
	 */
	private IItemEditor elementEditor;

	public EditorActionsView(IEditorViewListener editorListener, IItemEditor elementEditor) {
		this.editorListener = editorListener;
		this.elementEditor = elementEditor;
	}

	public EditorActionsView() {
	}

	@Override
	public void load() throws LocalizedException {

		if (getComponentCount() > 0) {
			return;
		}

		setSpacing(true);

		for (IActionEditor action : listener.getActions()) {
			addComponent(getComponent(action));
		}
	}

	/**
	 * Obtener el componente UI
	 * 
	 * @param action
	 * @return
	 */
	private Component getComponent(final IActionEditor action) {

		// Crear el componente UI
		CssButton b = new CssButton(UIContext.getText(action.getTitle()), UIContext.getText(action.getDescription()), action.getColor(), action.getIcon(), false, action.getIcon() == null);
		b.setSmall(true);
		//b.initComponent(UIContext.getText(action.getTitle()), UIContext.getText(action.getDescription()), action.getColor(), action.getIcon(), null, false, action.getIcon() == null, null, null);
		b.addStyleName(CssVaadin.getRounded());

		// Ejecutar acción de editor.
		addLayoutClickListener(new LayoutClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2948946784600383850L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				try {
					action.execute(editorListener, elementEditor);
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});

		return b;
	}

	@Override
	public void setListener(IEditorActionsListener listener) {
		this.listener = listener;
	}

}
