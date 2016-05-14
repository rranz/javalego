Crear profiles y ejecutar su construcción (ver ejemplo en proyecto javalego_store)
-----------------------------------------------------------------------------------
Botón derecho sobre el proyecto
Run As ...
Maven build ...
Goals= install -PTEST

Lanzar aplicación con un profile
-------------------------------------------------------------
Botón derecho sobre el proyecto
Run As ...
Maven build ...
	En el diálogo, incluir:
	
	Goals=jetty:run
	Profiles=TEST
	Marcar casilla "Resolve worksapce artifacts"
	
Ir la navegador e incluir la url: http://localhost:8080



Ejemplo pom.xml
-----------------------------------------------------------------
	<profiles>
		<profile>
			<id>TEST</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
			<properties>
				<!-- Specifies the build.profile.id property that must be equal than 
					the name of the directory that contains the profile specific configuration 
					file. Because the name of the directory that contains the configuration file 
					of the production profile is prod, we must set the value of the build.profile.id 
					property to prod. -->
				<build.profile.id>test</build.profile.id>
			</properties>
		</profile>
		<profile>
			<id>DEV</id>
			<properties>
				<!-- Specifies the build.profile.id property that must be equal than 
					the name of the directory that contains the profile specific configuration 
					file. Because the name of the directory that contains the configuration file 
					of the production profile is prod, we must set the value of the build.profile.id 
					property to prod. -->
				<build.profile.id>dev</build.profile.id>
			</properties>
		</profile>
	</profiles>

			<plugin>
				<groupId>org.eclipse.jetty</groupId>
				<artifactId>jetty-maven-plugin</artifactId>
			</plugin>