package com.javalego.ui.mvp.beans.view.list.adapter;

import com.javalego.model.keys.Icon;
import com.javalego.ui.mvp.beans.view.list.adapter.IListBeansViewAdapter;

/**
 * Adapter básico de los datos del bean para mostrarlos en la vista de una
 * lista.
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class ListBeansViewAdapter<T> implements IListBeansViewAdapter<T> {

	private String imageFieldName;
	private String title;
	private int defaultImageResource;
	private Integer dimens;

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param imageFieldName
	 */
	public ListBeansViewAdapter(String title, String imageFieldName) {
		this.imageFieldName = imageFieldName;
		this.title = title;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 */
	public ListBeansViewAdapter(String title) {
		this.title = title;
	}

	/**
	 * Constructor
	 */
	public ListBeansViewAdapter() {
	}	
	
	/**
	 * Constructor
	 * 
	 * @param title
	 * @param imageFieldName
	 * @param defaultImageResource
	 */
	public ListBeansViewAdapter(String title, String imageFieldName, int defaultImageResource) {
		this(title, imageFieldName);
		this.defaultImageResource = defaultImageResource;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param imageFieldName
	 * @param defaultImageResource
	 * @param dimens
	 */
	public ListBeansViewAdapter(String title, String imageFieldName, int defaultImageResource, int dimens) {
		this(title, imageFieldName);
		this.defaultImageResource = defaultImageResource;
		this.dimens = dimens;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getImageFieldName() {
		return imageFieldName;
	}

	@Override
	public Icon getIcon(T bean) {
		return null;
	}

	public void setImageFieldName(String imageFieldName) {
		this.imageFieldName = imageFieldName;
	}

	@Override
	public int getDefaultImageResource() {
		return defaultImageResource;
	}

	public void setDefaultImageResource(int defaultImageResource) {
		this.defaultImageResource = defaultImageResource;
	}

	public Integer getDimens() {
		return dimens;
	}

	public void setDimens(Integer dimens) {
		this.dimens = dimens;
	}

	@Override
	public boolean contains(T bean, String contains) {
		if (contains != null) {
			String text = toHtml(bean);
			return text.toLowerCase().indexOf(contains.toLowerCase()) > -1;
		} else {
			return false;
		}
	}

}
