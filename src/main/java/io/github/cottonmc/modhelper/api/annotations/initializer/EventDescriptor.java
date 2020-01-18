package io.github.cottonmc.modhelper.api.annotations.initializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by event handling interfaces to store their informations for generation.
 * <p>
 * The information
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventDescriptor {
    String value();

    boolean hasBefore() default true;
}
