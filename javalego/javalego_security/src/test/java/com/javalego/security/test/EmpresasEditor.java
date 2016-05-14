package com.javalego.security.test;

import com.javalego.security.annotation.DenyAll;
import com.javalego.security.annotation.PermitAll;
import com.javalego.security.annotation.RolesAllowed;
import com.javalego.security.permission.CustomPermission;

// Diferentes anotaciones soportadas por la arquitectura para validar los permisos de acceso a la clase
@CustomPermission(type=EmpresasEditorPermisos.class)
@RolesAllowed({"admin", "otro"})
@DenyAll
@PermitAll
public class EmpresasEditor {

}
