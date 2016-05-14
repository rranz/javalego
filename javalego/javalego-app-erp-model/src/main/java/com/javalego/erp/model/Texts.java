package com.javalego.erp.model;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Definición de textos de la aplicación en varios idiomas. Los códigos
 * enumerados sirven como referencia y las anotaciones permiten obtener, en
 * tiempo de ejecución, el texto en el idioma de la sesión de usuario.
 * 
 * 
 * @author ROBERTO RANZ
 * 
 */
public enum Texts implements Key {

	@Languages(locales = { @Locale(value = "Nombre"), @Locale(locale = "en", value = "Name") })
	NOMBRE,

	@Languages(locales = { @Locale(value = "Descripción"), @Locale(locale = "en", value = "Description") })
	DESCRIPCION,

	@Languages(locales = { @Locale(value = "Datos completos"), @Locale(locale = "en", value = "Full data") })
	DATOS_COMPLETOS,

	@Languages(locales = { @Locale(value = "Razón social"), @Locale(locale = "en", value = "Company name") })
	RAZONSOCIAL,

	@Languages(locales = { @Locale(value = "Domicilio"), @Locale(locale = "en", value = "Address") })
	DIRECCION,

	@Languages(locales = { @Locale(value = "Código postal"), @Locale(locale = "en", value = "Zip code") })
	CODIGOPOSTAL,

	@Languages(locales = { @Locale(value = "Foto"), @Locale(locale = "en", value = "Photo") })
	FOTO,

	@Languages(locales = { @Locale(value = "País"), @Locale(locale = "en", value = "Country") })
	PAIS,

	@Languages(locales = { @Locale(value = "Correo electrónico"), @Locale(locale = "en", value = "Email") })
	EMAIL,

	@Languages(locales = { @Locale(value = "Teléfono de empresa"), @Locale(locale = "en", value = "Phone number") })
	TELEFONO,

	@Languages(locales = { @Locale(value = "Dirección web"), @Locale(locale = "en", value = "URL") })
	URL,

	@Languages(locales = { @Locale(value = "Localidad"), @Locale(locale = "en", value = "City") })
	LOCALIDAD,

	@Languages(locales = { @Locale(value = "Provincia"), @Locale(locale = "en", value = "State") })
	PROVINCIA,

	@Languages(locales = { @Locale(value = "Representante"), @Locale(locale = "en", value = "Agent") })
	REPRESENTANTE,

	@Languages(locales = { @Locale(value = "Fecha creación"), @Locale(locale = "en", value = "Created date") })
	FECHACREACION,

	@Languages(locales = { @Locale(value = "Empresas"), @Locale(locale = "en", value = "Companies") })
	EMPRESAS,

	@Languages(locales = { @Locale(value = "Empresa"), @Locale(locale = "en", value = "Company") })
	EMPRESA,

	@Languages(locales = { @Locale(value = "Proveedores"), @Locale(locale = "en", value = "Providers") })
	PROVEEDORES,

	@Languages(locales = { @Locale(value = "Proveedor"), @Locale(locale = "en", value = "Provider") })
	PROVEEDOR,

	@Languages(locales = { @Locale(value = "Clientes"), @Locale(locale = "en", value = "Customers") })
	CLIENTES,

	@Languages(locales = { @Locale(value = "Cliente"), @Locale(locale = "en", value = "Customer") })
	CLIENTE,

	@Languages(locales = { @Locale(value = "Contactos"), @Locale(locale = "en", value = "Contacts") })
	CONTACTOS,

	@Languages(locales = { @Locale(value = "Contacto"), @Locale(locale = "en", value = "Contact") })
	CONTACTO,

	@Languages(locales = { @Locale(value = "Productos"), @Locale(locale = "en", value = "Products") })
	PRODUCTOS,

	@Languages(locales = { @Locale(value = "Tarifas de Productos"), @Locale(locale = "en", value = "Rates Products") })
	PRODUCTOS_TARIFAS,

	@Languages(locales = { @Locale(value = "Producto"), @Locale(locale = "en", value = "Product") })
	PRODUCTO,

	@Languages(locales = { @Locale(value = "Categorías"), @Locale(locale = "en", value = "Categories") })
	CATEGORIAS,

	@Languages(locales = { @Locale(value = "Categoría"), @Locale(locale = "en", value = "Category") })
	CATEGORIA,

	@Languages(locales = { @Locale(value = "Exportar datos"), @Locale(locale = "en", value = "Export data") })
	EXPORTAR_DATOS,

	@Languages(locales = { @Locale(value = "Importar datos"), @Locale(locale = "en", value = "Import data") })
	IMPORTAR_DATOS,

	@Languages(locales = { @Locale(value = "Plantilla de datos"), @Locale(locale = "en", value = "Template data") })
	PLANTILLA_DATOS,

	@Languages(locales = { @Locale(value = "Empresas de Madrid"), @Locale(locale = "en", value = "Madrid Companies") })
	FILTRAR_MADRID,

	@Languages(locales = { @Locale(value = "CIF"), @Locale(locale = "en", value = "CIF") })
	CIF,

	@Languages(locales = { @Locale(value = "Pyme"), @Locale(locale = "en", value = "Pyme") })
	PYME,

	@Languages(locales = { @Locale(value = "Logotipo"), @Locale(locale = "en", value = "Logo") })
	LOGO,

	@Languages(locales = { @Locale(value = "Departamentos"), @Locale(locale = "en", value = "Departaments") })
	DEPARTAMENTOS,

	@Languages(locales = { @Locale(value = "Departamento"), @Locale(locale = "en", value = "Departament") })
	DEPARTAMENTO,

	@Languages(locales = { @Locale(value = "Categorías profesionales"), @Locale(locale = "en", value = "Professional categories") })
	CATEGORIAS_PROFESIONALES,

	@Languages(locales = { @Locale(value = "Categoría profesional"), @Locale(locale = "en", value = "Professional category") })
	CATEGORIA_PROFESIONAL,

	@Languages(locales = { @Locale(value = "Personal"), @Locale(locale = "en", value = "Staff") })
	PERSONAL,

	@Languages(locales = { @Locale(value = "Ventas"), @Locale(locale = "en", value = "Sales") })
	VENTAS,

	@Languages(locales = { @Locale(value = "Recursos Humanos"), @Locale(locale = "en", value = "RRHH") })
	RRHH,

	@Languages(locales = { @Locale(value = "Compras"), @Locale(locale = "en", value = "Shopping") })
	COMPRAS,

	@Languages(locales = { @Locale(value = "Módulos"), @Locale(locale = "en", value = "Modules") })
	MODULOS,

	@Languages(locales = { @Locale(value = "Facturas"), @Locale(locale = "en", value = "Invoices") })
	FACTURAS,

	@Languages(locales = { @Locale(value = "Identificador"), @Locale(locale = "en", value = "PartNumber") })
	IDENTIFICADOR,

	@Languages(locales = { @Locale(value = "Homologado"), @Locale(locale = "en", value = "Homologated") })
	HOMOLOGADO,

	@Languages(locales = { @Locale(value = "Activo"), @Locale(locale = "en", value = "Active") })
	ACTIVO,

	@Languages(locales = { @Locale(value = "Compra"), @Locale(locale = "en", value = "Buy") })
	COMPRA,

	@Languages(locales = { @Locale(value = "Venta"), @Locale(locale = "en", value = "Sale") })
	VENTA,

	@Languages(locales = { @Locale(value = "Almacenado"), @Locale(locale = "en", value = "Stored") })
	ALMACENADO,

	@Languages(locales = { @Locale(value = "Características"), @Locale(locale = "en", value = "Features") })
	CARACTERISTICAS,

	@Languages(locales = { @Locale(value = "Tarifa"), @Locale(locale = "en", value = "Rate") })
	TARIFA,

	@Languages(locales = { @Locale(value = "Tarifa estándar"), @Locale(locale = "en", value = "Standar Rate") })
	TARIFA_ESTANDAR,

	@Languages(locales = { @Locale(value = "Precio mínimo"), @Locale(locale = "en", value = "Min Rate") })
	TARIFA_LIMITE,

	@Languages(locales = { @Locale(value = "Imagen"), @Locale(locale = "en", value = "Picture") })
	IMAGEN,

	@Languages(locales = { @Locale(value = "Importar contacto"), @Locale(locale = "en", value = "Import Contact") })
	IMPORTAR_CONTACTO,

	@Languages(locales = { @Locale(value = "Añadir contacto"), @Locale(locale = "en", value = "Add Contact") })
	INSERTAR_CONTACTO,

	@Languages(locales = { @Locale(value = "Empleados"), @Locale(locale = "en", value = "Staff") })
	EMPLEADOS,

	@Languages(locales = { @Locale(value = "Empleado"), @Locale(locale = "en", value = "Staff") })
	EMPLEADO,

	@Languages(locales = { @Locale(value = "Fecha de alta"), @Locale(locale = "en", value = "Created date") })
	FECHA_ALTA,

	@Languages(locales = { @Locale(value = "NIF / NIE"), @Locale(locale = "en", value = "Employer ID") })
	NIF,

	@Languages(locales = { @Locale(value = "Móvil"), @Locale(locale = "en", value = "Mobile phone") })
	MOVIL,

	@Languages(locales = { @Locale(value = "Tipo"), @Locale(locale = "en", value = "Type") })
	TIPO,

	@Languages(locales = { 
			@Locale(value = "Información de acceso"),
			@Locale(locale = "en", value = "Login information"),
			})
	LOGIN, 

	@Languages(locales = { 
			@Locale(value = "Esta aplicación está actualmente en fase beta. Los datos incluídos son ficticios y no persistentes. Incluya el usuario 'admin' y password 'admin' para iniciar sesión."),
			@Locale(locale = "en", value = "This application is currently in beta. The included data are fictional and not persistent. Include the username 'admin' and password 'admin' to login."),
			})
	LOGIN_DATA, 	

	@Languages(locales = { 
			@Locale(value = "TEST", description = "Entorno de TEST"),
			@Locale(locale = "en", value = "TEST", description = "TEST Environment")
			})
	TEST,	

}
