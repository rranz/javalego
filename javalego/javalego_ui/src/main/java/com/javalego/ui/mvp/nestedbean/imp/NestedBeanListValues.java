package com.javalego.ui.mvp.nestedbean.imp;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;
import com.javalego.ui.mvp.nestedbean.INestedBeanServices;

/**
 * Servicios de selección y validación de beans anidados (nested) vinculados a
 * un bean.
 * 
 * Esta clase se utilizará principalmente para la edición de los datos del bean
 * que contiene el bean nested.
 * 
 * Su principal función será:
 * <p>
 * 1. Mostrar una lista de posibles valores para un campo del bean nested (Ej.:
 * empresa.nombre) donde el usuario podrá autocompletar su valor (si
 * autoCompleted = true) o seleccionar un valor de una lista (si autoCompleted = false).
 * <p>
 * 2. Validar el valor y actualizar los valores del bean nested en función de si
 * existe o no dicho valor, en especial el campo ID para beans derivados de
 * IEntity.
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 *            Owner Bean (Ej.: Contacto bean que contiene el campo anidado
 *            empresa del contacto)
 * @param <U>
 *            Nested Bean (Ej.: Contacto.empresa = Empresa)
 * 
 */
public abstract class NestedBeanListValues<T, U> implements INestedBeanServices<T, U> {

	@Override
	public void validate(U bean) throws LocalizedException {
	}

	@Override
	public void setBean(U bean, T ownerBean) throws LocalizedException {
	}

	@Override
	public U getBean(T ownerBean) throws LocalizedException {
		return null;
	}

	@Override
	public IFormatBeansView<T> getFormatView() {
		return null;
	}

	@Override
	public ValueChangeListener getValueChangeListener() {
		return new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					valueChangeEvent(event);
				}
				catch (LocalizedException e1) {
				}
			}
		};
	}

	/**
	 * Evento que notifica el cambio de valor del campos anidado. En este evento
	 * tendremos que validar este valor de campo y actualizar resto de campos
	 * del bean nested. Ej.: Si hemos definido el campo nombre del bean empresa
	 * (nested) que pertenece al bean contacto (owner), buscaremos el nuevo
	 * valor definido por el usuario para establecer los valores de los campos
	 * del bean nested utilizados en el editor y en especial el campo ID para
	 * beans derivados de IEntity.
	 * 
	 * <pre>
	 * Código de ejemplo:
	 * 
	 * Cliente e = getDataProvider().getObject(Cliente.class, &quot;nombre = '&quot; + event.getValue() + &quot;'&quot;);
	 * if (e != null) {
	 * 	event.getEditorRules().setValue(EMPRESA_ID, e.getId());
	 * }
	 * else {
	 * 	event.getEditorRules().setValue(EMPRESA_ID, null);
	 * 	event.getEditorRules().setValue(EMPRESA_NOMBRE, null);
	 * }
	 * </pre>
	 * 
	 * 
	 * @param event
	 * @throws LocalizedException
	 */
	public abstract void valueChangeEvent(ValueChangeEvent event) throws LocalizedException;

	/**
	 * Obtener la lista de valores clave posibles para el campo anidado.
	 * 
	 * <pre>
	 * Código de ejemplo:
	 * 
	 * (Collection<String>)  getDataProvider().getFieldValues(Cliente.class, NOMBRE, constraint != null ? "nombre like'" + constraint + "%'" : null, NOMBRE);
	 * </pre>
	 * 
	 * @param constraint
	 *            Valor de filtro aplicado para obtener la lista de valores
	 *            posibles seleccionables por el usuario.
	 * @return
	 * @throws LocalizedException
	 */
	public abstract Collection<String> getKeys(String constraint) throws LocalizedException;

	@Override
	public Collection<U> getBeans(String filter) throws LocalizedException {
		return null;
	}

}
