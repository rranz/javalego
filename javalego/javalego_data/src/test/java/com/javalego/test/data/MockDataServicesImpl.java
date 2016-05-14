package com.javalego.test.data;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.test.data.entities.Invoice;
import com.javalego.test.data.entities.Product;

/**
 * {@inheritDoc} 
 * @author ROBERTO RANZ
 */
public class MockDataServicesImpl implements MockDataServices {

	private MockDataProvider provider = new MockDataProvider();
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Invoice> getAllInvoices() throws LocalizedException {
		return (Collection<Invoice>) provider.getList(Invoice.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Product> getAllProducts() throws LocalizedException {
		return (Collection<Product>) provider.getList(Product.class);
	}

}
