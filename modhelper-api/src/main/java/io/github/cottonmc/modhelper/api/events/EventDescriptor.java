package io.github.cottonmc.modhelper.api.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by event handling interfaces to store their informations for generation
 * */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface EventDescriptor {
    /**
     * The String that determines where the mixin should be created
     * */
    String mixinString();
    /**
     * the fully qualified name of the class it should mix into
     * */
    String targetClass();
    /**
     * A cache for the method signature, used in code generation.
     * Can be used to generate non-mixin handlers.
     * */
    Class[] methodInfo();
}
