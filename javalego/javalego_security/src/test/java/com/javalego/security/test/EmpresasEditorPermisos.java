package com.javalego.security.test;

import com.javalego.security.permission.Permission;


public class EmpresasEditorPermisos implements Permission {

	@Override
	public boolean isPermitted() {
		return true;
	}

}
