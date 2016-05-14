package com.javalego.jfreechart;

import java.awt.Dimension;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Gráfico de red o Spider.
 * 
 * @author ROBERTO RANZ
 */
public class SpiderChart extends BasicChart {

	protected DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	public SpiderChart(String title, String axisLabel, String axisValue, boolean showLegend) {
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

		ChartPanel chartPanel = new ChartPanel(freeChart, false);

		chartPanel.setPreferredSize(new Dimension(500, 270));

		return chartPanel;
	}

	/**
	 * Crear freeChart
	 */
	@Override
	public JFreeChart loadChart() throws Exception {
		
		SpiderWebPlot spiderwebplot = new SpiderWebPlot(dataset);
		spiderwebplot.setStartAngle(54D);
		spiderwebplot.setWebFilled(true);

		spiderwebplot.setInteriorGap(0.40000000000000002D);
		spiderwebplot.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		freeChart = new JFreeChart(title, TextTitle.DEFAULT_FONT, spiderwebplot, false);
		// LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		// legendtitle.setPosition(RectangleEdge.BOTTOM);
		// jfreechart.addSubtitle(legendtitle);
		return freeChart;
	}

	public DefaultCategoryDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataset = dataset;
	}

	
	public static void main(String args[]) {

		SpiderChart c = new SpiderChart("Título", "Labels", "Values", false);
		c.setShowLabelValue(true);
		c.dataset.addValue(100.0, "Serie", "S1");
		c.dataset.addValue(120.0, "Serie", "S2");
		c.dataset.addValue(150.0, "Serie", "S3");
		c.dataset.addValue(200.0, "Serie", "S4");
		c.dataset.addValue(150.0, "Serie", "S5");

	}

}
