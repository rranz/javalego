package com.javalego.ui.mvp.editor.layout;

import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.IEditorModel;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorView.LayoutEditorViewListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Definición de la distribución de campos de un layout basada en agrupaciones.
 * 
 * <pre>
 * LayoutEditorPresenter lp = new LayoutEditorPresenter(new LayoutEditorModel(), new LayoutEditorView());
 * lp.addChildren(&quot;Datos generales&quot;, CssColors.ORANGE, model, &quot;nombre&quot;, &quot;razon_social&quot;, &quot;cif&quot;);
 * lp.addChildren(&quot;Datos adicionales&quot;, CssColors.NAVY, model, &quot;fecha_creacion&quot;, &quot;localidad&quot;, &quot;representante&quot;);
 * </pre>
 * 
 * @author ROBERTO RANZ
 */
public class LayoutEditorPresenter implements LayoutEditorViewListener, IPresenter {

	private static final Logger logger = Logger.getLogger(LayoutEditorPresenter.class);

	private ILayoutEditorModel model;

	private ILayoutEditorView view;

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param model
	 * @param view
	 */
	public LayoutEditorPresenter(ILayoutEditorModel model, ILayoutEditorView view) {
		this.model = model;
		this.view = view;
		view.setListener(this);
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public ILayoutEditorView getView() {
		return view;
	}

	public ILayoutEditorModel getModel() {
		return model;
	}

	@Override
	public List<IItemEditor> getDataEditor() {
		if (model == null) {
			logger.error("THERE IS NO MODEL");
			return null;
		}
		else {
			return model.getData();
		}
	}

	@Override
	public List<ILayoutEditorModel> getChildren() {
		if (model == null) {
			logger.error("THERE IS NO MODEL");
			return null;
		}
		else {
			return model.getChildren();
		}
	}

	/**
	 * Añadir layout hijo definiendo su configuración y lista de campos del
	 * editor incluidos.
	 * 
	 * @param title
	 * @param color
	 * @param model
	 * @param fieldNames
	 */
	public void addChildren(Key title, Colors color, IEditorModel model, String... fieldNames) {
		addChildren(title, null, color, model, fieldNames);
	}

	/**
	 * Añadir layout hijo definiendo su configuración y lista de campos del
	 * editor incluidos.
	 * 
	 * @param title
	 * @param model
	 * @param fieldNames
	 */
	public void addChildren(Key title, IEditorModel model, String... fieldNames) {
		addChildren(title, null, null, model, fieldNames);
	}

	/**
	 * Añadir layout hijo definiendo su configuración y lista de campos del
	 * editor incluidos.
	 * 
	 * @param title
	 * @param icon
	 * @param color
	 * @param model
	 * @param fieldNames
	 */
	public void addChildren(Key title, Icon icon, Colors color, IEditorModel model, String... fieldNames) {

		getChildren().add(new LayoutEditorModel(title, icon, color, model, fieldNames));
	}

}
