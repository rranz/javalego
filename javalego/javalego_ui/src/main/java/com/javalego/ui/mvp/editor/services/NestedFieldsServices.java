package com.javalego.ui.mvp.editor.services;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.javalego.entity.Entity;
import com.javalego.exception.JavaLegoException;
import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.nestedbean.INestedBeanServices;
import com.javalego.ui.mvp.nestedbean.imp.NestedBeanListValues;
import com.javalego.ui.patterns.IService;
import com.javalego.util.ReflectionUtils;

/**
 * Funciones relativas a campos nested relacionados con otras entidades
 * definidas en el editor de beans.
 * 
 * @author ROBERTO RANZ
 */
public class NestedFieldsServices implements IService {

	private static final Logger logger = Logger.getLogger(NestedFieldsServices.class);

	/**
	 * Comprueba si el campo es nested y editable
	 * 
	 * @param fieldModel
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNestedField(IEditorServices services, FieldModel fieldModel) {

		return isNestedField(services, fieldModel.getName(), false);
	}

	/**
	 * Comprueba si el bean es anidado
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNestedField(IEditorServices services, String name, boolean find) {

		if (services != null) {
			return getNestedField(services, name, find) != null;
		}
		return false;
	}

	/**
	 * Comprueba si el bean es anidado
	 * 
	 * @param name
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection<String> getNestedKeys(IEditorServices services, String name) throws LocalizedException {

		if (services != null) {
			INestedBeanServices beanservices = getNestedField(services, name, false);
			if (beanservices instanceof NestedBeanListValues) {
				return ((NestedBeanListValues) beanservices).getKeys(null);
			}
		}
		return null;
	}

	/**
	 * Comprueba si el bean es anidado
	 * 
	 * @param name
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("rawtypes")
	public static NestedBeanListValues getNestedServices(IEditorServices services, String name) throws LocalizedException {

		if (services != null) {
			INestedBeanServices beanservices = getNestedField(services, name, false);
			if (beanservices instanceof NestedBeanListValues) {
				return ((NestedBeanListValues) beanservices);
			}
		}
		return null;
	}

	/**
	 * Buscar el campo nested en los servicios definidos para este tipo de
	 * campos.
	 * 
	 * @param name
	 * @param find
	 *            Buscar en campos del mismo nested bean que no sea el definido
	 *            en el servicio como campo de edición.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static INestedBeanServices getNestedField(IEditorServices services, String name, boolean find) {

		if (services == null) {
			return null;
		}

		Map<String, INestedBeanServices> list = services.getNestedFieldModel();
		INestedBeanServices s = list != null && list.size() > 0 ? list.get(name) : null;

		if (s == null && find) {
			String root = name.substring(0, name.indexOf(".") + 1);
			for (Entry<String, INestedBeanServices> ns : list.entrySet()) {
				if (ns.getKey().indexOf(root) == 0) {
					return ns.getValue();
				}
			}

			return null;
		}
		else {
			return s;
		}
	}

	/**
	 * Obtener el filtro para establecer la relación master/detail de un bean
	 * nested existente en una entidad. (Ej.: ContactoEmpresa.empresa donde
	 * nestedBean = Empresa instancia de la que obtenemos su ID para establecer
	 * el filtro empresa.id = value.
	 * 
	 * @param nestedBean
	 * @param beanClass
	 * @return
	 */
	public static String getMasterBeanFilter(Object nestedBean, Class<?> beanClass) {

		String masterFilter = null;

		// Localizar el campo del bean que tenga el tipo de campo master (Ej.:
		// En EmpresaContacto buscar en campo Empresa para asignar el bean
		// master en relaciones master/detail).
		if (nestedBean != null && beanClass != null) {

			for (Field field : ReflectionUtils.getAllDeclaratedFields(beanClass)) {

				if (field.getType() == nestedBean.getClass()) {
					try {
						Long id = ReflectionUtils.getPropertyValue(nestedBean, Entity.DEFAULT_ID);
						masterFilter = field.getName() + "." + Entity.DEFAULT_ID + " = " + (id == null ? "-1" : id);
					}
					catch (JavaLegoException e) {
						logger.error("METHOD MasterBeanFilter: ERROR SET PROPERTY VALUE MASTERBEAN '" + field.getName());
					}
					break;
				}
			}
		}

		return masterFilter;
	}

	/**
	 * Comprueba si el campo de la entidad corresponde a un campo del bean
	 * anidado. Ej.: ContactoEmpresa.empresa.nombre es true si pasamos como
	 * masterBean Empresa.
	 * 
	 * @param fieldname
	 * @param masterBean
	 * @param beanClass
	 * @return
	 */
	public static boolean isFieldVisible(String fieldname, Class<?> nestedBeanClass, Class<?> beanClass) {

		for (Field field : ReflectionUtils.getAllDeclaratedFields(beanClass)) {

			if (field.getType() == nestedBeanClass && fieldname.indexOf(field.getName() + ".") == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Establece la propiedad de campo nested en el bean con el objeto masterBean pasado como parámetro. 
	 * Ej.: ContactoEmpresa.empresa = masterbean (Empresa).
	 * @param bean
	 * @param masterBean
	 */
	public static void setMasterBean(Object bean, Object masterBean) {
		
		for (Field field : ReflectionUtils.getAllDeclaratedFields(bean.getClass())) {
			
			if (field.getType() == masterBean.getClass()) {
				try {
					ReflectionUtils.setPropertyValue(bean, field.getName(), masterBean);
					return;
				}
				catch (JavaLegoException e) {
					logger.error("ERROR SET PROPERTY VALUE MASTERBEAN '" + field.getName());
				}
				break;
			}
		}		
		logger.error("NESTED BEANS: " + masterBean.getClass().getCanonicalName() + " NOT EXIST INTO " + bean.getClass());
	}
}
