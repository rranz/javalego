package com.javalego.ui.vaadin.view.editor.beans.table;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.UIContext;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.DateFieldModel;
import com.javalego.ui.field.impl.NumberFieldModel;
import com.javalego.ui.mvp.beans.view.IBeansView;
import com.javalego.ui.mvp.beans.view.paged.IPagedBeansModel;
import com.javalego.ui.vaadin.component.table.TableExt;
import com.javalego.ui.vaadin.component.util.BeanItemContainerUtil;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Vista abstract de beans basada en el componente Table.
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public abstract class AbstractTableBeansView<T> extends VerticalLayout implements IBeansView<T> {

	private static final long serialVersionUID = -4867980632673494834L;

	protected Collection<T> beans;

	protected TableExt<T> table;

	@SuppressWarnings("rawtypes")
	protected BeanItemContainer container;

	/**
	 * Lista de modelo de campos que queremos mostrar en la vista.
	 * 
	 * @return
	 */
	protected Collection<FieldModel> fieldModels;

	/**
	 * Lista de campos a visualizar en la tabla.
	 */
	private String[] columnNames;

	/**
	 * Constructor
	 * 
	 * @param fieldModels
	 *            Lista de campos basada en IFieldModel
	 * @param columnNames Lista de columnas de la tabla.
	 */
	public AbstractTableBeansView(Collection<FieldModel> fieldModels, String[] columnNames) {
		this.fieldModels = fieldModels;
		this.columnNames = columnNames;
	}

	/**
	 * Cargar grid de datos.
	 * 
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	protected Component getGrid() throws LocalizedException {

		if (table != null) {
			return table;
		}

		// Crear la tabla
		table = getTable();

		table.setWidth("100%");

		// Crear el container de tipo bean
		container = getBeanItemContainer();

		table.setContainerDataSource(container);

		// Incluir las propiedades nested (Ej. empresa.nombre) antes de establecer setVisibleColumns().
		BeanItemContainerUtil.addNestedProperties(container, columnNames);
		
		// Establecer las columnas visibles obtenidas del listener que es donde se filtran y seleccionan las columnas visibles.
		table.setVisibleColumns((Object[])columnNames);

		// Crear los objetos de las propiedades nested.
		BeanItemContainerUtil.createNestedProperties(beans, columnNames);

		if (beans != null) {
			container.addAll(beans);
		}

		// Configurar las columnas en base al modelo.
		configColumns(fieldModels);

		// Añadir eventos de selección y edición del bean.
		addListeners();

		return table;
	}

	/**
	 * Añadir los eventos de selección de bean con doble click o click.
	 */
	private void addListeners() {

		// Editor objeto del grid seleccionado.
		table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			private static final long serialVersionUID = -2697174150852993510L;

			@Override
			public void itemClick(ItemClickEvent event) {
				T bean = getSelectedBean();
				if (bean != null) {
					try {
						if (event.isDoubleClick()) {
							editBean(bean);
						}
					} catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}
			}
		});
	}

	protected BeanItemContainer<T> getBeanItemContainer() {

		return new BeanItemContainer<T>(getBeanClass());
	}

	/**
	 * Fijar el título de las columnas con el título de la propiedad.
	 * 
	 * @param model
	 */
	private void configColumns(Collection<FieldModel> model) {

		for (FieldModel property : model) {

			// Fijar el título de la columan
			table.setColumnHeader(property.getName(), UIContext.getTitle(property));

			// Adaptar el componente de visualización de la propiedad a su tipo
			// correspondiente. Sólo para casos especiales como estados,
			// workflows, enumerados, ...
			// generateColumns(property);

			// Alineación de la columna en base al tipo de dato
			if (property instanceof NumberFieldModel) {
				table.setColumnAlignment(property.getName(), Align.RIGHT);
			} else if (property instanceof DateFieldModel) {
				table.setColumnAlignment(property.getName(), Align.CENTER);
			}

			// Establecer el ancho de la columna en base a la propiedad
			// displayWidth o size definida en la propiedad para la columna del
			// browser.
//			if (!property.isAutoSizeColumn()) {
//				table.setColumnWidth(property.getName(), property.getColumnLength() * 8);
//			}
//			// Ajustar tamaño de las columnas en base a la definición de la
//			// propiedad.
//			else if (model.size() < 5) {
//				table.setColumnWidth(property.getName(), property.getColumnLength() * 8);
//			} else {
//				table.setColumnWidth(property.getName(), -1);
//			}
		}
	}

	/**
	 * Obtener el componente Table
	 * 
	 * @return
	 */
	public TableExt<T> getTable() {

		if (table != null) {
			return table;
		}

		table = new TableExt<T>();

		table.addStyleName(ValoTheme.TABLE_SMALL);

		table.setPageLength(IPagedBeansModel.SIZE_PAGE_BEANS);

		return table;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void reloadBeans(Collection<T> beans) throws LocalizedException {

		if (beans != null) {

			container.removeAllItems();
			container.addAll(beans);
		}
	}

	/**
	 * Obtener el bean actualmente seleccionado.
	 * 
	 * @return
	 */
	public T getValue() {
		return (T) table.getValue();
	}

	@Override
	public void removeSelectedBean() {
		table.setValue(null);
	}

	@Override
	public T getSelectedBean() {
		return (T) table.getValue();
	}

	@Override
	public void setSelectedBeans(Collection<T> beans) {
		table.setValue(beans);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> getSelectedBeans() {
		return (Collection<T>) table.getValue();
	}

	@Override
	public void removeBean(T bean) {
		table.removeItem(bean);
	}

	@Override
	public void update(T bean) {

		if (table.getValue() == bean) {
			table.refreshRowCache();
		} else {
			T actual = (T) table.getValue();
			// Falta cambiar objeto al editar ya que hay que realiar un
			// reloadObject para obtener la versión actual del objeto en
			// base de datos.
			table.addItemAfter(actual, bean);
			table.removeItem(actual);
			table.select(bean);
		}
	}

	@Override
	public void insert(T bean) {

		if (bean != null) {
			table.addItem(bean);
			table.select(bean);
		}
	}

	/**
	 * Editar bean
	 * 
	 * @param bean
	 * @throws LocalizedException
	 */
	public abstract void editBean(T bean) throws LocalizedException;

	/**
	 * Aplicar un filtro de selección
	 * 
	 * @param statement
	 */
	@Override
	public abstract void applyFilter(String statement) throws LocalizedException;

	/**
	 * Eliminar el filtro actual.
	 */
	@Override
	public abstract void removeCurrentFilter() throws LocalizedException;

	/**
	 * Clase del bean
	 * 
	 * @return
	 */
	public abstract Class<? super T> getBeanClass();

	@Override
	public Collection<T> getBeans() throws LocalizedException {
		return beans;
	}
	
}
