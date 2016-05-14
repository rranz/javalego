package com.javalego.jfreechart;

import java.awt.Dimension;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * Gráfico de tarta.
 * 
 * @author ROBERTO RANZ
 */
public class PieChart extends BasicChart {

	protected DefaultPieDataset dataset = new DefaultPieDataset();

	public PieChart(String title, String axisLabel, String axisValue, boolean showLegend) {
		super(title, axisLabel, axisValue, showLegend);
	}

	/**
	 * Crear panel
	 * 
	 * @return
	 */
	@Override
	public ChartPanel getChartPanel() throws Exception {

		loadChart();

//		try {
//			saveToFile("h:/img.jpg", 700, 460, 500);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ChartPanel chartPanel = new ChartPanel(freeChart, false);

		chartPanel.setPreferredSize(new Dimension(500, 270));

		return chartPanel;
	}

	/**
	 * Crear freeChart
	 */
	@Override
	public JFreeChart loadChart() throws Exception {
		
		freeChart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		if (!isShowLegend())
			freeChart.removeLegend();

		PiePlot3D plot = (PiePlot3D) freeChart.getPlot();

		// Formador del porcentaje con 2 decimales.
		NumberFormat f = NumberFormat.getPercentInstance();
		f.setMinimumFractionDigits(2);

		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(getFormatLabel(), NumberFormat.getNumberInstance(), f)); // t.,.2

		plot.setStartAngle(290D);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.7F);
		plot.setNoDataMessage("Sin datos");
		return freeChart;
	}

	public DefaultPieDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultPieDataset dataset) {
		this.dataset = dataset;
	}

	public static void main(String args[]) {

		PieChart c = new PieChart("Título", "Labels", "Values", false);
		c.setShowLabelValue(true);
		c.dataset.setValue("S1", 1);
		c.dataset.setValue("S2", 8);
		c.dataset.setValue("S3", 4);
		c.dataset.setValue("S4", 1.4);
		c.dataset.setValue("S5", 3);
		c.dataset.setValue("S6", 1);

	}

}
