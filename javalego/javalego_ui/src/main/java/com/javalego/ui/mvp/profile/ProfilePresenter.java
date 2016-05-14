package com.javalego.ui.mvp.profile;

import com.javalego.exception.LocalizedException;
import com.javalego.security.model.Profile;
import com.javalego.ui.mvp.profile.IProfileView.IPerfilViewListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter perfil de usuario.
 * 
 * @author ROBERTO RANZ
 */
public class ProfilePresenter implements IPresenter, IPerfilViewListener {
	
	protected IProfileView view;
	protected Profile model;

	public ProfilePresenter(Profile model, IProfileView view) {
		this.view = view;
		this.model = model;
		view.setListener(this);
	}

	@Override
	public IProfileView getView() {
		return view;
	}

	@Override
	public Profile getModel() {
		return model;
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}
	
}
