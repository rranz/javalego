package com.javalego.ui.binding.bean;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.javalego.ui.binding.Property;

/**
 * Abstract base class for {@link Property} implementations.
 * 
 * Handles listener management for {@link ValueChangeListener}s and
 * {@link ReadOnlyStatusChangeListener}s.
 * 
 * @since 6.6
 */
public abstract class AbstractProperty<T> implements Property<T>, Property.ValueChangeNotifier, Property.ReadOnlyStatusChangeNotifier {

	private static final long serialVersionUID = 6552545999206047316L;

	/**
	 * List of listeners who are interested in the read-only status changes of
	 * the Property
	 */
	private LinkedList<ReadOnlyStatusChangeListener> readOnlyStatusChangeListeners = null;

	/**
	 * List of listeners who are interested in the value changes of the Property
	 */
	private LinkedList<ValueChangeListener> valueChangeListeners = null;

	/**
	 * Is the Property read-only?
	 */
	private boolean readOnly;

	/**
	 * {@inheritDoc}
	 * 
	 * Override for additional restrictions on what is considered a read-only
	 * property.
	 */
	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	@Override
	public void setReadOnly(boolean newStatus) {
		boolean oldStatus = isReadOnly();
		readOnly = newStatus;
		if (oldStatus != isReadOnly()) {
			fireReadOnlyStatusChange();
		}
	}

	/* Events */

	/**
	 * An <code>Event</code> object specifying the Property whose read-only
	 * status has been changed.
	 */
	protected static class ReadOnlyStatusChangeEvent extends java.util.EventObject implements Property.ReadOnlyStatusChangeEvent {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7950083891094647616L;

		/**
		 * Constructs a new read-only status change event for this object.
		 * 
		 * @param source
		 *            source object of the event.
		 */
		@SuppressWarnings("rawtypes")
		protected ReadOnlyStatusChangeEvent(Property source) {
			super(source);
		}

		/**
		 * Gets the Property whose read-only state has changed.
		 * 
		 * @return source Property of the event.
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Property getProperty() {
			return (Property) getSource();
		}

	}

	/**
	 * Registers a new read-only status change listener for this Property.
	 * 
	 * @param listener
	 *            the new Listener to be registered.
	 */
	@Override
	public void addReadOnlyStatusChangeListener(Property.ReadOnlyStatusChangeListener listener) {
		if (readOnlyStatusChangeListeners == null) {
			readOnlyStatusChangeListeners = new LinkedList<ReadOnlyStatusChangeListener>();
		}
		readOnlyStatusChangeListeners.add(listener);
	}

	/**
	 * Removes a previously registered read-only status change listener.
	 * 
	 * @param listener
	 *            the listener to be removed.
	 */
	@Override
	public void removeReadOnlyStatusChangeListener(Property.ReadOnlyStatusChangeListener listener) {
		if (readOnlyStatusChangeListeners != null) {
			readOnlyStatusChangeListeners.remove(listener);
		}
	}

	/**
	 * Sends a read only status change event to all registered listeners.
	 */
	protected void fireReadOnlyStatusChange() {
		if (readOnlyStatusChangeListeners != null) {
			final Object[] l = readOnlyStatusChangeListeners.toArray();
			final Property.ReadOnlyStatusChangeEvent event = new ReadOnlyStatusChangeEvent(this);
			for (int i = 0; i < l.length; i++) {
				((Property.ReadOnlyStatusChangeListener) l[i]).readOnlyStatusChange(event);
			}
		}
	}

	/**
	 * An <code>Event</code> object specifying the Property whose value has been
	 * changed.
	 */
	private static class ValueChangeEvent extends java.util.EventObject implements Property.ValueChangeEvent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3200962330436560645L;

		/**
		 * Constructs a new value change event for this object.
		 * 
		 * @param source
		 *            source object of the event.
		 */
		@SuppressWarnings("rawtypes")
		protected ValueChangeEvent(Property source) {
			super(source);
		}

		/**
		 * Gets the Property whose value has changed.
		 * 
		 * @return source Property of the event.
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Property getProperty() {
			return (Property) getSource();
		}

		@Override
		public Object getCurrentValue() {
			return getProperty().getValue();
		}

	}

	@Override
	public void addValueChangeListener(ValueChangeListener listener) {
		if (valueChangeListeners == null) {
			valueChangeListeners = new LinkedList<ValueChangeListener>();
		}
		valueChangeListeners.add(listener);

	}

	@Override
	public void removeValueChangeListener(ValueChangeListener listener) {
		if (valueChangeListeners != null) {
			valueChangeListeners.remove(listener);
		}

	}

	/**
	 * Sends a value change event to all registered listeners.
	 */
	protected void fireValueChange() {
		if (valueChangeListeners != null) {
			final Object[] l = valueChangeListeners.toArray();
			final Property.ValueChangeEvent event = new ValueChangeEvent(this);
			for (int i = 0; i < l.length; i++) {
				((Property.ValueChangeListener) l[i]).valueChange(event);
			}
		}
	}

	public Collection<?> getListeners(Class<?> eventType) {
		if (Property.ValueChangeEvent.class.isAssignableFrom(eventType)) {
			if (valueChangeListeners == null) {
				return Collections.EMPTY_LIST;
			}
			else {
				return Collections.unmodifiableCollection(valueChangeListeners);
			}
		}
		else if (Property.ReadOnlyStatusChangeEvent.class.isAssignableFrom(eventType)) {
			if (readOnlyStatusChangeListeners == null) {
				return Collections.EMPTY_LIST;
			}
			else {
				return Collections.unmodifiableCollection(readOnlyStatusChangeListeners);
			}
		}

		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings("unused")
	private static Logger getLogger() {
		return Logger.getLogger(AbstractProperty.class.getName());
	}
}
