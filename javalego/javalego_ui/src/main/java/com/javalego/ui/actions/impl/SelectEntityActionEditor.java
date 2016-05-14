package com.javalego.ui.actions.impl;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.javalego.data.DataContext;
import com.javalego.entity.Entity;
import com.javalego.exception.JavaLegoException;
import com.javalego.exception.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.UIContext;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.IValueDataEditor;
import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.javalego.ui.mvp.finder.FinderPresenter;
import com.javalego.ui.mvp.finder.IFinderModel;
import com.javalego.ui.mvp.finder.IFinderView;
import com.javalego.util.ReflectionUtils;

/**
 * Selección de un bean de entidad.
 * 
 * Esta clase gestiona la búsqueda de la clave y valida que el campo clave que representa la entidad sea válido.
 * 
 * Se requiere la clase entidad para poder realizar las búsquedas en el proveedor de datos actual. Si quiere utilizar datos no persistentes en base de datos, debe crear 
 * un provider específico para este tipo de datos o implementar el método getCustomObjects() que sirve para usar estos objetos de entidad en el proceso de búsqueda y validación.
 * 
 * @author ROBERTO RANZ
 * 
 */
public abstract class SelectEntityActionEditor<T> extends SelectActionEditor {

	private static final long serialVersionUID = 7085188091452724181L;

	private static final Logger logger = Logger.getLogger(SelectEntityActionEditor.class);
	
	private String filter;

	private String order;

	private String[] fieldNames;

	private String[] headers;

	private FinderPresenter<T> finderPresenter;

	private Key titleFinder;

	private Class<? extends Entity> entityClass;

	private boolean notFound;

	/**
	 * Constructor
	 * @param entityClass Clase de la entidad que vamos a utilizar para realizar la validación y búsqueda.
	 * @param icon Icono del componente UI de búsqueda
	 * @param titleFinder Título de la vista que permite buscar una entidad.
	 * @param fieldNames Campos a mostrar en la vista
	 * @param headers Títulos de los campos de la vista
	 * @param filter Filtro utilizado para obtener entidades y validar
	 * @param order Ordenación utilizada en la vista.
	 */
	public SelectEntityActionEditor(Class<? extends Entity> entityClass, Icon icon, Key titleFinder, String[] fieldNames, String[] headers, String filter, String order) {
		super(icon);
		this.entityClass = entityClass;
		this.titleFinder = titleFinder;
		this.fieldNames = fieldNames;
		this.headers = headers;
		this.filter = filter;
		this.order = order;
	}

	/**
	 * Constructor
	 * @param entityClass Clase de la entidad que vamos a utilizar para realizar la validación y búsqueda.
	 * @param titleFinder Título de la vista que permite buscar una entidad.
	 * @param fieldNames Campos a mostrar en la vista
	 * @param headers Títulos de los campos de la vista
	 * @param filter Filtro utilizado para obtener entidades y validar
	 * @param order Ordenación utilizada en la vista.
	 */
	public SelectEntityActionEditor(Class<? extends Entity> entityClass, Key titleFinder, String[] fieldNames, String[] headers, String filter, String order) {
		super();
		this.entityClass = entityClass;
		this.titleFinder = titleFinder;
		this.fieldNames = fieldNames;
		this.headers = headers;
		this.filter = filter;
		this.order = order;
	}

	@Override
	public void validate(IEditorViewListener listener, IValueDataEditor dataEditor, Object oldValue, Object newValue) throws LocalizedException {

		if (notFound) {
			throw UIContext.getException(CommonErrors.KEY_NOT_FOUND);
		}
	}

	@Override
	public void valueChangeEvent(IEditorViewListener listener, IValueDataEditor dataEditor, Object currentValue) throws LocalizedException {

		// No validar si los objetos son creados temporalmente
		if (getCustomObjects() != null) {
			
			validateFinderObjects(listener, dataEditor, currentValue);
		}
		// Buscar utilizando el provider por defecto para validar el valor de la nueva clave de entidad (puede ser su id o un campo con valor no repetible en la tabla).
		else if (DataContext.getProvider() != null && currentValue != null) {

			// Condición del valor
			String whereValue = currentValue instanceof String ? "'" + currentValue + "'" : currentValue.toString();

			// Campo de búsqueda de la clave
			String idFieldName = dataEditor.getName().indexOf(".") > 0 ? dataEditor.getName().substring(dataEditor.getName().indexOf(".") + 1) : dataEditor.getName();

			Object findObject = DataContext.getProvider().getObject("from " + entityClass.getCanonicalName() + " where " + idFieldName + " = " + whereValue + (filter != null ? " and " + filter : ""));

			notFound = findObject == null;
			
			listener.setBeanValues(dataEditor, findObject);
		}
		// Eliminar toda la información del bean si la clave no tiene valor porque el usuario la ha eliminado del control de edición.
		else if (currentValue == null) {
			listener.setBeanValues(dataEditor, null);
		}
	}

	/**
	 * Realizar la validación sobre los objetos temporales u obtenidos de otras fuentes distintas al provider por defecto.
	 * @param listener
	 * @param dataEditor
	 * @param currentValue
	 * @throws LocalizedException
	 */
	private void validateFinderObjects(IEditorViewListener listener, IValueDataEditor dataEditor, Object currentValue) throws LocalizedException {
		
		if (currentValue == null) {
			listener.setBeanValues(dataEditor, null);
		}
		else {
			notFound = true;
			String idFieldName = dataEditor.getName().indexOf(".") > 0 ? dataEditor.getName().substring(dataEditor.getName().indexOf(".") + 1) : dataEditor.getName();
			Collection<T> list = getCustomObjects();
			for(T object : list) {
				try {
					Object value = ReflectionUtils.getPropertyValue(object, idFieldName);
					if (value != null && currentValue.toString().equals(value.toString())) {
						listener.setBeanValues(dataEditor, object);
						notFound = false;
						break;
					}
				}
				catch (JavaLegoException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (notFound) {
				listener.setBeanValues(dataEditor, null);
			}
		}
	}

	@Override
	public void execute(IEditorViewListener listener, IItemEditor dataEditor) throws LocalizedException {

		if (finderPresenter == null) {
			finderPresenter = getFinder(listener, dataEditor, titleFinder, fieldNames, headers);
		}
		finderPresenter.getView().show();
	}

	/**
	 * Creación del presenter de búsqueda de objetos para utilizarlo en un campo
	 * en edición o en otros contextos (es independiente este buscador).
	 * 
	 * @param bean
	 * @return
	 * @throws LocalizedException
	 */
	private FinderPresenter<T> getFinder(final IEditorViewListener listener, final IItemEditor elementEditor, final Key title, final String[] fieldNames, final String[] headers)
			throws LocalizedException {

		IFinderView<T> finderView = getFinderView();

		FinderPresenter<T> finderPresenter = new FinderPresenter<T>(new IFinderModel<T>() {

			@Override
			public void setValue(T value) throws LocalizedException {
				// Establecer el valor de un campo de tipo bean o campo anidado.
				listener.setBeanValues(elementEditor, value);
			}

			@SuppressWarnings("unchecked")
			@Override
			public Collection<T> getObjects() throws LocalizedException {
				
				Collection<T> list = getCustomObjects();
				
				if (list == null) {
					return DataContext.getProvider() != null ? (Collection<T>) DataContext.getProvider().getList(getEntityClass(), filter, order) : null;
				}
				else {
					return list;
				}
			}

			@Override
			public String[] getProperties() {
				return fieldNames;
			}

			@Override
			public String[] getHeaders() {
				return headers;
			}

			@Override
			public Key getTitle() {
				return title;
			}
		}, finderView);

		finderPresenter.load();

		return finderPresenter;
	}

	/**
	 * Obtener la vista de una implementación IFinderView.
	 * 
	 * @return
	 */
	public abstract IFinderView<T> getFinderView();

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public Key getTitleFinder() {
		return titleFinder;
	}

	public void setTitleFinder(Key titleFinder) {
		this.titleFinder = titleFinder;
	}

	public Class<? extends Entity> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<? extends Entity> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Lista de objetos proporcionados por la clase que instancia este objeto y sirve para poder probar esta acción de selección de entidad con 
	 * entidades temporales u obtenidas de otras fuentes de información (temporal, servicios web, archivos, ...)
	 * 
	 * Debe sobreescribir este método para incluir estos objetos en el proceso de selección y validación de entidades. 
	 * 
	 * @return
	 */
	public Collection<T> getCustomObjects() {
		return null;
	}

}
