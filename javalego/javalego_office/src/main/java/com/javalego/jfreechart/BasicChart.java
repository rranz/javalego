package com.javalego.jfreechart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * Datos básicos de un gráfico.
 * @author ROBERTO RANZ
 */
public abstract class BasicChart {

	protected String title;
	
	protected boolean showLegend = true;
	
	protected String axisLabel;
	
	protected String axisValue;

	/**
	 * Incluir el porcentaje en el label de cada sección del pie.
	 */
	protected boolean showLabelPercent = true;
	
	/**
	 * Incluir el valor en el label de cada sección del pie.
	 */
	protected boolean showLabelValue = false;
	
	/**
	 * Formateo del valor de las etiquetas de cada sección del pie.
	 * Utilizar {0} = Título {1} = valor {2} = porcentaje.
	 */
	protected String customFormatLabel;
	
	/**
	 * Gráfico.
	 */
	protected JFreeChart freeChart;
	
	public BasicChart(String title, String axisLabel, String axisValue, boolean showLegend) {
		this.title = title;
		this.axisLabel = axisLabel;
		this.axisValue = axisValue;
		this.showLegend = showLegend;
	}

	public String getAxisLabel() {
		return axisLabel;
	}

	/**
	 * Cargar gráfico.
	 * @return
	 * @throws Exception
	 */
	public abstract JFreeChart loadChart() throws Exception;
	
	public void setAxisLabel(String axisLabel) {
		this.axisLabel = axisLabel;
	}

	public String getAxisValue() {
		return axisValue;
	}

	public void setAxisValue(String axisValue) {
		this.axisValue = axisValue;
	}

	public abstract ChartPanel getChartPanel() throws Exception;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isShowLegend() {
		return showLegend;
	}

	public void setShowLegend(boolean showLegend) {
		this.showLegend = showLegend;
	}

	/**
	 * Pegar la imagen del gráfico al clipboard
	 * @return
	 */
	public static ImageIcon getImageIcon(JFreeChart chart, int width, int height) throws Exception {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsJPEG(out, chart, width, height);
		return new ImageIcon(out.toByteArray());
	}

	/**
	 * Pegar la imagen del gráfico al clipboard
	 * @return
	 */
	public ImageIcon getImageIcon(int width, int height) throws Exception {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsJPEG(out, freeChart, width, height);
		return new ImageIcon(out.toByteArray());
	}


	public boolean isShowLabelPercent() {
		return showLabelPercent;
	}

	public void setShowLabelPercent(boolean showLabelPercent) {
		this.showLabelPercent = showLabelPercent;
	}

	public boolean isShowLabelValue() {
		return showLabelValue;
	}

	public void setShowLabelValue(boolean showLabelValue) {
		this.showLabelValue = showLabelValue;
	}

	/**
	 * Formato de visualización de la etiqueta de cada sección del gráfico.
	 * @return
	 */
	public String getFormatLabel() {
		
		// Etiqueta por cada sección del pie.
		String label = "{0}";
		
		if (customFormatLabel != null)
			label = customFormatLabel;
		
		if (showLabelValue && showLabelPercent)
			label += " = {1}  ({2})";
			
		else if (showLabelValue)
			label += " = {1}";
		
		else if (showLabelPercent)
			label += " = {2}";

		
		return label;
	}

	public String getCustomFormatLabel() {
		return customFormatLabel;
	}

	public void setCustomFormatLabel(String customFormatLabel) {
		this.customFormatLabel = customFormatLabel;
	}

	public JFreeChart getFreeChart() {
		return freeChart;
	}

	public JFreeChart getChart() {
		return freeChart;
	}

	/**
	 * Grabar gráfico en un archivo
	 * 
	 * @param aFileName
	 * @param width
	 * @param height
	 * @param quality
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveToFile(String aFileName, int width, int height, double quality) throws FileNotFoundException, IOException {

		ChartUtilities.saveChartAsPNG(new File(aFileName), freeChart, width, height, null, true, 1);
		
//		System.out.println("1" + aFileName + " " + width + " " + height + " " + quality);
//		BufferedImage img = draw(width, height);
//
//		System.out.println("11");
//		FileOutputStream fos = new FileOutputStream(aFileName);
//
//		System.out.println("12");
//		JPEGImageEncoder encoder2 =	JPEGCodec.createJPEGEncoder(fos);
//
//		System.out.println("13");
//		JPEGEncodeParam param2 = encoder2.getDefaultJPEGEncodeParam(img);
//
//		System.out.println("14");
//		param2.setQuality((float) quality, true);
//
//		System.out.println("15");
//		encoder2.encode(img, param2);
//
//		System.out.println("16");
//		fos.close();
	}

	/**
	 * Dibujar el gráfico antes de grabarlo en el archivo.
	 * @param width
	 * @param height
	 * @return
	 */
//	private BufferedImage draw(int width, int height) {
//
//		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		Graphics2D g2 = img.createGraphics();
//
//		Rectangle2D r = new Rectangle2D.Double(0, 0, width, height);	
//			freeChart.draw(g2, r);
//
//		g2.dispose();
//		return img;
//	}

	/**
	 * Obtiene un array de bytes del gráfico generado.
	 * @return
	 * @throws Excpetion
	 */
	public byte[] getByteArray() throws Exception {
		
		if (freeChart == null)
			loadChart();
		
		if (freeChart != null)
			return GraphicUtils.getByteArray(freeChart);
		else
			return null;
	}

}
