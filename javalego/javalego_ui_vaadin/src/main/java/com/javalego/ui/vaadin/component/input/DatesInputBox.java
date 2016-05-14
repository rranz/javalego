/**
 * 
 */
package com.javalego.ui.vaadin.component.input;

import java.util.Date;

import com.javalego.ui.field.impl.DateFieldModel;

/**
 * Entrada de datos para definir un período de tiempo. Fechas desde y fecha hasta.
 * Ej.:
			final DatesInputBox md = new DatesInputBox(new Date(), new Date());
			final MessageBox m = new MessageBox(main, "Fechas", Icon.QUESTION, md, Alignment.BOTTOM_LEFT, new MessageBox.ButtonConfig(ButtonType.OK, "Aceptar"), new MessageBox.ButtonConfig(ButtonType.CANCEL,	"Cancelar"));
			m.show(new EventListener() {
				@Override
				public void buttonClicked(ButtonType buttonType) {
					if (buttonType == ButtonType.OK) {
					}
					m.close();
				}
			});

 * @author ROBERTO RANZ
 */
public class DatesInputBox extends InputBox {

	private static final long serialVersionUID = -1981209909960430451L;

	private InputProperty init;
	private InputProperty end;

	public DatesInputBox() {
		init(null, null, null);
	}
	
	/**
	 * @param date
	 * @param date2
	 */
	public DatesInputBox(Date init, Date end, String title) {
		
		init(init, end, title);
	}

	private void init(Date init, Date end, String title) {
		
		DateFieldModel p = new DateFieldModel();
		p.setName("Desde");
		p.setRequired(true);
		this.init = addProperty(p, init);
		
		p = new DateFieldModel();
		p.setName("Hasta");
		p.setRequired(true);
		this.end = addProperty(p, end);
		
		setTitle(title);
		
		load();
	}

	/**
	 * Fecha de inicio
	 * @return
	 */
	public Date getInitDate() {
		return (Date)init.getValue();
	}
	
	/**
	 * Fecha de finalización
	 * @return
	 */
	public Date getEndDate() {
		return (Date)end.getValue();
	}
	
}
