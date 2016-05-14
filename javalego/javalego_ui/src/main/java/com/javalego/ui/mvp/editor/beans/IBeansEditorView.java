package com.javalego.ui.mvp.editor.beans;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.actions.IActionBeansEditor;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.mvp.beans.view.IBeansView;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.bean.IBeanEditorView;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorPresenter;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;
import com.javalego.ui.mvp.editor.services.IEditorServices;
import com.javalego.ui.patterns.IView;

/**
 * Vista de menú básica
 * 
 * @author ROBERTO RANZ
 */
public interface IBeansEditorView<T> extends IView, ISelectBeans<T> {

	/**
	 * Métodos básicos para la visualización y edición de una lista de beans.
	 * 
	 * @author ROBERTO RANZ
	 */
	public static interface BeansViewListener<T> {

		/**
		 * Modelo de datos a editar.
		 * 
		 * @return
		 * @throws LocalizedException
		 */
		Collection<T> getBeans(String filter, String order) throws LocalizedException;

		/**
		 * Clase de Bean requerida para crear los componentes visuales cuando
		 * getData() no devuelve información (size = 0 o null).
		 * 
		 * @return
		 */
		Class<T> getBeanClass();

		/**
		 * Editar bean
		 * 
		 * @param bean
		 * @throws LocalizedException
		 */
		void editBean(T bean) throws LocalizedException;

		/**
		 * Aplicar un filtro de selección
		 * 
		 * @param statement
		 * @throws LocalizedException
		 */
		void applyFilter(String statement) throws LocalizedException;

		/**
		 * Eliminar el actual filtro de selección.
		 * 
		 * @throws LocalizedException
		 */
		void removeCurrentFilter() throws LocalizedException;
	}
	
	
	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del
	 * Presenter y que son utilizados por la vista (mvp pattern).
	 * 
	 * @author ROBERTO RANZ
	 */
	public static interface BeansEditorViewListener<T> extends BeansViewListener<T> {

		/**
		 * Lista de modelo de campos que incluiremos en la tabla.
		 * 
		 * @return
		 */
		Collection<FieldModel> getFieldModels();

		/**
		 * Comprueba si se han definido filtro de selección de registros en el
		 * modelo.
		 * 
		 * @return
		 */
		boolean hasFilters();

		/**
		 * Comprueba si se han definido vistas alternativas de datos
		 * 
		 * @return
		 */
		boolean hasViews();

		/**
		 * Lista de formatos de visualización de los beans dependiendo de la
		 * naturaleza de la vista utilizada: list, table, ...
		 * 
		 * @return
		 */
		Collection<IFormatBeansView<T>> getFormatViews();

		/**
		 * Obtiene el actual formato de visualización de los datos de bean.
		 * 
		 * @return
		 */
		IFormatBeansView<T> getCurrentFormatView();

		/**
		 * Establece el actual formato de visualización de los datos del bean.
		 * 
		 * @param formatView
		 */
		void setCurrentFormatView(IFormatBeansView<T> formatView);

		/**
		 * Comprueba si se han definido acciones adicionales
		 * 
		 * @return
		 */
		boolean hasActions();

		/**
		 * Obtiene la lista de servicios de generación de filtros.
		 * 
		 * @return
		 */
		Collection<IFilterService> getFilters();

		/**
		 * Obtener un nuevo bean para su edición y persistencia.
		 * 
		 * @return
		 */
		T getNewBean();

		/**
		 * Layout de distribución de campos
		 * 
		 * @param model
		 * @return
		 */
		LayoutEditorModel getLayoutEditorModel(IBeanEditorModel<T> model);

		/**
		 * Layout de distribución de campos
		 * 
		 * @param model
		 * @return
		 */
		ICustomLayoutEditor<T> getCustomLayoutEditor(IBeanEditorModel<T> model);

		/**
		 * Comprueba si la edición es de solo lectura.
		 * 
		 * @return
		 */
		boolean isReadOnly();

		/**
		 * Lista de acciones
		 * 
		 * @return
		 */
		Collection<IActionBeansEditor> getActions();

		/**
		 * Título del editor de beans (Ej.: Empresas)
		 * 
		 * @return
		 */
		Key getTitle();

		/**
		 * Título utilizado al editar un bean. (Ej.: Empresa)
		 * 
		 * @return
		 */
		Key getEditionTitle();

		/**
		 * Servicios del editor de beans.
		 * 
		 * Actualmente, se utiliza para servicios de obtención y validación de
		 * beans nested como campos de entidad incluidos en un objeto de tipo
		 * entidad (JPA).
		 * 
		 * @return
		 */
		IEditorServices<T> getEditorServices();

		/**
		 * Imagen asociada al editor
		 * 
		 * @return
		 */
		Icon getIcon();

		/**
		 * Obtiene el presenter para la edición del bean.
		 * 
		 * @param bean
		 * @param inserting
		 * @param view
		 *            Vista del editor de los campos del bean
		 * @return
		 */
		BeanEditorPresenter<T> getEditorPresenter(T bean, boolean inserting, IBeanEditorView<T> view);

		/**
		 * Comprobar si un campo debe mostrarse en la vista en la edición del
		 * bean. Ej.: campo nested del master bean (contacto.empresa.nombre no
		 * visible si editamos un contacto en un editor master/detail) ya que la
		 * empresa ya está asignada por el editor.
		 * 
		 * @param fieldname
		 * @return
		 */
		boolean isVisibleField(String fieldname);

		/**
		 * Nombre de la lista de columnas o campos visibles.
		 * @return
		 */
		String[] getColumnNames();
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios
	 * proporcionados por el Presenter que son necesarios para la vista (mvp
	 * pattern).
	 * 
	 * @param listener
	 */
	void setListener(BeansEditorViewListener<T> listener);

	/**
	 * Obtiene la vista de beans utilizada por el editor para mostrar su lista.
	 * Si no se define, se establece una vista por defecto (actualmente la
	 * paginada).
	 * 
	 * @return
	 */
	IBeansView<T> getBeansView();

	/**
	 * Editar bean
	 * 
	 * @param bean
	 */
	void editBean(T bean);

	/**
	 * Aplicar un filtro de selección
	 * 
	 * @param statement
	 * @throws LocalizedException
	 */
	void applyFilter(String statement) throws LocalizedException;

	/**
	 * Eliminar el actual filtro de selección.
	 * 
	 * @throws LocalizedException
	 */
	void removeCurrentFilter() throws LocalizedException;
}
