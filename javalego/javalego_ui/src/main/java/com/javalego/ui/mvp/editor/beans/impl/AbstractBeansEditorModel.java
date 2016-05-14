package com.javalego.ui.mvp.editor.beans.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.javalego.data.DataContext;
import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.actions.IActionBeanEditor;
import com.javalego.ui.actions.IActionBeansEditor;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;
import com.javalego.ui.mvp.editor.services.IEditorServices;

/**
 * Editor de beans abstract para simplificar la implementación de la interface
 * IBeansEditorModel.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public abstract class AbstractBeansEditorModel<T> implements IBeansEditorModel<T> {

	private static final Logger logger = Logger.getLogger(AbstractBeansEditorModel.class);

	private static final long serialVersionUID = -5406695532098324371L;

	private Collection<IBeansEditorModel<?>> beanDetail;
	private Collection<FieldModel> fieldModels;
	private Collection<IFormatBeansView<T>> formatViews;
	private Collection<IFilterService> filters;
	private Collection<IActionBeansEditor> listActions;
	private Collection<IActionBeanEditor> editingActions;
	private Key editionTitle;
	private Key title;
	private Key description;
	private Icon icon;
	private IEditorServices<T> editorServices;

	@Override
	public Collection<IBeansEditorModel<?>> getBeanDetail() {
		return beanDetail;
	}

	@Override
	public Collection<FieldModel> getFieldModels() {
		return fieldModels;
	}

	@Override
	public Collection<IFormatBeansView<T>> getFormatViews() {
		return formatViews;
	}

	@Override
	public Collection<IFilterService> getFilters() {
		return filters;
	}

	@Override
	public Collection<IActionBeansEditor> getListActions() {
		return listActions;
	}

	@Override
	public Collection<IActionBeanEditor> getEditingActions() {
		return editingActions;
	}

	@Override
	public Key getEditionTitle() {
		return editionTitle;
	}

	@Override
	public DataProvider<Entity> getDataProvider() {
		return DataContext.getProvider();
	}

	@Override
	public Key getTitle() {
		return title;
	}

	@Override
	public Key getDescription() {
		return description;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public IEditorServices<T> getEditorServices() {
		return editorServices;
	}

	/**
	 * Editor detalle del bean (Ej.: contactos de una empresa).
	 * @param beanDetail
	 * @return
	 */
	public AbstractBeansEditorModel<T> setBeanDetail(Collection<IBeansEditorModel<?>> beanDetail) {
		this.beanDetail = beanDetail;
		return this;
	}

	/**
	 * Modelo de los campos del editor existentes como propiedades del bean editado.
	 * @param fieldModels
	 * @return
	 */
	public AbstractBeansEditorModel<T> setFieldModels(Collection<FieldModel> fieldModels) {
		this.fieldModels = fieldModels;
		return this;
	}

	/**
	 * Vistas de datos del bean (html)
	 * @param formatViews
	 * @return
	 */
	public AbstractBeansEditorModel<T> setFormatViews(Collection<IFormatBeansView<T>> formatViews) {
		this.formatViews = formatViews;
		return this;
	}

	/**
	 * Filtros de selección
	 * @param filters
	 * @return
	 */
	public AbstractBeansEditorModel<T> setFilters(Collection<IFilterService> filters) {
		this.filters = filters;
		return this;
	}
	
	/**
	 * Acciones asociadas a la lista de beans.
	 * @param listActions
	 * @return
	 */
	public AbstractBeansEditorModel<T> setListActions(Collection<IActionBeansEditor> listActions) {
		this.listActions = listActions;
		return this;
	}

	/**
	 * Acciones de edición
	 * @param editingActions
	 * @return
	 */
	public AbstractBeansEditorModel<T> setEditingActions(Collection<IActionBeanEditor> editingActions) {
		this.editingActions = editingActions;
		return this;
	}

	/**
	 * Título de la edición de un bean
	 * @param editionTitle
	 * @return
	 */
	public AbstractBeansEditorModel<T> setEditionTitle(Key editionTitle) {
		this.editionTitle = editionTitle;
		return this;
	}

	/**
	 * Título
	 * @param title
	 * @return
	 */
	public AbstractBeansEditorModel<T> setTitle(Key title) {
		this.title = title;
		return this;
	}

	/**
	 * Descripción
	 * @param description
	 * @return
	 */
	public AbstractBeansEditorModel<T> setDescription(Key description) {
		this.description = description;
		return this;
	}

	/**
	 * Icono (opcional)
	 * @param icon
	 * @return
	 */
	public AbstractBeansEditorModel<T> setIcon(Icon icon) {
		this.icon = icon;
		return this;
	}

	/**
	 * Establecer los servicios del editor. Ej.: Nested beans de relaciones con otras entidades.
	 * @param editorServices
	 * @return
	 */
	public AbstractBeansEditorModel<T> setEditorServices(IEditorServices<T> editorServices) {
		this.editorServices = editorServices;
		return this;
	}

	@Override
	public LayoutEditorModel getLayoutEditorModel(IBeanEditorModel<T> model) {
		return null;
	}

	@Override
	public ICustomLayoutEditor<T> getCustomLayoutEditor(IBeanEditorModel<T> model) {
		return null;
	}

	/**
	 * Añadir el modelo del editor detalle. Ej.: edición de contactos de una
	 * empresa.
	 * 
	 * @param beanDetail
	 */
	public void addBeanDetail(IBeansEditorModel<?> detail) {
		if (beanDetail == null) {
			beanDetail = new ArrayList<IBeansEditorModel<?>>();
		}
		beanDetail.add(detail);
	}

	/**
	 * Añadir un modelo de campo del editor de beans.
	 * 
	 * @param fieldModel
	 */
	public void addFieldModel(FieldModel fieldModel) {

		if (fieldModels == null) {
			fieldModels = new ArrayList<FieldModel>();
		}
		fieldModels.add(fieldModel);
	}

	/**
	 * Añadir una vista de tipo lista de beans donde aplicamos un formato html
	 * de visualización con las propiedades del bean.
	 * 
	 * @param formatView
	 */
	public void addFormatView(IFormatBeansView<T> formatView) {

		if (formatViews == null) {
			formatViews = new ArrayList<IFormatBeansView<T>>();
		}
		formatViews.add(formatView);
	}

	@Override
	public T getNewBean() {
		try {
			if (getBeanClass() != null) {
				return getBeanClass().newInstance();
			} else {
				logger.error("ERROR INSTANCE BEAN '" + getBeanClass().getCanonicalName() + ". Method getBeanClass() == null.");
			}
		} catch (InstantiationException e) {
			logger.error("ERROR INSTANCE BEAN '" + getBeanClass().getCanonicalName());
		} catch (IllegalAccessException e) {
			logger.error("ERROR INSTANCE BEAN '" + getBeanClass().getCanonicalName());
		}
		return null;
	}

	/**
	 * Añadir filtro de beans.
	 * 
	 * @param filter
	 */
	public void addFilterService(IFilterService filter) {

		if (filters == null) {
			filters = new ArrayList<IFilterService>();
		}
		filters.add(filter);
	}
}
