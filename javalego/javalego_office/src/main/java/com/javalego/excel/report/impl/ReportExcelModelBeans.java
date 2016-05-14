package com.javalego.excel.report.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.javalego.excel.report.ReportModelBeans;
import com.javalego.exception.JavaLegoException;
import com.javalego.exception.LocalizedException;
import com.javalego.locale.LocaleContext;
import com.javalego.model.BaseModel;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.util.ReflectionUtils;

public class ReportExcelModelBeans<T> implements ReportModelBeans<T> {

	private List<T> beans = new ArrayList<T>();
	private List<FieldModel> model = new ArrayList<FieldModel>();
	private String title;
	private String subtitle;
	private Locale locale;

	/**
	 * Constructor
	 * 
	 * @param beans
	 *            Lista de beans
	 * @param model
	 *            Modelo o definición de la lista de campos a imprimir
	 * @param title
	 */
	public ReportExcelModelBeans(Collection<T> beans, Collection<FieldModel> model, String title, Locale locale) {

		if (beans != null) {
			this.beans.addAll(beans);
		}

		if (model != null) {
			this.model.addAll(model);
		}

		purge();

		this.title = title;
		this.locale = locale;
	}

	/**
	 * Purgar campos no visibles o blob.
	 */
	private void purge() {
		
		if (model != null && model.size() > 0) {
		
			for (int i = model.size() - 1; i > -1; i--) {
			
				if (!model.get(i).isVisible() || !model.get(i).isVisibleBrowser() || model.get(i) instanceof ImageFieldModel) {
					model.remove(i);
				}
			}
		}
	}

	/**
	 * Constructor
	 * 
	 * @param beans
	 *            Lista de beans
	 * @param model
	 *            Modelo o definición de la lista de campos a imprimir
	 * @param title
	 * @param subtitle
	 */
	public ReportExcelModelBeans(Collection<T> beans, Collection<FieldModel> model, String title, String subtitle, Locale locale) {
		this(beans, model, title, locale);
		this.subtitle = subtitle;
	}

	@Override
	public List<T> getBeans() throws LocalizedException {
		return beans;
	}

	@Override
	public List<? extends BaseModel> getModel() {
		return model;
	}

	@Override
	public String getHeader(int column) {

		return getModel() != null && getModel().size() > column ? LocaleContext.getTitle(model.get(column), locale == null ? Locale.getDefault() : locale) : null;
	}

	@Override
	public String getHeader(String name) {

		if (getModel() != null) {
			for (BaseModel item : getModel()) {
				if (item.getName().equals(name)) {
					return LocaleContext.getTitle(item, locale == null ? Locale.getDefault() : locale);
				}
			}
		}
		return null;
	}

	/**
	 * Idioma utilizado para generar el informe y así poder traducir los valores
	 * enumerados del modelo de campos.
	 * 
	 * @return
	 */
	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public Object getValue(int row, int column) throws LocalizedException {

		if (getRows() > row) {
			try {
				return ReflectionUtils.getPropertyValue(getBeans().get(row), getModel().get(column).getName());
			}
			catch (JavaLegoException e) {
				throw new LocalizedException("ERROR GET VALUE '" + getModel().get(column).getName(), e);
			}
		}
		else {
			return null;
		}
	}

	@Override
	public int getColumns() {

		return getModel() != null ? getModel().size() : 0;
	}

	@Override
	public int getRows() throws LocalizedException {
		return getBeans() != null ? getBeans().size() : 0;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getSubTitle() {
		return subtitle;
	}

}
