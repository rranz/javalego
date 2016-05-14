package com.javalego.store.model;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.store.items.Type;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.editor.IBaseEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;

/**
 * Obtención de datos del modelo MVP
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ModelService {

	/**
	 * Modelo de campos del objeto Producto.
	 * @param type 
	 * @return
	 * @throws LocalizedException 
	 */
	Collection<FieldModel> getProductFields(Type type) throws LocalizedException;

	/**
	 * Modelo de campos del objeto Proyecto.
	 * @return
	 * @throws LocalizedException 
	 */
	Collection<FieldModel> getProjectFields(boolean readOnly) throws LocalizedException;

	/**
	 * Layout del editor de productos
	 * @return
	 */
	LayoutEditorModel getProductLayoutEditor(IBaseEditorModel model, boolean readOnly);

	/**
	 * Layout del editor de proyectos
	 * @return
	 */
	LayoutEditorModel getProjectLayoutEditor(IBaseEditorModel model, boolean readOnly);

	/**
	 * Modelo de campos del objeto Comentario.
	 * @return
	 */
	Collection<FieldModel> getCommentFields();

	/**
	 * Modelo de campos del objeto ScreenShot o imagen de producto o proyecto.
	 * @return
	 */
	Collection<FieldModel> getScreenShotFields();

	/**
	 * Modelo de campos para Repositorios de código
	 * @return
	 */
	Collection<FieldModel> getRepositoryFields();

	/**
	 * Modelo de campos para noticias
	 * @return
	 */
	Collection<FieldModel> getNewsFields();

	/**
	 * Modelo de campos para licencias
	 * @return
	 */
	Collection<FieldModel> getLicenseFields();

	/**
	 * Modelo de campos para proveedores o tecnologías.
	 * @return
	 */
	Collection<FieldModel> getProviderFields();

	/**
	 * Modelo de campos comunes a todos los items.
	 * @return
	 */
	Collection<FieldModel> getCommonFields();

}
