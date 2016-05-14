package com.javalego.jfreechart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Gráfico de barras. Configuración
 * @author ROBERTO RANZ
 */
public class BarChart extends BasicChart {

	/**
	 * Datos
	 */
	private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
	/**
	 * Orientación de los elementos del gráfico.
	 */
	protected PlotOrientation orientation = PlotOrientation.HORIZONTAL; 
	
	public BarChart(String title, String axisLabel, String axisValue, boolean showLegend) {
		super(title, axisLabel, axisValue, showLegend);
	}

	public void setVertical() {
		orientation = PlotOrientation.VERTICAL;
	}
	
	public void setHorizontal() {
		orientation = PlotOrientation.HORIZONTAL;
	}
	
	public boolean isVertical() {
		return orientation == PlotOrientation.VERTICAL;
	}
	
	public boolean isHorizontal() {
		return orientation == PlotOrientation.HORIZONTAL;
	}
	


	/**
	 * Crear panel
	 * @return
	 */
	@Override
	public ChartPanel getChartPanel() {

		ChartPanel chartPanel = new ChartPanel(loadChart(), false);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		return chartPanel;
	}

	/**
	 * Obtener el gráfico.
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Override
	public JFreeChart loadChart() {
		
		freeChart = ChartFactory.createBarChart(
				title, axisLabel, axisValue, dataset, orientation,
				showLegend,
				true, // tooltips?
				false // URLs?
				);
		
		CategoryPlot plot = (CategoryPlot)freeChart.getPlot();
    plot.setForegroundAlpha(0.7F);
    
    // Añade un margen en la parte superior. Muy utilizado si queremos incluir valores en cada item.
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setUpperMargin(0.15);    
    
    CategoryItemRenderer irenderer = plot.getRenderer();
    irenderer.setItemLabelGenerator(new LabelGenerator(1.0));
    irenderer.setItemLabelsVisible(true);
    
    // Cambiar color por defecto.
  	BarRenderer renderer = (BarRenderer) plot.getRenderer();
  	//renderer.setItemMargin(5.0);
  	GradientPaint gp0 = new GradientPaint(
				1.0f, 1.0f, Color.gray,
				1.0f, 1.0f, Color.blue //new Color(0, 0, 64)
				);
		renderer.setSeriesPaint(0, gp0);

		// Rotar la etiqueta.
//	renderer.setDrawBarOutline(false);
//	CategoryAxis domainAxis = plot.getDomainAxis();
//	domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
		return freeChart;
	}
	
	/**
	 * Añadir un valor
	 * @param value
	 * @param rowName
	 * @param columnName
	 */
	public void addValue(double value, String rowName, String columnName) {
		dataset.addValue(value, rowName, columnName);
	}

	/**
	 * Añadir un valor
	 * @param value
	 * @param rowName
	 * @param columnName
	 */
	public void addValue(Number value, String rowName, String columnName) {
		dataset.addValue(value, rowName, columnName);
	}
	
	public DefaultCategoryDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataset = dataset;
	}
	
	/**
	* A custom label generator.
	*/
	static public class LabelGenerator extends AbstractCategoryItemLabelGenerator implements CategoryItemLabelGenerator {
		
		/** The threshold. */
		//private double threshold;
		private static final long serialVersionUID = -2245137284166590805L;

		/**
		* Creates a new generator that only displays labels that are greater
		* than or equal to the threshold value.
		*
		* @param threshold the threshold value.
		*/
		public LabelGenerator(double threshold) {
			super("", NumberFormat.getInstance());
			//this.threshold = threshold;
		}

		/**
		* Generates a label for the specified item. The label is typically a
		* formatted version of the data value, but any text can be used.
		*
		* @param dataset the dataset (<code>null</code> not permitted).
		* @param series the series index (zero-based).
		* @param category the category index (zero-based).
		*
		* @return the label (possibly <code>null</code>).
		*/
		@Override
		public String generateLabel(CategoryDataset dataset, int series, int category) {
			String result = null;
			Number value = dataset.getValue(series, category);
			if (value != null) {
				//double v = value.doubleValue();
				//if (v > this.threshold) {
					result = value.toString(); // could apply formatting here
				//}
			}
			return result;
			}
		}
	}
