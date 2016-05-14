package com.javalego.poi.report.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;

import com.javalego.poi.report.BasicReport;
import com.javalego.poi.report.ExcelWorkbookXSSF;
import com.javalego.poi.report.ReportVisualStyle;
import com.javalego.util.StringUtils;

/**
 * Informe que genera un informe Excel desde un nodo y sus hijos (DefaultTreeNode)
 * 
 * @author ROBERTO RANZ
 */
public class ReportTree extends BasicReport {

	/**
	 * Nodo inicial desde donde se genera el informe.
	 */
	private final IReportTreeNode root;

	/**
	 * Número niveles de anidación que corresponden al número de columnas impresas.
	 */
	private int levels = 0;

	/**
	 * Definie si colapsan los grupos de nodos.
	 */
	private boolean collapsed;

	/**
	 * Imprimir el primer nodo. (ver cómo se elimina el nodo "Inicio").
	 */
	private boolean writeRootNode = true;

	/**
	 * Imprimir detalle de cada nodo.
	 */
	private boolean writeDetail;

	/**
	 * Título que representa a la información detalle contenida en detailObjects.
	 */
	protected String detailTitle;

	/**
	 * Estilos aplicados en cada nivel de anidación.
	 */
	private final ArrayList<ReportVisualStyle> styles = new ArrayList<ReportVisualStyle>();

	/**
	 * Objetos detalle.
	 */
	private List<?> detailObjects;

	/**
	 * Caso particular para la tecnología Vaadin que hay que retrasar las columnas una posición TODO : hay que investigar esta situación para que no se produzca.
	 */
	private boolean vaadin;

	public ReportTree(IReportTreeNode root) {
		this.root = root;
	}

	/**
	 * Redefinir el método de ejecución del informe adaptándolo a la estructura de árbol requerida.
	 * 
	 * @throws Exception
	 */
	@Override
	public void execute(boolean writeFile) throws Exception {
		rowIndex = -1;
		// setVisibleFields();
		// totalFields.initialize();
		writeHeader();
		// writeHeaderFields();
		// writeDetail();
		// writeTotalFields();
		writeNodes();
		writeFooter();
		configColumns();
		configPage();
		if (writeFile)
			writeFile();
	}

	/**
	 * Configurar columnas hasta 30 niveles.
	 */
	@Override
	protected void configColumns() {

		for (int i = 0; i <= levels; i++)
			excel.setColumnWidthCharacters(i, 4);
	}

	/**
	 * Generar los nodos jerárquicos.
	 */
	protected void writeNodes() throws Exception {

		generateNode(root, writeRootNode);
	}

	/**
	 * Generar la celda del nodo actual y sus hijos (y/o detalle).
	 * 
	 * @param node
	 */
	private void generateNode(IReportTreeNode node, boolean write) throws Exception {

		if (write) {
			addRowIndex();

			int column = getColumnFromParents(node, root);

			if (writeRootNode && vaadin)
				column -= 1;

			if (levels < column)
				levels = column;

			generate(node, rowIndex, column, writeDetail, getStyle(column));

			if (node.getChildCount() > 0)
				addRowIndex();
		}

		if (node.getChildCount() > 0)
			addRowIndex();

		for (int i = 0; i < node.getChildCount(); i++) {
			generateNode(node.getChildNodeAt(i), true);
		}

		if (write && node.getChildCount() > 0)
			addRowIndex();

		// Crear grupo excel para nodo e hijos.
		Sheet sheet = getExcel().getActiveSheet();

		if (node.getRowIndex() < getRowIndex()) {
			sheet.groupRow((short) node.getRowIndex() + 1, (short) getRowIndex());
			if (collapsed)
				sheet.setRowGroupCollapsed((short) node.getRowIndex(), true);
		}
	}

	/**
	 * Obtiene la posición de la columna en la hoja excel teniendo en cuenta el nivel de jerarquía.
	 * 
	 * @param node
	 * @return
	 */
	private int getColumnFromParents(IReportTreeNode node, IReportTreeNode root) {

		int column = 1;

		IReportTreeNode parent = node.getParentNode();
		if (!writeRootNode)
			column = 0;
		while (parent != null) {
			++column;
			parent = parent.getParentNode();
			if (parent == root) {
				++column;
				break;
			}
		}
		return column;
	}

	public static void main(String args[]) {

		ReportTreeNodeSwing node = new ReportTreeNodeSwing("Titulaciones");

		ReportTreeNodeSwing child = new ReportTreeNodeSwing("INGENIEROS");
		node.add(child);

		ReportTreeNodeSwing child2 = new ReportTreeNodeSwing("INGENIEROS 1");
		child.add(child2);

		ReportTreeNodeSwing child3 = new ReportTreeNodeSwing("INGENIEROS 3");
		child.add(child3);

		ReportTree t = new ReportTree(node);
		try {
			t.getHeader().setTitle("Titulaciones Universitarias");
			t.generateFileNameTmp();
			t.execute(true);
			t.show();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Obtener el estilo aplicada al nivel de anidación.
	 * 
	 * @param column
	 * @return
	 */
	private ReportVisualStyle getStyle(int columnIndex) {

		if (columnIndex - 1 < styles.size())
			return styles.get(columnIndex - 1);

		ReportVisualStyle styleCell = new ReportVisualStyle(getExcel());

		// Estilo de celda con respecto al tipo de valor.
		styleCell.setBox(false);
		styleCell.setFontSize(11);
		if (styleCell.getAlignment() < 0)
			styleCell.setAlignment(CellStyle.ALIGN_LEFT);

		short color = -1;
		if (columnIndex == 1)
			color = HSSFColor.LIGHT_BLUE.index;
		else if (columnIndex == 2)
			color = HSSFColor.DARK_BLUE.index;
		else if (columnIndex == 3)
			color = HSSFColor.GREY_80_PERCENT.index;
		else if (columnIndex == 4)
			color = HSSFColor.GREY_50_PERCENT.index;
		else
			color = HSSFColor.GREY_40_PERCENT.index;

		styleCell.setFontBold(true);
		styleCell.setForeColor(color);
		// if (styleCell.getBackgroundColor() < 0)
		// styleCell.setBackgroundColor(HSSFColor.BLUE.index);
		if (styleCell.getFontName() == null)
			styleCell.setFontName(getFontNameDefault());

		styles.add(styleCell);
		return styleCell;
	}

	@Override
	public Object getActualObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDataSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getFieldValue(Object dataObject, String fieldName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeDetail() throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public boolean isWriteRootNode() {
		return writeRootNode;
	}

	public void setWriteRootNode(boolean writeRootNode) {
		this.writeRootNode = writeRootNode;
	}

	public boolean isWriteDetail() {
		return writeDetail;
	}

	public void setWriteDetail(boolean writeDetail) {
		this.writeDetail = writeDetail;
	}

	public String getDetailTitle() {
		return detailTitle;
	}

	public void setDetailTitle(String detailTitle) {
		this.detailTitle = detailTitle;
	}

	/**
	 * Imprimir
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @throws Exception
	 */
	public void generate(IReportTreeNode node, int rowIndex, int columnIndex, boolean writeDetail, ReportVisualStyle style) {

		ExcelWorkbookXSSF excel = getExcel();
		Cell cell;

		// definir la posición de la columna dentro del informe.
		node.setRowIndex(rowIndex);

		cell = excel.getCell(rowIndex, columnIndex);
		cell.setCellValue(getExcel().createRichTextString(node.getTitle()));
		style.setApplyStyle(cell);

		// Generar registros detalle.
		if (writeDetail && (detailObjects != null || (node.getDetailObjects() != null && node.getDetailObjects().length > 0))) {
			generateDetail(node, rowIndex, style);
		}

	}

	/**
	 * Generar los registros detalle (courier font).
	 */
	private void generateDetail(IReportTreeNode node, int columnIndex, ReportVisualStyle styleCell) {

		addRowIndex();

		ExcelWorkbookXSSF excel = getExcel();
		Cell cell;

		// Título
		if (!StringUtils.isEmpty(getDetailTitle())) {

			styleCell = new ReportVisualStyle(getExcel());
			addRowIndex();
			cell = excel.getCell(getRowIndex(), columnIndex);
			cell.setCellValue(getExcel().createRichTextString(getDetailTitle()));
			styleCell.setBox(false);
			styleCell.setFontItalic(true);
			styleCell.setFontName("Courier");
			styleCell.setFontSize(9);
			styleCell.setAlignment(CellStyle.ALIGN_LEFT);
			styleCell.setForeColor(HSSFColor.GREY_50_PERCENT.index);
			styleCell.setApplyStyle(cell);
		}
		// Registros
		styleCell = new ReportVisualStyle(getExcel());
		styleCell.setBox(false);
		styleCell.setFontName("Courier");
		styleCell.setFontSize(11);
		styleCell.setAlignment(CellStyle.ALIGN_LEFT);
		short color = HSSFColor.BLACK.index;
		styleCell.setForeColor(color);

		Object[] detail = node.getDetailObjects();

		// Obtener objetos
		if (detail == null && detailObjects != null) {
			detail = fillReportDetailRecords(node, detailObjects, node.getId());
		}

		if (detail != null)
			for (int i = 0; i < detail.length; i++) {
				addRowIndex();
				cell = excel.getCell(getRowIndex(), columnIndex);
				cell.setCellValue(getExcel().createRichTextString(detail[i].toString()));
				styleCell.setApplyStyle(cell);
			}

		addRowIndex();
	}

	public boolean isVaadin() {
		return vaadin;
	}

	public void setVaadin(boolean vaadin) {
		this.vaadin = vaadin;
	}

	/**
	 * Añadir los registros detalle del nodo del el informe.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object[] fillReportDetailRecords(IReportTreeNode node, List objects, Integer id) {

		ArrayList list = null;

		String[] records = null;

		// Buscar los objetos que corresponden a la clave primaria procesada.
		for (int i = 0; i < objects.size(); i++) {

			Object[] values = (Object[]) objects.get(i);

			// Si el objeto corresponde con la clave del registro que estamos
			// procesando, ya que
			// objects contiene todos los registros de la vista detalle.
			if (values[values.length - 1].toString().equals(id.toString())) {

				if (list == null)
					list = new ArrayList();

				list.add(objects.get(i));
			}
		}
		// Obtener un array de string adaptado al máximo valor de los campos.
		if (list != null) {

			// Obtener máximos de cada columna en base a su valor.
			int[] widths = new int[100];
			for (int i = 0; i < list.size(); i++) {

				Object[] values = (Object[]) list.get(i);

				for (int k = 0; k < values.length - 1; k++) {

					int width = values[k] != null ? values[k].toString().length() : 0;

					if (widths[k] < width)
						widths[k] = width;
				}
			}

			// Obtener registros finales cuyos campos están en un String separados con
			// espacios que por ser una
			// letra proporcional se muestran perfectamente encolumnados.
			String record = "";
			records = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {

				Object[] values = (Object[]) list.get(i);

				record = "";

				for (int k = 0; k < values.length - 1; k++) {

					String value = (values[k] == null ? "" : values[k].toString());

					record += value + (k < values.length - 1 ? StringUtils.replicate(" ", widths[k] - value.length()) + "  " : "");
				}
				records[i] = record.trim();
			}

			node.setDetailObjects(records);
		}
		return records;
	}

	public List<?> getDetailObjects() {
		return detailObjects;
	}

	public void setDetailObjects(List<?> detailObjects) {
		this.detailObjects = detailObjects;
	}
}
