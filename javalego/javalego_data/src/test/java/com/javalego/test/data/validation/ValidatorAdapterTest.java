package com.javalego.test.data.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.javalego.model.validator.ValidatorAdapter;

/**
 * Ejemplo de uso de la implementación de validación incluida en este proyecto
 * para tecnologías como Android donde no se puede utilizar la de Hibernate.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ValidatorAdapterTest {

	public static void main(String[] args) {

		Set<ConstraintViolation<Car>> e = ValidatorAdapter.getCurrent().validateProperty(new Car(), "manufacturer");

		if (e != null) {
			for (ConstraintViolation<Car> item : e) {
				System.out.println(item.getMessage());
			}
		}
	}

}
