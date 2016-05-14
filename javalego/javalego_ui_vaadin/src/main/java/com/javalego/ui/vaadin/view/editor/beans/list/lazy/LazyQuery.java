package com.javalego.ui.vaadin.view.editor.beans.list.lazy;

import java.util.Collection;

import com.javalego.exception.LocalizedException;

/**
 * Obtención de beans a partir de posiciones de una lista paginada.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public interface LazyQuery<T> {

	/**
     * Get Size by a Query
	 * @throws LocalizedException 
     */
    public int getLazySize() throws LocalizedException;

    /**
     * Get items to view on the page using the same model of the container.
     */
    public Collection<T> getLazyItemsIds(int startIndex, int numberOfIds) throws LocalizedException;

    /**
     * Get Size for manual filter.
     */
    public int getLazyFilteredSize() throws LocalizedException;

    /**
     * Get items to view on the page for manual filter using the same model of the container.
     */
    public Collection<T> getLazyFilteredItemsIds(int startIndex, int numberOfIds) throws LocalizedException;
    
}
