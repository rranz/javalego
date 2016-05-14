package com.javalego.ui.vaadin.view.editor.beans.list.lazy;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

/**
 * Container que proporciona beans de forma paginada.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public class LazyPagedContainer<T> extends BeanItemContainer<T> {

	private static final long serialVersionUID = 6183223215084402510L;

	private LazyQuery<T> lazyQuery;

	private Boolean applyLazyFilter = false;

	private String fieldToCompare = "id";

	private boolean doString = false;

	private static final Logger logger = Logger.getLogger(LazyPagedContainer.class);
	
	public LazyPagedContainer(Class<? super T> type) throws IllegalArgumentException {
		super(type);
	}

	@Override
	public int size() {
		try {
			if (applyLazyFilter) {
				return lazyQuery.getLazyFilteredSize();
			}
			return lazyQuery.getLazySize();
		}
		catch (LocalizedException e) {
			e.printStackTrace();
			logger.error("ERROR GET SIZE LAZY PAGED CONTAINER. " + e.getLocalizedMessage());
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public BeanItem<T> getItem(Object itemId) {

		return new BeanItem<T>(((T) itemId));
	}

	@Override
	public List<T> getItemIds(int startIndex, int numberOfIds) {

		try {
			int i = 0;
			// Remove items and do it.
			internalRemoveAllItems();
			// Apply lazy or normal query.
			if (applyLazyFilter) {
				Collection<T> items = lazyQuery.getLazyFilteredItemsIds(startIndex, numberOfIds);
				if (items != null) {
					for (T item : items) {
						getAllItemIds().add(i, item);
						registerNewItem(i, item, new BeanItem<T>(item));
						i++;
					}
				}
			}
			else {
				Collection<T> items = lazyQuery.getLazyItemsIds(startIndex, numberOfIds);
				if (items != null) {
					for (T item : items) {
						getAllItemIds().add(i, item);
						registerNewItem(i, item, new BeanItem<T>(item));
						i++;
					}
				}
			}
			// Default return to show items
			return super.getItemIds(0, numberOfIds);
		}
		catch (LocalizedException e) {
			e.printStackTrace();
			logger.error("ERROR GET ITEMS LAZY PAGED CONTAINER. " + e.getLocalizedMessage());
			return null;
		}
	}

	@SuppressWarnings("unused")
	private int findVal(T item) {

		for (int i = 0; i < getAllItemIds().size(); i++) {
			try {
				Field fieldA = item.getClass().getDeclaredField(fieldToCompare);
				Field fieldB = getAllItemIds().get(i).getClass().getDeclaredField(fieldToCompare);

				fieldA.setAccessible(true);
				fieldB.setAccessible(true);

				if (doString) {
					if (fieldA.get(item).equals(fieldB.get(getAllItemIds().get(i)))) {
						return i;
					}
				}
				else {
					if (fieldA.get(item) == fieldB.get(getAllItemIds().get(i))) {
						return i;
					}
				}
			}
			catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				System.err.println(e);
			}
		}
		return -1;
	}

	public LazyQuery<T> getLazyQuery() {
		return lazyQuery;
	}

	public void setLazyQuery(LazyQuery<T> lazyQuery) {
		this.lazyQuery = lazyQuery;
	}

	public Boolean getApplyLazyFilter() {
		return applyLazyFilter;
	}

	public void setApplyLazyFilter(Boolean applyLazyFilter) {
		this.applyLazyFilter = applyLazyFilter;
	}

	public boolean hasItems() {
		return !(getVisibleItemIds().size() > 0);
	}

}
