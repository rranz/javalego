package com.javalego.store.ui.demos;

import java.util.ArrayList;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.impl.DataBean;
import com.javalego.ui.field.impl.EnumFieldModel;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.impl.BaseEditorPresenter.EditorListener;
import com.javalego.ui.mvp.editor.impl.EditorModel;
import com.javalego.ui.mvp.editor.impl.EditorPresenter;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;
import com.javalego.ui.vaadin.component.util.Html.H1;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.view.editor.EditorView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import entities.address.Ciudad;
import entities.address.Direccion;
import entities.address.Provincia;
import entities.address.Vias;

/**
 * Demo de edición de una dirección de España.
 * 
 * Proveedor: Vaadin.
 * 
 * @author ROBERTO RANZ
 */
public class FormDireccion extends CustomComponent implements View {
	
	private static final long serialVersionUID = 7945457989964737794L;

	// Nombre de la vista Vaadin.
	public static final String NAME = "FORMADDRESS";
	
	private Direccion direccion;
	
	/**
	 * Constructor
	 * @throws LocalizedException
	 */
	public FormDireccion() throws LocalizedException {
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(new H1("Editar una Dirección"));
		layout.addComponent(new Label("<br>Sólo existen en esta demo ciudades para Madrid y Sevilla", ContentMode.HTML));
		layout.setMargin(true);
		layout.setSpacing(true);

		// Obtemos el componente de dirección.
		layout.addComponent(getAddress());

		setCompositionRoot(layout);		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

	/**
	 * Generar el panel de edición de todos los datos de la dirección.
	 * @return
	 * @throws LocalizedException
	 */
	private Component getAddress() throws LocalizedException {

		// Instanciar Bean de dirección
		direccion = new Direccion();
		
		// Modelo de edición donde se definen los campos a editar.
		EditorModel model = new EditorModel();

		// Definición de una dato de tipo Bean compuesto de varios campos. 
		// Cada campo tiene un tipo de modelo asociado a la naturaleza del dato que contiene (Texto, fecha, número, enumerado, ...)
		// El proveedor de la vista (en este caso Vaadin EditorView) será la encargada de generar los componentes visuales
		// adaptados a cada tipo de campo del modelo.
		IDataBean<Direccion> databean = new DataBean<Direccion>(direccion);
		
		// Lista de campos
		databean.addField(new EnumFieldModel("provincia", Textos.PROVINCIA, getProvincias()));
		databean.addField(new EnumFieldModel("ciudad", Textos.LOCALIDAD));
		databean.addField("codigo_postal", Textos.CODIGOPOSTAL, 5, 5);
		databean.addField(new EnumFieldModel("via", Textos.VIA, Vias.class));
		databean.addField("direccion", Textos.DIRECCION, 100, 30);
		databean.addField("puerta", Textos.PUERTA, 25, 5);
		databean.addField("escalera", Textos.ESCALERA, 25, 5);
		databean.addField("bloque", Textos.BLOQUE, 20, 10);
		
		//databean.addAll();

		// Añadimos del dato de tipo Bean al modelo.
		model.add(databean);

		// Layout: Distribución de campos en dos secciones.
		LayoutEditorModel em = new LayoutEditorModel(model);
		em.addChildLayout(Textos.PROVINCIA, Colors.BLUE, "provincia", "ciudad");
		em.addChildLayout(Textos.DIRECCION, Colors.ORANGE, "codigo_postal", "via", "direccion", "puerta", "escalera", "bloque");
		
		// Patrón: Model View Presenter MVP para el editor de datos del bean.
		// EditorView = Vista del editor implementada en Vaadin. (Ver implementación en Android -en construcción y todavía no disponible-).
		EditorPresenter p = new EditorPresenter(model, new EditorView(), em);

		// Recargar información de las ciudades de la provincia seleccionada de forma dinámica cuando el valor de la provincia cambie.
		p.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -2609125102224388725L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getFieldName().equals("provincia")) {
					event.getEditorRules().setEnumValues("ciudad", getCiudades((Provincia) event.getValue()));
				}
			}
		});

		// Mostrar la dirección editada, si no existen errores.
		p.setEditorListener(new EditorListener() {
			@Override
			public void remove() throws LocalizedException {
			}
			@Override
			public void discard() throws LocalizedException {
			}
			@Override
			public void commit() throws LocalizedException {
				MessagesUtil.information("Dirección:", direccion.toHtml());
			}
			@Override
			public void loadDetailBeanEditor(IBeansEditorModel<?> detail) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// Cargar la vista del editor
		p.load();

		// Obtener el componente UI para insertarlo en este formulario.
		return (Component) p.getView();
	}

	/**
	 * Obtenemos la lista de ciudades de la provincia pasada como parámetro.
	 * @param provincia
	 * @return
	 */
	protected List<?> getCiudades(Provincia provincia) {

		List<Ciudad> list = new ArrayList<Ciudad>();

		// Sólo implementamos la carga de ciudades para Madrid y Sevilla en esta demo. 
		if (provincia.getCodigo().equals("28")) {
			list.add(new Ciudad(provincia, "Alcobendas"));
			list.add(new Ciudad(provincia, "La Latina"));
			list.add(new Ciudad(provincia, "Tres Cantos"));
		}
		else if (provincia.getCodigo().equals("41")) {
			list.add(new Ciudad(provincia, "Dos hermanas"));
			list.add(new Ciudad(provincia, "El Rocio"));
		}
		return list;
	}

	/**
	 * Obtenemos la lista de provincias.
	 * @return
	 */
	private List<Provincia> getProvincias() {
		
		List<Provincia> list = new ArrayList<Provincia>();
		list.add(new Provincia("01", "Alava"));
		list.add(new Provincia("02", "Albacete"));
		list.add(new Provincia("03", "Alicante"));
		list.add(new Provincia("04", "Almería"));
		list.add(new Provincia("33", "Asturias"));
		list.add(new Provincia("05", "Avila"));
		list.add(new Provincia("06", "Badajoz"));
		list.add(new Provincia("07", "Baleares"));
		list.add(new Provincia("8", "Barcelona"));
		list.add(new Provincia("9", "Burgos"));
		list.add(new Provincia("10", "Cáceres"));
		list.add(new Provincia("11", "Cádiz"));
		list.add(new Provincia("39", "Cantabria"));
		list.add(new Provincia("12", "Castellón"));
		list.add(new Provincia("51", "Ceuta"));
		list.add(new Provincia("13", "Ciudad Real"));
		list.add(new Provincia("14", "Córdoba"));
		list.add(new Provincia("15", "A Coruña"));
		list.add(new Provincia("16", "Cuenca"));
		list.add(new Provincia("17", "Girona"));
		list.add(new Provincia("18", "Granada"));
		list.add(new Provincia("19", "Guadalajara"));
		list.add(new Provincia("20", "Guipuzcoa"));
		list.add(new Provincia("21", "Huelva"));
		list.add(new Provincia("22", "Huesca"));
		list.add(new Provincia("23", "Jaen"));
		list.add(new Provincia("24", "León"));
		list.add(new Provincia("25", "Lérida"));
		list.add(new Provincia("27", "Lugo"));
		list.add(new Provincia("28", "Madrid"));
		list.add(new Provincia("29", "Málaga"));
		list.add(new Provincia("52", "Melilla"));
		list.add(new Provincia("30", "Murcia"));
		list.add(new Provincia("31", "Navarra"));
		list.add(new Provincia("32", "Ourense"));
		list.add(new Provincia("34", "Palencia"));
		list.add(new Provincia("35", "Las Palmas"));
		list.add(new Provincia("36", "Pontevedra"));
		list.add(new Provincia("26", "La Rioja"));
		list.add(new Provincia("37", "Salamanca"));
		list.add(new Provincia("38", "S.C.Tenerife"));
		list.add(new Provincia("40", "Segovia"));
		list.add(new Provincia("41", "Sevilla"));
		list.add(new Provincia("42", "Soria"));
		list.add(new Provincia("43", "Tarragona"));
		list.add(new Provincia("44", "Teruel"));
		list.add(new Provincia("45", "Toledo"));
		list.add(new Provincia("46", "Valencia"));
		list.add(new Provincia("47", "Valladolid"));
		list.add(new Provincia("48", "Vizcaya"));
		list.add(new Provincia("49", "Zamora"));
		list.add(new Provincia("50", "Zaragoza"));

		return list;
	}
	
}
