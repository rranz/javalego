package com.javalego.ui.vaadin.component.criteria;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ContainerHierarchicalWrapper;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class CriteriaBuilder extends Panel {
	protected TreeTable tree;
	protected Button addButton;
	protected Button addGroupButton;
	protected Button delButton;
	protected AbstractOrderedLayout mainLayout;
	protected AbstractOrderedLayout buttonLayout;
	protected Collection<CriteriaField> criteriaFields;
	protected CriteriaConditionContainer conditionContainer;
	protected ContainerHierarchicalWrapper itemContainerWrapper;
	protected CriteriaItemFactory defaultCriteriaItemFactory;
	private CriteriaItemFactory criteriaItemFactory;
	public Map<Object, ComboBox> elementConditionFields = new HashMap<Object, ComboBox>();
	public Map<Object, ComboBox> fieldFields = new HashMap<Object, ComboBox>();
	public Map<Object, TextField> valueConditionFields = new HashMap<Object, TextField>();

	public CriteriaBuilder(Collection<CriteriaField> criteriaFields) {
		this(criteriaFields, null);
	}

	public CriteriaBuilder(Collection<CriteriaField> criteriaFields, ContainerHierarchicalWrapper existingItemContainerWrapper) {
		this.criteriaFields = criteriaFields;
		mainLayout = new VerticalLayout();
		buttonLayout = new HorizontalLayout();
		conditionContainer = new CriteriaConditionContainer();
		ResourceBundle bundle = getBundle();
		defaultCriteriaItemFactory = new DefaultCriteriaItemFactory();

		addButton = new Button(bundle.getString("addItem"));
		addButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addItem(false);
			}
		});
		addGroupButton = new Button(bundle.getString("addGroup"));
		addGroupButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addItem(true);
			}
		});
		delButton = new Button(bundle.getString("delete"));
		delButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				delItem();
			}
		});

		tree = new TreeTable();
		if (existingItemContainerWrapper == null) {
			this.itemContainerWrapper = new ContainerHierarchicalWrapper(new BeanItemContainer<CriteriaItem>(CriteriaItem.class));
			addRootElement();
		}
		else {
			itemContainerWrapper = existingItemContainerWrapper;
		}

		tree.setContainerDataSource(this.itemContainerWrapper);

		initUiTree();
		expandAllTree();

		fillTestData();
		tree.setSizeFull();
		tree.setSelectable(true);
		tree.setTableFieldFactory(new CriteriaTableFieldFactory());
		tree.setImmediate(true);
		tree.setBuffered(false);
		tree.setEditable(true);

		buttonLayout.setMargin(true);
		buttonLayout.addComponent(addButton);
		buttonLayout.addComponent(addGroupButton);
		buttonLayout.addComponent(delButton);

		mainLayout.setMargin(true);
		mainLayout.setSizeFull();
		mainLayout.addComponent(buttonLayout);
		mainLayout.addComponent(tree);

		setContent(mainLayout);
	}

	public void fillTestData() {

	}

	protected ComboBox createAndOrComboBox() {
		ComboBox comboBox = new ComboBox();
		comboBox.addItem("AND");
		comboBox.addItem("OR");
		comboBox.setRequired(true);
		comboBox.setValue("AND");
		return comboBox;
	}

	protected ComboBox createConditionComboBox() {
		ComboBox comboBox = new ComboBox();
		comboBox.addItem("Greater then");
		comboBox.addItem("Less then");
		comboBox.addItem("Equal");
		return comboBox;
	}

	protected ComboBox createCriteriaFieldComboBox() {
		ComboBox comboBox = new ComboBox();
		comboBox.addItems(criteriaFields);
		return comboBox;
	}

	public Collection<CriteriaField> getCriteriaFields() {
		return criteriaFields;
	}

	public void setCriteriaFields(Collection<CriteriaField> criteriaFields) {
		this.criteriaFields = criteriaFields;
	}

	private void addRootElement() {
		CriteriaItem root = new CriteriaItem(null, false, conditionContainer.getGroupCondition("and"));
		itemContainerWrapper.addItem(root);
		itemContainerWrapper.setChildrenAllowed(root, false);
	}

	private void addItem(boolean flGroup) {
		CriteriaItem selectedItem = (CriteriaItem) tree.getValue();
		if (selectedItem == null)
			selectedItem = getRootItem();
		if (!selectedItem.isFlagGroup()) {
			selectedItem = selectedItem.getParent();
		}
		CriteriaItem item;
		CriteriaItemFactory curCriteriaItemFactory;
		if (criteriaItemFactory == null) {
			curCriteriaItemFactory = defaultCriteriaItemFactory;
		}
		else {
			curCriteriaItemFactory = criteriaItemFactory;
		}

		if (flGroup)
			item = curCriteriaItemFactory.makeGroup((CriteriaItem) selectedItem, false, conditionContainer.getGroupCondition("and"));
		else
			item = curCriteriaItemFactory.makeItem((CriteriaItem) selectedItem, null, false, conditionContainer.getElementCondition("equal"), "", true);
		itemContainerWrapper.setChildrenAllowed(selectedItem, true);
		itemContainerWrapper.addItem(item);
		itemContainerWrapper.setParent(item, selectedItem);
		itemContainerWrapper.setChildrenAllowed(item, false);
		tree.setCollapsed(selectedItem, true);
		tree.setCollapsed(selectedItem, false);
	}

	private void delItem() {
		CriteriaItem selectedItem = (CriteriaItem) tree.getValue();
		if (selectedItem == null)
			return;
		if (selectedItem == getRootItem() || itemContainerWrapper.hasChildren(selectedItem))
			return;
		itemContainerWrapper.removeItem(selectedItem);
	}

	public ContainerHierarchicalWrapper getItemContainerWrapper() {
		return itemContainerWrapper;
	}

	public CriteriaItem getRootItem() {
		Collection<?> collectionRoots = itemContainerWrapper.rootItemIds();
		if (collectionRoots.size() == 0) {
			addRootElement();
			collectionRoots = itemContainerWrapper.rootItemIds();
		}
		return (CriteriaItem) collectionRoots.iterator().next();
	}

	protected ResourceBundle getBundle() {
		return conditionContainer.getBundle();
	}

	@SuppressWarnings("unchecked")
	public boolean validate() {
		for (CriteriaItem item : (Collection<CriteriaItem>) (itemContainerWrapper.getItemIds())) {
			int valid = item.validate();
			if (valid == CriteriaItem.VALID_RECORD)
				continue;
			tree.select(item);
			switch (valid) {
			case CriteriaItem.ERR_NA_ELEMENT_CONDITION:
				elementConditionFields.get(item).focus();
				Notification.show(getBundle().getString("naElementCondition"));
				break;
			case CriteriaItem.ERR_INVALID_ELEMENT_CONDITION:
				elementConditionFields.get(item).focus();
				Notification.show(getBundle().getString("invalidElementCondition"));
				break;
			case CriteriaItem.ERR_NA_VALUE:
			case CriteriaItem.ERR_INVALID_VALUE:
				valueConditionFields.get(item).focus();
				Notification.show(getBundle().getString("invalidValue"));
				break;
			case CriteriaItem.ERR_NA_FIELD:
				fieldFields.get(item).focus();
				Notification.show(getBundle().getString("naField"));
				break;
			}
			return false;
		}
		return true;
	}

	public CriteriaItemFactory getCriteriaItemFactory() {
		return criteriaItemFactory;
	}

	public void setCriteriaItemFactory(CriteriaItemFactory criteriaItemFactory) {
		this.criteriaItemFactory = criteriaItemFactory;
	}

	public AbstractOrderedLayout getButtonLayout() {
		return buttonLayout;
	}

	class CriteriaTableFieldFactory implements TableFieldFactory {

		@Override
		public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
			boolean flGroup = container.getContainerProperty(itemId, "flagGroup").getValue().equals(Boolean.TRUE);
			final Object fItemId = itemId;
			if ("field".equals(propertyId) && !flGroup) {
				ComboBox cb = new ComboBox(null, criteriaFields);
				cb.setSizeFull();
				// cb.setRequired(true);
				cb.setValidationVisible(true);
				cb.setImmediate(true);
				cb.addValueChangeListener(new Property.ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						CriteriaField field = (CriteriaField) (event.getProperty().getValue());
						if (field == null)
							return;
						ComboBox comp = elementConditionFields.get(fItemId);
						if (comp == null)
							return;
						Container newContainer;
						if (field.getClassField() == CriteriaField.ClassField.STRING)
							newContainer = conditionContainer.getElementConditionContainer();
						else
							newContainer = conditionContainer.getElementConditionWoStringContainer();
						if (comp.getContainerDataSource() != newContainer) {
							Object oldValue = comp.getValue();
							comp.setContainerDataSource(newContainer);
							if (newContainer.getItem(oldValue) != null)
								comp.setValue(oldValue);
						}
					}
				});
				fieldFields.put(itemId, cb);
				return cb;
			}

			if ("elementCondition".equals(propertyId) && !flGroup) {
				final ComboBox cb = new ComboBox(null, conditionContainer.getElementConditionContainer());
				cb.setSizeFull();
				// cb.setRequired(true);
				cb.setValidationVisible(true);
				elementConditionFields.put(itemId, cb);
				return cb;
			}

			if ("groupCondition".equals(propertyId) && flGroup) {
				ComboBox cb = new ComboBox(null, conditionContainer.getGroupConditionCollection());
				cb.setSizeFull();
				// cb.setRequired(true);
				return cb;
			}

			if ("flagNot".equals(propertyId)) {
				CheckBox chb = new CheckBox();
				return chb;
			}

			if ("value".equals(propertyId) && !flGroup) {
				TextField txt = new TextField();
				txt.setSizeFull();
				valueConditionFields.put(itemId, txt);
				return txt;
			}

			if ("ignoreCase".equals(propertyId)) {
				CheckBox chb = new CheckBox();
				chb.setReadOnly(flGroup);
				return chb;
			}

			return null;
		}

	}

	private void initUiTree() {
		ResourceBundle bundle = getBundle();
		tree.setVisibleColumns(new Object[] { "flagNot", "field", "elementCondition", "value", "ignoreCase", "groupCondition" });
		tree.setColumnHeaders(bundle.getString("not"), bundle.getString("field"), bundle.getString("elementCondition"), bundle.getString("value"), bundle.getString("ignoreCase"),
				bundle.getString("groupCondition"));

		tree.setColumnWidth("not", 30);
		tree.setColumnWidth("ignoreCase", 40);
		tree.setColumnWidth("groupCondition", 80);

		tree.setColumnExpandRatio("field", 30);
		tree.setColumnExpandRatio("elementCondition", 40);
		tree.setColumnExpandRatio("value", 30);
	}

	private void expandAllTree() {
		for (Object itemId : tree.getItemIds()) {
			tree.setCollapsed(itemId, false);
		}
	}

	public void freeContainer() {
		tree.detach();
	}

	public CriteriaConditionContainer getConditionContainer() {
		return conditionContainer;
	}

}
