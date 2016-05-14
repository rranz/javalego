package com.javalego.ui.vaadin.factory;

import com.javalego.ui.mvp.beans.view.IBeansView;
import com.javalego.ui.mvp.beans.view.list.ListEditorBeansViewPresenter;
import com.javalego.ui.mvp.beans.view.list.adapter.IListBeansViewAdapter;
import com.javalego.ui.mvp.beans.view.paged.PagedBeansDataModel;
import com.javalego.ui.mvp.beans.view.paged.PagedBeansPresenter;
import com.javalego.ui.mvp.beans.view.table.TableBeansViewPresenter;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansEditorViewListener;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansViewListener;
import com.javalego.ui.vaadin.view.editor.beans.list.ListBeansView;
import com.javalego.ui.vaadin.view.editor.beans.table.TableBeansView;
import com.javalego.ui.vaadin.view.editor.beans.table.paged.TablePagedBeansView;

/**
 * Factoría de generación de editores en las diferentes tipologías disponibles
 * en la arquitectura para Vaadin.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class EditorsFactoryVaadin {

	private EditorsFactoryVaadin() {
	}

	/**
	 * Obtener el editor de beans basados en una tabla paginada.
	 * 
	 * @param listener
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IBeansView getTablePagedBeansView(BeansEditorViewListener listener) {

		TablePagedBeansView beansView = new TablePagedBeansView(listener.getFieldModels(), listener.getColumnNames());

		PagedBeansDataModel dataModel = new PagedBeansDataModel(null, listener.getBeanClass(), listener.getFieldModels());

		new PagedBeansPresenter<>(listener, dataModel, beansView);

		return beansView;
	}

	/**
	 * Obtener la vista de tipo lista para ver los beans del editor.
	 * 
	 * @param listener
	 * @param adapter
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IBeansView getListBeansView(BeansViewListener listener, IListBeansViewAdapter<?> adapter) {

		ListBeansView beansView = new ListBeansView(adapter);

		new ListEditorBeansViewPresenter(listener, beansView);

		return beansView;
	}

	
	/**
	 * Obtener la vista de tipo Table (sin paginación) para ver los beans del editor.
	 * 
	 * @param listener
	 * @param adapter
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IBeansView getTableBeansView(BeansEditorViewListener listener) {

		TableBeansView beansView = new TableBeansView(listener.getFieldModels(), listener.getColumnNames());

		new TableBeansViewPresenter(listener, beansView);

		return beansView;
	}
	
}
