package com.javalego.ui.editor.data.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.exception.JavaLegoException;
import com.javalego.model.keys.Key;
import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.editor.data.IDataFieldModel;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.bean.IDataBeanFieldModel;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.factory.FieldModelFactory;
import com.javalego.ui.field.impl.AbstractFieldModel;
import com.javalego.util.ReflectionUtils;

/**
 * Bean de datos donde se define la lista de propiedades que vamos a editar o
 * visualizar.
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public class DataBean<T> extends AbstractDataEditor implements IDataBean<T> {

	private T bean;

	private static final Logger logger = Logger.getLogger(DataBean.class);

	/**
	 * Lista de propiedades del bean.
	 */
	private List<IDataBeanFieldModel> fields = new ArrayList<IDataBeanFieldModel>();

	/**
	 * Constructor
	 * 
	 * @param bean
	 */
	public DataBean(T bean) {

		super(bean.getClass().getSimpleName(), null);

		this.bean = bean;
	}

	@Override
	public T getBean() {
		return this.bean;
	}

	@Override
	public IDataBeanFieldModel addField(String name) {

		if (find(name) == null) {

			DataBeanFieldModel db = new DataBeanFieldModel(this, getFieldModel(name));

			fields.add(db);

			return db;
		}
		return null;
	}

	@Override
	public IDataBeanFieldModel addField(String name, boolean readOnly) {

		IDataBeanFieldModel db = this.addField(name);

		db.getFieldModel().setReadOnly(readOnly);

		return db;
	}

	@Override
	public IDataBeanFieldModel addField(String name, IActionEditor... actions) {

		if (!fields.contains(name)) {

			DataBeanFieldModel db = new DataBeanFieldModel(this, getFieldModel(name), actions);

			fields.add(db);

			return db;
		}
		return null;
	}

	@Override
	public IDataBeanFieldModel addField(String name, Key title, int size, int displayWidth) {

		if (find(name) == null) {

			AbstractFieldModel field = getFieldModel(name);

			field.setTitle(title);
			field.setMaxSize(size);
			field.setColumns(displayWidth);

			IDataBeanFieldModel db = new DataBeanFieldModel(this, field);
			fields.add(db);

			return db;
		}
		return null;
	}

	@Override
	public IDataBeanFieldModel addField(Key title) {

		String name = title.name().toLowerCase();

		if (find(name) == null) {

			AbstractFieldModel field = getFieldModel(name);

			if (field != null) {

				field.setTitle(title);

				IDataBeanFieldModel db = new DataBeanFieldModel(this, field);
				fields.add(db);

				return db;
			}
			else {
				logger.error("FIELD '" + name + "' NOT EXIST ON DATABEAN");
			}
		}
		return null;
	}

	@Override
	public IDataBeanFieldModel addField(Key title, boolean required) {

		IDataBeanFieldModel field = addField(title);

		field.getFieldModel().setRequired(required);

		return field;
	}

	@Override
	public void addFields(Key... titles) {

		if (titles != null) {

			for (Key title : titles) {

				String name = title.name().toLowerCase();

				if (find(name) == null) {

					AbstractFieldModel field = getFieldModel(name);

					if (field == null) {
						logger.error("FIELD '" + name + "' NOT EXIST ON DATABEAN");
					}
					else {
						field.setTitle(title);

						IDataBeanFieldModel db = new DataBeanFieldModel(this, field);
						fields.add(db);
					}
				}
			}
		}
	}

	@Override
	public int getCount() {

		return fields.size();
	}

	/**
	 * Nombres de propiedades a editar o visualizar.
	 * 
	 * @return
	 */
	@Override
	public List<IDataBeanFieldModel> getFields() {

		return fields;
	}

	/**
	 * Nombres de propiedades a editar.
	 * 
	 * @param fields
	 */
	public void setPropertyNames(List<IDataBeanFieldModel> fields) {

		this.fields = fields;
	}

	@Override
	public Object getValue(String name) throws LocalizedException {

		try {
			return ReflectionUtils.getPropertyValue(bean, name);
		}
		catch (JavaLegoException e) {
			throw new LocalizedException(e);
		}
	}

	@Override
	public IDataBeanFieldModel addField(FieldModel fieldModel) {

		DataBeanFieldModel db = new DataBeanFieldModel(this, fieldModel);

		fields.add(db);

		return db;
	}

	@Override
	public void addFields(FieldModel... fieldModels) {

		if (fieldModels != null) {

			for (FieldModel fieldModel : fieldModels) {

				DataBeanFieldModel db = new DataBeanFieldModel(this, fieldModel);

				fields.add(db);
			}
		}
	}

	@Override
	public IDataBeanFieldModel addField(FieldModel fieldModel, IActionEditor... actions) {

		IDataBeanFieldModel db = new DataBeanFieldModel(this, fieldModel, actions);

		fields.add(db);

		return db;
	}

	@Override
	public IDataFieldModel find(String name) {

		for (IDataFieldModel fm : fields) {

			if (fm.getName().equals(name)) {
				return fm;
			}
		}
		return null;
	}

	/**
	 * Obtener la propiedad asociada al tipo de campo
	 * 
	 * @param type2
	 * @return
	 * @throws LocalizedException
	 */
	public AbstractFieldModel getFieldModel(String name) {

		AbstractFieldModel property = null;

		Field type;
		try {
			type = ReflectionUtils.getField(bean.getClass(), name);

			property = (AbstractFieldModel) FieldModelFactory.getFieldModel(type.getType());

			property.setName(name);

			return property;
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public IDataBeanFieldModel addField(String name, Key title) {

		if (!fields.contains(name)) {

			AbstractFieldModel field = getFieldModel(name);

			field.setTitle(title);

			IDataBeanFieldModel db = new DataBeanFieldModel(this, field);

			fields.add(db);

			return db;
		}
		return null;
	}

	@Override
	public void addAll() {

		for (Field field : bean.getClass().getDeclaredFields()) {

			if (!field.getName().toLowerCase().equals("serialversionuid")) {
				addField(field.getName(), null, 0, 0);
			}
		}

	}

	@Override
	public List<LocalizedException> validate(Object value) {
		return null;
	}

	@Override
	public void setBean(T bean) {
		this.bean = bean;
	}

	/**
	 * Añadir una lista de campos del bean como campos de edición.
	 * 
	 * @param list
	 * @return
	 */
	public IItemEditor add(Collection<FieldModel> list) {

		for (FieldModel item : list) {
			addField(item);
		}
		return this;
	}
}
