package com.javalego.util.url;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class ProxyTest {

	public static void main(String args[]) {
		try {
			//getSocksProxy();
			Text();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void Text() {
    try {
      System.setProperty("java.net.useSystemProxies","true");

  		URL ur = new URL("http://www.hedimaonline.es/gana_aproser/services/DataProvider");			

      HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
      try {
	      conn.setConnectTimeout(2000);
	      //int code = conn.getResponseCode();
	      UrlFile f = new UrlFile();
	      f.setFileName("c:\\temporal\\d.txt");
	      f.setUrl("http://www.hedimaonline.es/gana_aproser/services/DataProvider");
	      f.execute();
	      //InputStream s = conn.getInputStream();
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
    	
    	
        System.setProperty("java.net.useSystemProxies","true");
        List list = ProxySelector.getDefault().select(
                    new URI("http://www.hedimaonline.es/"));

        for (Iterator iter = list.iterator(); iter.hasNext(); ) {

        	java.net.Proxy proxy = (java.net.Proxy) iter.next();

            System.out.println("proxy hostname : " + proxy.type());

            InetSocketAddress addr = (InetSocketAddress)
                proxy.address();

            if(addr == null) {

                System.out.println("No Proxy");

            } else {

                System.out.println("proxy hostname : " +
                        addr.getHostName());

                System.out.println("proxy port : " +
                        addr.getPort());

            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
	}
	
	@SuppressWarnings("rawtypes")
	public static void getSocksProxy() throws Exception
	{
			System.setProperty("java.net.useSystemProxies","true");
			//String hostname=new String();
			//String port=new String();
			String url="http://www.yahoo.com/";
			url = "http://192.168.41.85";
			List l = ProxySelector.getDefault().select(new URI(url));
			
			ProxySelector.getDefault().select(new URI(url)).toString();
			
			URL ur = new URL("http://www.hedimaonline.com/" );			

      HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
      System.out.println(conn.getResponseCode());
			
			
			//URLConnection c = new HttpURLConnection();
			
      
      
			for (Iterator iter = l.iterator(); iter.hasNext(); ) {
			//java.net.Proxy proxy = (java.net.Proxy) iter.next();
			
			System.out.println("proxy hostname : ");
			}
//			InetSocketAddress addr = (InetSocketAddress)proxy.address();
//			if(addr == null) {
//			System.out.println("No Proxy");
//			} else {
//			System.out.println("proxy hostname : " + addr.getHostName());
//			hostname=addr.getHostName();
//			System.out.println("proxy port : " + addr.getPort());
//			port=String.valueOf(addr.getPort());
			

	}
	
	
}
