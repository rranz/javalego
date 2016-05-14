package com.javalego.store.items.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import com.javalego.model.keys.Icon;
import com.javalego.store.items.IMember;
import com.javalego.store.ui.icons.MenuIcons2;

/**
 * Miembro de la comunidad.
 * 
 * @author ROBERTO RANZ
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Member extends BaseModel implements IMember {

	private static final long serialVersionUID = -5501297994374562118L;

	@Transient
	private Icon icon = MenuIcons2.MEMBER;

	private Date registration;

	private byte[] image;

	public Member() {
	}

	protected Member(String name, Date registration) {
		this.name = name;
		this.registration = registration;
	}

	@Override
	public String getTitle() {
		return toString();
	}

	@Override
	public Icon getIcon() {
		return icon == null ? MenuIcons2.MEMBER : icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	@Override
	public Date getRegistration() {
		return registration;
	}

	@Override
	public byte[] getImage() {
		return image;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
