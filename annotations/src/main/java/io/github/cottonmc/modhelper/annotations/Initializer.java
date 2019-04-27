package io.github.cottonmc.modhelper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the annotated element as a fabric-loader initializer.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface Initializer {
    /**
     * The entrypoint type.
     *
     * If this value is an empty string, the annotation processor will try to
     * automatically detect the proper type from the annotated element.
     *
     * @return the entrypoint type
     * @see Entrypoints
     */
    String entrypointType() default "";

    /**
     * The language adapter.
     *
     * If this value is empty, it will not be written in the resulting
     * fabric.mod.json.
     *
     * @return the language adapter
     */
    String adapter() default "";
}
