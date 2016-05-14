Usaremos Archiva para publicar nuestras librerías jars en un repositorio local y,
posteriormente, transferirlos a Maven Central o MVCMaven

Descargar en http://archiva.apache.org/index.cgi

Ejecutar /bin/archiva.bat console

Acceder a la consola en el navegador http://localhost:8080/#search

Cambiar usuario y contraseña accediendo a la opción login admin (usuario: admin password: admin1) para acceder
a todas las opciones de la herramienta.

Hay que crear un usuario (ej: roberto pass: roberto1) y asignarle el rol Repository Manager para poder desplegar las librerías desde Maven.

Para añadir las librerías desde Maven pom.xml

1. Configurar settings.xml en .m2/ incluyendo

<settings>
	<servers>
		<server>
			<id>archiva.internal</id>
			<username>roberto</username>
			<password>roberto1</password>
		</server>
		<server>
			<id>archiva.snapshots</id>
			<username>roberto</username>
			<password>roberto1</password>
		</server>
	</servers>
</settings>

2. Configurar el pom.xml (root o project) incluyendo las siguientes secciones para cada repositorio de Archiva que queramos subir:

	<distributionManagement>
		<repository>
			<id>internal</id>
			<url>http://localhost:8080/repository/internal/</url>
		</repository>
	</distributionManagement>

	<repositories>
		<repository>
			<id>internal</id>
			<name>Archiva Managed Internal Repository</name>
			<url>http://localhost:8080/repository/internal/</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>
	<pluginRepositories>
		<pluginRepository>
			<id>internal</id>
			<name>Archiva Managed Internal Repository</name>
			<url>http://localhost:8080/repository/internal/</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>  

	También podemos usar profiles para seleccionar los repositorios a los que queramos subir cuando ejecutemos el goal jar:jar deploy:deploy
	
	<profiles>
		<profile>
			<id>Repositorios Locales</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
			<repositories>
				<repository>
					<id>archiva.internal</id>
					<url>http://localhost:8080/archiva/repository/internal/</url>
					<releases>
						<enabled>true</enabled>
					</releases>
					<snapshots>
						<enabled>false</enabled>
					</snapshots>
				</repository>
				<repository>
					<id>archiva.snapshots</id>
					<url>http://localhost:8080/archiva/repository/snapshots/</url>
					<releases>
						<enabled>false</enabled>
					</releases>
					<snapshots>
						<enabled>true</enabled>
					</snapshots>
				</repository>
			</repositories>
		</profile>
	</profiles>	
	
3. Ejecutar Run.Build... con el goal = jar:jar deploy:deploy

