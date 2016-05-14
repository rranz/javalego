package com.javalego.xls.report.tree;

/**
 * Interface que representa un objeto dentro de una estructura jerárquica que queremos generar en un informe 
 * @see ReportExcel
 * @author ROBERTO RANZ
 */
public interface IReportTreeNode {

	int getChildCount();

	IReportTreeNode getParentNode();

	int getRowIndex();

	IReportTreeNode getChildNodeAt(int index);

	String getTitle();

	Object[] getDetailObjects();

	void setRowIndex(int rowIndex);

	void setDetailObjects(Object[] records);

	Integer getId();

}
