package com.javalego.jfreechart;

import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

/**
 * Factoría de generación de gráficos Gantt en diversos frameworks de generación de gráficos (JFreeChart, ...)
 * 
 * @author ROBERTO RANZ
 */
public class GanttChart extends BasicChart {

	private Gantt gantt;

	public GanttChart(Gantt gantt, String title, String axisLabel, String axisValue, boolean showLegend) {
		super(title, axisLabel, axisValue, showLegend);
		this.gantt = gantt;
	}

	@Override
	public JFreeChart loadChart() throws Exception {

		freeChart = ChartFactory.createGanttChart(title, // chart title
				axisLabel, // domain axis label
				axisValue, // range axis label
				getDataSet(), // data
				true, // include legend
				true, // tooltips
				false // urls
				);

		// Código para alinear las labels a la izquierda.
		CategoryPlot categoryplot = freeChart.getCategoryPlot();
		categoryplot.setForegroundAlpha(1.0F);
		categoryplot.setRangePannable(true);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		CategoryLabelPositions categorylabelpositions = categoryaxis.getCategoryLabelPositions();
		CategoryLabelPosition categorylabelposition = new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, 0.0D, CategoryLabelWidthType.RANGE, 0.3F);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(categorylabelpositions, categorylabelposition));

		return freeChart;
	}

	/**
	 * Generar DataSet en base a la configuración del objeto Gantt.
	 * 
	 * @return
	 */
	private IntervalCategoryDataset getDataSet() {

		TaskSeriesCollection collection = new TaskSeriesCollection();

		for (Gantt.Serie serie : gantt.getSeries()) {

			TaskSeries series = new TaskSeries(title);
			collection.add(series);

			for (Gantt.Task task : serie.getTasks()) {

				Task t = new Task(task.getTitle(), task.getInit(), task.getEnd());

				if (task.getPercent() > 0.0)
					t.setPercentComplete(task.getPercent() / 100);

				series.add(t);

			}
		}

		return collection;
	}

	@Override
	public ChartPanel getChartPanel() throws Exception {

		ChartPanel chartPanel = new ChartPanel(freeChart, false);

		chartPanel.setPreferredSize(new Dimension(500, 270));

		return chartPanel;
	}

	public ChartPanel getChartPanel(int width, int height) throws Exception {

		ChartPanel chartPanel = new ChartPanel(freeChart, false);

		chartPanel.setPreferredSize(new Dimension(width, height));

		return chartPanel;
	}

	public Gantt getGantt() {
		return gantt;
	}

	public void setGantt(Gantt gantt) {
		this.gantt = gantt;
	}

}
