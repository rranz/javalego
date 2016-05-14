package com.javalego.locale.translator;

/**
 * Factoría para instanciar las diferentes tipologías de traductores para la
 * localización de textos.
 * 
 * @author ROBERTO RANZ
 *
 */
public class TranslatorFactory {

	private TranslatorFactory() {
	}

	/**
	 * Traductor basado en archivos de recursos.
	 * 
	 * @param lowercase
	 *            Cambiar a minúsculas la clave de búsqueda.
	 * @param resources
	 *            Lista de archivos de paquetes de recursos
	 */
	public static Translator getTranslatorResources(boolean lowercase, String... resources) {
		return new TranslatorResources(lowercase, resources);
	}

	/**
	 * Traductor basado en enumerados y anotaciones localizadas.
	 */
	public static Translator getTranslatorDefault() {
		return new TranslatorCode();
	}
}
