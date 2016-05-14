package com.javalego.ui.mvp.editor.beans;

import java.util.Collection;

import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.actions.IActionBeansEditor;
import com.javalego.ui.editor.builder.BeanEditorsBuilder;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.bean.IBeanEditorView;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorPresenter;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansEditorViewListener;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;
import com.javalego.ui.mvp.editor.services.IEditorServices;
import com.javalego.ui.mvp.editor.services.NestedFieldsServices;
import com.javalego.ui.patterns.IPresenter;
import com.javalego.util.ArrayUtils;

/**
 * Presenter cambio de idioma de la sesión de usuario.
 * 
 * @author ROBERTO RANZ
 */
public class BeansEditorPresenter<T> implements BeansEditorViewListener<T>, ISelectBeans<T>, IPresenter {

	/**
	 * Modelo
	 */
	protected IBeansEditorModel<T> model;

	/**
	 * Vista
	 */
	protected IBeansEditorView<T> view;

	/**
	 * Edición de solo lectura
	 */
	private boolean readOnly;

	/**
	 * Formato de vista actualmente utilizado para mostrar los datos del bean.
	 */
	private IFormatBeansView<T> currentFormatView;

	/**
	 * Formatos de vistas disponibles.
	 */
	private Collection<IFormatBeansView<T>> formatViews;

	/**
	 * Objeto maestro para filtrar registros e incluir su id al insertar nuevos
	 * beans en este editor detalle.
	 */
	private Object masterBean;

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param model
	 * @param view
	 */
	public BeansEditorPresenter(IBeansEditorModel<T> model, IBeansEditorView<T> view) {
		this.model = model;
		this.view = view;
		if (view != null) {
			view.setListener(this);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param model
	 */
	public BeansEditorPresenter(IBeansEditorModel<T> model) {
		this.model = model;
	}	

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param model
	 * @param view
	 * @param masterBean
	 *            Objeto master asociado al editor de beans que servirá para
	 *            realizar el filtro de sus registros y eliminar los campos a
	 *            editar de este bean.
	 */
	public BeansEditorPresenter(IBeansEditorModel<T> model, IBeansEditorView<T> view, Object masterBean) {
		this.model = model;
		this.view = view;
		if (view != null) {
			view.setListener(this);
		}
		this.masterBean = masterBean;
	}

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param model
	 * @param view
	 * @param layoutEditorPresenter
	 *            distribuidor de campos
	 * @param readOnly
	 */
	public BeansEditorPresenter(IBeansEditorModel<T> model, IBeansEditorView<T> view, boolean readOnly) {
		this(model, view);
		this.readOnly = readOnly;
	}

	@Override
	public void load() throws LocalizedException {
		if (view != null) {
			view.load();
		}
	}

	@Override
	public Collection<T> getBeans(String filter, String order) throws LocalizedException {

		return model.getBeans(getInternalFilter(filter), order);
	}

	@Override
	public IBeansEditorView<T> getView() {
		return view;
	}

	@Override
	public Collection<FieldModel> getFieldModels() {
		return model.getFieldModels();
	}

	@Override
	public Class<T> getBeanClass() {
		return model.getBeanClass();
	}

	@Override
	public boolean hasFilters() {
		return model.getFilters() != null && model.getFilters().size() > 0;
	}

	@Override
	public boolean hasViews() {
		return model.getFormatViews() != null && model.getFormatViews().size() > 0;
	}

	@Override
	public boolean hasActions() {
		return model.getListActions() != null && model.getListActions().size() > 0;
	}

	@Override
	public Collection<IFilterService> getFilters() {
		return model.getFilters();
	}

	@Override
	public T getNewBean() {

		T bean = model.getNewBean();

		if (bean instanceof Entity && masterBean instanceof Entity) {
			NestedFieldsServices.setMasterBean(bean, masterBean);
		}

		return bean;
	}

	@Override
	public boolean isVisibleField(String fieldname) {

		return masterBean instanceof Entity ? NestedFieldsServices.isFieldVisible(fieldname, masterBean.getClass(), model.getBeanClass()) : true;
	}

	/**
	 * Crear el filtro de selección de registros en base al filtro actual de la
	 * vista seleccionado por el usuario (opcional) y el filtro del bean master,
	 * si es un editor master/detail.
	 * 
	 * @param filter
	 * @return
	 * @throws LocalizedException
	 */
	public String getInternalFilter(String filter) {

		String masterFilter = NestedFieldsServices.getMasterBeanFilter(masterBean, model.getBeanClass());

		return filter != null && masterFilter != null ? "(" + filter + ") and " + masterFilter : masterFilter != null ? masterFilter : filter;
	}

	@Override
	public Collection<T> getSelectedBeans() {
		return view != null ? view.getSelectedBeans() : null;
	}

	@Override
	public void setSelectedBeans(Collection<T> beans) {
		if (view != null) {
			view.setSelectedBeans(beans);
		}
	}

	@Override
	public T getCurrentBean() {
		return view != null ? view.getCurrentBean() : null;
	}

	@Override
	public void removeSelectedBeans() {
		if (view != null) {
			view.removeSelectedBeans();
		}
	}

	@Override
	public LayoutEditorModel getLayoutEditorModel(IBeanEditorModel<T> model) {
		return this.model.getLayoutEditorModel(model);
	}

	@Override
	public ICustomLayoutEditor<T> getCustomLayoutEditor(IBeanEditorModel<T> model) {
		return this.model.getCustomLayoutEditor(model);
	}

	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	@Override
	public Collection<IActionBeansEditor> getActions() {
		return model.getListActions();
	}

	@Override
	public Key getTitle() {
		return model.getTitle();
	}

	@Override
	public Key getEditionTitle() {
		return model.getEditionTitle();
	}

	@Override
	public Icon getIcon() {
		return model.getIcon();
	}

	@Override
	public Collection<IFormatBeansView<T>> getFormatViews() {
		if (formatViews == null) {
			formatViews = model.getFormatViews();
		}
		return formatViews;
	}

	@Override
	public IFormatBeansView<T> getCurrentFormatView() {
		if (currentFormatView == null && getFormatViews() != null && getFormatViews().size() > 0) {
			currentFormatView = getFormatViews().iterator().next();
		}
		return currentFormatView;
	}

	@Override
	public void setCurrentFormatView(IFormatBeansView<T> formatView) {
		currentFormatView = formatView;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IEditorServices getEditorServices() {
		return model.getEditorServices();
	}

	@Override
	public BeanEditorPresenter<T> getEditorPresenter(T bean, boolean inserting, IBeanEditorView<T> view) {

		BeanEditorPresenter<T> p = new BeanEditorsBuilder<T>().getEditor(model, view, bean, isReadOnly(), inserting);

		if (masterBean != null) {
			p.setMasterBean(masterBean);
		}
		return p;
	}

	@Override
	public void editBean(T bean) throws LocalizedException {
		if (view != null) {
			view.editBean(bean);
		}
	}

	@Override
	public void applyFilter(String statement) throws LocalizedException {
		if (view != null) {
			view.applyFilter(statement);
		}
	}

	@Override
	public void removeCurrentFilter() throws LocalizedException {
		if (view != null) {
			view.removeCurrentFilter();
		}
	}

	@Override
	public String[] getColumnNames() {

		String[] columnNames = new String[0];

		for (FieldModel field : model.getFieldModels()) {

			// No incluir campos blob en la tabla y los campos del bean master.
			if (isVisibleField(field.getName()) && !(field instanceof ImageFieldModel)) {

				columnNames = (String[]) ArrayUtils.add(columnNames, field.getName());
			}
		}
		return columnNames;
	}

}
