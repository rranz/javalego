package com.javalego.store.ui.editor.impl;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.IMember;
import com.javalego.store.ui.components.UIFactory;
import com.javalego.store.ui.editor.BaseItemEditor;

/**
 * Mostrar detalle de un miembro de la comunidad.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class MemberEditor extends BaseItemEditor<IMember> {

	private static final long serialVersionUID = -8516024879302589225L;

	private IMember member;

	public MemberEditor(IMember member, boolean readOnly, boolean remove) {
		this.member = member;
		this.readOnly = readOnly;
		this.remove = remove;
	}

	@Override
	public void load() throws LocalizedException {
		super.load();
		addComponent(UIFactory.getResources(member.getSocial(), null, null));
	}

	@Override
	public IBaseItem getItem() {
		return member;
	}

	@Override
	protected String getBeanTitle() {
		return member.getTitle();
	}

	@Override
	protected IMember getBean() {
		return member;
	}

	@Override
	protected Icon getBeanIcon() {
		return member.getIcon();
	}

}
