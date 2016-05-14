package com.javalego.erp.data;

import java.util.Collection;

import com.javalego.data.DataContext;
import com.javalego.data.DataProvider;
import com.javalego.data.BusinessService;
import com.javalego.entity.Entity;
import com.javalego.erp.model.editors.ModeloCampos;
import com.javalego.erp.model.entity.Categoria;
import com.javalego.erp.model.entity.CategoriaProfesional;
import com.javalego.erp.model.entity.Cliente;
import com.javalego.erp.model.entity.ClienteContacto;
import com.javalego.erp.model.entity.Departamento;
import com.javalego.erp.model.entity.Empleado;
import com.javalego.erp.model.entity.Empresa;
import com.javalego.erp.model.entity.Producto;
import com.javalego.erp.model.entity.ProductoTarifa;
import com.javalego.erp.model.entity.Proveedor;
import com.javalego.erp.model.entity.ProveedorContacto;
import com.javalego.exception.LocalizedException;

/**
 * Servicios de negocio para la aplicación ERP Demo.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ErpDataServices implements BusinessService, ModeloCampos {

	
	/**
	 * Categorías
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Categoria> getCategorias(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<Categoria>) getDataProvider().getList(Categoria.class, filter, order == null ? NOMBRE : order);
	}

	/**
	 * Proveedor de datos
	 * 
	 * @return
	 */
	private DataProvider<Entity> getDataProvider() {
		return DataContext.getProvider();
	}

	/**
	 * Categorías profesionales
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<CategoriaProfesional> getCategoriasProfesionales(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<CategoriaProfesional>) getDataProvider().getList(CategoriaProfesional.class, filter, order == null ? NOMBRE : order);
	}

	@SuppressWarnings("unchecked")
	public boolean hasClienteContactos(Cliente cliente) throws LocalizedException {

		if (getDataProvider() == null) {
			return false;
		}

		Collection<ClienteContacto> list = (Collection<ClienteContacto>) getDataProvider().getList(ClienteContacto.class, "empresa_id = " + cliente.getId(), null);
		return list != null && list.size() > 0;
	}

	/**
	 * Clientes
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Cliente> getClientes(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<Cliente>) getDataProvider().getList(Cliente.class, filter, order == null ? "nombre" : order);
	}

	/**
	 * Contactos de clientes
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<ClienteContacto> getClienteContactos(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<ClienteContacto>) getDataProvider().getList(ClienteContacto.class, filter, order == null ? NOMBRE : order);
	}

	/**
	 * Contactos de proveedores
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<ProveedorContacto> getProveedorContactos(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<ProveedorContacto>) getDataProvider().getList(ProveedorContacto.class, filter, order == null ? NOMBRE : order);
	}

	/**
	 * Departamentos
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Departamento> getDepartamentos(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<Departamento>) getDataProvider().getList(Departamento.class, filter, order == null ? NOMBRE : order);
	}

	/**
	 * Empleados
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Empleado> getEmpleados(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<Empleado>) getDataProvider().getList(Empleado.class, filter, order == null ? null : order);
	}

	/**
	 * Empresas
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Empresa> getEmpresas(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<Empresa>) getDataProvider().getList(Empresa.class, filter, order == null ? "nombre" : order);
	}

	/**
	 * Productos
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Producto> getProductos(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<Producto>) getDataProvider().getList(Producto.class, filter, order == null ? NOMBRE : order);
	}

	/**
	 * Tarifas de productos
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<ProductoTarifa> getProductoTarifas(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : (Collection<ProductoTarifa>) getDataProvider().getList(ProductoTarifa.class, filter, order == null ? null : order);
	}

	/**
	 * Proveedores
	 * 
	 * @param filter
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Proveedor> getProveedores(String filter, String order) throws LocalizedException {

		return getDataProvider() == null ? null : getDataProvider() == null ? null : (Collection<Proveedor>) getDataProvider().getList(Proveedor.class, filter, order == null ? "nombre" : order);
	}
}
