package com.javalego.jfreechart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.javalego.util.DateUtils;

/**
 * Definición de una vista para generar un gráfico de tipo Gantt. (JFreeChart, JasperReports, ...)
 * @author ROBERTO RANZ
 */
public class Gantt {

	private String title;
	
	private List<Serie> series = new ArrayList<Gantt.Serie>();
	
	public static void main(String args[]) {
		Gantt g = new Gantt();
		Serie s = g.addSerie("Serie 1");
		s.addTask("Tarea 1", new Date(), DateUtils.addDays(new Date(), 10), 80);
		s.addTask("Tarea 2", new Date(), DateUtils.addDays(new Date(), 30));
		s.addTask("Tarea 3", new Date(), DateUtils.addDays(new Date(), 30));
		s.addTask("Tarea 4", new Date(), DateUtils.addDays(new Date(), 30));
		s.addTask("Tarea 5", new Date(), DateUtils.addDays(new Date(), 30));
		s.addTask("Tarea 6", new Date(), DateUtils.addDays(new Date(), 30));
		s.addTask("Tarea 7", DateUtils.addDays(new Date(), 20), DateUtils.addDays(new Date(), 90));
		
		GanttChart gantt = new GanttChart(g, "Title", "Labels", "Values", true);
		try {
			gantt.loadChart();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Serie addSerie(String title) {
		
		Serie serie = new Serie(title);
		series.add(serie);
		return serie;
	}
	
	/**
	 * Serie de vista chart Gantt.
	 * @author ROBERTO RANZ
	 */
	public static class Serie {

		String title;
		
		public Serie(String title) {
			this.title = title;
		}

		public Task addTask(String title, Date init, Date end, double percent) {
			
			Task task = new Task(title, init, end, percent);
			tasks.add(task);
			return task;
		}
		
		public Task addTask(String title, Date init, Date end) {
			
			Task task = new Task(title, init, end, -1);
			tasks.add(task);
			return task;
		}
		
		private List<Task> tasks = new ArrayList<Gantt.Task>();

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<Task> getTasks() {
			return tasks;
		}

		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}
		
	}
	
	/**
	 * Vista de Task de una Seria para un gráfico Gantt.
	 * @author Roberto
	 *
	 */
	public static class Task {
		String title;
		Date init;
		Date end;
		double percent;
		
		public Task(String title, Date init, Date end, double percent) {
			this.title = title;
			this.init = init;
			this.end = end;
			this.percent = percent;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Date getInit() {
			return init;
		}

		public void setInit(Date init) {
			this.init = init;
		}

		public Date getEnd() {
			return end;
		}

		public void setEnd(Date end) {
			this.end = end;
		}

		public double getPercent() {
			return percent;
		}

		public void setPercent(double percent) {
			this.percent = percent;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}
	
}
