package com.javalego.store.ui;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

/**
 * Vaadin Servlet 
 * 
 * @throws Exception
 */
@WebServlet(value = { "/home/*", "/VAADIN/*" }, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = StoreUI.class)
public class StoreServlet extends VaadinServlet {

	private static final long serialVersionUID = 7412428125364055047L;

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
