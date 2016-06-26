package com.javalego.icons.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import com.javalego.errors.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.icons.ResourceRepositoryIcons;
import com.javalego.icons.types.IconItem;
import com.javalego.icons.types.ResourceIcon;
import com.javalego.icons.types.UriIcon;
import com.javalego.icons.types.UrlIcon;
import com.javalego.model.AbstractBaseModel;
import com.javalego.model.keys.Icon;

/**
 * Repositorio de recursos de iconos cargados a partir de una lista de
 * enumerados y de directorios de recursos de imágenes.
 * <p>
 * El nombre de enumerados y de imágenes (nombre en minúsculas) debe coincidir
 * para su correcta localización.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ResourceIcons extends AbstractBaseModel implements ResourceRepositoryIcons<Icon> {

	private static final long serialVersionUID = 7680493582749570937L;

	private static Logger logger = Logger.getLogger(ResourceIcons.class);

	/**
	 * Localización de los recursos
	 */
	private Locale locale = null;

	public ResourceIcons() {
	}

	public ResourceIcons(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Model Lista de iconos registrados.
	 */
	protected HashMap<String, IconItem> icons = new HashMap<String, IconItem>();

	@Override
	public void addUrl(String key, String url) {

		if (icons.get(key) != null) {
			logger.error("ICON KEY '" + key + "' EXIST.");
		}

		icons.put(key, new UrlIcon(url));
	}

	@Override
	public void addUri(String key, String uri) {

		if (icons.get(key) != null) {
			logger.error("ICON KEY '" + key + "' EXIST.");
		}

		icons.put(key, new UriIcon(uri));
	}

	@Override
	public void addResource(String key, String resource) {

		if (icons.get(key) != null) {
			logger.error("ICON KEY '" + key + "' EXIST.");
		}

		icons.put(key, new ResourceIcon(resource));
	}

	@Override
	public ResourceIcons load(Class<?> enumIcons, String path) throws LocalizedException {
		load(enumIcons, path, null);
		return this;
	}

	@Override
	public ResourceIcons load(Class<?> enumIcons, Locale locale, String path) throws LocalizedException {
		load(enumIcons, path, null);
		this.locale = locale;
		return this;
	}

	@Override
	public ResourceIcons load(String name, Class<?> enumIcons, Locale locale, String path) throws LocalizedException {
		load(enumIcons, path, null);
		setName(name);
		this.locale = locale;
		return this;
	}

	@Override
	public ResourceIcons load(Class<?> enumIcons, String path, String ext) throws LocalizedException {

		// Comprueba que la clase sea un enumerado.
		if (enumIcons == null || !enumIcons.isEnum()) {
			throw new LocalizedException(CommonErrors.NOT_ENUM, enumIcons == null ? "class is null" : enumIcons.getSimpleName());
		}
		
		// Localizar el path de ubicación del directorio dentro de los recursos
		// de la aplicación classpath + jars.
		URL dirURL = getClass().getResource(path);

		if (dirURL == null) {
			dirURL = getClass().getClassLoader().getResource(path);
		}

		if (dirURL == null) {
			logger.error("URL " + path + " NOT EXIST. RESOURCE NOT FOUND");
			return this;
		}

		List<String> list = new ArrayList<String>();

		// Si el directory está en un jar.
		if (dirURL.getProtocol().equals("jar")) {

			String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
			JarFile jar = null;
			try {
				jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));

				Enumeration<JarEntry> entries = jar.entries();

				while (entries.hasMoreElements()) {

					String name = entries.nextElement().getName();

					// logger.debug(name);

					if (name.startsWith(path.substring(1))) {

						// filter according to the path
						String entry = name.substring(path.length());

						int checkSubdir = entry.indexOf("/");

						if (checkSubdir >= 0) {
							// if it is a subdirectory, we just return the
							// directory name
							entry = entry.substring(0, checkSubdir);
						}
						if (!"".equals(entry) && entry.indexOf(".") > 0) {
							list.add(entry);
						}
					}
				}
			}
			catch (IOException e) {
				logger.error("ERROR READ JAR FILES '" + jarPath + "'.", e);
			}
			finally {
				if (jar != null) {
					try {
						jar.close();
					}
					catch (IOException e) {
						logger.error("ERROR CLOSE JAR '" + jarPath + "'.", e);
					}
				}
			}
		}
		// Si el directorio es un fichero en disco ubicado en la aplicación.
		else if (dirURL.getProtocol().equals("file")) {

			String fpath = null;
			try {
				fpath = URLDecoder.decode(dirURL.getFile(), "UTF-8");

				File file = new File(fpath);

				if (file.isDirectory()) {

					for (File item : file.listFiles()) {

						list.add(item.getName());
					}
				}
				else {
					logger.warn("FILE " + file.getAbsolutePath() + " NOT DIRECTORY.");
				}
			}
			catch (IOException e) {
				logger.error("ERROR READ JAR FILES '" + fpath + "'.", e);
			}
		}

		if (list.size() != 0) {

			// Recorrer todas las enumeraciones y buscar el archivo en el
			// directorio
			for (Object value : Arrays.asList(enumIcons.getEnumConstants())) {

				boolean exist = false;

				for (String item : list) {

					if ((ext != null && item.toUpperCase().equals((value.toString() + "." + ext).toUpperCase()) || (ext == null && item.toUpperCase().indexOf((value.toString() + ".").toUpperCase()) == 0))) {

						addResource(enumIcons.getSimpleName() + "." + value.toString(), path + "/" + item);

						exist = true;

						break;
					}
				}
				if (!exist) {
					logger.warn("ICON '" + value + "' NO EXIST.");
				}
			}
		}
		else {
			logger.warn("ICONS NOT FOUND INTO '" + dirURL);
		}

		return this;
	}

	/**
	 * Buscar icono en la lista cargada.
	 * 
	 * @param key
	 * @return
	 * @throws LocalizedException
	 */
	@Override
	public byte[] getBytes(String key) throws LocalizedException {

		IconItem icon = icons.get(key);
		if (icon == null) {
			throw new LocalizedException(CommonErrors.NOT_FOUND_ICON, key);
		}
		else {
			try {
				return icon.getBytes();
			}
			catch (Exception e) {
				throw new LocalizedException(e.getMessage());
			}
		}
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
	 * Obtener el nombre de un icono
	 * 
	 * @param key
	 * @return
	 */
	protected String getIconString(Icon key) {

		return key == null ? null : key.getClass().getSimpleName() + "." + key.name();
	}

	/**
	 * Buscar el icono por su clave
	 * 
	 * @param key
	 * @return
	 */
	protected IconItem getIcon(String key) {

		IconItem icon = icons.get(key);
		if (icon == null) {
			return null;
		}
		else {
			return icon;
		}
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Establecer la localización de los recursos
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public boolean exist(String key) {
		return icons.get(key) != null;
	}

	@Override
	public String[] getNames() {
		return icons.keySet().toArray(new String[icons.keySet().size()]);
	}

}
