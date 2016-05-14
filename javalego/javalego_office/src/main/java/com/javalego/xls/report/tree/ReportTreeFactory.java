package com.javalego.xls.report.tree;


/**
 * Factoría de generación de informes de tipo jerárquico.
 * @author ROBERTO RANZ
 */
public class ReportTreeFactory {

	private ReportTreeFactory() {};

	/**
	 * Obtener un informe de tipo árbol basado en la estructura jerárquica de la información contenida en el objeto rootNode.
	 * @param rootNode
	 * @param title
	 * @param writeRootNode
	 * @return
	 */
	static public ReportTree getReport(IReportTreeNode rootNode, String title, boolean writeRootNode) {
		
		ReportTree t = new ReportTree(rootNode);
		t.setWriteRootNode(writeRootNode);
		t.getHeader().setTitle(title);
		t.generateFileNameTmp();
		
		return t;
		
	}
}
