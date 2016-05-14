package com.javalego.jfreechart;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.javalego.util.StringUtils;

/**
 * Servicios de obtención de gráficos.
 * 
 * @author ROBERTO RANZ
 */
public class GraphicUtils {

	/**
	 * Crear gráfico de tarta
	 */
	public static PieChart getPieChart(String title, List<?> objects) throws Exception {

		PieChart pie = new PieChart(title, null, null, false);

		for (Object o : objects) {
			Object[] values = (Object[]) o;
			pie.getDataset().setValue(values[0].toString(), StringUtils.toDouble(((Object[]) o)[1]));
		}
		return pie;

	}

	/**
	 * Crear gráfico de tarta
	 */
	public static BarChart getBarChart(String title, List<?> objects) throws Exception {

		BarChart pie = new BarChart(title, null, null, false);

		for (Object o : objects) {
			Object[] values = (Object[]) o;
			pie.getDataset().addValue(StringUtils.toDouble(((Object[]) o)[1]), "Row 1", values[0].toString());
		}
		return pie;

	}

	/**
	 * Crear gráfico Spider.
	 * 
	 * @param title
	 * @param description
	 * @param objects
	 * @return
	 */
	public static SpiderChart getSpiderChart(String title, List<?> objects) {

		SpiderChart c = new SpiderChart(title, "Labels", "Values", false);
		c.setShowLabelValue(true);

		for (Object o : objects) {
			c.getDataset().addValue(StringUtils.toDouble(((Object[]) o)[1]), "Serie", ((Object[]) o)[0].toString());
		}
		return c;
	}

	/**
	 * Obtenemos un array de bytes del gráfico pasado como parámetro.
	 * 
	 * @param chart
	 * @return
	 * @throws Exception
	 */
	public static byte[] getByteArray(JFreeChart chart) throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		ChartUtilities.writeChartAsJPEG(out, chart, 750, 500);
		
		return out.toByteArray();
	}
}
