package com.javalego.ui.vaadin.component.listview;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Component;

public class TestItemListViewUI implements IItemListView {

	String html = "HTML TEXT";
	private ArrayList<Component> list;
	
	public TestItemListViewUI(String html) {
		this.html = html;
	}

	@Override
	public String getHtml() {
		return html;
	}

	@Override
	public String getIcon() {
		return "bell";
	}

	@Override
	public List<Component> getComponents() {

		if (list != null) {
			return list;
		}
		
		list = new ArrayList<Component>();

//		ToolBarButton btn = new ToolBarButton("Aceptar", ICollectionImages.BELL_BIG, null, true);
//		btn.addLayoutClickListener(new LayoutClickListener() {
//			@Override
//			public void layoutClick(LayoutClickEvent event) {
//				System.out.println(TestItemListViewUI.this.getHtml());
//			}
//		});
//		list.add(btn);
//		list.add(new ToolBarButton(null, ICollectionImages.BASKET, null, true));
		return list;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return html;
	}
	
	
	@Override
	public String toString() {
		return html;
	}

}
