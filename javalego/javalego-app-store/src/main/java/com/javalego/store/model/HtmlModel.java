package com.javalego.store.model;

/**
 * Texto en formato Html para ser utilizados en los diferentes temas de la plataforma.
 * 
 * @author ROBERTO RANZ
 *
 */
@Deprecated
public class HtmlModel {

	/**
	 * Productos de arquitecturas
	 * @return
	 */
	public static final String getArchitecture() {
		
		String text = "<font size=2>";

		text += "Los Productos de Arquitectura que se publicarán en esta sección no pretender crear nuevas arquitecturas a las ya existentes, sino componentes que resuelvan funcionalidades comunes requeridas para nuestros desarrollos." +
		"Normalmente, estos productos requieren de una configuración de un modelo y la selección de una implementación o tecnología existente (Ej.: en productos de interface de usuario podría ser Vaadin o Android)." +
		"Con esta metodología, podremos desarrollar nuestro código sin acoplarnos a ningún framework y librería específica. " +
		"<br><br>" + 
		"Estos productos se generan a partir de proyectos registrados en la plataforma y tendrán asociada una licencia de uso open source o comercial, ofreciendo acceso a todos sus recursos: documentación, repositorio de código fuente (licencia open source), foros, métricas, redes sociales, ...";
		text += "</font>";

		return text;
	}
	
	/**
	 * Negocio
	 * @return
	 */
	public static final String getBusiness() {
		
		String text = "<font size=2>";

		text += "<p>Los Productos de Negocio que se publican en esta sección ofrecen servicios desacoplados de grandes soluciones con el objeto de poder desarrollar aplicaciones empresariales de cualquier nivel de complejidad, desde un simple formulario, módulo o hasta soluciones integrales. Esta metodología permitirá una mejor escalabilidad del sistema.</p>";

		text += "<p>Los productos se generarán desde los proyectos que deberán estar registrados en esta plataforma y podrán estar asociados a las diferentes modalidades de licencias existentes. Podrán ser licencias de código abierto o productos comerciales.</p>";

		text += "<p>Para desarrollar estos productos podrá utilizar, y así lo recomendamos, los productos de la Arquitectura, reduciendo los tiempos, mejorando su calidad y preservando su inversión cuando necesite adaptarse a nuevas tecnologías. La funcionalidad no debe estar acoplada a la tecnología.</p>";

		text += "</font>";

		return text;
	}

	/**
	 * Community
	 * @return
	 */
	public static final String getCommunity() {
		
		String text = "<font size=2>";

		text += "<p>La comunidad está compuesta de miembros que pueden ser desarrolladores que ofrezcan sus proyectos o su colaboración en otros proyectos, o pueden ser empresas que ofrezcan sus productos y quieran gestionar sus proyectos en esta plataforma.</p>";

		text += "<p>Cada miembro podrá incluir los accesos a las redes sociales que tenga para ampliar la información personal o de empresa.</p>";

		text += "</font>";

		return text;
	}

	/**
	 * Acceso clientes
	 * @return
	 */
	public static final String getClient() {
		
		String text = "<font size=2>";

		text += "<p>Los proyectos y productos registrados en la plataforma pueden establecer su privacidad de forma que se puedan establecer qué personas o empresas pueden acceder a su información o gestionar sus contenidos.</p>";

		text += "<p>Al darse de alta como miembro de la comunidad, podrá registrarse en la plataforma definiendo sus datos de acceso.</p>";

		text += "</font>";

		return text;
	}
	
	/**
	 * Cloud
	 * @return
	 */
	public static final String getCloud() {
		
		String text = "<font size=2>";

		text += "<p>Desarrolle sus productos de negocio y ofrezca sus servicios en la nube.</p>";
		
		text += "<p>Dentro de esta sección podrá encontrar aquellos productos de negocio que han sido alojados en un cloud y vea sus términos de uso.</p>";

		text += "</font>";

		return text;
	}
	
	/**
	 * Projects
	 * @return
	 */
	public static final String getProjects() {
		
		String text = "<font size=2>";

		text += "<p>Los usuarios de la comunidad podrán registrar sus proyectos y podrán, si así lo desean, exponer sus productos tanto de arquitectura como de negocio en la modalidad de licencia que desee.</p>";

		text += "<p>La información de los proyectos puede ser muy completa:</p>";
		
		text += "<lu><li>Podremos vincularlo a nuestros repositorios de código (Ej.: GitHub, Jira)</li><li>Definir los miembros de la comunidad que constituirán los equipos de desarrollo.</li><li>Definir los productos que genera y publicarlos baja la modalidad de licencia que desee.</li><li>Podrá definir las conexiones hacia las redes sociales más populares generando noticias para publicar sus trabajos al resto de la comunidad y mantenerlos siempre informados.</li><li>Ofrezca calidad mediante la divulgación de guías de usuario, tutoriales, javadoc, métricas (Ej. SonarQube), coberturas de código), ...</li><li>Recibir comentarios</li><li>Definir una dirección para ofrecer una aplicación de demostración de sus productos y funcionalidades.</li>";

		text += "</font>";

		return text;
	}
	
	/**
	 * Presentación
	 * @return
	 */
	public static final String getPresentation() {
		
		String text = "<font size=2>";

		text += "<p><b>JAVALEGO</b> es un plataforma tecnológica que ofrece a la comunidad de desarrolladores Java la posibilidad de compartir sus conocimientos y trabajos.</p>";

		text += "<p>Este portal web permite registrar proyectos de empresas o desarrolladores que quieran compartir o comercializar sus productos a precios muy competitivos, similar a las plataformas móviles actuales.</p>";

		text += "<p>Queremos innovar ofreciendo a los desarrolladores un nueva experiencia de trabajo colaborativo, muy diferente a las plataformas de alojamiento de proyectos actuales que sirven únicamente como repositorio de código. Este portal permite crear proyectos que definan sus productos finales, tanto para arquitecturas como para el negocio. Crear componentes de arquitectura de calidad, potentes y fáciles de usar que nos permitan agilizar nuestros desarrollos. Además, servirán como base del conocimiento para el uso de muchos de los frameworks actuales. Esta capa de abstracción nos permitirá la especialización para el uso de multitud de librerías, frameworks y productos comerciales (denominados providers o proveedores de componentes). En definitiva, evitar inventar la rueda constantemente cada vez que surge nuevas tecnologías. No olvidemos que surgen nuevas tecnologías pero nuestras necesidades a la hora de desarrollar nuestras aplicaciones empresariales suelen cambiar muy poco.</p>";

		text += "<p>Los proyectos estarán vinculados a la plataforma OpenSource (como GitHub) que nos ofrecen un hosting online para alojar nuestros repositorios utilizando un control de versiones (como Git, SVN, ...) para el mantenimiento y versionado de nuestro código fuente.</p>";

		text += "<p>El portal permite registrarse a personas y empresas con el objetivo de permitir colaborar juntos en la construcción de productos que puedan compartir o comercializar.</p>";

		text += "</font>";

		return text;
	}
	
	/**
	 * Antecedentes
	 * @return
	 */
	public static final String getAntecedentes() {
		
		String text = "<font size=2>";

		text += "<p><b>Problema</b>";
		text += "<br><p>En la actualidad existen multitud de librerías dentro del mundo Java que ofrecen diferentes componentes para todo tipo de propósitos (acceso a datos, frameworks web, seguridad, gráficos, cms, bmp, workflows,....) que no siempre implementan una especificación existente en J2EE (Plataforma Java) y obligan a los equipos de desarrollo de las empresas a realizar un duro aprendizaje y desarrollo para poder utilizarlas dentro de sus aplicaciones. Además, con el paso del tiempo estas librerías requieren ser actualizadas o sustituidas por multitud de motivos: no existe soporte y desaparece, se requiere una nueva actualización, la librería no puede escalar a otras versiones de J2EE o servidores de aplicaciones,...</p>";
		
		text += "<p><b>Solución</b>";
		text += "<br><p>Para solucionar esta anarquía, nuestra plataforma ofrece una marco de trabajo y colaboración entre los profesionales de informática para mitigar este impacto y ofrecer un desarrollo sostenible y de protección de la inversión a las empresas para hacerlas más competitivas tanto económica como tecnológicamente al poder adaptarse a nuevas tecnologías de una forma sencilla y con el mínimo coste posible.</p>";

		text += "<p><b>Problema</b>";
		text += "<br><p>Las empresas invierten mucho dinero en desarrollar código propio o en adquirir productos finales que le permitan informatizar su negocio. Estas empresas pertenecen a multitud de sectores (industria, transporte, medicina, entidades financieras,...) y si analizamos la situación actual, podríamos darnos cuenta de la cantidad de librerías Java que ofrecen las mismas funcionalidades pero construidas en multitud de tecnologías. El problema es que la inversión de estas empresas no está garantizada y depende de su equipo de desarrollo (código propio) o de empresas de productos como Sage, Sap,... para crecer y adaptarse a las nuevas tecnologías para no quedarse obsoletas.</p>";
		
		text += "<p><b>Solución</b>";
		text += "<br><p>Una plataforma de desarrollo, bajo los principios de código abierto, que ofrezca esas mismas funcionalidades que requieren las empresas utilizando un código libre basado en: un lenguaje simple, bien documentado, debidamente probado y estableciendo unas garantías de certificación.</p>";
		text += "<p>Le permitirá:</p>";
		text += "<ul>";
		text += "<li>Informatizar su negocio con una mínima inversión.</li>";
		text += "<li>Transformar los componentes y productos de la plataforma adaptándolo a sus necesidades, preservando así su extensibilidad futura en cuanto a negocio y tecnología.</li>";
		text += "<li>Colaborar con otras empresas del sector para crear nuevos productos.</li>";
		text += "<li>Crear productos innovadores para cualquier sector.</li>";
		text += "<li>Minimizar la inversión en migración de aplicaciones heredadas (Legacy).</li>";
		text += "<li>Aprender en el uso de nuevas tecnologías utilizando componentes de la arquitectura que integran tecnologías: como CMS Google Drive, BI BIRT Eclipse, Vaadin, Spring,…</li>";
		text += "</ul>";
		text += "<p>Ejemplos de situaciones actuales:</p>";
		text += "<ul>";
		text += "<li>¿Cuántas aplicaciones existen que tienen un formulario o módulo de personas, provincias, divisas, cuentas bancarias, organigrama,... que podrían ser reutilizados?</li>";
		text += "<li>¿Cuántas aplicaciones tienen rutinas para cálculos de nif, nass, iva, código postal,... con codificaciones no correctas u obsoletas?</li>";
		text += "<li>¿Cuántas aplicaciones han desarrollado componentes gráficos para relaciones entre tablas, edición de direcciones (cp, población, c/...), moneda, email,... que no servirán si deciden cambiar de framework web?</li>";
		text += "<li>¿Cuántas aplicaciones para del sector sanitario han desarrollado informes para mostrar datos de un simple análisis de sangre que ofrecen datos incompletos, no estándar y en multitud de formatos que deficultan su  comprensión?. ¿No sería mejor crear un servicio de negocio reutilizable y siempre actualizado que ofrezca esta misma información de forma clara, completa y en un formato estándar que las empresas y organismos pudiesen entregar a sus pacientes?. Los pacientes siempre obtendrían la información esperada y las empresas se evitarían tener que desarrollar y mantener estos componentes de generación de informes e interpretación de datos.</li>";
		text += "</ul>";
		
		text += "</font>";
		return text;
	}
	
}
