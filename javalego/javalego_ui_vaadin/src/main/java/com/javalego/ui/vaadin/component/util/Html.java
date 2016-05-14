package com.javalego.ui.vaadin.component.util;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Funciones de utilidad relacionadas con Vaadin
 * 
 * @author ROBERTO RANZ
 */
public class Html {

	// Construir templates Html para injetar controles visuales:
	// ver ejemplo donde se crear un archivo html con Dreamweaver y el código
	// Vaadin para realizar la
	// injección de controles mediante un CustomLayout
	// http://vaadin.com/book/-/page/layout.customlayout.html
	// Injectar Html dinámico mediante un customLayout o fichero:
	// layout.addComponent(new CustomLayout(new
	// ByteArrayInputStream("<b>Template</b>".getBytes())));
	// new CustomLayout(new FileInputStream(file));

	public static String getSpace() {
		return "&nbsp;";
	}

	/**
	 * Label Style H1
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class H1 extends Label {

		private static final long serialVersionUID = -7474724282411358402L;

		public H1(String caption) {
			super(caption, ContentMode.HTML);
			setStyleName(ValoTheme.LABEL_H1);
		}

		public H1 colored() {
			addStyleName(ValoTheme.LABEL_COLORED);
			return this;
		}
	}

	/**
	 * Strong Style Label
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class Strong extends Label {

		private static final long serialVersionUID = -2677546467817330627L;

		public Strong(String caption) {
			super("<strong>" + caption + "</strong>", ContentMode.HTML);
		}

	}

	/**
	 * Label style H2
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class H2 extends Label {

		private static final long serialVersionUID = -8869623493712530404L;

		public H2(String caption) {

			super(caption, ContentMode.HTML);
			setStyleName(ValoTheme.LABEL_H2);
		}

		public H2 colored() {
			addStyleName(ValoTheme.LABEL_COLORED);
			return this;
		}
	}

	/**
	 * Label Style H3
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class H3 extends Label {

		private static final long serialVersionUID = 3599474827433667016L;

		public H3(String caption) {
			super(caption, ContentMode.HTML);
			setStyleName(ValoTheme.LABEL_H3);
		}

		public H3 colored() {
			addStyleName(ValoTheme.LABEL_COLORED);
			return this;
		}
	}

	/**
	 * Label Style H4
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class H4 extends Label {

		private static final long serialVersionUID = 7694438888453431018L;

		public H4(String caption) {
			super(caption, ContentMode.HTML);
			setStyleName(ValoTheme.LABEL_H4);
		}

		public H4 colored() {
			addStyleName(ValoTheme.LABEL_COLORED);
			return this;
		}
	}

	/**
	 * Label Style H1
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class LabelExt extends Label {

		private static final long serialVersionUID = -5878858497830736091L;

		public LabelExt(String caption) {
			super(caption, ContentMode.HTML);
		}
	}

	/**
	 * Texto pequeño
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class SmallText extends Label {

		private static final long serialVersionUID = -7705003887075764536L;

		public SmallText(String caption) {
			super(caption, ContentMode.HTML);
			setStyleName(ValoTheme.LABEL_SMALL);
		}
	}

	/**
	 * Ruler o barra horizontal de separación.
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class Ruler extends Label {

		private static final long serialVersionUID = -7315037633756954583L;

		public Ruler() {
			super("<hr/>", ContentMode.HTML);
		}
	}

	/**
	 * Incluye una nueva línea <br/>
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class NewLine extends Label {

		private static final long serialVersionUID = 7773804891796848006L;

		public NewLine() {
			super("<br/>", ContentMode.HTML);
		}
	}

	/**
	 * Incluye un espacio.
	 * 
	 * @param count
	 * @return
	 */
	public static String getSpace(int count) {
		String text = "";
		for (int i = 0; i < count; i++)
			text += "&nbsp;";
		return text;
	}
}
