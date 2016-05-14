package com.javalego.util;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

/**
 * Utilidades para trabajar con procesos http
 * 
 */
public class HttpUtil {

	/**
	 * Convierte los parametros de una url en una hashtable.
	 * 
	 * @param parametros
	 *            Cadena a resolver con formato a=cami%F3n&b=asdf
	 * @param salida
	 *            Hashtable en la que meter los valores, no tiene porqué estar
	 *            vacía
	 * @param encode
	 *            String con la codificación de los parámetros "UTF-8",
	 *            "latin1", etc
	 */
	public static void encodedParams2hash(String parametros,
			Hashtable<String, String> salida, String encode) {
		String lstrClave = "";
		String lstrValor = "";

		String[] lstrParts = parametros.split("\\&");
		int lintNPars = lstrParts.length;

		for (int j = 0; j < lintNPars; j++) {
			String[] lstrVal = lstrParts[j].split("=");
			lstrClave = lstrVal[0];
			if (lstrVal.length > 1) {
				lstrValor = lstrVal[1];
			} else {
				lstrValor = "";
			}
			try {
				lstrValor = URLDecoder.decode(lstrValor, encode);
			} catch (Exception e) {
				// No se pudo convertir el encoding, lo devolvemos como está
			}

			if (salida.containsKey(lstrClave)) {
				lstrValor = ((String) salida.get(lstrClave)) + "," + lstrValor;
			}

			salida.put(lstrClave, lstrValor);

		}

	}

	/**
	 * Realiza una peticción http GET devolviendo un String con los datos
	 * devueltos.
	 * 
	 * @param sUrl
	 *            Dirección http a resolver
	 * @return String con el texto devuelto
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String httpGet(String sUrl) throws MalformedURLException,
			IOException {
		return httpGet_Completo(sUrl, null, null, null, null, null, null);

	}

	public static String httpGet(String sUrl, int timeoutMs)
			throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, null, null, null, null, null, null, null,
				timeoutMs, 0);

	}

	/**
	 * Realiza una peticción http GET utilizando el proxy especificado
	 * 
	 * @param sUrl
	 *            Dirección http a resolver
	 * @param sProxy
	 *            Nombre del proxy, p.e. proxy.bde.es
	 * @param sProxyPort
	 *            Puerto del proxy, p.e. 80
	 * @return String con el texto devuelto
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String httpGet(String sUrl, String sProxy, String sProxyPort)
			throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, null, null, null,
				null);
	}

	public static String httpGet(String sUrl, String sProxy, String sProxyPort,
			int timeoutMs) throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, null, null, null,
				null, null, timeoutMs, 0);
	}

	/**
	 * Realiza una peticción http GET utilizando el proxy especificado con login
	 * en el proxy
	 * 
	 * @param sUrl
	 *            Dirección http a resolver
	 * @param sProxy
	 *            Nombre del proxy, p.e. proxy.bde.es
	 * @param sProxyPort
	 *            Puerto del proxy, p.e. 80
	 * @return String con el texto devuelto
	 * @throws MalformedURLException
	 * @throws IOException
	 * @deprecated En el BdE la identificación contra el proxy es por la sesión
	 *             de windows. Hay que ejecutar el proceso como un servicio
	 *             windows.
	 */
	public static String httpGetWithProxyLogin(String sUrl, String sProxy,
			String sProxyPort, String sProxyUser, String sProxyPwd)
			throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, sProxyUser,
				sProxyPwd, null, null);
	}

	public static String httpGetWithProxyLogin(String sUrl, String sProxy,
			String sProxyPort, String sProxyUser, String sProxyPwd,
			int timeoutMs) throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, sProxyUser,
				sProxyPwd, null, null, null, timeoutMs, 0);
	}

	public static String httpGetWithUrlLogin(String sUrl, String sProxy,
			String sProxyPort, String sUrlUsr, String sUrlPwd)
			throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, null, null, sUrlUsr,
				sUrlPwd);
	}

	public static String httpGetWithUrlLogin(String sUrl, String sProxy,
			String sProxyPort, String sUrlUsr, String sUrlPwd, int timeoutMs)
			throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, null, null, sUrlUsr,
				sUrlPwd, null, timeoutMs, 0);
	}

	/**
	 * Petición de una página con logado en el proxy y en la página destino
	 * 
	 * @deprecated En el BdE la identificación contra el proxy es por la sesión
	 *             de windows. Hay que ejecutar el proceso como un servicio
	 *             windows.
	 */
	public static String httpGetWithProxyAndUrlLogin(String sUrl,
			String sProxy, String sProxyPort, String sProxyUser,
			String sProxyPwd, String sUrlUser, String sUrlPwd)
			throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, sProxyUser,
				sProxyPwd, sUrlUser, sUrlPwd, null, 0, 0);
	}

	public static String httpGetWithProxyAndUrlLogin(String sUrl,
			String sProxy, String sProxyPort, String sProxyUser,
			String sProxyPwd, String sUrlUser, String sUrlPwd, int timeoutMs)
			throws MalformedURLException, IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, sProxyUser,
				sProxyPwd, sUrlUser, sUrlPwd, null, timeoutMs, 0);
	}

	private static String httpGet_Completo(String sUrl, String sProxy,
			String sProxyPort, String sProxyUser, String sProxyPwd,
			String urluser, String urlpassword) throws MalformedURLException,
			IOException {
		return httpGet_Completo(sUrl, sProxy, sProxyPort, sProxyUser,
				sProxyPwd, urluser, urlpassword, null, 0, 0);

	}

	/**
	 * Conexión con identificación básica o nula
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	private static String httpGet_Completo(String sUrl, String sProxy,
			String sProxyPort, String sProxyUser, String sProxyPwd,
			String urluser, String urlpassword, String pCookies, int timeoutMs,
			int numeroDeRedireccionActual) throws MalformedURLException,
			IOException {
		URL url = new URL(sUrl);
		HttpURLConnection con = null;

		// Creamos la conexión con la url
		if (sProxy != null && sProxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					sProxy, Integer.parseInt(sProxyPort)));

			con = (HttpURLConnection) url.openConnection(proxy);
		} else {
			con = (HttpURLConnection) url.openConnection();
		}
		// Evitamos que se sigan los redirects ya que los redirects los hace sin
		// pasar las cookies lo cual es un bug.
		con.setInstanceFollowRedirects(false);
		// Evitamos el uso de respuestas cacheadas
		con.setUseCaches(false);

		// Fijamos los timeouts. Si se indica un valor 0 o negativo entonces no
		// se fija ningún timeout
		if (timeoutMs > 0) {
			con.setConnectTimeout(new Integer(timeoutMs).intValue());
			con.setReadTimeout(new Integer(timeoutMs).intValue());
		}

		// Identificación ante el proxy
		if (!StringUtils.isEmpty(sProxyUser)) {
			String auth = "Basic "
					+ (new sun.misc.BASE64Encoder()
							.encode((sProxyUser + ":" + sProxyPwd).getBytes()));
			con.setRequestProperty("Proxy-Authorization", auth);

		}

		// Identificación ante la página
		if (!StringUtils.isEmpty(urluser)) {
			String auth = "Basic "
					+ (new sun.misc.BASE64Encoder()
							.encode((urluser + ":" + urlpassword).getBytes()));
			con.setRequestProperty("Authorization", auth);
		}

		// Enviamos las cookies
		if (!StringUtils.isEmpty(pCookies)) {
			con.setRequestProperty("Cookie", pCookies);
		}

		// Se modifica el User-Agent para que funcione el Test AGE-1 del Tester
		con.setRequestProperty(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");

		// Respuesta de la petición
		con.connect();
		InputStream in = con.getInputStream();
		StringBuffer sb = new StringBuffer();

		int c;
		while ((c = in.read()) != -1)
			sb.append((char) c);
		int responseCode = con.getResponseCode();
		String cookies = HttpUtil.getResponseHeader((URLConnection) con,
				"Set-Cookie");
		if (!StringUtils.isEmpty(pCookies) && !StringUtils.isEmpty(cookies)) {
			cookies = pCookies + "; " + cookies;
		}
		String location = HttpUtil.getResponseHeaderLocation(sUrl,
				(URLConnection) con);
		con.disconnect();

		// Comprobación de si es una redirección
		if (responseCode == 301 || responseCode == 302) {
			if (numeroDeRedireccionActual < 10) {
				return httpGet_Completo(location, sProxy, sProxyPort,
						sProxyUser, sProxyPwd, null, null, cookies, timeoutMs,
						numeroDeRedireccionActual + 1);
			} else {
				throw new IOException(
						"Se han producido 10 redirecciones consecutivas al solicitar la página web. Se cancela la petición");
			}
		} else {
			return sb.toString();

		}

	}

	/*
	 * public static String httpGetWithWebSealLogin(String sUrl, String
	 * urlLogin, String urlLogout, String urluser, String urlpassword) throws
	 * MalformedURLException, IOException { return httpGetWithWebSealLogin(
	 * sUrl, urlLogin, urlLogout, urluser, urlpassword, 0); }
	 * 
	 * public static String httpGetWithWebSealLogin(String sUrl, String
	 * urlLogin, String urluser, String urlpassword) throws
	 * MalformedURLException, IOException { String urlLogout = ""; urlLogout =
	 * urlLogin.replace("pkmslogin.form","pkmslogout"); return
	 * httpGetWithWebSealLogin( sUrl, urlLogin, urlLogout, urluser, urlpassword,
	 * 0); }
	 */

	public static String httpGetWithWebFormLogin(String sUrl, String urlLogin,
			String urlLogout, List<String[]> params, String paramEncoding,
			String proxy, String proxyport) throws MalformedURLException,
			IOException {
		return httpGetWithWebSealLogin(sUrl, urlLogin, urlLogout, params,
				paramEncoding, 0, proxy, proxyport, null, null);
	}

	/*
	 * TODO: REMOVE THIS? public static String httpGetWithWebSealLogin(String
	 * sUrl, String urlLogin, String urluser, String urlpassword, int timeoutMs)
	 * throws MalformedURLException, IOException { String urlLogout = "";
	 * urlLogout = urlLogin.replace("pkmslogin.form","pkmslogout"); return
	 * httpGetWithWebSealLogin( sUrl, urlLogin, urlLogout, urluser, urlpassword,
	 * timeoutMs); }
	 * 
	 * public static String httpGetWithWebSealLogin(String sUrl, String
	 * urlLogin, String urlLogout, String urluser, String urlpassword,int
	 * timeoutMs) throws MalformedURLException, IOException { return
	 * httpGetWithWebSealLogin( sUrl, urlLogin, urlLogout, urluser, urlpassword,
	 * timeoutMs, null , null, null, null); }
	 */
	/**
	 * Conexión con identificación web forms via POST mediante parámetros
	 */
	@SuppressWarnings("restriction")
	public static String httpGetWithWebSealLogin(String sUrl, String urlLogin,
			String urlLogout, List<String[]> params, String paramEncoding,
			int timeoutMs, String sProxy, String sProxyPort, String sProxyUser,
			String sProxyPwd) throws MalformedURLException, IOException {

		// Primera petición autenticándonos ante webseal
		// ---------------------------------------------

		URL url;
		HttpURLConnection urlConn;
		DataOutputStream printout;
		DataInputStream in;
		// URL of CGI-Bin script.
		url = new URL(urlLogin);
		// URL connection channel.

		if (sProxy != null && sProxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					sProxy, Integer.parseInt(sProxyPort)));

			urlConn = (HttpURLConnection) url.openConnection(proxy);
		} else {
			urlConn = (HttpURLConnection) url.openConnection();
		}

		// Fijamos los timeouts
		if (timeoutMs > 0) {
			urlConn.setConnectTimeout(timeoutMs);
			urlConn.setReadTimeout(timeoutMs);
		}
		// Let the run-time system (RTS) know that we want input.
		urlConn.setDoInput(true);
		// Let the RTS know that we want to do output.
		urlConn.setDoOutput(true);
		// No caching, we want the real thing.
		urlConn.setUseCaches(false);
		// Specify the content type.
		urlConn.setRequestProperty("Referer", sUrl);
		// urlConn.setRequestProperty("Cookie", cookie);
		urlConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// Identificación ante el proxy
		if (!StringUtils.isEmpty(sProxyUser)) {
			String auth = "Basic "
					+ (new sun.misc.BASE64Encoder()
							.encode((sProxyUser + ":" + sProxyPwd).getBytes()));
			urlConn.setRequestProperty("Proxy-Authorization", auth);

		}

		// Add params
		String content = "";
		for (String[] p : params) {
			// Add param separator if needed
			if (content.length() > 0)
				content += "&";

			// Add param, check it is well formed
			if (p != null && p.length == 2 && p[0] != null && p[1] != null)
				content += URLEncoder.encode(p[0], paramEncoding) + "="
						+ URLEncoder.encode(p[1], paramEncoding);
		}

		// Send POST output.
		printout = new DataOutputStream(urlConn.getOutputStream());
		printout.writeBytes(content);
		printout.flush();
		printout.close();

		// Get response data.
		in = new DataInputStream(urlConn.getInputStream());
		StringBuffer sb = new StringBuffer("");
		int c;
		while ((c = in.read()) != -1)
			sb.append((char) c);
		// System.out.println(sb.toString());

		int code = urlConn.getResponseCode();
		if (code != 200) {
			throw new IOException(
					"El sitio web devolvió un código HTTP de error: " + code);
		}

		// Recorremos los headers para memorizar la cookie que nos ha devuelto
		// el web site
		String cookie2 = HttpUtil.getResponseHeader(urlConn, "Set-Cookie");
		if (StringUtils.isEmpty(cookie2)) {
			throw new IOException(
					"El sitio web no admitió el usuario o la contraseña indicadas ya que no hay cookies.");
		}

		String sRetorno = "";
		// Una vez que el web site nos ha dado la cookie, realizamos la petición
		// que deseabamos ---------------------------------------------
		try {
			sRetorno = HttpUtil.httpGet_Completo(sUrl, sProxy, sProxyPort,
					sProxyUser, sProxyPwd, null, null, cookie2, timeoutMs, 0);
			// System.out.println(sRetorno);
			return sRetorno;

		} catch (Exception e) {
			throw new IOException(
					"No se ha podido realizar la petición HTTP al sistema monitorizado: "
							+ (e.getMessage() == null ? "(sin información adicional)"
									: e.getMessage()));
		} finally {
			// Cerramos la sesión en web site
			try {
				if (!StringUtils.isEmpty(cookie2)
						&& !StringUtils.isEmpty(urlLogout)) {
					HttpUtil.httpGet_Completo(urlLogout, sProxy, sProxyPort,
							sProxyUser, sProxyPwd, null, null, cookie2,
							timeoutMs, 0);
				}
			} catch (Exception e) {
				Logger.getRootLogger()
						.error("No se ha podido realizar el logOut del sitio web: "
								+ (e.getMessage() == null ? "(sin información adicional)"
										: e.getMessage()));
			}
		}

	}

	/**
	 * Recupera los valores de un header devueltas por la conexión, separando
	 * todos los valores por "; "
	 * 
	 * @param urlConn
	 *            Conexión que contiene la página devuelta
	 * @param header
	 *            Nombre del header a recuperar
	 * @param textoAExcluir
	 *            Indica el texto que no se debe borrar de los headers
	 * @throws IOException
	 */
	private static String getResponseHeader(URLConnection urlConn, String header)
			throws IOException {
		String valor = "";
		Map<String, List<String>> hs = urlConn.getHeaderFields();
		Iterator<String> it = hs.keySet().iterator();
		while (it.hasNext()) {
			String k = valNotNull((String) it.next());
			if (header.toLowerCase().equals(k.toLowerCase())) {
				List<String> ls = (List<String>) hs.get(k);
				Iterator<String> it2 = ls.iterator();
				while (it2.hasNext()) {
					valor = valor + "; "
							+ ((String) it2.next()).replace("; Path=/", "");
					// valor = ((String) it2.next()).replace("; Path=/","")+
					// "; "+valor;
				}
			}
		}

		if (valor.length() > 0) {
			valor = valor.substring(1);
		}

		return valor;
	}

	private static String valNotNull(String next) {
		return next == null ? "" : next;
	}

	public static String urlAbsoluta(String urlRelativa, String urlBase) {
		String sal = "";
		if (urlRelativa.length() > 7
				&& urlRelativa.substring(0, 7).equalsIgnoreCase("http://")) {
			// Ya era una dirección absoluta
			sal = urlRelativa;
		} else if (urlRelativa.length() > 8
				&& urlRelativa.substring(0, 8).equalsIgnoreCase("https://")) {
			// Ya era una dirección absoluta
			sal = urlRelativa;
		} else if (urlRelativa.startsWith("/")) {
			// La url relativa parte de la raiz
			String dominio = urlBase.split("/")[2];

			String protocolo = urlBase.split("/")[0];
			sal = protocolo + "//" + dominio + urlRelativa;

		} else if (urlRelativa.startsWith("#")) {
			// La url es un ancla al documento
			sal = urlBase + urlRelativa;
		} else if (urlRelativa.toLowerCase().startsWith("javascript:")) {
			sal = "";
		} else {
			// La url es relativa a la posicición actual
			// Buscamos la primera barra después de las dos barras.
			int i = urlBase.indexOf("/", urlBase.indexOf("//") + 2);
			if (i == -1) {
				// Ejemplo: http://www.bde.es noticias/bla.htm
				sal = urlBase + "/" + urlRelativa;
			} else {
				// Ejemplo: http://www.bde.es/home.htm noticias/bla.htm
				String dominio = urlBase.substring(0, urlBase.lastIndexOf("/"));
				sal = dominio + "/" + urlRelativa;
			}

		}
		if (sal.indexOf("/", 7) != -1) {
			String d = sal.substring(0, sal.indexOf("/", 7)); // http://www.bd.es
			String r = sal.substring(sal.indexOf("/", 7)); // /estadis/esta.htm
			// Sustituímos /bbb/../ por /
			// Ojo: es obligatorio que aaa exista
			String patron = "/[^/]*/\\.\\./";
			Pattern pattern = Pattern.compile(patron);
			Matcher matcher;
			String salAnt = "";
			while (!salAnt.equals(r)) {
				salAnt = r;
				matcher = pattern.matcher(r);
				// Hay que hacerlo de uno en uno, sino /../../ se acaba
				// sustituyendo por /../ lo que es incorrecto
				r = matcher.replaceFirst("/");
			}
			sal = d + r;

			// Sustituímos /./ por /
			while (sal.indexOf("/./") != -1) {
				sal = sal.replaceAll("/\\./", "/");
			}
			// http://www.bde.es/../home.htm equivale a
			// http://www.bde.es/home.htm
			while (sal.indexOf("/../") != -1) {
				sal = sal.replaceAll("/\\.\\./", "/");
			}
		}
		return sal;

	}

	/**
	 * Descarga una página http guardándola en un fichero local. Sólo funciona
	 * cuando no es necesario utilizar un proxy
	 * 
	 * @param uri
	 *            dirección http a descargar
	 * @param localFileName
	 *            Fichero en el que guardar la descarga
	 * @throws ApplicationException .
	 */
	public static void httpDownload(String uri, String localFileName)
			throws Exception {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(uri);
			out = new BufferedOutputStream(new FileOutputStream(localFileName));
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}
			// System.out.println(localFileName + "\t" + numWritten);
			// System.err.println(numWritten);
		} catch (Exception exception) {
			throw new Exception("Error al descargar " + uri, exception);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
			}
		}
	}

	/**
	 * Realiza una peticción https GET utilizando el proxy especificado
	 * 
	 * @param sUrl
	 *            Dirección http a resolver
	 * @param sProxy
	 *            Nombre del proxy, p.e. proxy.bde.es
	 * @param sProxyPort
	 *            Puerto del proxy, p.e. 80
	 * @return String con el texto devuelto
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String httpsGet(String sUrl, String sProxy,
			String sProxyPort, int timeoutMs, String pCookies,
			int numeroDeRedireccionActual) throws MalformedURLException,
			IOException {

		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {

			return null;
		}

		HttpsURLConnection con = null;
		URL url = new URL(sUrl);

		// Creamos la conexión con la url
		if (sProxy != null && sProxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					sProxy, Integer.parseInt(sProxyPort)));

			con = (HttpsURLConnection) url.openConnection(proxy);
		} else {
			con = (HttpsURLConnection) url.openConnection();
		}

		// Evitamos que se sigan los redirects ya que los redirects los hace sin
		// pasar las cookies lo cual es un bug.
		con.setInstanceFollowRedirects(false);
		// Evitamos el uso de respuestas cacheadas
		con.setUseCaches(false);

		// Fijamos los timeouts
		if (timeoutMs > 0) {
			con.setConnectTimeout(timeoutMs);
			con.setReadTimeout(timeoutMs);
		}

		// Enviamos las cookies
		if (!StringUtils.isEmpty(pCookies)) {
			con.setRequestProperty("Cookie", pCookies);
		}

		// Respuesta de la petición
		con.connect();

		InputStream in = con.getInputStream();
		StringBuffer sb = new StringBuffer();

		int c;
		while ((c = in.read()) != -1)
			sb.append((char) c);

		int responseCode = con.getResponseCode();
		String cookies = HttpUtil.getResponseHeader((URLConnection) con,
				"Set-Cookie");
		if (!StringUtils.isEmpty(pCookies) && !StringUtils.isEmpty(cookies)) {
			cookies = pCookies + "; " + cookies;
		}
		String location = HttpUtil.getResponseHeaderLocation(sUrl,
				(URLConnection) con);
		con.disconnect();

		// Comprobación de si es una redirección
		if (responseCode == 301 || responseCode == 302) {
			if (numeroDeRedireccionActual < 10) {
				Logger.getRootLogger().debug("Redirigiendo a " + location);
				Logger.getRootLogger().debug("Cookies enviadas:" + cookies);
				return httpsGet(location, sProxy, sProxyPort, timeoutMs,
						cookies, numeroDeRedireccionActual);
			} else {
				throw new IOException(
						"Se han producido 10 redirecciones consecutivas al solicitar la página web. Se cancela la petición");
			}
		} else {
			return sb.toString();

		}
	}

	private static String getResponseHeaderLocation(String urlActual,
			URLConnection con) throws IOException {
		String location = HttpUtil.getResponseHeader((URLConnection) con,
				"Location");
		if (!StringUtils.isEmpty(location)) {
			location = location.trim();
			if (location.startsWith("/")) {
				StringTokenizer st = new StringTokenizer(urlActual, "/", true); // true
																				// para
																				// que
																				// devuelva
																				// los
																				// separadores
																				// como
																				// tokens
				String domain = "";
				if (st.countTokens() < 4) {
					throw new IOException(
							"No se puede extraer el dominio de la url: "
									+ urlActual);
				} else {
					domain = st.nextToken() + st.nextToken() + st.nextToken()
							+ st.nextToken();
				}
				location = domain + location;
			}

		}
		return location;
	}

	/**
	 * Realiza una peticción https GET utilizando el proxy especificado (con
	 * certificado)
	 * 
	 * @param sUrl
	 *            Dirección http a resolver
	 * @param sProxy
	 *            Nombre del proxy, p.e. proxy.bde.es
	 * @param sProxyPort
	 *            Puerto del proxy, p.e. 80
	 * @return String con el texto devuelto
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String httpsGetCertificado(String sUrl, String sProxy,
			String sProxyPort, int timeoutMs, String alias, String keyStore,
			String trustStore, String keyStorePassword,
			String trustStorePassword, String pCookies,
			int numeroDeRedireccionActual) throws MalformedURLException,
			IOException {

		try {
			SSLSocketFactory sc = new SSLSocketFactoryGenerator(alias,
					keyStore, trustStore, keyStorePassword, trustStorePassword)
					.getSSLSocketFactory();
			HttpsURLConnection.setDefaultSSLSocketFactory(sc);

		} catch (Exception e) {

			return null;
		}

		HttpsURLConnection con = null;
		URL url = new URL(sUrl);

		// Creamos la conexión con la url
		if (sProxy != null && sProxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					sProxy, Integer.parseInt(sProxyPort)));

			con = (HttpsURLConnection) url.openConnection(proxy);
		} else {
			con = (HttpsURLConnection) url.openConnection();
		}

		// Evitamos que se sigan los redirects ya que los redirects los hace sin
		// pasar las cookies lo cual es un bug.
		con.setInstanceFollowRedirects(false);
		// Evitamos el uso de respuestas cacheadas
		con.setUseCaches(false);

		// Fijamos los timeouts
		if (timeoutMs > 0) {
			con.setConnectTimeout(timeoutMs);
			con.setReadTimeout(timeoutMs);
		}
		// Respuesta de la petición
		con.connect();

		InputStream in = con.getInputStream();
		StringBuffer sb = new StringBuffer();

		int c;
		while ((c = in.read()) != -1)
			sb.append((char) c);
		int responseCode = con.getResponseCode();
		String cookies = HttpUtil.getResponseHeader((URLConnection) con,
				"Set-Cookie");
		if (!StringUtils.isEmpty(pCookies) && !StringUtils.isEmpty(cookies)) {
			cookies = pCookies + "; " + cookies;
		}
		String location = HttpUtil.getResponseHeaderLocation(sUrl,
				(URLConnection) con);
		con.disconnect();

		// Comprobación de si es una redirección
		if (responseCode == 301 || responseCode == 302) {
			if (numeroDeRedireccionActual < 10) {
				return httpsGetCertificado(location, sProxy, sProxyPort,
						timeoutMs, alias, keyStore, trustStore,
						keyStorePassword, trustStorePassword, cookies,
						numeroDeRedireccionActual + 1);
			} else {
				throw new IOException(
						"Se han producido 10 redirecciones consecutivas al solicitar la página web. Se cancela la petición");
			}
		} else {
			return sb.toString();

		}
	}

}
