package com.javalego.ui.vaadin.component;

import java.util.Map;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.ComboBox;

public class MyComboBox extends ComboBox {

	private static final long serialVersionUID = -3368131972303134516L;

	@Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        if (variables.containsKey("filter")) {
            final String text = variables.get("filter").toString();
            fireEvent(new TextChangeEvent(this) {
				private static final long serialVersionUID = 8857261824661794787L;

				@Override
                public String getText() {
                    return text;
                }

                @Override
                public int getCursorPosition() {
                    return text.length();
                }
            });
        }
        super.changeVariables(source, variables);
    }

    public void addListener(TextChangeListener listener) {
        addListener(TextChangeListener.EVENT_ID, TextChangeEvent.class,
                listener, TextChangeListener.EVENT_METHOD);
    }

    public void removeListener(TextChangeListener listener) {
        removeListener(TextChangeListener.EVENT_ID, TextChangeEvent.class,
                listener);
    }
}
