package io.github.cottonmc.modhelper.api.annotations.initializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated classes will subscribe to the event of the interface they implement.
 * Will use @Sided annotations
 * <p>
 * Mixin classes with this annotation will be added to a mixin jspn.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Subscribe {
}
