package com.javalego.ui.vaadin.icons;

import java.util.HashMap;

import com.javalego.exception.LocalizedException;
import com.javalego.icons.impl.ResourceIcons;
import com.javalego.icons.types.IconItem;
import com.javalego.icons.types.ResourceIcon;
import com.javalego.model.keys.Icon;
import com.javalego.ui.icons.RepositoryIconsUI;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Link;

/**
 * Recursos de iconos que obtienen componentes UI para Vaadin.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ResourceIconsVaadin extends ResourceIcons implements RepositoryIconsUI<Embedded, Icon> {

	private static final long serialVersionUID = -6892705058503896554L;

	/**
	 * Instancia actual
	 */
	private static ResourceIconsVaadin current = null;

	private static HashMap<String, Resource> resources = new HashMap<String, Resource>();

	/**
	 * Obtener la instancia actual de los servicios de iconos.
	 * 
	 * @return
	 */
	public synchronized static ResourceIconsVaadin getCurrent() {

		if (current == null) {
			current = new ResourceIconsVaadin();
		}
		return current;
	}

	@Override
	public Embedded getComponent(String caption, Icon key) throws LocalizedException {

		return getComponent(caption, getIconString(key));
	}

	@Override
	public Embedded getComponent(String caption, String description, Icon key) throws LocalizedException {

		Embedded e = getComponent(caption, getIconString(key));
		e.setDescription(description);

		return e;
	}

	//@Override
	public Embedded getComponent(String key) throws LocalizedException {

		return getComponent(null, key);
	}

	//@Override
	public Embedded getComponent(String caption, String key) throws LocalizedException {

		Embedded e = new Embedded(caption, getResource(key));
		return e;
	}

	/**
	 * Componente de tipo Link de Vaadin.
	 * 
	 * @param url
	 * @param key
	 * @return
	 * @throws LocalizedException
	 */
	public Link getLink(String url, Icon key) throws LocalizedException {

		Link link = new Link(null, new ExternalResource(url));
		link.setIcon(getResource(getIconString(key)));
		link.setTargetName("_blank");
		return link;
	}

	@Override
	public Embedded getComponent(Icon key) throws LocalizedException {

		return getComponent(getIconString(key));
	}

	/**
	 * Obtiene un ClassResource del icono pasado como parámetro.
	 * 
	 * @param key
	 * @return
	 * @throws LocalizedException
	 */
	public Resource getResource(Icon key) {

		return getResource(getIconString(key));
	}

	@Override
	public byte[] getBytes(Icon key) throws LocalizedException {

		return getBytes(getIconString(key));
	}

	@Override
	public boolean exist(Icon key) {

		return icons.get(getIconString(key)) != null;
	}

	/**
	 * Obtiene un ClassResource del icono pasado como parámetro.
	 * 
	 * @param key
	 * @return
	 * @throws LocalizedException
	 */
	public Resource getResource(String key) {

		Resource resource = resources.get(key);

		if (resource == null) {

			IconItem icon = getIcon(key);

			if (icon != null) {

				resource = new ClassResource(((ResourceIcon) icon).getResource());

				resources.put(key, resource);

			}
			else {
				return null;
			}
		}
		return resource;
	}

	//@Override
	public Embedded getComponent(String key, int size) throws LocalizedException {

		Embedded e = new Embedded(null, getResource(key));
		e.setSizeUndefined();

		if (size > 0) {
			e.setWidth(size + "px");
			e.setHeight(size + "px");
		}

		return e;
	}

	@Override
	public Embedded getComponent(Icon key, int size) throws LocalizedException {

		return getComponent(getIconString(key), size);
	}

}
