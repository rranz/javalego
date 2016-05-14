package com.javalego.ui.vaadin.component.util;

import java.io.Serializable;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * <p>
 * <b>DESCRIPTION AND FEATURES</b>
 * </p>
 * <p>
 * MessageBox is a flexible utility class for generating different styles of
 * message boxes. The message box is typically a modal dialog, with an icon on
 * the left side, a message on the right of the icon and some buttons on the
 * bottom of the dialog.
 * </p>
 * <p>
 * The class is designed to be very flexible. E.g. you can define
 * <ul>
 * <li>the window caption</li>
 * <li>different types of icons (see {@link Icon})</li>
 * <li>a simple message as string, optionally formatted with HTML elements</li>
 * <li>a complex message designed with multiple Vaadin components</li>
 * <li>how many buttons are placed on the dialog</li>
 * <li>the caption for the buttons for your specific language</li>
 * <li>the icon of the buttons (see {@link ButtonType})</li>
 * <li>one simple event listener (see {@link EventListener}) for all buttons</li>
 * <li>the buttons alignment (left, centered, right)</li>
 * <li>optionally the window width and height, if required (full automated
 * layout seems not to be possible yet)</li>
 * </ul>
 * There are different simple/complex constructors are available to influence
 * the appearance of the message box.
 * </p>
 * <p>
 * Additionally, you can override the default behavior of the dialog and its
 * components by using the published static member fields:
 * <ul>
 * <li>the size of the dialog using {@code DIALOG_DEFAULT_WIDTH} and
 * {@code DIALOG_DEFAULT_HEIGHT}</li>
 * <li>the button default alignment using {@code BUTTON_DEFAULT_ALIGNMENT}</li>
 * <li>the button default width using {@code BUTTON_DEFAULT_WIDTH}</li>
 * <li>the displayed icons and button icons using the fields starting with
 * {@code RESOURCE_}</li>
 * </ul>
 * The static member fields should be overridden at the start of the
 * application. (Okay, that information should not really surprise).
 * </p>
 * <br>
 * <p>
 * <b>USAGE</b>
 * </p>
 * <p>
 * <code>
 * <pre> MessageBox mb = new MessageBox(getWindow(), 
 * 				"My message ...", 
 * 				MessageBox.Icon.INFO, 
 * 				"Hello World!",  
 * 				new MessageBox.ButtonConfig(ButtonType.OK, "Ok"));
 * mb.show();</pre></code> This example shows a simple message dialog, with
 * "My message ..." as dialog caption, an info icon, "Hello World!" as message
 * and an "Ok" labeled button. The dialog is displayed modally. You can simply
 * add further ButtonConfigs after the "Ok"-button. To receive an event of the
 * pressed button, add an event listener (see {@link EventListener}) as
 * parameter to the show method (see {@link #show(EventListener)} or
 * {@link #show(boolean, EventListener)}). Dialog width and height can be
 * optionally set, if required.
 * </p>
 * <br>
 * <p>
 * <b>LICENSE</b>
 * </p>
 * <p>
 * The licenses are suitable for commercial usage.
 * </p>
 * 
 * <p>
 * Code license: Apache License 2.0
 * </p>
 * 
 * <p>
 * Dialog icons:
 * <ul>
 * <li>Author: Dieter Steinwedel</li>
 * <li>License: Creative Commons Attribution 2.5 License</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Button icons:
 * <ul>
 * <li>Author: Mark James</li>
 * <li>URL: <a
 * href="http://www.famfamfam.com/lab/icons/silk/">http://www.famfamfam
 * .com/lab/icons/silk/</a></li>
 * <li>License: Creative Commons Attribution 2.5 License</li>
 * </ul>
 * </p>
 * 
 * @author Dieter Steinwedel
 * 
 * 
 *         MessageBox mb = new MessageBox(getWindow(), "My message ...",
 *         MessageBox.Icon.INFO, "Hello World!", new
 *         MessageBox.ButtonConfig(ButtonType.OK, "Ok")); mb.show();
 * 
 *         MessageBox mb = new MessageBox(HausOverview.this.getWindow(),
 *         "Are you sure?", MessageBox.Icon.QUESTION,
 *         "Do you really want to continue?", new
 *         MessageBox.ButtonConfig(MessageBox.ButtonType.YES, "Yes"), new
 *         MessageBox.ButtonConfig(MessageBox.ButtonType.NO, "No")); mb.show(new
 *         EventListener() {
 * 
 *         private static final long serialVersionUID = 1L;
 * @Override public void buttonClicked(ButtonType buttonType) {
 *           System.out.println("This button was pressed: " + buttonType); } });
 * 
 * 
 *           MessageBox mb = new MessageBox(getMainWindow(), "Are you sure?",
 *           MessageBox.Icon.QUESTION, "Do you really want to continue?",
 *           MessageBox.getButtonYes(), MessageBox.getButtonNo()); mb.show(new
 *           EventListener() { private static final long serialVersionUID = 1L;
 * @Override public void buttonClicked(ButtonType buttonType) { if (buttonType
 *           == ButtonType.YES) System.out.println("This button was pressed: " +
 *           buttonType); } });
 * 
 *           MessageBox mb = new MessageBox(getWindow(), "My message ...",
 *           MessageBox.Icon.INFO, "Hello World!", new
 *           MessageBox.ButtonConfig(ButtonType.OK, "Ok"));
 *           mb.setWidth("500px"); mb.show();
 * 
 * 
 *           // This is an example for fading in and out the MessageBox using
 *           the VisibilityInterceptor. // The fading effect needs only to be
 *           applied once e.g. on starting the application. // The example
 *           requires the AddOn "Animator". final AnimatorProxy ap = new
 *           AnimatorProxy(); MessageBox.VISIBILITY_INTERCEPTOR = new
 *           VisibilityInterceptor() { private static final long
 *           serialVersionUID = 1L;
 * @Override public boolean show(Window parentWindow, MessageBox
 *           displayedDialog) { ap.animate(displayedDialog,
 *           AnimType.FADE_IN).setDuration(200).setDelay(100); return true; }
 * @Override public boolean close(Window parentWindow, MessageBox
 *           displayedDialog) { ap.animate(displayedDialog,
 *           AnimType.FADE_OUT_REMOVE).setDuration(200).setDelay(100); return
 *           false; // ATTENTION: returns false, because the Animator closes the
 *           window itself after // finishing the fade out }
 * 
 *           };
 * 
 * 
 *           // Somewhere later in the code, when the MessageBox should be
 *           displayed. // The dialog will be faded in and out without changing
 *           any further line of code. MessageBox mb = new
 *           MessageBox(getWindow(), "My message ...", MessageBox.Icon.INFO,
 *           "Hello World!", new MessageBox.ButtonConfig(ButtonType.OK, "Ok"));
 *           mb.show();
 */
public class MessageBox extends Window {

	private static final long serialVersionUID = 1L;

	public static String DIALOG_DEFAULT_WIDTH = "350px";
	public static String DIALOG_DEFAULT_HEIGHT = null;
	public static String BUTTON_DEFAULT_WIDTH = "100px";
	public static Alignment BUTTON_DEFAULT_ALIGNMENT = Alignment.MIDDLE_RIGHT;
	public static String ICON_DEFAULT_SIZE = "48px";

	public static VisibilityInterceptor VISIBILITY_INTERCEPTOR;

	private EventListener listener;

	/**
	 * Defines the possible icon types for the message box.
	 * 
	 * @author Dieter Steinwedel
	 */
	public static enum Icon {
		NONE, QUESTION, INFO, WARN, ERROR
	}

	/**
	 * Defines the possible buttons type for the message box. The button type
	 * defines which icon is displayed on the button. The button type is also
	 * used for the event listener (see {@link EventListener}) to determine
	 * which button is pressed.
	 * 
	 * @author ds
	 */
	public static enum ButtonType {
		OK, ABORT, CANCEL, YES, NO, CLOSE, SAVE, RETRY, IGNORE,
		/**
		 * The message box is not closed on using this value. You may have to
		 * explicitly close the message box.
		 */
		HELP, NONE, CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4, CUSTOM5
	}

	/**
	 * This class defines the appearance and caption of a button displayed
	 * inside the message box.
	 * 
	 * @author Dieter Steinwedel
	 */
	public static class ButtonConfig implements Serializable {

		private static final long serialVersionUID = 1L;

		private Resource optionalResource;
		private ButtonType buttonType;
		private String caption;
		private String width;

		/**
		 * Equals to {@link ButtonConfig(ButtonType, String, String)}, but the
		 * button icon resource is set explicitly.
		 * 
		 * @param optionalResource
		 *            an none default resource, that should be displayed as icon
		 */
		public ButtonConfig(ButtonType buttonType, String caption, String width, Resource optionalResource) {
			super();
			if (buttonType == null) {
				throw new IllegalArgumentException("The field buttonType cannot be null");
			}
			this.optionalResource = optionalResource;
			this.buttonType = buttonType;
			this.caption = caption;
			this.width = width;
		}

		/**
		 * Equals to {@link ButtonConfig(ButtonType, String)}, but the button
		 * width is set explicitly instead of using {@code BUTTON_DEFAULT_WIDTH}
		 * .
		 * 
		 * @param width
		 *            button width
		 */
		public ButtonConfig(ButtonType buttonType, String caption, String width) {
			this(buttonType, caption, width, null);
		}

		/**
		 * Creates an instance of this class for defining the appearance and
		 * caption of a button displayed inside the message box.
		 * 
		 * @param buttonType
		 *            which button type (see {@link ButtonType})
		 * @param caption
		 *            caption of the button
		 */
		public ButtonConfig(ButtonType buttonType, String caption) {
			this(buttonType, caption, BUTTON_DEFAULT_WIDTH);
		}

		public ButtonConfig(ButtonType buttonType, LocaleEditor caption) {
			this(buttonType, UIContext.getText(caption), BUTTON_DEFAULT_WIDTH);
		}

		/**
		 * Returns the optional resource, if set.
		 * 
		 * @return optional resource.
		 */
		public Resource getOptionalResource() {
			return optionalResource;
		}

		/**
		 * Returns the button type.
		 * 
		 * @return button type
		 */
		public ButtonType getButtonType() {
			return buttonType;
		}

		/**
		 * Returns the button caption.
		 * 
		 * @return button caption
		 */
		public String getCaption() {
			return caption;
		}

		/**
		 * Returns the button width.
		 * 
		 * @return button width
		 */
		public String getWidth() {
			return width;
		}

	}

	/**
	 * Intercepts the displaying and closing of the dialog.
	 * 
	 * @author ds
	 */
	public interface VisibilityInterceptor extends Serializable {

		/**
		 * Intercepts the displaying of the dialog.
		 * 
		 * @param parentWindow
		 *            The parent Window for the <code>MessageBox</code>
		 * @param displayedDialog
		 *            The <code>MessageBox</code> instance to be displayed
		 * @return Returns <code>false</code>, if the method implementation
		 *         opens the <code>MessageBox</code> window itself. Otherwise
		 *         returns <code>true</code>.
		 */
		public boolean show(MessageBox displayedDialog);

		/**
		 * Intercepts the closing of the dialog.
		 * 
		 * @param parentWindow
		 *            The parent Window for the <code>MessageBox</code>
		 * @param displayedDialog
		 *            The <code>MessageBox</code> instance to be displayed
		 * @return Returns <code>false</code>, if the method implementation
		 *         closes the <code>MessageBox</code> window itself. Otherwise
		 *         returns <code>true</code>.
		 */
		public boolean close(MessageBox displayedDialog);

	}

	/**
	 * This event listener is triggered when a button of the message box is
	 * pressed.
	 * 
	 * @author ds
	 */
	public interface EventListener extends Serializable {

		/**
		 * The method is triggered when a button of the message box is pressed.
		 * The parameter describes, which configured button was pressed.
		 * 
		 * @param buttonType
		 *            pressed button
		 * @return
		 */
		public boolean buttonClicked(ButtonType buttonType) throws LocalizedException;

	}

	/**
	 * Private implementation for handling the button events.
	 * 
	 * @author ds
	 */
	private class ButtonClickListener implements ClickListener {

		private static final long serialVersionUID = 1L;

		private ButtonType buttonType;

		/**
		 * The constructor.
		 */
		public ButtonClickListener(ButtonType buttonType) {
			this.buttonType = buttonType;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			if (listener != null) {
				try {
					if (listener.buttonClicked(buttonType)) {
						close();
					}
				} catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
			if (!buttonType.equals(ButtonType.HELP)) {
				// close();
			}
		}
	}

	/**
	 * Similar to
	 * {@link #MessageBox(Window, String, Icon, String, Alignment, ButtonConfig...)}
	 * , but the message component is defined explicitly. The component can be
	 * even a composite of a layout manager and manager further Vaadin
	 * components.
	 * 
	 * @param messageComponent
	 *            a Vaadin component
	 */
	public MessageBox(String dialogCaption, Icon dialogIcon, Component messageComponent, Alignment buttonsAlignment, ButtonConfig... buttonConfigs) {

		super();

		setResizable(false);
		setClosable(false);

		setCaption(dialogCaption);

		GridLayout mainLayout = new GridLayout(2, 2);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		mainLayout.setSizeUndefined();
		messageComponent.setSizeUndefined();

		// Add Content
		if (dialogIcon == null || Icon.NONE.equals(dialogIcon)) {

			mainLayout.addComponent(messageComponent, 0, 0, 1, 0);
			mainLayout.setRowExpandRatio(0, 1.0f);
			mainLayout.setColumnExpandRatio(0, 1.0f);
		} else {
			mainLayout.addComponent(messageComponent, 1, 0);

			mainLayout.setRowExpandRatio(0, 1.0f);
			mainLayout.setColumnExpandRatio(1, 1.0f);

//			Label icon = new Label();
//			switch (dialogIcon) {
//			case QUESTION:
//				icon.setIcon(FontAwesome.QUESTION);
//				break;
//			case INFO:
//				icon.setIcon(FontAwesome.INFO);
//				break;
//			case WARN:
//				icon.setIcon(FontAwesome.WARNING);
//				break;
//			case ERROR:
//				icon.setIcon(FontAwesome.STOP);
//				break;
//			default:
//				break;
//			}
//			mainLayout.addComponent(icon, 0, 0);
		}

		// Add Buttons
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.setMargin(true);
		mainLayout.addComponent(buttonLayout, 0, 1, 1, 1);
		mainLayout.setComponentAlignment(buttonLayout, buttonsAlignment);

		for (ButtonConfig buttonConfig : buttonConfigs) {

			ButtonExt button = new ButtonExt(buttonConfig.getCaption());

//			if (buttonConfig.buttonType == ButtonType.YES || buttonConfig.buttonType == ButtonType.OK) {
//				button.blue();
//			}
			
			button.addClickListener(new ButtonClickListener(buttonConfig.getButtonType()));
			buttonLayout.addComponent(button);
		}

		setContent(mainLayout);
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 * @param question
	 * @param message
	 * @param buttonYes
	 * @param buttonNo
	 */
	public MessageBox(String text, Icon question, String message, ButtonConfig buttonYes, ButtonConfig buttonNo) {
		this(text, question, new Label(message, ContentMode.HTML), Alignment.MIDDLE_LEFT, buttonYes, buttonNo);
	}

	// private Color getColor(ButtonType type) {
	//
	// switch (type) {
	// case ABORT:
	// return Color.RED;
	// case CANCEL:
	// return Color.RED;
	// case CLOSE:
	// return Color.GREEN;
	// case HELP:
	// return Color.BLUE;
	// case OK:
	// return Color.GREEN;
	// case YES:
	// return Color.GREEN;
	// case NO:
	// return Color.RED;
	// case SAVE:
	// return Color.ORANGE;
	// case RETRY:
	// return Color.YELLOW;
	// case IGNORE:
	// return Color.RED;
	// default:
	// return Color.GRAY;
	// }
	// }

	/**
	 * Displays the message box in modal style. No listener is used.
	 */
	public void show() {
		show(true, null);
	}

	/**
	 * Displays the message box. No listener is used.
	 * 
	 * @param modal
	 *            switches the message box modal or none-modal
	 */
	public void show(boolean modal) {
		show(modal, null);
	}

	/**
	 * Displays the message box in modal style with triggering the event
	 * listener on pressing a button.
	 * 
	 * @param listener
	 *            trigger the parameterized listener on pressing a button
	 */
	public void show(EventListener listener) {
		show(true, listener);
	}

	/**
	 * Displays the message box with triggering the event listener on pressing a
	 * button.
	 * 
	 * @param modal
	 *            switches the message box modal or none-modal
	 * @param listener
	 *            trigger the parameterized listener on pressing a button
	 */
	public void show(boolean modal, EventListener listener) {
		this.listener = listener;
		setModal(modal);
		if (VISIBILITY_INTERCEPTOR == null || (VISIBILITY_INTERCEPTOR != null && VISIBILITY_INTERCEPTOR.show(this))) {
			UI.getCurrent().addWindow(this);
		}
	}

	/**
	 * Closes the message box. Call this method, if the dialog should be closed
	 * without a button event, e.g. by a timeout. The buttons inside the dialog
	 * close automatically the message box.
	 */
	@Override
	public void close() {
		
		if (VISIBILITY_INTERCEPTOR == null || (VISIBILITY_INTERCEPTOR != null && VISIBILITY_INTERCEPTOR.close(this))) {
			UI.getCurrent().removeWindow(this);
		}
	}

	/**
	 * Obtener un botón Yes
	 * 
	 * @return
	 */
	public static ButtonConfig getButtonYes() {
		
		return new MessageBox.ButtonConfig(MessageBox.ButtonType.YES, LocaleEditor.YES);
	}

	/**
	 * Obtener un botón Close
	 * 
	 * @return
	 */
	public static ButtonConfig getButtonClose() {
		
		return new MessageBox.ButtonConfig(MessageBox.ButtonType.CLOSE, LocaleEditor.EXIT);
	}

	/**
	 * Obtener un botón No
	 * 
	 * @return
	 */
	public static ButtonConfig getButtonNo() {
		
		return new MessageBox.ButtonConfig(MessageBox.ButtonType.NO, LocaleEditor.NO);
	}

	/**
	 * Mostrar un componente con información que deseamos mostrar en un mensaje
	 * de diálogo.
	 * 
	 * @param component
	 */
	public static void information(String title, Component component) {

		MessageBox m = new MessageBox(title, Icon.INFO, component, Alignment.BOTTOM_RIGHT, new MessageBox.ButtonConfig(ButtonType.CLOSE, UIContext.getText(LocaleEditor.EXIT)));
		m.show(true, new EventListener() {
			private static final long serialVersionUID = 1298634606186258527L;

			@Override
			public boolean buttonClicked(ButtonType buttonType) {
				return true;
			}
		});
	}

}
