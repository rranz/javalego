package com.javalego.test.model;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.model.keys.MaterialColors;

/**
 * Test modelo de datos.
 * 
 * TODO Pendiente
 * 
 * @author ROBERTO RANZ
 *
 */
public class ModelTest {

	public static final Logger logger = Logger.getLogger(ModelTest.class);

	@Test
	public void test() {
		logger.info("Material color: " + MaterialColors.DEEP_ORANGE_A400);
	}
}
