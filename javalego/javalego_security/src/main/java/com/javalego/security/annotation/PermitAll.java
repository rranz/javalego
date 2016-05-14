package com.javalego.security.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies that all security roles are allowed to invoke the specified
 * method(s) i.e that the specified method(s) are "unchecked". It can be
 * specified on a class or on methods. Specifying it on the class means that it
 * applies to all methods of the class. If specified at the method level, it
 * only affects that method. If the RolesAllowed is specified at the class level
 * and this annotation is applied at the method level, the PermitAll annotation
 * overrides the RolesAllowed for the specified method.
 *
 * @see RolesAllowed
 * @see DenyAll
 *
 * @since Common Annotations 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface PermitAll {
}
