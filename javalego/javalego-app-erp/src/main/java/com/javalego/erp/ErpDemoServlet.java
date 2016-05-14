package com.javalego.erp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

/**
 * Vaadin Servlet
 * 
 * @throws Exception
 */
@WebServlet(urlPatterns = "/*", name = "ErpDemoAppUIServlet", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = ErpDemoUI.class)
public class ErpDemoServlet extends VaadinServlet {

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		/*
		 * Configure the viewport meta tags appropriately on mobile devices.
		 * Instead of device based scaling (default), using responsive layouts.
		 * 
		 * If using Vaadin TouchKit, this is done automatically and it is
		 * sufficient to have an empty servlet class extending TouchKitServlet.
		 */
		getService().addSessionInitListener(new ViewPortSessionInitListener());
	}
}
