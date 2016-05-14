package com.javalego.store.items.impl;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import com.javalego.model.keys.Icon;
import com.javalego.store.items.IScreenShot;
import com.javalego.store.ui.icons.MenuIcons2;

@Entity
public class ScreenShot extends BaseModel implements IScreenShot {

	private static final long serialVersionUID = 7945436051643278025L;

	@Basic(fetch = FetchType.LAZY)
	@Lob
	private byte[] image;

	public ScreenShot() {
	}

	@Override
	@Column(name = "screenshot_id")
	public String getName() {
		return super.getName();
	}

	public ScreenShot(String title, String description, byte[] image) {
		super(null, title, description);
		this.image = image;
	}

	@Override
	public byte[] getImage() {
		return image;
	}

	@Override
	public Icon getIcon() {
		return MenuIcons2.PICTURE;
	}

	@Override
	public String toHtml() {
		return title;
	}

	@Override
	public String toTile() {
		return toHtml();
	}

	/**
	 * Imagen de un array de bytes.
	 * 
	 * @param image
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

}
