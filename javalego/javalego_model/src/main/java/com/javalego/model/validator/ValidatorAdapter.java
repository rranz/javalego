package com.javalego.model.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.apache.log4j.Logger;

import com.javalego.exception.JavaLegoException;
import com.javalego.model.validator.Validator.InvalidValueException;
import com.javalego.util.BeanUtil;
import com.javalego.util.ReflectionUtils;

/**
 * Adapter that takes a JSR-303 <code>javax.validator.Validator</code>
 * 
 * @author ROBERTO RANZ
 * @since 3.0
 */
public class ValidatorAdapter implements Validator {

	private static final Logger logger = Logger.getLogger(ValidatorAdapter.class);

	private static ValidatorAdapter current;

	/**
	 * Instancia de validador por defecto para su reutilización.
	 * 
	 * @return
	 */
	public static synchronized final ValidatorAdapter getCurrent() {
		if (current == null) {
			current = new ValidatorAdapter();
		}
		return current;
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {

		Set<ConstraintViolation<T>> list = new HashSet<ConstraintViolation<T>>();

		for (Field field : BeanUtil.getProperties(object.getClass())) {
			Set<ConstraintViolation<T>> result = validateField(object, field);
			if (result != null) {
				list.addAll(result);
			}
		}

		return list.size() > 0 ? list : null;
	}

	/**
	 * Validar todas las anotaciones de la propiedad de un bean.
	 * 
	 * @param object
	 * @param field
	 * @return
	 */
	private <T> Set<ConstraintViolation<T>> validateField(T object, Field field) {

		Annotation[] annotations = field.getAnnotations();

		if (annotations != null) {

			Set<ConstraintViolation<T>> list = new HashSet<ConstraintViolation<T>>();

			for (Annotation annotation : annotations) {

				Set<ConstraintViolation<T>> result;

				try {
					result = validateAnnotation(object, annotation, field);

					if (result != null) {
						list.addAll((Collection<? extends ConstraintViolation<T>>) result);
					}

				}
				catch (JavaLegoException e) {
					logger.error("ERROR VALIDATION FIELD '" + field.getName(), e);
				}
			}

			return list.size() > 0 ? list : null;
		}
		return null;
	}

	/**
	 * Validar las anotaciones de cada campo del Bean.
	 * 
	 * @param annotation
	 * @throws JavaLegoException
	 */
	private <T> Set<ConstraintViolation<T>> validateAnnotation(T object, Annotation annotation, Field field) throws JavaLegoException {

		return validateAnnotation(annotation, field.getName(), ReflectionUtils.getPropertyValue(object, field.getName()));
	}

	/**
	 * Validar las anotaciones de cada campo del Bean pasando un valor de
	 * validación.
	 * 
	 * @param annotation
	 * @throws JavaLegoException
	 */
	private <T> Set<ConstraintViolation<T>> validateAnnotation(Annotation annotation, String fieldName, Object value) throws JavaLegoException {

		Set<ConstraintViolation<T>> list = new HashSet<ConstraintViolation<T>>();

		com.javalego.model.validator.Validator v = ValidatorFactory.getInstance(annotation);

		if (v == null) {
			return null;
		}
		try {
			v.validate(value, null);
		}
		catch (InvalidValueException e) {
			list.add(new BeanConstraintViolation<T>(e, fieldName, null));
		}

		return list.size() > 0 ? list : null;
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		try {
			return validateField(object, ReflectionUtils.getField(object.getClass(), propertyName)); //object.getClass().getDeclaredField(propertyName));
		}
//		catch (NoSuchFieldException e) {
//			logger.error("FIELD '" + propertyName + "' NOT EXIST");
//			return null;
//		}
		catch (JavaLegoException e) {
			logger.error("FIELD '" + propertyName + "' NOT EXIST");
			return null;
		}
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {

		return null;
	}

	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> type) {
		return null;
	}

	@Override
	public ExecutableValidator forExecutables() {
		return null;
	}

	/**
	 * Validar el valor de una propiedad.
	 * 
	 * @param _class
	 * @param value
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public Set<ConstraintViolation<?>> validateValue(Class<?> _class, Object value, String propertyName) {

		Field field = null;
		try {
			field = ReflectionUtils.getField(_class, propertyName);
		}
		catch (Exception e1) {
			logger.warn("ERROR VALIDATION. FIELD '" + propertyName + "'. Message: " + e1.getMessage());
			return null;
		}

		if (field == null) {
			logger.warn("ERROR VALIDATION. FIELD '" + propertyName + "' NOT EXIST.");
			return null;
		}

		Annotation[] annotations = field.getAnnotations();

		if (annotations != null) {

			Set<ConstraintViolation<?>> list = new HashSet<ConstraintViolation<?>>();

			for (Annotation annotation : annotations) {

				Set<ConstraintViolation<Object>> result;

				try {
					result = validateAnnotation(annotation, field.getName(), value);

					if (result != null) {
						list.addAll((Collection<? extends ConstraintViolation<?>>) result);
					}

				}
				catch (JavaLegoException e) {
					logger.error("ERROR VALIDATION FIELD '" + field.getName(), e);
				}
			}

			return list.size() > 0 ? list : null;
		}
		return null;
	}

	/**
	 * Validar el modelo con respecto al valor de la propiedad.
	 * 
	 * @param model
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Set<ConstraintViolation<?>> validateValue(String name, boolean required, Object value, Class<?> _class) {

		Set<ConstraintViolation<?>> list = new HashSet<ConstraintViolation<?>>();

		Field field = null;
		try {
			field = ReflectionUtils.getField(_class, name);
		}
		catch (Exception e1) {
			logger.warn("ERROR VALIDATION. FIELD '" + name + "'. Message: " + e1.getMessage());
			return null;
		}

		Annotation[] annotations = field.getAnnotations();

		// Requerido
		if (required) {

			boolean include = true;

			if (annotations != null) {
				for (Annotation annotation : annotations) {
					if (annotation instanceof NotNull) {
						include = false;
					}
				}
			}

			if (include) {
				Set<ConstraintViolation<Object>> result;

				try {
					
					// TODO Falta incluir el resto de posibles validaciones: min, size, max, ...
					result = validateAnnotation(new NotNull() {
						@Override
						public Class<? extends Annotation> annotationType() {
							return null;
						}
						@Override
						public Class<? extends Payload>[] payload() {
							return null;
						}
						@Override
						public String message() {
							return null;
						}
						@Override
						public Class<?>[] groups() {
							return null;
						}
					}, name, value);

					if (result != null) {
						list.addAll((Collection<? extends ConstraintViolation<?>>) result);
					}

				}
				catch (JavaLegoException e) {
					logger.error("ERROR VALIDATION FIELD '" + name, e);
				}
			}
		}

		return list.size() > 0 ? list : null;
	}

}
