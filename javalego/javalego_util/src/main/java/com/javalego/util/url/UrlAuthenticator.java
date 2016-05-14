package com.javalego.util.url;

import java.net.PasswordAuthentication;

/**
 * Clase que permite la autentificación del usuario en el proxy.
 */
public class UrlAuthenticator extends java.net.Authenticator {
  String user;
  String password;
  String proxyUser;
  String proxyPassword;

  String host;
  int port;
  String proxyHost;
  int proxyPort;

  public UrlAuthenticator(String user, String password, String proxyUser, String proxyPassword) {
    this.user = user;
    this.password = password;
    this.proxyUser = proxyUser;
    this.proxyPassword = proxyPassword;
  }

  public UrlAuthenticator(String proxyUser, String proxyPassword) {
    this.proxyUser = proxyUser;
    this.proxyPassword = proxyPassword;
  }

	@Override
  public PasswordAuthentication getPasswordAuthentication() {
	  String reqHost = getRequestingHost();
	  int reqPort = getRequestingPort();
	
	  if (reqHost != null && reqHost.equals(host) && reqPort == port) {
      return new PasswordAuthentication(user, password.toCharArray());
	  }
	
    if (reqHost != null && reqHost.equals(proxyHost) && reqPort == proxyPort) {
	    return new PasswordAuthentication(proxyUser, proxyPassword.toCharArray());
    }
	  return null;
  }

  public void setHost(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void setProxy(String proxyHost, int proxyPort) {
    this.proxyHost = proxyHost;
    this.proxyPort = proxyPort;
  }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}

