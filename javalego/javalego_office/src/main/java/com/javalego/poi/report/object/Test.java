package com.javalego.poi.report.object;

import com.javalego.poi.report.object.ReportObject;
import com.javalego.poi.report.object.Test;
import com.javalego.poi.report.object.elements.Group;
import com.javalego.poi.report.object.elements.GroupProperties;
import com.javalego.poi.report.object.elements.Property;
import com.javalego.poi.report.object.elements.Section;
import com.javalego.poi.report.object.elements.table.ColumnTable;
import com.javalego.poi.report.object.elements.table.RowTable;
import com.javalego.poi.report.object.elements.table.TableProperties;

public class Test {

	private String nombre;
	private double precio;

	public static final void main(String args[]) {

		Test t = new Test();
		t.setNombre("Plantilla");
		t.setPrecio(10034.34);

		ReportObject r = new ReportObject();
		r.setObject(t);

		r.getHeader().setTitle("Resumen");
		r.getHeader().setSubTitle("Gana");

		Section s = new Section(r);

		Group g = new Group(r);
		g.setTitle("Grupo de propiedades");

		GroupProperties gp = new GroupProperties(r);
		gp.setTitle("Plan de Formación");

		Property p = new Property(r);
		p.setName("nombre");
		//p.setTitle("Nombre de la Acción formativa");

		Property p2 = new Property(r);
		p2.setName("precio");
		//p2.setTitle("Importe");

		gp.getProperties().add(p);
		gp.getProperties().add(p2);

		g.getGroupsProperties().add(gp);
		g.getGroupsProperties().add(gp);

		//s.getGroups().add(g);
		//s.getGroups().add(g);

		TableProperties table = new TableProperties(r);
		table.setTitle("PRUEBA");

		RowTable rt = new RowTable(r);
		rt.setTitle("Fila");
		rt.getProperties().add(p);
		rt.getProperties().add(p2);

		ColumnTable co = new ColumnTable(r);
		co.setTitle("Columna");
		table.getRows().add(rt);
		table.getRows().add(rt);

		table.getColumns().add(co);
		table.getColumns().add(co);

		//s.getGroups().add(table);

		r.getSections().add(s);

		//r.getSections().add(s);

		try {
			r.execute(true);
			r.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
