package com.javalego.erp.model.editors;

import java.util.Collection;

import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.erp.model.entity.Empresa;
import com.javalego.exception.LocalizedException;
import com.javalego.security.annotation.PermitAll;
import com.javalego.ui.UIContext;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.mvp.beans.view.list.adapter.ListBeansViewAdapter;

/**
 * Formulario de mantenimiento de Empresas
 * 
 * @author ROBERTO RANZ
 * 
 */
@PermitAll
public class EditorEmpresas extends BaseEditor<Empresa> {

	private static final long serialVersionUID = 5921778441774881778L;

	/**
	 * Constructor para inicializar modelo.
	 */
	public EditorEmpresas() {

		setTitle(Texts.EMPRESAS);
		setIcon(Icons.COMPANIES);
		setEditionTitle(Texts.EMPRESA);
		
		// Vistas de datos del bean
		addFormatView(new ListBeansViewAdapter<Empresa>(UIContext.getText(Texts.NOMBRE) + " + Cif + " + UIContext.getText(Texts.LOCALIDAD), LOGO) {
			@Override
			public String toHtml(Empresa bean) {
				return "<h2>" + bean.getNombre() + "</h2>Id: <b>" + bean.getId() + "</b><br><i>Cif: <b>" + bean.getCif() + "</b></i>"
						+ (bean.getLocalidad() != null ? "<br>" + bean.getLocalidad() : "");
			}
		});

		addFormatView(new ListBeansViewAdapter<Empresa>(UIContext.getText(Texts.RAZONSOCIAL) + " + " + UIContext.getText(Texts.NOMBRE), LOGO) {
			@Override
			public String toHtml(Empresa bean) {
				return (bean.getRazon_social() == null ? "" : "<h2>" + bean.getRazon_social() + "</h2>") + bean.getNombre();
			}
		});

	}

	@Override
	public Collection<Empresa> getBeans(String filter, String order) throws LocalizedException {

		return getDataServices().getEmpresas(filter, order);
	}

	@Override
	public Class<Empresa> getBeanClass() {
		return Empresa.class;
	}

	@Override
	public Collection<FieldModel> getFieldModels() {

		return ModeloEmpresa.getFieldModels();
	}

	@Override
	public Collection<IFilterService> getFilters() {

		return ModeloEmpresa.getFilters();
	}

	@Override
	public Empresa getNewBean() {

		Empresa e = new Empresa();
		// País por defecto obtenido de la localización de la sesión de usuario.
		e.setPais(UIContext.getCurrent().getCountry().toUpperCase());
		return e;
	}

}
