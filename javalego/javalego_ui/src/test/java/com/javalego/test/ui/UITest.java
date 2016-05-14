package com.javalego.test.ui;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.ui.UIContext;

/**
 * Test de las funcionalidades de UIContext.
 * 
 * @author ROBERTO RANZ
 *
 */
@SuppressWarnings("unused")
public class UITest {

	public static final Logger logger = Logger.getLogger(UITest.class);

	@Test
	public void test() {
		logger.info("Locale user session: " + UIContext.getUserSessionLocale().getCountry());
	}

}
