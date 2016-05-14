package com.javalego.store.mvp.list.presenter;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.IComment;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.mvp.list.AbstractListItemPresenter;
import com.javalego.store.mvp.list.ListItemView;

/**
 * Lista de comentarios
 * 
 * @author ROBERTO RANZ
 */
public class CommentListItemPresenter extends AbstractListItemPresenter<IComment> {

	private ListItemView<IComment> view;

	private StoreDataServices services;

	private Collection<IComment> comments;
	
	/**
	 * Constructor
	 * 
	 * @param view
	 */
	public CommentListItemPresenter(StoreDataServices services, Collection<IComment> comments, ListItemView<IComment> view) throws LocalizedException {
		this.view = view;
		this.services = services;
		this.comments = comments;
		view.setListener(this);
		load();
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public ListItemView<IComment> getView() {
		return view;
	}

	@Override
	public Collection<IComment> getItems() {

		return (Collection<IComment>) comments;
	}

	@Override
	public IComment getInstance() throws LocalizedException {
		return services.newInstanceComment();
	}

	@Override
	public boolean isInsertable() {
		
		return true && !view.isInsertable();
	}

	@Override
	public Colors getColor(IComment item) throws LocalizedException {
		
		return services.getColor(item);
	}

	@Override
	public Icon getIcon(IComment bean) {

		return services.getIcon(bean);
	}
	
	@Override
	public Collection<IComment> getBeans() throws LocalizedException {
		return view.getBeans();
	}

	@Override
	public boolean isReadOnly(IComment bean) {
		return services.isReadOnly(bean);
	}

	@Override
	public void save(IComment bean) {
	}

	@Override
	public void delete(IComment bean) {
	}

	@Override
	public boolean isFullWidth() {
		return true;
	}

}
