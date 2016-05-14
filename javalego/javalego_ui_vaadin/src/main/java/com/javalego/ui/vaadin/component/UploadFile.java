package com.javalego.ui.vaadin.component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.component.util.WindowUtils;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Upload;

/**
 * Componente para subir archivos al servidor.
 * 
 * @author ROBERTO RANZ
 */
public class UploadFile extends CustomComponent implements Upload.SucceededListener, Upload.FailedListener, Upload.Receiver {

	private static final long serialVersionUID = 1L;

	private HorizontalLayout root; // Root element for contained components.
	// Panel imagePanel; // Panel that contains the uploaded image.
	// File file; // File to write to.

	// private Window owner;

	private ByteArrayOutputStream data;

	public UploadFile() {

		root = new HorizontalLayout(); // ("Buscar");
		setCompositionRoot(root);
		// Create the Upload component.
		final Upload upload = new Upload("Nombre de archivo", this);
		// Use a custom button caption instead of plain "Upload".
		upload.setButtonCaption("Subir archivo");

		// Listen for events regarding the success of upload.
		upload.addSucceededListener((Upload.SucceededListener) this);
		upload.addFailedListener((Upload.FailedListener) this);
		// upload.setDescription();
		root.addComponent(upload);

		Button btn = new Button("window");
		btn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -5397962977938684743L;

			@Override
			public void buttonClick(ClickEvent event) {
				UploadFile.showWindow();

			}
		});
		root.addComponent(btn);
		// root.addComponent(new Label("Click 'Busar' para " +
		// "seleccionar un archivo y click 'Subir'."));
		// Create a panel for displaying the uploaded image.
		// imagePanel = new Panel("Uploaded image");
		// imagePanel.addComponent(new Label("No image uploaded yet"));
		// root.addComponent(imagePanel);
	}

	// Callback method to begin receiving the upload.
	@Override
	public OutputStream receiveUpload(String filename, String MIMEType) {

		data = null; // Output stream to write to

		// String path = getApplication().getContext().getBaseDirectory() +
		// "/tmp/uploads/" + filename;

		// path = path.replace("%", " ");

		// System.out.println(path);

		// file = new File(path);
		try {
			// Open the file for writing.
			data = new ByteArrayOutputStream();

		}
		catch (final Exception e) {
			MessagesUtil.error(new LocalizedException(e));
			return null;
		}
		return data; // Return the output stream to write to
	}

	// This is called if the upload is finished.
	@Override
	public void uploadSucceeded(Upload.SucceededEvent event) {

		MessagesUtil.information("Archivo " + event.getFilename() + " subido correctamente.");
	}

	// This is called if the upload fails.
	@Override
	public void uploadFailed(Upload.FailedEvent event) {
		// Log the failure on screen.
		// imagePanel.removeAllComponents();
		MessagesUtil.warning("Error al cargar el archivo " + event.getFilename() + " de tipo '" + event.getMIMEType() + "'.");
	}

	@Override
	public ByteArrayOutputStream getData() {
		return data;
	}

	public static void showWindow() {

		UploadFile upload = new UploadFile();

		WindowUtils.addWindow("Cargar archivo", upload);
	}

}
