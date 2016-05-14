package com.javalego.ui.mvp.editor.bean;

import com.javalego.ui.mvp.editor.IEditorPresenter;
import com.javalego.ui.mvp.editor.impl.BaseEditorPresenter.EditorListener;

/**
 * Presenter del editor de un bean
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBeanEditorPresenter<T> extends IEditorPresenter {

	void setEditorListener(EditorListener editorListener);

}
