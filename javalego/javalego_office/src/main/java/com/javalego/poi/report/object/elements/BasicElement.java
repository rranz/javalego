package com.javalego.poi.report.object.elements;

import java.io.Serializable;

import com.javalego.poi.report.ReportVisualStyle;
import com.javalego.poi.report.elements.IReportItem;
import com.javalego.poi.report.object.ReportObject;

/**
 * Clase básico de elemento del informe objectReport.
 * @author ROBERTO RANZ
 *
 */
public abstract class BasicElement implements Serializable, IReportItem {
	
	private static final long serialVersionUID = -6924607819174082050L;

	protected String title;
	
	protected String description;
	
	protected ReportVisualStyle style;
	
	protected ReportObject report;
	
	public BasicElement(ReportObject report) {
		this.report = report;
		style = new ReportVisualStyle(report.getExcel());
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReportVisualStyle getStyle() {
		return style;
	}

	public void setStyle(ReportVisualStyle style) {
		if (style == null && report != null)
			style = new ReportVisualStyle(report.getExcel());
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ReportObject getReport() {
		return report;
	}

	public void setReport(ReportObject report) {
		this.report = report;
	}

}
