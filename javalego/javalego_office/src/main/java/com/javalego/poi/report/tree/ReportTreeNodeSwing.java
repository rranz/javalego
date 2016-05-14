package com.javalego.poi.report.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import com.javalego.poi.report.tree.IReportTreeNode;

/**
 * Node adaptado a la generación de informes donde se le incluyen propiedades y métodos
 * como: indexRow, totales, etc. 
 * Cada nodo tendrá que gestionar la generación de celdas para su hijos y los registros
 * detalle de otras tablas relacionadas.
 * @author ROBERTO RANZ
 */
public class ReportTreeNodeSwing extends DefaultMutableTreeNode implements IReportTreeNode {

	private static final long serialVersionUID = 6412251544077231460L;

	protected int columnIndex;
	
	protected int rowIndex;
	
	/**
	 * Lista de objetos que representan la información detalle del nodo.
	 * El sistema utilizara el tipo de letra courier proporcional para mostrar en una línea los campos de cada registro.
	 */
	protected Object[] detailObjects;
	
  public ReportTreeNodeSwing(Object userObject) {
  	super(userObject, true);
  }

  public ReportTreeNodeSwing(Object userObject, String[] detailObjects) {
  	super(userObject, true);
  	this.detailObjects = detailObjects;
  }

  @Override
	public String getTitle() {
  	return getUserObject().toString();
  }
  
	public int getColumnIndex() {
		return columnIndex;
	}

	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public Object[] getDetailObjects() {
		return detailObjects;
	}

	@Override
	public void setDetailObjects(Object[] detailObjects) {
		this.detailObjects = detailObjects;
	}

	@Override
	public IReportTreeNode getParentNode() {

		return (IReportTreeNode)getParent();
	}

	@Override
	public IReportTreeNode getChildNodeAt(int index) {
		
		return (IReportTreeNode)getChildAt(index);
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
