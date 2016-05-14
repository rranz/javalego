package com.javalego.util;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.javalego.exception.JavaLegoException;

/**
 * Clase de utilidad para trabajar con par&aacute;metros por referencia de tipo
 * fichero usando URI.
 * 
 */
public class FileURIHandler extends URIHandler {
	private static final String FILE_SCHEME_TYPE = "file";

	/**
	 * Construye un objeto para ayudar a manejar una URI. Constructor por
	 * defecto. La URI es inv&aacute;lida.
	 */
	protected FileURIHandler() {
		super();
	}

	/**
	 * Construye un objeto para ayudar a manejar una URI.
	 * 
	 * @param uri
	 *            URL del recurso de tipo fichero.
	 */
	public FileURIHandler(URI uri) {
		super(uri);
		int newLimit = UNDEFINED_LIMIT;
		this.setResourceLimit(newLimit);
	}

	/**
	 * Obtiene la lista de URI de los recursos que contiene la URI. Si no hay
	 * recursos devuelve un array vac&iacute;o. Para una URI remota de un
	 * servidor Windows usar un esquema similar a:
	 * <code>file://///server/share/path</code>.
	 * 
	 * @throws JavaLegoException
	 *             Si no es posible acceder a la URI.
	 */
	@Override
	public URI[] getURIResources() throws JavaLegoException {
		List<URI> uris = new ArrayList<URI>();
		File resourceDir = this.getThisFile();
		File[] files = resourceDir.listFiles();
		if (files.length == 0) {
			throw new JavaLegoException("URI_RESOURCES_NOT_FOUND");
		}
		for (File file : files) {
			uris.add(file.toURI());
		}
		return uris.toArray(new URI[uris.size()]);
	}

	private File getThisFile() throws JavaLegoException {
		if (this.getURI() == null)
			throw new JavaLegoException("ERROR_INVALID_URI");

		// Mantiene las 5 barras '/////' para identificar recursos en máquinas
		// remotas.
		URL url;
		try {
			url = this.getURI().toURL();
			if (url == null || !url.getProtocol().equals(FILE_SCHEME_TYPE)) {
				throw new JavaLegoException("ERROR_INVALID_URI");
			}
			return new File(this.getURI());
		}
		catch (MalformedURLException e) {
			throw new JavaLegoException("ERROR_INVALID_URI", e);
		}
	}

	/**
	 * @param extension
	 * @param ignoreCase
	 * @return {@link File}[] array de ficheros que cumplen el filtro.
	 * @throws JavaLegoException
	 */
	public URI[] listFilesWithExtension(final String extension, final boolean ignoreCase) throws JavaLegoException {
		File[] files = this.getThisFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name == null)
					return false;
				if (ignoreCase)
					return name.toLowerCase().endsWith(extension.toLowerCase());
				return name.endsWith(extension);
			}
		});
		URI[] uris = new URI[files.length];
		for (int i = 0; i < files.length; i++) {
			uris[i] = files[i].toURI();
		}
		return uris;
	}

	/**
	 * @param filterFileName
	 * @param ignoreCase
	 * @return {@link File}[] array de ficheros que cumplen el filtro.
	 * @throws JavaLegoException
	 */
	public URI[] listFilesStartingWith(final String filterFileName, final boolean ignoreCase) throws JavaLegoException {
		File[] files = this.getThisFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name == null)
					return false;
				if (ignoreCase)
					return name.toLowerCase().startsWith(filterFileName.toLowerCase());
				return name.toLowerCase().startsWith(filterFileName.toLowerCase());
			}
		});

		URI[] uris = new URI[files.length];
		for (int i = 0; i < files.length; i++) {
			uris[i] = files[i].toURI();
		}
		return uris;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists() {
		return new File(getURI()).exists();
	}

	// private String encodeURIComponent(String s)
	// {
	// String result;
	//
	// try
	// {
	// result = URLEncoder.encode(s, "UTF-8")
	// .replaceAll("\\+", "%20")
	// .replaceAll("\\%21", "!")
	// .replaceAll("\\%27", "'")
	// .replaceAll("\\%28", "(")
	// .replaceAll("\\%29", ")")
	// .replaceAll("\\%7E", "~");
	// }
	// catch (UnsupportedEncodingException e)
	// {
	// result = s;
	// }
	//
	// return result;
	// }
}