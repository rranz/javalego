1. Usar Maven con repositorios https:// sin certificados (descargas de artefactos a través de proxys de empresa)

/maven/lib/​​mvn install -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true

o incluir como variables al ejecutar un goal en Eclipse.



2. Salvar todas las dependencias de un proyecto jar un el directory /target/lib:

Run.Build...

incluir en Goals:
	dependency:copy-dependencies -DoutputDirectory=${project.build.directory}/lib
	
	
En pom.xml

	<!-- Para incluir en el directory target/lib todas las dependencias ejecutar Run.Build... goals=install y profile=qa -->
	<profiles>
		<profile>
			<id>qa</id>
			<build>
				<plugins>
					<plugin>
						<artifactId>maven-dependency-plugin</artifactId>
						<executions>
							<execution>
								<phase>install</phase>
								<goals>
									<goal>copy-dependencies</goal>
								</goals>
								<configuration>
									<outputDirectory>${project.build.directory}/lib</outputDirectory>
								</configuration>
							</execution>
						</executions>
					</plugin>
				</plugins>
			</build>
		</profile>
	