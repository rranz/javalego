package com.javalego.ui.vaadin.component.criteria;

import java.util.Collection;
import java.util.ResourceBundle;

import com.vaadin.data.util.ContainerHierarchicalWrapper;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class CriteriaBuilderWindow extends Window {
	protected CriteriaBuilder criteriaBuilder;
	protected Button applyButton;
	protected Button cancelButton;
	private CriteriaBuilderWindowListener applyEvent;
	private CriteriaBuilderWindowListener cancelEvent;
	private AbstractLayout layout = new VerticalLayout();

	public CriteriaBuilderWindow(Collection<CriteriaField> criteriaFields) {
		this(criteriaFields, null);
	}

	public CriteriaBuilderWindow(Collection<CriteriaField> criteriaFields, ContainerHierarchicalWrapper itemContainerWrapper) {
		super();
		criteriaBuilder = new CriteriaBuilder(criteriaFields, itemContainerWrapper);
		ResourceBundle bundle = criteriaBuilder.getBundle();
		applyButton = new Button(bundle.getString("apply"));
		criteriaBuilder.getButtonLayout().addComponent(applyButton);
		applyButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (!criteriaBuilder.validate())
					return;
				if (applyEvent != null) {
					applyEvent.execute();
				}
				close();
			}
		});

		cancelButton = new Button(bundle.getString("cancel"));
		criteriaBuilder.getButtonLayout().addComponent(cancelButton);
		cancelButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (cancelEvent != null) {
					cancelEvent.execute();
				}
				close();
			}
		});

		layout.addComponent(criteriaBuilder);
		setContent(layout);
		center();
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return criteriaBuilder;
	}

	public Button getApplyButton() {
		return applyButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public CriteriaBuilderWindowListener getApplyEvent() {
		return applyEvent;
	}

	public void setApplyEvent(CriteriaBuilderWindowListener applyEvent) {
		this.applyEvent = applyEvent;
	}

	public CriteriaBuilderWindowListener getCancelEvent() {
		return cancelEvent;
	}

	public void setCancelEvent(CriteriaBuilderWindowListener cancelEvent) {
		this.cancelEvent = cancelEvent;
	}

}
