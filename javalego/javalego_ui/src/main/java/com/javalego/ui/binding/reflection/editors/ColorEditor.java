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

import java.awt.Color;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.javalego.ui.binding.reflection.PropertyChangeEvent;
import com.javalego.ui.binding.reflection.PropertyChangeListener;
import com.javalego.ui.binding.reflection.PropertyEditor;

@SuppressWarnings("serial")
public class ColorEditor extends Panel implements PropertyEditor {

    List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

    private Color value;

    private Object source;

    public ColorEditor(Object source) {
        if (null == source) {
            throw new NullPointerException();
        }
        this.source = source;
    }

    public ColorEditor() {
        super();
    }

    @Override
	public boolean supportsCustomEditor() {
        return true;
    }

    @Override
	public String getJavaInitializationString() {
        Color value = (Color) getValue();
        if (value != null) {
            StringBuilder sb = new StringBuilder("new java.awt.Color("); //$NON-NLS-1$
            sb.append(value.getRed());
            sb.append(',');
            sb.append(value.getGreen());
            sb.append(',');
            sb.append(value.getBlue() + ")"); //$NON-NLS-1$
            return sb.toString();
        }
        return null;
    }

    @Override
	public String[] getTags() {
        return null;
    }

    @Override
	public void setValue(Object value) {
        if (null == value) {
            return;
        }
        Object oldValue = this.value;
        this.value = (Color) value;
        PropertyChangeEvent changeAllEvent = new PropertyChangeEvent(this,
                "value", oldValue, value); //$NON-NLS-1$
        PropertyChangeListener[] copy = listeners
                .toArray(new PropertyChangeListener[0]);
        for (PropertyChangeListener listener : copy) {
            listener.propertyChange(changeAllEvent);
        }
    }

    @Override
	@SuppressWarnings("nls")
    public String getAsText() {
        Color value = (Color) getValue();
        if (value != null) {
            StringBuilder sb = new StringBuilder(14);
            sb.append(value.getRed());
            sb.append(',');
            sb.append(value.getGreen());
            sb.append(',');
            sb.append(value.getBlue());
            return sb.toString();
        }
        return ""; //$NON-NLS-1$
    }

    @Override
	@SuppressWarnings("nls")
    public void setAsText(String text) {
        if (null == text) {
            throw new NullPointerException();
        }
        String aText = text;
        try {
            int commaIndex = aText.indexOf(',');
            int red = Integer.parseInt(aText.substring(0, commaIndex));
            aText = aText.substring(commaIndex + 1);
            commaIndex = aText.indexOf(',');
            int green = Integer.parseInt(aText.substring(0, commaIndex));
            aText = aText.substring(commaIndex + 1);
            int blue = Integer.parseInt(aText);
            
            setValue(new Color(red, green, blue));
        } catch (Exception e) {
            throw new IllegalArgumentException(aText);
        }
    }

    @Override
	public boolean isPaintable() {
        return true;
    }

    @Override
	public Object getValue() {
        return value;
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
