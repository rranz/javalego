package com.javalego.office.export;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.javalego.exception.LocalizedException;
import com.javalego.locale.LocaleContext;
import com.javalego.model.BaseModel;
import com.javalego.xls.report.ReportBeans;

/**
 * Servicios de exportación de datos a varios formatos.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ExportBeans {

	/**
	 * Generar un archivo Excel a partir de una lista de beans y un modelo de campos que formarán la lista de columnas del informe.
	 * @param title
	 * @param fields
	 * @param beans
	 * @throws LocalizedException
	 */
	public String excel(String title, Collection<? extends BaseModel> fields, List<?> beans) throws LocalizedException {
		
		ReportBeans report = new ReportBeans();

		report.generateFileNameTmp();
		report.getHeader().setTitle(title);
		report.setBeans(beans);

		for (BaseModel property : fields) {
			report.addField(property.getName(), LocaleContext.getText(property.getTitle(), Locale.getDefault()));
		}

		try {
			report.execute(true);
			return report.getFileName();
		}
		catch (Exception e) {
			throw new LocalizedException(e);
		}
	}
	
	
}
