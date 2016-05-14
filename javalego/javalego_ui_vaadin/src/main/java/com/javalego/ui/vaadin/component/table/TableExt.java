package com.javalego.ui.vaadin.component.table;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import com.javalego.ui.vaadin.component.table.event.MValueChangeEvent;
import com.javalego.ui.vaadin.component.table.event.MValueChangeEventImpl;
import com.javalego.ui.vaadin.component.table.event.MValueChangeListener;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.ui.Table;

/**
 * Extensión del componente Table para añadir funcionales necesarias para el
 * CORE GANA.
 *

TableExt<Entity> t = new TableExt<Entity>(findBeans())
        .withProperties("property", "another")
        .withColumnHeaders("Property 1", "Second");
t.addMValueChangeListener(new MValueChangeListener<Entity>() {
    @Override
    public void valueChange(MValueChangeEvent<Entity> event) {
        editEntity(event.getValue());
    }
});

 * 
 * 
 * @author ROBERTO RANZ
 */
public class TableExt<T> extends Table {

	private static final long serialVersionUID = 1L;

	private BeanItemContainer<T> bic;
    
	private String[] pendingProperties;
    
    private String[] pendingHeaders;

	@SuppressWarnings("rawtypes")
	public TableExt(BeanItemContainer container) {
		initialize();
		setContainerDataSource(container);
	}

	public TableExt() {
		initialize();
	}

    @SuppressWarnings("unchecked")
	public TableExt(T... beans) {
        this();
        addBeans(beans);
        initialize();
    }

    public TableExt(Collection<T> beans) {
        this();
        if(beans != null) {
            ensureBeanItemContainer(beans.iterator().next());
            bic.addAll(beans);
        }
        initialize();
    }

	/**
	 * Inicializar table
	 */
	private void initialize() {

		setSelectable(true);
		setNullSelectionAllowed(false);
		setColumnReorderingAllowed(true);
		setColumnCollapsingAllowed(true);
		// setCellStyleGenerator(new ColorCellStyleGenerator());

		Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			private static final long serialVersionUID = -36814609978316207L;

			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				repaintTable();
			}
		});
	}

	/**
	 * Recargar tabla para dispositivos móviles cuando se cambia la posición de
	 * la pantalla (horizontal/vertical).
	 */
	protected void repaintTable() {
		markAsDirty();
	}

	/**
	 * Formatear el valor de texto al tipo de campo
	 */
	@Override
	protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {

		// Para evitar errores con propiedades Nested que pueda existir si el
		// valor de un campo es null. Ej.: Objecto.usuario = null y queremos
		// formatear la propiedad Objecto.usuario.codigo
		try {
			if (property == null || property.getValue() == null)
				return "";
		}
		catch (Exception e) {
			return "";
		}

		// Format by property type
		if (property.getValue() instanceof Date) {
			return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format((Date) property.getValue());
		}
		else {
			return String.valueOf(property.getValue());
		}
	}

	/**
	 * Seleccionar el último registro de la tabla.
	 */
	public void lastRecord() {

		if (getItemIds().size() > 0) {
			select(getItemIds().toArray()[getItemIds().size() - 1]);
		}
	}

	/**
	 * Seleccionar el primer registro de la tabla.
	 */
	public void firstRecord() {

		if (getItemIds().size() > 0) {
			select(getItemIds().toArray()[0]);
		}
	}

	/**
	 * Establecer Height en base a la lista de elementos que tenga actualmente
	 * la tabla.
	 */
	public void autoHeight() {

		setPageLength(getItemIds().size());
	}

    public TableExt<T> withProperties(String... visibleProperties) {
        
    	if (containerInitialized()) {
            setVisibleColumns((Object[]) visibleProperties);
        } else {
            pendingProperties = visibleProperties;
        }
        return this;
    }

    private boolean containerInitialized() {
        
    	return bic != null;
    }

    public TableExt<T> withColumnHeaders(String... columnNamesForVisibleProperties) {
        
    	if (containerInitialized()) {
            setColumnHeaders(columnNamesForVisibleProperties);
        } else {
            pendingHeaders = columnNamesForVisibleProperties;
        }
        return this;
    }

    @SuppressWarnings("unchecked")
	private void ensureBeanItemContainer(T bean) {
       
    	if (!containerInitialized()) {
            
    		bic = new BeanItemContainer<T>((Class<? super T>) bean.getClass());
            
    		setContainerDataSource(bic);
            
            if (pendingProperties != null) {
                setVisibleColumns((Object[]) pendingProperties);
                pendingProperties = null;
            }
            if (pendingHeaders != null) {
                setColumnHeaders(pendingHeaders);
                pendingHeaders = null;
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public T getValue() {
        return (T) super.getValue();
    }

    @Override
    public void setMultiSelect(boolean multiSelect) {
        super.setMultiSelect(multiSelect);
    }

    @SuppressWarnings("unchecked")
	public void addBeans(T... beans) {
        
    	addBeans(Arrays.asList(beans));
    }

    public void addBeans(Collection<T> beans) {
        
    	if (!beans.isEmpty()) {
            ensureBeanItemContainer(beans.iterator().next());
            bic.addAll(beans);
        }
    }

    @SuppressWarnings("unchecked")
	public void setBeans(T... beans) {
       
    	removeAllItems();
        addBeans(beans);
    }

    public void setBeans(Collection<T> beans) {
        
    	removeAllItems();
        addBeans(beans);
    }

    public TableExt<T> withFullWidth() {
        
    	setWidth(100, Unit.PERCENTAGE);
        return this;
    }	
    
    public void addMValueChangeListener(MValueChangeListener<T> listener) {
        
    	addListener(MValueChangeEvent.class, listener, MValueChangeEventImpl.VALUE_CHANGE_METHOD);
        // implicitly consider the table should be selectable
        setSelectable(true);
        // TODO get rid of this when 7.2 is out
        setImmediate(true);
    }

    public void removeMValueChangeListener(MValueChangeListener<T> listener) {
        
    	removeListener(MValueChangeEvent.class, listener, MValueChangeEventImpl.VALUE_CHANGE_METHOD);
        setSelectable(hasListeners(MValueChangeEvent.class));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected void fireValueChange(boolean repaintIsNotNeeded) {
        super.fireValueChange(repaintIsNotNeeded);
        fireEvent(new MValueChangeEventImpl(this));
    }    
}
