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

package com.javalego.ui.binding.reflection;

import java.util.EventListenerProxy;

import com.javalego.ui.binding.reflection.PropertyChangeEvent;
import com.javalego.ui.binding.reflection.PropertyVetoException;
import com.javalego.ui.binding.reflection.VetoableChangeListener;

@SuppressWarnings("rawtypes")
public class VetoableChangeListenerProxy extends EventListenerProxy implements
        VetoableChangeListener {

    private String propertyName;

    @SuppressWarnings("unchecked")
	public VetoableChangeListenerProxy(String propertyName,
            VetoableChangeListener listener) {
        super(listener);
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
	public void vetoableChange(PropertyChangeEvent evt)
            throws PropertyVetoException {
        VetoableChangeListener listener = (VetoableChangeListener) getListener();
            listener.vetoableChange(evt);
    }
}