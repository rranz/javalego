package com.javalego.ui.field.impl.state;

import java.util.ArrayList;
import java.util.Date;

import com.javalego.model.keys.Icon;
import com.javalego.ui.field.impl.AbstractFieldModel;
import com.javalego.ui.field.impl.IListValuesFieldModel;

/**
 * Propiedad que representa un estado de una propiedad. Normalmente, representa
 * el estado de un registro dentro de un contexto de ejecución. Las diferentes
 * opciones están recogidas en la propiedad items.(pendiente, denegado,
 * aceptado). Esta clase tiene los valores actuales de la propiedad: fecha,
 * estado y usuario de la última modificación (estos datos se obtendrán de los
 * campos de la vista de datos).
 * 
 * Ver AddessProperty es muy similar su comportamiento y edición visual.
 * 
 * @author ROBERTO RANZ
 */
public class StateFieldModel extends AbstractFieldModel implements IListValuesFieldModel {

	private static final long serialVersionUID = -712526173405293409L;

	/**
	 * Estados posibles.
	 */
	private ArrayList<ItemStateFieldModel> items = new ArrayList<ItemStateFieldModel>();

	/**
	 * Fecha de la última acción realizada.
	 */
	private Date date;

	/**
	 * Anotaciones
	 */
	private String notes;

	/**
	 * Nombre de la propiedad que contiene el valor de las anotaciones.
	 */
	private String notesFieldName;

	/**
	 * Nombre del fichero de documentación
	 */
	private String documentFileName;

	/**
	 * Gestiona incidencias que se mostrarán al usuario mediante un botón. Si es
	 * false, este botón se ocultará.
	 */
	private boolean incidences = true;

	/**
	 * Nombre del campo que contiene la fecha de la última modificación del
	 * estado.
	 */
	private String dateFieldName;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<ItemStateFieldModel> getItems() {
		return items;
	}

	public void setItems(ArrayList<ItemStateFieldModel> items) {
		this.items = items;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotesFieldName() {
		return notesFieldName;
	}

	public void setNotesFieldName(String notesFieldName) {
		this.notesFieldName = notesFieldName;
	}

	/**
	 * Obtiene el título de un item buscando por su valor.
	 * 
	 * @param string
	 * @return
	 */
	public String getItemTitle(String value) {

		for (ItemStateFieldModel item : items) {

			if (item.getValue().equals(value))
				return item.getTitle();
		}
		return getDefaultState() != null ? getDefaultState().getTitle() : "";
	}

	/**
	 * Obtiene el color de un item buscando por su valor.
	 * 
	 * @param string
	 * @return
	 */
	public String getItemColor(String value) {

		for (ItemStateFieldModel item : items) {

			if (item.getValue().equals(value))
				return item.getColor();
		}
		return null;
	}

	/**
	 * Si el item actualmente seleccionado es enabled = false es que se ha
	 * seleccionado teniendo en cuenta las posibles incidencias y errores.
	 */
	public boolean isItemEnabled(String value) {

		for (ItemStateFieldModel item : items) {
			if (item.getValue().equals(value) && item.isEnabled()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Buscar un item por su valor
	 */
	public ItemStateFieldModel getItemState(String value) {

		for (ItemStateFieldModel item : items) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Si el item actualmente seleccionado es enabled = false es que se ha
	 * seleccionado teniendo en cuenta las posibles incidencias y errores.
	 */
	public ItemStateFieldModel getDefaultState() {

		for (ItemStateFieldModel item : items) {
			if (item.isDefaultState()) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Lista de iconos
	 * 
	 * @return
	 */
	public Icon[] getListIcons() {

		if (items.size() > 0) {

			Icon[] values = new Icon[items.size()];

			for (int i = 0; i < items.size(); i++)
				values[i] = items.get(i).getIconSmall();

			return values;
		}
		else
			return null;
	}

	/**
	 * Lista de colores
	 * 
	 * @return
	 */
	@Override
	public String[] getListColors() {

		if (items.size() > 0) {

			String[] values = new String[items.size()];

			for (int i = 0; i < items.size(); i++)
				values[i] = items.get(i).getColor();

			return values;
		}
		else
			return null;
	}

	/**
	 * Lista de valores
	 * 
	 * @return
	 */
	@Override
	public String[] getListValues() {

		if (items.size() > 0) {

			String[] values = new String[items.size()];

			for (int i = 0; i < items.size(); i++)
				values[i] = items.get(i).getValue();

			return values;
		}
		else
			return null;
	}

	@Override
	public String[] getListLabels() {

		if (items.size() > 0) {

			String[] labels = new String[items.size()];

			for (int i = 0; i < items.size(); i++)
				labels[i] = items.get(i).getTitle();

			return labels;
		}
		else
			return null;
	}

	/**
	 * Lista de descripciones por cada item.
	 * 
	 * @return
	 */
	public String[] getListDescriptions() {

		if (items.size() > 0) {

			String[] labels = new String[items.size()];

			for (int i = 0; i < items.size(); i++)
				labels[i] = items.get(i).getDescription();

			return labels;
		}
		else
			return null;
	}

	public String getDateFieldName() {
		return dateFieldName;
	}

	public void setDateFieldName(String dateFieldName) {
		this.dateFieldName = dateFieldName;
	}

	public boolean isIncidences() {
		return incidences;
	}

	public void setIncidences(boolean incidences) {
		this.incidences = incidences;
	}

	public String getDocumentFileName() {
		return documentFileName;
	}

	public void setDocumentFileName(String documentFileName) {
		this.documentFileName = documentFileName;
	}

}
