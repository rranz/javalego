package com.javalego.ui.mvp.editor.layout;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.model.AbstractBaseModel;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.impl.DataBeanFieldModel;
import com.javalego.ui.mvp.editor.IBaseEditorModel;

/**
 * Implementación básica de ILayoutEditorModel.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class LayoutEditorModel extends AbstractBaseModel implements ILayoutEditorModel {

	private static final long serialVersionUID = -8597139985929577389L;

	/**
	 * Anidación de layouts.
	 */
	private List<ILayoutEditorModel> children;

	/**
	 * Datos incluidos en el layout
	 */
	private List<IItemEditor> data = new ArrayList<IItemEditor>();

	/**
	 * Icono representativo
	 */
	private Icon icon;

	/**
	 * Color de la cabecera.
	 */
	private Colors color;

	private IBaseEditorModel model;

	private static final Logger logger = Logger.getLogger(LayoutEditorModel.class);

	/**
	 * Constructor
	 * 
	 * @param service
	 * @param fieldNames
	 */
	public LayoutEditorModel(IBaseEditorModel editorModel, String... fieldNames) {
		addDataEditor(editorModel, fieldNames);
	}

	/**
	 * Constructor
	 * 
	 * @param service
	 */
	public LayoutEditorModel(IBaseEditorModel editorModel) {
		addDataEditor(editorModel, new String[] {});
	}

	/**
	 * Constructor
	 * 
	 * @param service
	 * @param fields
	 *            Obtenemos los nombres de los campos a partir de clave
	 *            (Key.name().toLowercase()).
	 */
	public LayoutEditorModel(IBaseEditorModel editorModel, Key... fields) {
		addDataEditor(editorModel, fields);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param service
	 * @param fieldNames
	 */
	public LayoutEditorModel(Key title, IBaseEditorModel editorModel, String... fieldNames) {
		this(editorModel, fieldNames);
		setTitle(title);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param icon
	 * @param service
	 * @param fieldNames
	 */
	public LayoutEditorModel(Key title, Icon icon, IBaseEditorModel editorModel, String... fieldNames) {
		this(title, editorModel, fieldNames);
		this.icon = icon;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param icon
	 * @param service
	 * @param fieldNames
	 */
	public LayoutEditorModel(Key title, Icon icon, Colors color, IBaseEditorModel editorModel, String... fieldNames) {
		this(title, icon, editorModel, fieldNames);
		this.color = color;
	}

	@Override
	public List<ILayoutEditorModel> getChildren() {

		if (children == null) {
			children = new ArrayList<ILayoutEditorModel>();
		}
		return children;
	}

	@Override
	public List<IItemEditor> getData() {
		return data;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	public void setData(List<IItemEditor> data) {
		this.data = data;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	@Override
	public void addDataEditor(IBaseEditorModel editorModel, String[] names) {

		this.model = editorModel;

		if (names != null) {
			for (String name : names) {
				addField(name);
			}
		}

	}

	@Override
	public void addDataEditor(IBaseEditorModel editorModel, Key[] names) {

		this.model = editorModel;

		if (names != null) {
			for (Key name : names) {
				addField(name.name().toLowerCase());
			}
		}
	}

	@Override
	public Colors getColor() {
		return color;
	}

	public void setColor(Colors color) {
		this.color = color;
	}

	@Override
	public void updateBean(IBaseEditorModel editorModel) {

		if (editorModel != null) {

			for (IItemEditor item : this.data) {

				if (item instanceof DataBeanFieldModel) {

					IItemEditor data = editorModel.find(item.getName());

					if (data instanceof DataBeanFieldModel) {
						((DataBeanFieldModel) item).setDataBean(((DataBeanFieldModel) data).getDataBean());
					}
				}
			}

			// Actualizar los layouts hijos de formar recursiva.
			if (children != null) {
				for (ILayoutEditorModel child : children) {
					child.updateBean(editorModel);
				}
			}

		}
	}

	@Override
	public void addChildLayout(ILayoutEditorModel layoutEditorModel) {

		getChildren().add(layoutEditorModel);
	}

	@Override
	public ILayoutEditorModel addChildLayout(Key title, Key... fields) {

		LayoutEditorModel layout = new LayoutEditorModel(model, fields);
		layout.setTitle(title);
		getChildren().add(layout);

		return layout;
	}

	@Override
	public ILayoutEditorModel addChildLayout(Key title, Colors color, Key... fields) {

		LayoutEditorModel layout = new LayoutEditorModel(model, fields);
		layout.setTitle(title);
		layout.setColor(color);
		getChildren().add(layout);

		return layout;
	}

	@Override
	public ILayoutEditorModel addChildLayout(Key title, Colors color, String... fieldnames) {

		LayoutEditorModel layout = new LayoutEditorModel(model, fieldnames);
		layout.setTitle(title);
		layout.setColor(color);
		getChildren().add(layout);

		return layout;
	}

	@Override
	public ILayoutEditorModel addChildLayout(Key title, String... fieldnames) {

		LayoutEditorModel layout = new LayoutEditorModel(model, fieldnames);
		layout.setTitle(title);
		getChildren().add(layout);

		return layout;
	}

	@Override
	public void addField(String fieldname, int index) {

		IItemEditor data = model.find(fieldname);
		if (data != null) {
			if (index > -1) {
				this.data.add(index, data);
			}
			else {
				this.data.add(data);
			}
		}
		else {
			logger.warn("DATA EDITOR '" + name + "' NOT EXIST.");
		}

	}

	@Override
	public void addField(String fieldname) {
		
		addField(fieldname, -1);
	}

}
