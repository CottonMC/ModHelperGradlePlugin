package io.github.cottonmc.modhelper.api.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by event handling interfaces to store their informations for generation.
 *
 * The information
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventDescriptor {
    /**
     * The String that determines where the mixin should be created
     * */
    String mixinString();
    /**
     * The fully qualified name of the class that it should mix into
     * */
    String targetClass();
    /**
     * Generalizes the injection point, used for code generation.
     * */
    EventType type();

    /**
     * Weather or not this method can cancel the the original function call.
     * */
    boolean cancelleable() default false;


    enum EventType{
        BEFORE,AFTER
    }
}
