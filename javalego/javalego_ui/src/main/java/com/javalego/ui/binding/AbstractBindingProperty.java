package com.javalego.ui.binding;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Abstract base class for {@link Property} implementations.
 * 
 * Handles listener management for {@link ValueChangeListener}s and
 * {@link ReadOnlyStatusChangeListener}s.
 * 
 * @author Vaadin Ltd. (adaptación para meccano4j)
 * 
 */
public abstract class AbstractBindingProperty<T> implements Property<T>, Property.ValueChangeNotifier, Property.ReadOnlyStatusChangeNotifier {

	private static final long serialVersionUID = 6081860366861210841L;

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
		private static final long serialVersionUID = -3804333357035889701L;

		/**
		 * Constructs a new read-only status change event for this object.
		 * 
		 * @param source
		 *            source object of the event.
		 */
		protected ReadOnlyStatusChangeEvent(Property<?> source) {
			super(source);
		}

		/**
		 * Gets the Property whose read-only state has changed.
		 * 
		 * @return source Property of the event.
		 */
		@Override
		public Property<?> getProperty() {
			return (Property<?>) getSource();
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
		private static final long serialVersionUID = -5807537972976314417L;

		/**
		 * Constructs a new value change event for this object.
		 * 
		 * @param source
		 *            source object of the event.
		 */
		protected ValueChangeEvent(Property<?> source) {
			super(source);
		}

		/**
		 * Gets the Property whose value has changed.
		 * 
		 * @return source Property of the event.
		 */
		@Override
		public Property<?> getProperty() {
			return (Property<?>) getSource();
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
}
