package com.javalego.ui.vaadin.component.rating;

import java.util.ArrayList;
import java.util.List;

import com.javalego.ui.UIContext;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * Valoración de n estrellas. Cada estrella está asociada a un tipo de
 * valoración: mala, buena, excelente, ... personalizable.
 *
 * Css Style:
 *
 * 
 * .color-star-on { color: $orange-300-color; } .color-star-off { color: silver;
 * } .color-star-off:hover { cursor: pointer; } .color-star-on:hover { cursor:
 * pointer; }
 * 
 * @author ROBERTO RANZ
 *
 */
public class Rating extends CssLayout {

	private static final long serialVersionUID = -4944121766717590238L;

	private int value = 0;

	private int maxvalue = 5;

	private List<RatingStar> stars = new ArrayList<RatingStar>();

	@Override
	protected String getCss(Component c) {
		return "display: inline-block;";
	}

	/**
	 * Constructor
	 * 
	 * @param value
	 */
	public Rating() {
		this(-1, -1);
	}

	/**
	 * Constructor
	 * 
	 * @param value
	 */
	public Rating(int value) {
		this(-1, value);
	}

	/**
	 * Constructor
	 * 
	 * @param maxvalue
	 * @param value
	 */
	@SuppressWarnings("serial")
	public Rating(int maxvalue, int value) {

		// Validaciones
		if (maxvalue > 0 && maxvalue < 101) {
			this.maxvalue = maxvalue;
		}

		if (value < 1 && value > maxvalue) {
			value = 0;
		}

		addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick() && event.getChildComponent() instanceof RatingStar) {
					update((RatingStar) event.getChildComponent());
				}
			}
		});

		// Inicializar estrellas
		for (int i = 1; i <= this.maxvalue; i++) {
			RatingStar rs = new RatingStar(i <= value);
			
			switch (i) {
			case 1:
				rs.setDescription(UIContext.getText(LocaleRating.AWFUL));
				break;
			case 2:
				rs.setDescription(UIContext.getText(LocaleRating.NOT_THAT_GOOD));
				break;
			case 3:
				rs.setDescription(UIContext.getText(LocaleRating.OK));
				break;
			case 4:
				rs.setDescription(UIContext.getText(LocaleRating.GREAT));
				break;
			case 5:
				rs.setDescription(UIContext.getText(LocaleRating.EXCELLENT));
				break;
			default:
				break;
			}
			
			stars.add(rs);
			addComponent(rs);
		}

		this.value = value;
	}

	/**
	 * Actualizar estrellas en base a la estrella seleccionada por el usuario.
	 * 
	 * @param star
	 */
	private void update(RatingStar star) {

		update(stars.indexOf(star) + 1);
	}

	/**
	 * Actualizar las estrellas en base al nuevo valor.
	 * 
	 * @param value
	 */
	private void update(int value) {

		// Desactivar todas las estrellas si se selecciona una ya activada.
		if (stars.get(value - 1).isChecked() && (value == stars.size() || !stars.get(value).isChecked())) {
			for (int i = 1; i < stars.size() + 1; i++) {
				stars.get(i - 1).setChecked(false);
			}
			this.value = 0;
		}
		// Activar las estrellas hasta la estrella marcada.
		else {
			for (int i = 1; i <= value; i++) {
				stars.get(i - 1).setChecked(true);
			}

			for (int i = value + 1; i < stars.size() + 1; i++) {
				stars.get(i - 1).setChecked(false);
			}
			this.value = value;
		}
	}

	/**
	 * Valor de estrellas marcadas.
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Establecer el valor de estrellas marcadas.
	 * 
	 * @param value
	 */
	public void setValue(int value) {

		if (value > 0 && value <= maxvalue) {
			
			this.value = value;
			
			if (stars.size() > 0) {
				update(value);
			}
		}
	}

	/**
	 * Obtener un texto html con la lista de estrellas de la calificación pasada como parámetro.
	 * 
	 * @param rating
	 * @return
	 */
	public static String getHtml(int rating) {
		
		String text = "";
		
		for(int i = 1; i <= rating; i++) {
			text += "<span class='v-icon color-star-on' style='font-family: FontAwesome;'>&#xf005;</span>"; // FontAwesome.STAR.getHtml();
		}
		
		return text;
	}

}
