package com.javalego.test.data;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.javalego.test.data.entities.Invoice;
import com.javalego.test.data.entities.Product;

/**
 * Test que muestra cómo implementar servicios y proveedores de datos usando
 * información en memoria para realizar pruebas de concepto.
 * 
 * @author ROBERTO RANZ
 *
 */
public class DataTest {

	private static MockDataServices services = null;

	private static final Logger logger = Logger.getLogger(DataTest.class);

	@BeforeClass
	public static void setUp() {
		services = new MockDataServicesImpl();
	}

	@Test
	public void invoices() throws Exception {

		for (Invoice invoice : services.getAllInvoices()) {
			logger.debug("Invoice: " + invoice.getTitle() + " - " + invoice.getTotal());
		}

	}

	@Test
	public void products() throws Exception {

		for (Product product : services.getAllProducts()) {
			logger.debug("Product: " + product.getTitle() + " - " + product.getDescription());
		}

	}

}