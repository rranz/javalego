package com.javalego.security.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies that no security roles are allowed to invoke the specified
 * method(s) - i.e that the methods are to be excluded from execution in the
 * J2EE container.
 *
 * @see RolesAllowed
 * @see PermitAll
 * @since Common Annotations 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface DenyAll {
}
