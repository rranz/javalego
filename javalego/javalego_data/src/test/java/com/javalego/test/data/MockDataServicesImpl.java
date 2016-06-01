package com.javalego.test.data;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.test.data.entities.Invoice;
import com.javalego.test.data.entities.Product;

/**
 * {@inheritDoc} 
 * @author ROBERTO RANZ
 */
public class MockDataServicesImpl implements MockDataServices {

	private MockDataProvider provider = new MockDataProvider();
	
	@Override
	public List<Invoice> getAllInvoices() throws LocalizedException {
		return provider.findAll(Invoice.class);
	}

	@Override
	public List<Product> getAllProducts() throws LocalizedException {
		return provider.findAll(Product.class);
	}

}
