package com.javalego.ui.vaadin.component;

//import com.gana.constants.IStyleNames;
import com.javalego.util.StringUtils;
import com.vaadin.data.Property;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Extensión TextField para incluir nuevas funcionalidad y formatos visuales.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class TextFieldExt extends TextField {

	private static final long serialVersionUID = 1L;

	private boolean lowerCase;

	private boolean upperCase;

	/**
	 * Constructs an empty <code>TextField</code> with no caption.
	 */
	public TextFieldExt() {
		super();
		initialize();
	}

	private void initialize() {

		setNullRepresentation("");

		// // Seleccionar texto al coger el foco.
		// addListener(new FieldEvents.FocusListener() {
		// @Override
		// public void focus(FocusEvent event) {
		// if (getValue() != null)
		// ((TextField)event.getComponent()).selectAll();
		// }
		// });

		// Style
		addStyleName(ValoTheme.TEXTFIELD_SMALL);

		// Control uppercase y lowercase.
		addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -9151719974150370083L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {

				// No realizar nada si el componente es readOnly ya que
				// tendríamos que poner a false readOnly para poder fijar su
				// valor sin generar un error.
				if (isReadOnly())
					return;

				// Lower y Upper case.
				if (isUpperCase() && getValue() != null) {
					setValue(getValue().toString().toUpperCase());
				}
				else if (isLowerCase() && getValue() != null) {
					setValue(getValue().toString().toLowerCase());
				}

				// Eliminar los espacios en blanco para evitar errores.
				if (getValue() != null) {
					// Poner el valor a null si el texto es igual a "".
					if ("".equals(getValue().toString().trim())) {
						setValue(null);
					}
					else if (getValue() instanceof String) {
						setValue(getValue().toString().trim());
					}
				}
			}
		});

		// addStyleName(IStyleNames.GANA_TEXT_FOCUS"ganatextfocus"); // TODO
	}

	/**
	 * Constructs an empty <code>TextField</code> with given caption.
	 * 
	 * @param caption
	 *            the caption <code>String</code> for the editor.
	 */
	public TextFieldExt(String caption) {
		super(caption);
		initialize();
	}

	/**
	 * Constructs a new <code>TextField</code> that's bound to the specified
	 * <code>Property</code> and has no caption.
	 * 
	 * @param dataSource
	 *            the Property to be edited with this editor.
	 */
	public TextFieldExt(Property<?> dataSource) {
		super(dataSource);
		initialize();
	}

	/**
	 * Constructs a new <code>TextField</code> that's bound to the specified
	 * <code>Property</code> and has the given caption <code>String</code>.
	 * 
	 * @param caption
	 *            the caption <code>String</code> for the editor.
	 * @param dataSource
	 *            the Property to be edited with this editor.
	 */
	public TextFieldExt(String caption, Property<?> dataSource) {
		super(caption, dataSource);
		initialize();
	}

	/**
	 * Constructs a new <code>TextField</code> with the given caption and
	 * initial text contents. The editor constructed this way will not be bound
	 * to a Property unless
	 * {@link com.vaadin.data.Property.Viewer#setPropertyDataSource(Property)}
	 * is called to bind it.
	 * 
	 * @param caption
	 *            the caption <code>String</code> for the editor.
	 * @param text
	 *            the initial text content of the editor.
	 */
	public TextFieldExt(String caption, String value) {
		super(caption, value);
		initialize();
	}

	public boolean isLowerCase() {
		return lowerCase;
	}

	public void setLowerCase(boolean lowerCase) {
		this.lowerCase = lowerCase;
		// addStyleName(IStyleNames.LOWERCASE);
	}

	public boolean isUpperCase() {
		return upperCase;
	}

	public void setUpperCase(boolean upperCase) {
		this.upperCase = upperCase;
		// addStyleName(IStyleNames.UPPERCASE);
	}

	/**
	 * Obtener el valor en formato String
	 * 
	 * @return
	 */
	public String getText() {
		return StringUtils.toString(getValue());
	}
}
