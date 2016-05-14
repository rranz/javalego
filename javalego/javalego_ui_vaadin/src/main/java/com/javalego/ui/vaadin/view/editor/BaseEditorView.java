package com.javalego.ui.vaadin.view.editor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.javalego.exception.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.editor.IEditorView;
import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorPresenter;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.LayoutUtils;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.view.editor.layout.LayoutEditorView;
import com.vaadin.server.Page;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Edición de los datos completos del modelo de forma secuencial.
 * 
 * @author ROBERTO RANZ
 * 
 */
public abstract class BaseEditorView extends CustomComponent implements IEditorView {

	private static final long serialVersionUID = -5538453577528127924L;

	/**
	 * Administrador del bindado de campos y propiedades de beans y datos
	 * primitivos. Servirá para obtener información o modificar datos o
	 * componentes.
	 */
	protected DataEditorManager dataEditorManager;

	/**
	 * Habilitar opciones de aceptar y cancelar.
	 */
	private boolean toolbar = true;

	/**
	 * Establecer full width para el layout y sus componentes
	 */
	private boolean fullWidth;

	/**
	 * Establecer light style para el layout
	 */
	private boolean light;

	/**
	 * Observer del proceso de creación del editor.
	 */
	private IEditorViewObserver observer;

	/**
	 * Primer campo localizado para posicionar el foco en la edición.
	 */
	private Field<?> firstField;

	public BaseEditorView() {
	}

	/**
	 * Listener del editor
	 * 
	 * @return
	 */
	public abstract IEditorViewListener getListener();

	/**
	 * Establecer 100% width el componente
	 * 
	 * @return
	 */
	public BaseEditorView fullWidth() {
		fullWidth = true;
		return this;
	}

	/**
	 * Establecer estilo Light para mostrar AbstractField sin contornos de
	 * edición.
	 * 
	 * @return
	 */
	public BaseEditorView light() {
		light = true;
		return this;
	}

	/**
	 * Incluir o no incluir la barra de herramientas
	 * 
	 * @param toolbar
	 * @return
	 */
	public BaseEditorView setToolbar(boolean toolbar) {
		this.toolbar = toolbar;
		return this;
	}

	@Override
	public void load() throws LocalizedException {

		if (getListener() == null) {
			throw new LocalizedException("NOT LISTENER EDITOR IN EDITORVIEW.");
		}

		if (dataEditorManager == null) {
			dataEditorManager = new DataEditorManager(getListener(), observer);
		}

		CssLayout main = new CssLayout();

		Layout layout = null;

		ILayoutEditorModel le = getListener().getLayoutEditorModel();

		List<Component> components = null;

		// Incluir layout de distribución de campos
		if (le != null) {

			LayoutEditorView lv = new LayoutEditorView();

			new LayoutEditorPresenter(le, lv);

			layout = lv;

			// Utilizar el dataEditorManager del editor para no generar otra
			// instancia.
			lv.setDataEditorManager(dataEditorManager);

			lv.loadFields(getListener());

			components = lv.getComponents();
		}
		else {
			// Generar un layout básico basado en FormLayout
			layout = new FormLayout();

			// Generar los componentes visuales de cada campo del modelo
			components = dataEditorManager.getComponents(getListener().getData());

			// Incluirlos en el layout
			fillComponents(components, layout);
		}

		// Adaptar componentes al estilo de visualización definido. Actualmente,
		// light Valo.
		if (components != null) {
			adapterComponents(components);
		}

		layout.addStyleName(CssVaadin.getScrollable());

		// Añadir sección de detalle del bean editado (sólo en edición).
		if (getListener().hasRemove()) {

			Collection<IBeansEditorModel<?>> detail = getListener().getDetail();

			if (detail != null && detail.size() > 0) {
				main.addComponent(getBeanDetail(detail));
			}
		}

		// Añadir sección de edición de campos
		main.addComponent(layout);

		// Añadir sección de opciones de grabar, cancelar y eliminar.
		if (toolbar) {
			main.addComponent(getToolBar());
		}
		else if (light) {
			main.addComponent(LayoutUtils.getCssLayout(true, null, false));
		}

		if (fullWidth) {
			if (layout != null) {
				layout.setWidth("100%");
			}
			main.setWidth("100%");
			setWidth("100%");
		}

		if (light && layout != null) {
			layout.addStyleName(CssVaadin.getLight());
		}

		setCompositionRoot(main);

		// Seleccionar el primer campo y marcar la selección de su contenido.
		if (firstField != null && !firstField.isReadOnly()) {

			firstField.focus();

			if (firstField instanceof AbstractTextField) {
				((AbstractTextField) firstField).selectAll();
			}
		}
	}

	/**
	 * Crear el componente de los accesos al detalle del bean editado.
	 * 
	 * @param detail
	 * @return
	 */
	private Component getBeanDetail(Collection<IBeansEditorModel<?>> detail) {

		CssLayout layout = new CssLayout();

		layout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		layout.addStyleName(CssVaadin.getMargin10());

		for (final IBeansEditorModel<?> item : detail) {

			ButtonExt b = new ButtonExt(item.getTitle(), item.getDescription(), item.getIcon());
			b.large();
			b.addClickListener(new ClickListener() {
				private static final long serialVersionUID = -2388033254634200894L;

				@Override
				public void buttonClick(ClickEvent event) {
					getListener().loadDetailBeanEditor(item);
				}
			});
			layout.addComponent(b);
		}

		return layout;
	}

	/**
	 * Opciones
	 * 
	 * @return
	 */
	private Component getToolBar() {

		return new ToolBarEditorView(getListener());
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
				layout.addComponent(component);
			}
		}
	}

	/**
	 * Adaptador de los campos incluidos en el layout según el estio definido y
	 * para obtener el primer campo editable para pasarle el foco inicial en la
	 * edición.
	 * 
	 * @param components
	 * @param layout
	 */
	private void adapterComponents(List<Component> components) {

		// Añadir todos los componentes generados.
		if (components != null) {

			for (Component component : components) {

				// Adaptar el componente para estilo Light
				if (light) {
					if (component instanceof AbstractTextField) {
						((AbstractTextField) component).setColumns(0);
					}
					component.setWidth("100%");
				}

				if (firstField == null && component instanceof Field && !component.isReadOnly()) {
					firstField = (Field<?>) component;
				}

			}
		}
	}

	// Reglas de edición.

	@Override
	public void setReadOnly(String fieldName, boolean readOnly) {
		dataEditorManager.setReadOnly(fieldName, readOnly);
	}

	@Override
	public void setEnabled(String fieldName, boolean enabled) {
		dataEditorManager.setEnabled(fieldName, enabled);
	}

	@Override
	public void setVisible(String fieldName, boolean visible) {
		dataEditorManager.setVisible(fieldName, visible);
	}

	@Override
	public void setDescription(String fieldName, String description) {
		dataEditorManager.setDescription(fieldName, description);
	}

	@Override
	public void setRequired(String fieldName, boolean required) {
		dataEditorManager.setRequired(fieldName, required);
	}

	@Override
	public void setRequiredError(String fieldName, String requiredError) {
		dataEditorManager.setRequiredError(fieldName, requiredError);
	}

	@Override
	public void setValue(String fieldName, Object value) {
		dataEditorManager.setValue(fieldName, value);
	}

	@Override
	public void setCaption(String fieldName, String caption) {
		dataEditorManager.setCaption(fieldName, caption);
	}

	@Override
	public Object getValue(String fieldName) {
		return dataEditorManager.getValue(fieldName);
	}

	@Override
	public boolean isRequired(String fieldName) {
		return dataEditorManager.isRequired(fieldName);
	}

	@Override
	public boolean isVisible(String fieldName) {
		return dataEditorManager.isVisible(fieldName);
	}

	@Override
	public boolean isEnabled(String fieldName) {
		return dataEditorManager.isEnabled(fieldName);
	}

	@Override
	public boolean isReadOnly(String fieldName) {
		return dataEditorManager.isReadOnly(fieldName);
	}

	@Override
	public String getCaption(String fieldName) {
		return dataEditorManager.getCaption(fieldName);
	}

	@Override
	public String getRequiredError(String fieldName) {
		return dataEditorManager.getRequiredError(fieldName);
	}

	@Override
	public String getDescription(String fieldName) {
		return dataEditorManager.getDescription(fieldName);
	}

	@Override
	public List<LocalizedException> commit() throws LocalizedException {
		return dataEditorManager.commit();
	}

	@Override
	public List<LocalizedException> validate() {
		return dataEditorManager.validate();
	}

	@Override
	public void discard() {
		try {
			dataEditorManager.discard();
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}

	@Override
	public void showErrors(List<LocalizedException> exceptions) {

		String text = "<br>" + UIContext.getText(CommonErrors.ERRORS, exceptions.size());

		int index = 1;
		text += "<br>";
		for (LocalizedException ex : exceptions) {
			text += "<br>" + (index++) + ". " + ex.getMessage();
		}

		new Notification(UIContext.getText(LocaleEditor.WARN), text, Type.WARNING_MESSAGE, true).show(Page.getCurrent());
	}

	@Override
	public Collection<?> getValues() {
		return dataEditorManager.getValues();
	}

	@Override
	public Map<String, ?> getFieldValues() {
		return dataEditorManager.getFieldValues();
	}

	@Override
	public void discard(String fieldName) {
		dataEditorManager.discard(fieldName);
	}

	@Override
	public void setEnumValues(String fieldName, List<?> values) {
		dataEditorManager.setEnumValues(fieldName, values);
	}

	/**
	 * Observer del proceso de creación del editor.
	 * 
	 * @return
	 */
	public IEditorViewObserver getObserver() {
		return observer;
	}

	/**
	 * Observer del proceso de creación del editor.
	 * 
	 * @param observer
	 */
	public void setObserver(IEditorViewObserver observer) {
		this.observer = observer;
	}

}
