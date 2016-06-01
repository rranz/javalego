package com.javalego.test.data;

import java.util.List;

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
	List<Invoice> getAllInvoices() throws LocalizedException;

	/**
	 * Obtener todos los productos
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	List<Product> getAllProducts() throws LocalizedException;

}
