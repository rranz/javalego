package com.javalego.ui.mvp.beans.view.paged;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.beans.view.IBeansViewPresenter;
import com.javalego.ui.mvp.beans.view.paged.IPagedBeansView.PagedBeansViewListener;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansEditorViewListener;

/**
 * Presenter para mostrar una lista de beans paginada.
 * 
 * <pre>
		List<Person> list = new ArrayList<Person>();
		for (int i = 0; i < 27; i++) {
			list.add(new Person("DIRECCION " + i));
		}

		final PagedBeansPresenter<Person> p = new PagedBeansPresenter<>(new AbstractPagedBeansModel<Person>(list, 5) {
			@Override
			public String toHtml(Person bean) {
				return bean.getName() + " Dirección: " + bean.getAddress();
			}
		}, new PagedBeansEditorView<Person>());

		p.load();
		return (Component) p.getView();
 * </pre>
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public class PagedBeansPresenter<T> implements PagedBeansViewListener<T>, IBeansViewPresenter<T> {

	/**
	 * Modelo de obtención de beans usando paginación.
	 */
	private AbstractPagedBeansModel<T> model;

	/**
	 * Vista de la table paginada de beans
	 */
	private IPagedBeansView<T> view;
	
	/**
	 * Listener del editor de beans necesario para notificar la edición del bean
	 * de la lista.
	 */
	private BeansEditorViewListener<T> beansEditorListener;
	
	public PagedBeansPresenter(BeansEditorViewListener<T> beansEditorListener, AbstractPagedBeansModel<T> model, IPagedBeansView<T> view) {
		this.model = model;
		this.beansEditorListener = beansEditorListener;
		this.view = view;
		view.setListener(this);
	}

	@Override
	public Collection<T> getNextPageBeans() throws LocalizedException {
		return model.getNextPageBeans();
	}

	@Override
	public Collection<T> getPriorPageBeans() throws LocalizedException {
		return model.getPriorPageBeans();
	}

	@Override
	public Collection<T> getFirstPageBeans() throws LocalizedException {
		return model.getFirstPageBeans();
	}

	@Override
	public Collection<T> getLastPageBeans() throws LocalizedException {
		return model.getLastPageBeans();
	}

	@Override
	public Collection<T> getPageBeans(int index) throws LocalizedException {
		return model.getPageBeans(index);
	}

	@Override
	public int getCountPages() throws LocalizedException {
		return model.getCountPages();
	}

	@Override
	public int getCurrentPage() {
		return model.getCurrentPage();
	}

	@Override
	public long getCountBeans() throws LocalizedException {
		return model.getCountBeans();
	}

	@Override
	public int getSizePageBeans() {
		return model.getSizePageBeans();
	}

	@Override
	public Collection<T> getBeansCurrentPage() throws LocalizedException {
		return model.getBeansCurrentPage();
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public IPagedBeansView<T> getView() {
		return view;
	}

	@Override
	public void firstPage() throws LocalizedException {
		view.reloadBeans(model.getFirstPageBeans());
	}

	@Override
	public void nextPage() throws LocalizedException {
		view.reloadBeans(model.getNextPageBeans());
	}

	@Override
	public void lastPage() throws LocalizedException {
		view.reloadBeans(model.getLastPageBeans());
	}

	@Override
	public void priorPage() throws LocalizedException {
		view.reloadBeans(model.getPriorPageBeans());
	}

	@Override
	public void loadPage(int index) throws LocalizedException {
		view.reloadBeans(model.getPageBeans(index));
	}

	@Override
	public Class<T> getBeanClass() {
		return model.getBeanClass();
	}

	@Override
	public String toHtml(T bean) {
		return model instanceof HtmlPagedBeansModel ? ((HtmlPagedBeansModel<T>)model).toHtml(bean) : "";
	}

	@Override
	public Collection<FieldModel> getFieldsModel() {
		return model instanceof FieldsPagedBeansModel ? ((FieldsPagedBeansModel<T>)model).getFieldsModel() : null;
	}

	@Override
	public void goSort(Object[] propertyId, boolean[] ascending) throws LocalizedException {
		model.sort(propertyId, ascending);
		view.reloadBeans(model.getFirstPageBeans());
	}

	@Override
	public void refreshPage() throws LocalizedException {
		view.reloadBeans(model.getPageBeans());
	}

	/**
	 * Aplicar filtro de selección de registros.
	 * @param statement
	 * @throws LocalizedException 
	 */
	@Override
	public void applyFilter(String filter) throws LocalizedException {
		model.setFilter(filter);
		view.reloadBeans(model.getFirstPageBeans());
	}

	/**
	 * Eliminar el filtro actual
	 * @throws LocalizedException 
	 */
	@Override
	public void removeCurrentFilter() throws LocalizedException {
		model.setFilter(null);
		view.reloadBeans(model.getFirstPageBeans());
	}

	@Override
	public void editBean(T bean) throws LocalizedException {
		if (beansEditorListener != null) {
			beansEditorListener.editBean(bean);
		}
	}

	@Override
	public Collection<T> getBeans(String filter, String order) throws LocalizedException {
		// TODO Auto-generated method stub
		return null;
	}

}
