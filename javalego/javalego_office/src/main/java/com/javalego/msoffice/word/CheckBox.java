package com.javalego.msoffice.word;

import com.jacob.com.Dispatch;

/**
 * CheckBox o campo de formulario de un documento.
 * @author ROBERTO RANZ
 */
public class CheckBox extends BasicElementDocument {

	/**
	 * Nombre del bookmark
	 */
	private String name;
	
	private FormFields formFields;
	
	public CheckBox(FormFields formFields) {
		this.formFields = formFields;
	}

	public CheckBox(FormFields formFields, String name) throws Exception {
		this.formFields = formFields;
		setName(name);
	}

	@Override
	protected String getInternalName() {
		return "CheckBox";
	}

	/**
	 * Obtener el texto del Bookmark
	 * @return
	 * @throws Exception
	 */
	public boolean isChecked() throws Exception {
		return Dispatch.get(pointer, "Value").getBoolean();
	}

	/**
	 * Obtener el texto del Bookmark
	 * @return
	 * @throws Exception
	 */
	public void setChecked(boolean value) throws Exception {
		Dispatch.put(pointer, "Value", value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws Exception {
		setPointer();
		this.name = name;
	}

	@Override
	protected void setPointer() throws Exception {
		//pointer = (DispatchPtr)bookmarks.getPointer().invoke("Item", new Integer(3));
	}

	public void setPointer(Dispatch pointer) {
		this.pointer = pointer;
	}

	public FormFields getFormFields() {
		return formFields;
	}

	public void setFormFields(FormFields formFields) {
		this.formFields = formFields;
	}

}
