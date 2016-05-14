package com.javalego.test.data.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.javalego.model.validator.custom.UpperCase;

public class Car {

	@NotNull
	private String manufacturer;

	@NotNull
	@Size(min = 2, max = 14)
	@UpperCase
	private String licensePlate;

	public Car() {}
	
	public Car(String manufacturer, String licensePlate) {

		super();
		this.manufacturer = manufacturer;
		this.licensePlate = licensePlate;
	}
}
