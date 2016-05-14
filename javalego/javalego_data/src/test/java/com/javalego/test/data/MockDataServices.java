package com.javalego.test.data;

import java.util.Collection;

import com.javalego.data.BusinessService;
import com.javalego.exception.LocalizedException;
import com.javalego.test.data.entities.Invoice;
import com.javalego.test.data.entities.Product;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public interface MockDataServices extends BusinessService {

	/**
	 * Obtener todas las facturas
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	Collection<Invoice> getAllInvoices() throws LocalizedException;

	/**
	 * Obtener todos los productos
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	Collection<Product> getAllProducts() throws LocalizedException;

}
