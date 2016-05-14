-----------------------------------------------------------------------------------------
CREAR REPOSITORIO LOCAL CON NUESTROS PROYECTOS Y SUBIR A GITHUB
-----------------------------------------------------------------------------------------
1. Crear repositorio local desde la perspectiva Git
2. Copiar todos los proyectos o carpeta javalego completa en el directorio del repositorio git creado.
3. Desde la perspectiva Git, seleccionar la carpeta "Working Tree", botón derecho y seleccionar "Import projects ...",
seleccionar la carpeta javalego o projectos anteriormente copiado e importarlos al workspace.
4. Una vez configurado correctamente el repositorio local, subiremos los proyectos al repositorio remoto:
	Desde la perspectiva Git, seleccionar "Remotes".origin. icono nube flecha roja, botón derecho y opción "Push".
	Definimos nuestro usuario o email y password y aceptamos la subida al repositorio remoto.




--------------------------------------------------------------------------------------------------
SEGUNDA OPCIÓN
--------------------------------------------------------------------------------------------------
Integrar proyectos Maven JavaLego con Git en Eclipse (EGit plugin).

Pasos a seguir:

1. Importar proyecto root /javalego (no como proyecto Maven).

2. Compartir este proyecto en Git: Team.Shared.Git path /git/javalego (es un ejemplo).

3. Commit del proyecto en Git.

4. Eliminar proyecto del workspace (sin borrarlo).

5. Desde las perspectiva Git: Seleccionar el repositorio creado (javalego), botón derecho y
	pulsar "Import projects...", seleccionado la carpeta javalego (root maven).

6. Volver a la perspectiva Java o Java EE y esperar a que actualice y valide todo el workspace. Deberías de
	ver los proyectos Maven multimódulo correctamente. La ubicación original
	se ha eliminado y ahora los proyectos estarán en los directorios de Git.
	
	
SUBIR PROYECTOS A GITLAB O GITHUB

https://gitlab.com/ranzgrajal/javalego.git
ranzgrajal
morrxxxxxx

guía:

https://www.youtube.com/watch?v=pGTjdeX_Y48

En resumen:

Añadir vista de repositorios
Seleccionar repositorio a javalego y botón derecho sobre la opción "Remotes" para establecer la conexión con gitlab.
	1. Create Remote
	2. Incluir la url https://gitlab.com/ranzgrajal/javalego.git
	3. Pulsar la opción "Advanced". Seleccionar "master" combo, + add specif. y seleccionar check updates y "Save ...".
	4. Finalizar diálogo pulsando Save
Finalmente, seleccionar el proyecto que queremos subir (en nuestro caso el root de javalego, subirá todos los proyectos), 
botón derecho, seleccionaremos "Team.Push to upstream", OK y ya tendremos subidos todos nuestros archivos a Github o gitlab.
Se puede configurar en "Preferences.Team.Ignore resources y en el repositorio Github o Gitlabl los tipos de archivos que queremos ignorar: carpetas, *.class, .... 

	