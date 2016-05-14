Cambiar el JAVA_HOME de JVM en desde la consola de Windows:

@echo off
echo Setting JAVA_HOME
set JAVA_HOME=C:\TRABAJO\TEMP\jdk1.7.0_45
echo setting PATH
set PATH=C:\TRABAJO\TEMP\jdk1.7.0_45\bin;%PATH%
echo Display java version
java -version


Nos permitirá cambiar la versión de JVM si queremos ejecutar alguna aplicación con esta versión sin cambiar la ruta actual.