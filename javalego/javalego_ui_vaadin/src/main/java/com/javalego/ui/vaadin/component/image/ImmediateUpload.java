package com.javalego.ui.vaadin.component.image;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.component.UploadEvents;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;

/**
 * Descargar un archivo
 */
public class ImmediateUpload extends CssLayout {

	// private Label status = new
	// Label("Por favor, seleccione el archivo que desea subir");

	/**
	 * 
	 */
	private static final long serialVersionUID = 8066544941178986125L;

	private ProgressBar pi = new ProgressBar();

	private MyReceiver receiver = new MyReceiver();

	private HorizontalLayout progressLayout = new HorizontalLayout();

	private Upload upload = new Upload(null, receiver);

	/**
	 * Constructor
	 * 
	 * @param events
	 */
	public ImmediateUpload(final UploadEvents events) {

		//addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		// Slow down the upload
		receiver.setSlow(true);

		// addComponent(status);
		addComponent(upload);
		addComponent(progressLayout);

		// Make uploading start immediately when file is selected
		upload.setImmediate(true);
		upload.setButtonCaption(UIContext.getText(LocaleEditor.SEARCH));

		progressLayout.setSpacing(true);
		progressLayout.setVisible(false);
		progressLayout.addComponent(pi);
		progressLayout.setComponentAlignment(pi, Alignment.MIDDLE_LEFT);

		final Button cancelProcessing = new ButtonExt(LocaleEditor.CANCEL);
		cancelProcessing.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2761687034167375505L;

			@Override
			public void buttonClick(ClickEvent event) {
				upload.interruptUpload();
			}
		});
		cancelProcessing.setStyleName("small");
		progressLayout.addComponent(cancelProcessing);

		/**
		 * =========== Add needed listener for the upload component: start,
		 * progress, finish, success, fail ===========
		 */

		upload.addStartedListener(new Upload.StartedListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7510695953716738069L;

			@Override
			public void uploadStarted(StartedEvent event) {
				// This method gets called immediatedly after upload is started
				upload.setVisible(false);
				progressLayout.setVisible(true);
				pi.setValue(0f);
				//pi.setPollingInterval(500);
				// status.setValue("Cargando archivo \"" + event.getFilename() + "\"");
			}
		});

		upload.addProgressListener(new Upload.ProgressListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1386411051758498153L;

			@Override
			public void updateProgress(long readBytes, long contentLength) {
				// This method gets called several times during the update
				pi.setValue(new Float(readBytes / (float) contentLength));
			}

		});

		upload.addSucceededListener(new Upload.SucceededListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4987959155880754905L;

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				// This method gets called when the upload finished successfully
				// status.setValue("Archivo \"" + event.getFilename() +
				// "\" subido correctamente");
				if (events != null)
					try {
						events.succeeded(receiver.getStream().toByteArray(), event.getFilename());
					} catch (LocalizedException e) {
						MessagesUtil.error(e);
					}

				// ResourceUtils.showStreamResource(receiver.getStream(),
				// getApplication(), Functions.getFileExtension(receiver.fileName));
			}
		});

		upload.addFailedListener(new Upload.FailedListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2312377364123357420L;

			@Override
			public void uploadFailed(FailedEvent event) {
				// This method gets called when the upload failed
				// status.setValue("Subida cancelada");
			}
		});

		upload.addFinishedListener(new Upload.FinishedListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5595278117689656779L;

			@Override
			public void uploadFinished(FinishedEvent event) {
				// This method gets called always when the upload finished,
				// either succeeding or failing
				progressLayout.setVisible(false);
				upload.setVisible(true);
				// upload.setCaption("Seleccionar otro archivo");
			}
		});

	}

	/**
	 * Receptor de información
	 */
	public static class MyReceiver implements Receiver {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7648132629557865827L;
		private String fileName;
		private String mtype;
		private boolean sleep;
		private int total = 0;

		private ByteArrayOutputStream stream;

		@Override
		public OutputStream receiveUpload(String filename, String mimetype) {
			fileName = filename;
			mtype = mimetype;
			stream = new ByteArrayOutputStream() {
				@Override
				public void write(int b) {
					total++;
					if (sleep && total % 10000 == 0) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			return stream;
		}

		public String getFileName() {
			return fileName;
		}

		public String getMimeType() {
			return mtype;
		}

		public void setSlow(boolean value) {
			sleep = value;
		}

		public ByteArrayOutputStream getStream() {
			return stream;
		}

	}

}
