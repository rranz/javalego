package com.javalego.ui.mvp.editor.beans;

import java.io.Serializable;
import java.util.Collection;

import com.javalego.data.DataProvider;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.actions.IActionBeanEditor;
import com.javalego.ui.actions.IActionBeansEditor;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;
import com.javalego.ui.mvp.editor.services.IEditorServices;

/**
 * Modelo de editor de objetos.
 * 
 * @see IDataBean
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface IBeansEditorModel<T> extends Serializable {

	/**
	 * Lista de beans del editor.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	Collection<T> getBeans(String filter, String order) throws LocalizedException;

	/**
	 * Modelo de editores detalle (Ej.: empresas con detalle de: contactos, empleados, ...
	 * @return
	 */
	Collection<IBeansEditorModel<?>> getBeanDetail();
	
	/**
	 * Clase de Bean requerida para crear los componentes visuales cuando
	 * getData() no devuelve información (size = 0 o null).
	 * 
	 * @return
	 */
	Class<T> getBeanClass();

	/**
	 * Lista de modelo de campos que queremos mostrar en la vista.
	 * 
	 * @return
	 */
	Collection<FieldModel> getFieldModels();

	/**
	 * Lista de formatos de visualización de los beans dependiendo de la
	 * naturaleza de la vista utilizada: list, table, ...
	 * 
	 * @return
	 */
	Collection<IFormatBeansView<T>> getFormatViews();

	/**
	 * Lista de servicios de generación de filtros de beans o registros.
	 * 
	 * @return
	 */
	Collection<IFilterService> getFilters();

	/**
	 * Nuevo objeto
	 * 
	 * @return
	 */
	T getNewBean();

	/**
	 * Definición de la distribución de campos de un layout basada en
	 * agrupaciones.
	 * 
	 * <pre>
	 * LayoutEditorPresenter lp = new LayoutEditorPresenter(new LayoutEditorModel(), new LayoutEditorView());
	 * lp.addChildren(&quot;Datos generales&quot;, CssColors.ORANGE, model, &quot;nombre&quot;, &quot;razon_social&quot;, &quot;cif&quot;);
	 * lp.addChildren(&quot;Datos adicionales&quot;, CssColors.NAVY, model, &quot;fecha_creacion&quot;, &quot;localidad&quot;, &quot;representante&quot;);
	 * </pre>
	 * 
	 * 
	 * @return
	 */
	LayoutEditorModel getLayoutEditorModel(IBeanEditorModel<T> model);

	/**
	 * Definición de un layout de distribución de campos personalizado.
	 * En el método getLayout() podrá generar su propio layout adaptado a las necesidades concretas de su aplicación. 
	 * 
	 * @return
	 */
	ICustomLayoutEditor<T> getCustomLayoutEditor(IBeanEditorModel<T> model);	
	
	/**
	 * Lista de acciones del editor asociadas al browser de registros.
	 * 
	 * @return
	 */
	Collection<IActionBeansEditor> getListActions();

	/**
	 * Lista de acciones del editor asociadas al editor del bean.
	 * 
	 * @return
	 */
	Collection<IActionBeanEditor> getEditingActions();

	/**
	 * Título utilizado al editar un bean. (Ej.: Empresa)
	 * 
	 * @return
	 */
	Key getEditionTitle();

	/**
	 * Proveedor de datos utilizado para la persistencia de beans (CRUD).
	 * Nota: si es igual a null, no se realizará persistencia y los datos solo se actualizarán en memoria.
	 * @return
	 */
	DataProvider getDataProvider();
	
	/**
	 * Título del editor
	 * @return
	 */
	Key getTitle();
	
	/**
	 * Descripción del editor
	 * @return
	 */
	Key getDescription();

	/**
	 * Icono asociado al editor
	 * @return
	 */
	Icon getIcon();
	
	/**
	 * Servicios del editor relacionados con la edición, campos anidados o nested beans para campos relacionados tipo JPA, ...
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	IEditorServices getEditorServices();

}
