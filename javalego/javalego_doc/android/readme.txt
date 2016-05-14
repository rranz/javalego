En Android Studio para evitar los errores de compilación gradle por encoding utf-8 ya que
los proyectos están con windows 1252 hay que configurar en File.Settings.Gradle el campo
 VM Options incluyendo en valor: -Dfile.encoding=ISO-8859-1 