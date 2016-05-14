package com.javalego.ui.vaadin.view.actions;

import java.util.Arrays;
import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleActions;
import com.javalego.office.export.ExportBeans;
import com.javalego.ui.UIContext;
import com.javalego.ui.actions.IActionBeansEditor;
import com.javalego.ui.actions.impl.AbstractAction;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansEditorViewListener;
import com.javalego.ui.vaadin.component.util.ResourceUtils;

/**
 * Ejecutar una acción
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ExportServices extends AbstractAction implements IActionBeansEditor {

	private static final long serialVersionUID = 8888134638536393500L;

	private ExportBeans service;

	private ExportFormat format;
	
	private String reportTitle;

	/**
	 * Constructor
	 * @param reportTitle título a incluir en el informe generado.
	 * @param service
	 * @param format
	 * @param listener
	 */
	public ExportServices(String reportTitle, ExportBeans service, ExportFormat format) {
		this.service = service;
		this.format = format;
		this.reportTitle = reportTitle;
		icon = IconEditor.REPORT;
		setTitle(getFormat(format));
	}

	/**
	 * Constructor
	 * @param service
	 * @param format
	 * @param listener
	 */
	public ExportServices(ExportBeans service, ExportFormat format) {
		this(UIContext.getText(getFormat(format)), service, format);
	}

	private static LocaleActions getFormat(ExportFormat format) {
		return format == ExportFormat.EXCEL ? LocaleActions.EXCEL : format == ExportFormat.XML ? LocaleActions.EXPORT_XML : LocaleActions.EXPORT_JSON;
	}

	public enum ExportFormat {
		XML, JSON, EXCEL
	}

	/**
	 * Generación y visualización del archivo excel de informe.
	 * @param fields 
	 * @param beans 
	 * 
	 * @throws LocalizedException
	 */
	private void excel(String title, Collection<FieldModel> fields, Collection<?> beans) throws LocalizedException {
		
		String fileName = service.excel(title, fields, beans != null ? Arrays.asList(beans.toArray()) : null);

		if (fileName != null) {
			try {
				ResourceUtils.showExcelFileName(fileName, false);
			}
			catch (Exception e) {
				throw new LocalizedException(e);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(BeansEditorViewListener listener) throws LocalizedException {

		if (format == ExportFormat.EXCEL) {
			excel(reportTitle != null ? reportTitle : (listener.getTitle() != null ? UIContext.getText(listener.getTitle()) : UIContext.getText(getTitle())), listener.getFieldModels(), listener.getBeans(null, null));
		}
		else {
			throw new LocalizedException("FORMAT '" + format + " NOT VALID.");
		}
	}

}
