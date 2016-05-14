package com.javalego.ui.mvp.editor.rules;

import java.io.Serializable;

/**
 * Listeners para capturar los cambios realizados en la edición.
 * 
 * @author ROBERTO RANZ
 */
public interface IEditionRulesListener {

    /**
     * The interface for adding and removing <code>ValueChangeEvent</code>
     * listeners. If a Property wishes to allow other objects to receive
     * <code>ValueChangeEvent</code> generated by it, it must implement this
     * interface.
     * <p>
     * Note : The general Java convention is not to explicitly declare that a
     * class generates events, but to directly define the
     * <code>addListener</code> and <code>removeListener</code> methods. That
     * way the caller of these methods has no real way of finding out if the
     * class really will send the events, or if it just defines the methods to
     * be able to implement an interface.
     * </p>
     * 
     * @author Vaadin Ltd.
     * @since 3.0
     */
	public interface ValueChangeNotifier extends Serializable {

	    /**
	     * Registers a new value change listener for this Property.
	     * 
	     * @param listener
	     *            the new Listener to be registered
	     */
	    public void addValueChangeListener(ValueChangeListener listener);

	    /**
	     * Removes a previously registered value change listener.
	     * 
	     * @param listener
	     *            listener to be removed
	     */
	    public void removeValueChangeListener(ValueChangeListener listener);
	}	

    /**
     * The <code>listener</code> interface for receiving
     * <code>ValueChangeEvent</code> objects.
     * 
     * @author ROBERTO RANZ
     */	
	public interface ValueChangeListener extends Serializable {

        /**
         * Notifies this listener that the Property's value has changed.
         * 
         * @param event
         *            value change event object
         */
        public void valueChange(ValueChangeEvent event);
    }
    
    /**
     * An <code>Event</code> object specifying the Property whose value has been changed.
     * 
     * @author ROBERTO RANZ
     * @since 3.0
     */	
    public interface ValueChangeEvent extends Serializable {

        /**
         * Retrieves old value that has been modified.
         * 
         * @return value
         */
        Object getOldValue();

        /**
         * Retrieves field that has been modified.
         * 
         * @return value
         */
        String getFieldName();

        /**
         * Retrieves new value that has been modified.
         * 
         * @return value
         */
        Object getValue();
        
        /**
         * Cancelar 
         */
        void discard();
        
        /**
         * Reglas de edición para realizar todo tipo de cambios en la edición.
         * @return
         */
        IEditionRules getEditorRules();
        
    }

	void valueChange(ValueChangeEvent event); 	
}
