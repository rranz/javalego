/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.javalego.ui.binding.reflection.editors;

import java.awt.Font;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.javalego.ui.binding.reflection.PropertyChangeEvent;
import com.javalego.ui.binding.reflection.PropertyChangeListener;
import com.javalego.ui.binding.reflection.PropertyEditor;

@SuppressWarnings("serial")
public class FontEditor extends Panel implements PropertyEditor {
    
    List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
    
    private Font value;
    
    private Object source;
    
    public FontEditor(Object source) {
        if(source== null){
            throw new NullPointerException();
        }
        this.source = source;
    }

    public FontEditor() {
        super();
    }

    @Override
	public boolean supportsCustomEditor() {
        return true;
    }

    @Override
	public String getJavaInitializationString() {
        if (value != null) {
            StringBuilder sb = new StringBuilder("new Font("); //$NON-NLS-1$
            sb.append(value.getName());
            sb.append(',');
            sb.append(value.getStyle());
            sb.append(',');
            sb.append(value.getSize() + ")"); //$NON-NLS-1$
            return sb.toString();
        }
        return null;
    }

    @Override
	public String[] getTags() {
        return null;
    }

    @Override
	public void setValue(Object newValue) {
        Object oldValue = value;
        value = (Font)newValue;
        PropertyChangeEvent changeAllEvent = new PropertyChangeEvent(this,
                "value", oldValue, value); //$NON-NLS-1$
        PropertyChangeListener[] copy = listeners
                .toArray(new PropertyChangeListener[0]);
        for (PropertyChangeListener listener : copy) {
            listener.propertyChange(changeAllEvent);
        }
    }

    @Override
	public boolean isPaintable() {
        return true;
    }

    @Override
	public String getAsText() {
        return null;
    }

    @Override
	public Object getValue() {
        return value;
    }

    @Override
	public void setAsText(String text) throws IllegalArgumentException {
        throw new IllegalArgumentException(text==null?text:value.toString());
    }
    
    @Override
    public synchronized void removePropertyChangeListener(
            PropertyChangeListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    @Override
    public synchronized void addPropertyChangeListener(
            PropertyChangeListener listener) {
        listeners.add(listener);
    }
    
    public void firePropertyChange() {
        if (listeners.isEmpty()) {
            return;
        }

        List<PropertyChangeListener> copy = new ArrayList<PropertyChangeListener>(
                listeners.size());
        synchronized (listeners) {
            copy.addAll(listeners);
        }

        PropertyChangeEvent changeAllEvent = new PropertyChangeEvent(source,
                null, null, null);
        for (Iterator<PropertyChangeListener> listenersItr = copy.iterator(); listenersItr
                .hasNext();) {
            PropertyChangeListener listna = listenersItr.next();
            listna.propertyChange(changeAllEvent);
        }
    }
}
