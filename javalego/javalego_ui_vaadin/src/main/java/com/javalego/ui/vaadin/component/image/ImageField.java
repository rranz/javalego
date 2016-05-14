package com.javalego.ui.vaadin.component.image;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.component.util.ResourceUtils;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;

/**
 * Campo que muestra una imagen recuperada de un campo blob
 * 
 * @author ROBERTO RANZ
 */
public class ImageField extends BaseImageField implements Field<byte[]> {

	private static final Logger logger = Logger.getLogger(ImageField.class);

	// public static final String DEFAULT_PICTURE =
	// "icons_editor/ic_action_picture.png";

	private static final long serialVersionUID = 1L;

	private Embedded embedded;

	private Button btnRemove;

	private Property<byte[]> datasource;

	private boolean required;

	private String requiredMessage;

	private int tabIndex;

	private boolean invalidValueAllowed;

	private boolean buffered;

	private boolean committed;

	/**
	 * Establecer el modelo del campo
	 * 
	 * @param model
	 * @param editorFactory
	 */
	public void setFieldModel(ImageFieldModel model, boolean readOnly) {

		this.model = model;

		try {
			setEmbedded();
			if (!readOnly) {
				addOptions();
			}
			setImmediate(true);

		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR IMAGE FIELD", e);
		}
	}

	/**
	 * Añadir lista de opciones: descargar imagen, eliminar.
	 */
	private void addOptions() {

		includeUpload();

		addBtnRemove();
	}

	/**
	 * Añadir el botón de eliminar.
	 */
	private void addBtnRemove() {

		if (!model.isReadOnly()) {

			btnRemove = new ButtonExt(LocaleEditor.REMOVE);
			btnRemove.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 4928129556924327292L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						remove();
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}
			});
			try {
				btnRemove.setVisible(getStream() != null);
			}
			catch (Exception e) {
				logger.error("ERROR ADD REMOVE BUTTON IMAGE FIELD.", e);
			}
			toolbar.addComponent(btnRemove);
		}
	}

	/**
	 * Aplicar imagen stream al componente principal del Layout.
	 * 
	 * @throws Exception
	 */
	private void setEmbedded() throws LocalizedException {

		byte[] stream = getStream();

		if (embedded == null) {

			if (stream == null)
				embedded = ResourceIconsVaadin.getCurrent().getComponent(IconEditor.IC_ACTION_PICTURE); // new
																											// Embedded(null,
																											// ResourceUtils.getStreamResourcePng(IOUtils.toByteArray(getClass().getClassLoader().getResource(DEFAULT_PICTURE))));
			else
				embedded = new Embedded(null, ResourceUtils.getStreamResourcePng(stream));

			embedded.setImmediate(true);

			addComponent(embedded, 0);
			embedded.markAsDirty();

		}
		else {
			embedded.setSource(stream == null ? ResourceIconsVaadin.getCurrent().getResource(IconEditor.IC_ACTION_PICTURE) : ResourceUtils.getStreamResourcePng(stream));
			embedded.markAsDirty();
		}

		if (btnRemove != null) {
			btnRemove.setVisible(stream != null);
		}

	}

	/**
	 * Obtener una nuevo documento o imagen.
	 */
	@Override
	protected void remove() throws LocalizedException {

		super.remove();
		setEmbedded();
	}

	@Override
	public void succeeded(byte[] data, String fileName) throws LocalizedException {

		super.succeeded(data, fileName);
		setEmbedded();
	}

	@Override
	public boolean isInvalidCommitted() {
		return committed;
	}

	@Override
	public void setInvalidCommitted(boolean isCommitted) {
		committed = isCommitted;
	}

	@Override
	public void commit() throws SourceException, InvalidValueException {
		if (datasource != null) {
			datasource.setValue(stream);
		}
	}

	@Override
	public void discard() throws SourceException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setBuffered(boolean buffered) {
		this.buffered = buffered;
	}

	@Override
	public boolean isBuffered() {
		return buffered;
	}

	@Override
	public boolean isModified() {
		return true;
	}

	@Override
	public void addValidator(Validator validator) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeValidator(Validator validator) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeAllValidators() {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<Validator> getValidators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void validate() throws InvalidValueException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isInvalidAllowed() {
		return invalidValueAllowed;
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {
		this.invalidValueAllowed = invalidValueAllowed;
	}

	@Override
	public byte[] getValue() {
		return stream;
	}

	@Override
	public void setValue(byte[] newValue) throws ReadOnlyException {
		stream = newValue;
	}

	@Override
	public Class<byte[]> getType() {
		return byte[].class;
	}

	@Override
	public void addValueChangeListener(ValueChangeListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addListener(ValueChangeListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeValueChangeListener(ValueChangeListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeListener(ValueChangeListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setPropertyDataSource(Property newDataSource) {

		datasource = newDataSource;

		if (datasource != null) {
			stream = (byte[]) datasource.getValue();

			if (stream != null) {
				try {
					setEmbedded();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Property getPropertyDataSource() {
		if (datasource == null) {
			datasource = new ObjectProperty(stream, byte[].class);
		}
		return datasource;
	}

	@Override
	public int getTabIndex() {
		return tabIndex;
	}

	@Override
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public void setRequiredError(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	@Override
	public String getRequiredError() {
		return requiredMessage;
	}

}
