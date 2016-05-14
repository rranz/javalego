package com.javalego.test.data.validation;

import org.junit.BeforeClass;
import org.junit.Test;

public class CarTest {

   // private static Validator validator;

    @BeforeClass
    public static void setUp() {
 
//    	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        
//    	
//    	validator = factory.getValidator();
 
       //validator.validateProperty(Car.class,  "name", UpperCaseValidator.class);
        
    }

    @Test
    public void testLicensePlateNotUpperCase() {

    	
       // Car car = new Car("AUDI", "AA-123");

        
        try {
        //Set<ConstraintViolation<Car>> constraintViolations =
         //   validator.validate(car);
//        assertEquals(1, constraintViolations.size());
//        assertEquals(
//            "String must be upper-case.", 
//            constraintViolations.iterator().next().getMessageTemplate());
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

    @Test
    public void testCarIsValid() {

//        Car car = new Car("Audi", "DD-AB-123");
//
//        Set<ConstraintViolation<Car>> constraintViolations = 
//            validator.validate(car);
//        assertEquals(0, constraintViolations.size());
    }
}