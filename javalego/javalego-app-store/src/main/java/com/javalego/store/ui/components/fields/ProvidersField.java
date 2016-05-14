package com.javalego.store.ui.components.fields;

import java.util.Collection;
import java.util.HashSet;

import com.javalego.exception.LocalizedException;
import com.javalego.store.items.IProvider;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.store.ui.components.UIFactory;
import com.javalego.ui.vaadin.component.OptionGroupResponsive;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;

@SuppressWarnings("rawtypes")
public class ProvidersField extends CustomField<Collection> {

	private static final long serialVersionUID = 4818991708852923486L;

	private OptionGroupResponsive<IProvider> optionsGroup;

	@Override
	protected Component initContent() {
		
		if (isReadOnly()) {
			return getReadOnly();
		}
		else {
			try {
				return getEdit();
			}
			catch (LocalizedException e) {
				MessagesUtil.error(e);
				return null;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private Component getReadOnly() {

		return UIFactory.getProviders(getValue());
	}

	@SuppressWarnings("unchecked")
	private Component getEdit() throws LocalizedException {

		CssLayout layout = new CssLayout();
		layout.setWidth("100%");

		optionsGroup = new OptionGroupResponsive("120px");

		optionsGroup.setReadOnly(isReadOnly());

		optionsGroup.setWidth("100%");

		for (IProvider c : StoreAppContext.getCurrent().getDataServices().getAllProviders()) {

			// Buscar en providers actuales del proyecto.
			boolean selected = false;
			Collection<IProvider> providers = (Collection<IProvider>) super.getInternalValue();
			if (providers != null) {
				for (IProvider p : providers) {
					if (p.getName().equals(c.getName())) {
						selected = true;
						break;
					}
				}
			}

			optionsGroup.addItem(c, c.getIcon(), selected);
		}
		layout.addComponent(optionsGroup);

		return layout;
	}

	@Override
	public Class<Collection> getType() {
		return Collection.class;
	}

	@Override
	protected void setInternalValue(Collection provider) {

		super.setInternalValue(provider);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IProvider> getInternalValue() {

		Collection<IProvider> providers = null;
		
		if (optionsGroup != null) {

			providers = new HashSet<IProvider>();
			
			for(IProvider p : optionsGroup.getSelectedItems()) {
				providers.add(p);
			}
		}
		else {
			providers = super.getInternalValue();
		}
		return providers;
	}

}
