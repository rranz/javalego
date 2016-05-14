package com.javalego.ui.vaadin.component.util;

import java.io.File;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WebBrowser;

/**
 * Utilidades relativas al navegador donde se está ejecutando la aplicación de la sesión del usuario.
 * 
 * @author ROBERTO RANZ
 */
public class BrowserUtil {

	public static boolean isIExplorer() {
		return Page.getCurrent().getWebBrowser().isIE();
	}

	public static boolean isOpera() {
		return Page.getCurrent().getWebBrowser().isOpera();
	}

	public static boolean isChrome() {
		return Page.getCurrent().getWebBrowser().isChrome();
	}

	public static boolean isFirefox() {
		return Page.getCurrent().getWebBrowser().isFirefox();
	}

	public static boolean isSafari() {
		return Page.getCurrent().getWebBrowser().isSafari();
	}

	public static boolean isTouchDevice() {
		return Page.getCurrent().getWebBrowser().isTouchDevice();
	}

	public static boolean isWindows() {
		return Page.getCurrent().getWebBrowser().isWindows();
	}

	public static boolean isLinux() {
		return Page.getCurrent().getWebBrowser().isLinux();
	}

	public static boolean isAndroid() {
		return Page.getCurrent().getWebBrowser().isAndroid();
	}

	public static boolean isIOS() {
		return Page.getCurrent().getWebBrowser().isIOS();
	}

	public static boolean isMacOX() {
		return Page.getCurrent().getWebBrowser().isMacOSX();
	}

	public static String getAddress() {
		return Page.getCurrent().getWebBrowser().getAddress();
	}

	public static int getHeight() {
		return Page.getCurrent().getBrowserWindowHeight();
	}

	public static int getWidth() {
		return Page.getCurrent().getBrowserWindowWidth();
	}

	/**
	 * Obtiene la url de la aplicación Ej.: http://localhost:8080/application
	 * 
	 * @return
	 */
	public static String getUrl() {

		String url = Page.getCurrent().getLocation().toString();

		// quitar el tag de la vista actual
		return url.indexOf("#") > -1 ? url.substring(0, url.indexOf("#") - 1) : url;
	}

	/**
	 * Obtener el nombre del sistema operativo
	 * 
	 * @param webBrowser
	 * @return
	 */
	public static String getOperatingSystem(WebBrowser webBrowser) {
		if (webBrowser.isWindows()) {
			return "Windows";
		}
		else if (webBrowser.isMacOSX()) {
			return "Mac OSX";
		}
		else if (webBrowser.isLinux()) {
			return "Linux";
		}
		else {
			return "an unknown operating system";
		}
	}

	/**
	 * ID de la sesión del usuario.
	 * 
	 * @return
	 */
	public static String getSessionID() {
		return VaadinSession.getCurrent().getSession().getId();
	}

	/**
	 * Dispositivo móvil con un ancho de pantalla inferior a 480px.
	 * @return
	 */
	public static boolean isSmallScreenDevice() {
		float viewPortWidth = Page.getCurrent().getBrowserWindowWidth();
		return viewPortWidth < 480;
	}

	/**
	 * Dispositivo móvil con un ancho de pantalla superior a 1024px.
	 * @return
	 */
	public static boolean isLargeScreenDevice() {
		float viewPortWidth = Page.getCurrent().getBrowserWindowWidth();
		return viewPortWidth > 1024;
	}
	
	/**
	 * Dispositivo móvil con un ancho de pantalla estándar entre 480px y 1024px.
	 * @return
	 */
	public static boolean isStandarScreenDevice() {
		float viewPortWidth = Page.getCurrent().getBrowserWindowWidth();
		return viewPortWidth >= 480 && viewPortWidth <= 1024;
	}

	/**
	 * Directorio base donde está físicamente grabada la aplicación en el servidor de aplicaciones.
	 * @return
	 */
	public static File getFileBaseDir() {
		return VaadinService.getCurrent().getBaseDirectory();
	}
}
