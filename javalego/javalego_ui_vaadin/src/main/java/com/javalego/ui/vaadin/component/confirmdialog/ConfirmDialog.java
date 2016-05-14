package com.javalego.ui.vaadin.component.confirmdialog;

import java.io.Serializable;

import com.vaadin.server.JsonPaintTarget;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Diálogo de confirmación.
 * 
 * @author ROBERTO RANZ
 * 
 *         Examples: http://sami.virtuallypreinstalled.com/confirmdialog
 * 
 *         // The quickest way to confirm ConfirmDialog.show(getMainWindow(),
 *         repeat(MESSAGE_1), new ConfirmDialog.Listener() {
 * 
 *         public void onClose(ConfirmDialog dialog) { if (dialog.isConfirmed())
 *         { // Confirmed to continue feedback(dialog.isConfirmed()); } else {
 *         // User did not confirm feedback(dialog.isConfirmed()); } } });
 * 
 *         // Using custom captions. ConfirmDialog.show(getMainWindow(),
 *         "Please Confirm:", "Are you really sure?", "I am", "Not quite", new
 *         ConfirmDialog.Listener() {
 * 
 *         public void onClose(ConfirmDialog dialog) { if (dialog.isConfirmed())
 *         { // Confirmed to continue feedback(dialog.isConfirmed()); } else {
 *         // User did not confirm feedback(dialog.isConfirmed()); } } });
 * 
 * 
 *         // License confirmation. ConfirmDialog.show(getMainWindow(),
 *         "Please Accept the License",
 *         resourceToString("Apache-License-2.0.txt"), "Accept", "Cancel", new
 *         ConfirmDialog.Listener() {
 * 
 *         public void onClose(ConfirmDialog dialog) { if (dialog.isConfirmed())
 *         { // Confirmed to continue feedback(dialog.isConfirmed()); } else {
 *         // User did not confirm feedback(dialog.isConfirmed()); } } });
 * 
 *         // Customization: a black, HTML confirmation dialog. ConfirmDialog d
 *         = ConfirmDialog .show( getMainWindow(), "Please agree with me",
 *         "Actually you <b>have to</b> agree with me.
 *         <ul>
 *         <li>One</li>
 *         <li>Two</li>
 *         </ul>
 *         ", "OK", "No, I dont agree", new ConfirmDialog.Listener() {
 * 
 *         public void onClose(ConfirmDialog dialog) { if (dialog.isConfirmed())
 *         { // Confirmed to continue feedback(dialog.isConfirmed()); } else {
 *         // User did not confirm feedback(dialog.isConfirmed()); } } });
 *         d.setStyleName(Reindeer.WINDOW_BLACK);
 *         d.setContentMode(ConfirmDialog.CONTENT_HTML); d.setHeight("16em");
 *         d.getCancelButton().setStyleName(Reindeer.BUTTON_LINK);
 * 
 * 
 *         // Customization via the factory. This makes all subsequent dialog //
 *         calls to use this application-wide. ConfirmDialog.Factory df = new
 *         DefaultConfirmDialogFactory() { String MY_CAPTION = "Sanos ny";
 *         String MY_MESSAGE = "Et sunka s nii meinannu?"; String MY_OK_CAPTION
 *         = "Kyl maar"; String MY_CANCEL_CAPTION = "Juuei";
 * 
 *         // We change the default language to "turku" and also // allow dialog
 *         to resize.
 * @Override public ConfirmDialog create(String caption, String message, String
 *           okCaption, String cancelCaption) { ConfirmDialog d; d =
 *           super.create(caption == null ? MY_CAPTION : caption, message ==
 *           null ? MY_MESSAGE : message, okCaption == null ? MY_OK_CAPTION :
 *           okCaption, cancelCaption == null ? MY_CANCEL_CAPTION :
 *           cancelCaption); d.setResizable(true);
 *           return d; }
 * 
 *           }; ConfirmDialog.setFactory(df);
 * 
 *           // Now we can confirm the standard way
 *           ConfirmDialog.show(getMainWindow(), new ConfirmDialog.Listener() {
 * 
 *           public void onClose(ConfirmDialog dialog) { if
 *           (dialog.isConfirmed()) { // Confirmed to continue
 *           feedback(dialog.isConfirmed()); } else { // User did not confirm
 *           feedback(dialog.isConfirmed()); } } });
 * 
 */
public class ConfirmDialog extends Window {

	private static final long serialVersionUID = -2363125714643244070L;

	public interface Factory extends Serializable {
		ConfirmDialog create(String windowCaption, String message, String okTitle, String cancelTitle);
	}

	public static final int CONTENT_TEXT_WITH_NEWLINES = -1;
	public static final int CONTENT_DEFAULT = CONTENT_TEXT_WITH_NEWLINES;

	/**
	 * Listener for dialog close events. Implement and register an instance of
	 * this interface to dialog to receive close events.
	 * 
	 * @author Sami Ekblad
	 * 
	 */
	public interface Listener {
		void onClose(ConfirmDialog dialog);
	}

	/**
	 * Default dialog factory.
	 * 
	 */
	private static ConfirmDialog.Factory factoryInstance;

	/**
	 * Get the ConfirmDialog.Factory used to create and configure the dialog.
	 * 
	 * By default the {@link DefaultConfirmDialogFactory} is used.
	 * 
	 * @return
	 */
	public static ConfirmDialog.Factory getFactory() {
		
		if (factoryInstance == null) {
			factoryInstance = new DefaultConfirmDialogFactory();
		}
		return factoryInstance;
	}

	/**
	 * Set the ConfirmDialog.Factory used to create and configure the dialog.
	 * 
	 * By default the {@link DefaultConfirmDialogFactory} is used.
	 * 
	 * @return
	 */
	public static void setFactory(final ConfirmDialog.Factory newFactory) {
		
		factoryInstance = newFactory;
	}

	/**
	 * Show a modal ConfirmDialog in a window.
	 * 
	 * @param parentWindow
	 * @param listener
	 */
	public static ConfirmDialog show(final Listener listener) {
		
		return show(null, null, null, null, listener);
	}

	/**
	 * Show a modal ConfirmDialog in a window.
	 * 
	 * @param parentWindow
	 * @param messageLabel
	 * @param listener
	 * @return
	 */
	public static ConfirmDialog show(final String message, final Listener listener) {
		
		return show(null, message, null, null, listener);
	}

	/**
	 * Show a modal ConfirmDialog in a window.
	 * 
	 * @param parentWindow
	 *          Main level window.
	 * @param windowCaption
	 *          Caption for the confirmation dialog window.
	 * @param message
	 *          Message to display as window content.
	 * @param okCaption
	 *          Caption for the ok button.
	 * @param cancelCaption
	 *          Caption for cancel button.
	 * @param listener
	 *          Listener for dialog result.
	 * @return
	 */
	public static ConfirmDialog show(final String windowCaption, final String message, final String okCaption, final String cancelCaption, final Listener listener) {
		
		ConfirmDialog d = getFactory().create(windowCaption, message, okCaption, cancelCaption);
		d.show(listener, true);
		return d;
	}

	private Listener confirmListener = null;
	private boolean isConfirmed = false;
	private Label messageLabel = null;
	private Button okBtn = null;
	private Button cancelBtn = null;
	private String originalMessageText;
	private ContentMode msgContentMode = ContentMode.TEXT;

	/**
	 * Show confirm dialog.
	 * 
	 * @param listener
	 */
	public final void show(final Listener listener, final boolean modal) {
		confirmListener = listener;
		center();
		setModal(modal);
		UI.getCurrent().addWindow(this);
	}

	/**
	 * Did the user confirm the dialog.
	 * 
	 * @return
	 */
	public final boolean isConfirmed() {
		return isConfirmed;
	}

	public final Listener getListener() {
		return confirmListener;
	}

	protected final void setOkButton(final Button okButton) {
		okBtn = okButton;
	}

	public final Button getOkButton() {
		return okBtn;
	}

	protected final void setCancelButton(final Button cancelButton) {
		cancelBtn = cancelButton;
	}

	public final Button getCancelButton() {
		return cancelBtn;
	}

	protected final void setMessageLabel(final Label message) {
		messageLabel = message;
	}

	public final void setMessage(final String message) {
		originalMessageText = message;
		messageLabel.setValue(msgContentMode == ContentMode.TEXT ? formatDialogMessage(message) : message);
	}

	public final String getMessage() {
		return originalMessageText;
	}

	public final ContentMode getContentMode() {
		return msgContentMode;
	}

	public final void setContentMode(final ContentMode contentMode) {
		msgContentMode = contentMode;
		messageLabel.setContentMode(contentMode);
		messageLabel.setValue(msgContentMode == ContentMode.TEXT ? formatDialogMessage(originalMessageText) : originalMessageText);
	}

	/**
	 * Format the messageLabel by maintaining text only.
	 * 
	 * @param text
	 * @return
	 */
	protected final String formatDialogMessage(final String text) {
		return JsonPaintTarget.escapeXML(text).replaceAll("\n", "<br />");
	}

	/**
	 * Set the isConfirmed state.
	 * 
	 * Note: this should only be called internally by the listeners.
	 * 
	 * @param isConfirmed
	 */
	protected final void setConfirmed(final boolean confirmed) {
		isConfirmed = confirmed;
	}
}
