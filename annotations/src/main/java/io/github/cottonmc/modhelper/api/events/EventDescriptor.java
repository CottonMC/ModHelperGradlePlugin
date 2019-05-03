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
     * The class of the value that could be returned
     * */
    String returnType() default "";


    Side side() default Side.COMMON;
    /**
     * Describes the kind of behaviour has to be implemented in an event.
     * {@link #BEFORE}
     * {@link #AFTER}
     * {@link #BEFORE_CANCELLABLE}
     * */
    enum EventType{
        /**
         * called at the beginning of the function, that we mix into
         * * */
        BEFORE
        /**
         * called after the function that we mix into
         * */
        ,AFTER
        /**
         * same as {@link #BEFORE}, but we gain the option to cancel the original call.
         * */
        , BEFORE_CANCELLABLE
    }

    enum Side{
        CLIENT,SERVER,COMMON
    }
}
