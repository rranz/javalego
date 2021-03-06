/*
 * $Id: DataConnection.java,v 1.1 2005/02/27 00:24:55 rbair Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package com.javalego.util.sql;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Maintains a connection to some underlying data store. Generally used by a
 * DataProvider to interact with the DataStore, however, it is not specifically
 * necessary for a DataProvider to use a DataConnection.
 *
 * @author rbair
 */
public abstract class DataConnection implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Logger used for log statements
	 */
	private static final Logger LOG = Logger.getLogger(DataConnection.class.getName());

	/**
	 * Helper used for notifying of bean property changes. In particular, this
	 * is used to notify of changes in the connection status
	 */
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/**
	 * Flag indicating the logical connection status of this DataConnection. It
	 * is possible for the DataConnection to be logically connected but
	 * physically disconnected (ie HTTP), or vice versa.
	 */
	private boolean connected = false;

	/**
	 * Manages the connected state of this DataConnection. The connected state
	 * is this DataConnections "logical" connection status. The underlying
	 * connection may be disconnected, and yet the DataConnection may still be
	 * "logically" connected. For example, an HTTP based connection may not be
	 * persistent, but is always "logically" connected.
	 * <p/>
	 * It is possible for listeners to be attached to the DataConnection that
	 * can react to the connection status. For instance, a status bar icon might
	 * change to indicate its disconnected status. Or DataSets could be flushed
	 * of their data.
	 * 
	 * @param b
	 *            If true, opens any necessary connections to the source. For
	 *            instance, this could be used to open a connection to a
	 *            database, or to a URL. If false, then any current connections
	 *            are logically closed, and may be physically closed, depending
	 *            on the DataConnection implementation
	 * @throws IOException
	 */
	public void setConnected(boolean b) {
		if (b && !connected) {
			try {
				LOG.fine("Attempting to connect to the data store");
				connect();
				connected = true;
				LOG.fine("Connected to the data store, firing property change event");
				pcs.firePropertyChange("connected", false, true);
			}
			catch (Exception e) {
				LOG.log(Level.SEVERE, "Failed to connect to the data store.", e);
				// no need to fire a property change event here, because
				// connected was
				// false when this method began
				connected = false;
			}
		}
		else if (!b && connected) {
			try {
				LOG.fine("Attempting to disconnect from the data store");
				disconnect();
			}
			catch (Exception e) {
				LOG.log(Level.WARNING, "Failed to physically disconnect from the data store, " + "but will continue to logically disconnect from the data store.", e);
				e.printStackTrace();
			}
			finally {
				connected = false;
				LOG.fine("Logically disconnected from the data store, firing property change event");
				pcs.firePropertyChange("connected", true, false);
			}
		}
	}

	/**
	 * @return whether the DataStoreConnection is logically connected or not.
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Optional method to make a connection to the data store. This method is
	 * called whenever the connection state changes from !connected to
	 * connected. It is up to the child class implementation to manage the
	 * physical connection to the data store. This method may or may not be
	 * useful in that context.
	 * 
	 * @throws Exception
	 */
	protected abstract void connect() throws Exception;

	/**
	 * Optional method to disconnect from the data store. This method is called
	 * whenever the connection state changes from connected to !connected. It is
	 * up to the child class implementation to manage the physical connection to
	 * the data store. This method may or may not be useful in that context.
	 * 
	 * @throws Exception
	 */
	protected abstract void disconnect() throws Exception;

	/**
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	/**
	 * 
	 * @param propertyName
	 * @param listener
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

}
