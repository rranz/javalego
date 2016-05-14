package com.javalego.ui.mvp.nestedbean;


/**
 * Vista para la selección del nested bean que asignaremos al bean owner.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface INestedBeanView<T, U> {

	/**
	 * Método a implementar el presenter
	 * @author ROBERTO RANZ
	 *
	 * @param <T>
	 * @param <U>
	 */
	public static interface INestedBeanListener<T, U> {
		
		/**
		 * Validar el nested bean asignable al owner
		 * @param bean
		 * @throws Exception
		 */
		public abstract void validate(U bean) throws Exception;

		/**
		 * Asignar el nested bean al owner.
		 * @param bean
		 * @param ownerBean
		 * @throws Exception
		 */
		void setBean(U bean, T ownerBean) throws Exception;

		/**
		 * Obtener el nested bean
		 * @param ownerBean
		 * @return
		 * @throws Exception
		 */
		U getBean(T ownerBean) throws Exception;
		
	}
	
	/**
	 * Establecer el listener para ser usado en la vista.
	 * @param listener
	 */
	abstract void setListener(INestedBeanListener<T, U> listener);
}
