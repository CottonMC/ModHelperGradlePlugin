package io.github.cottonmc.modhelper.api.events;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used by event handling interfaces to store their informations for generation
 * */
@Retention(RetentionPolicy.CLASS)
public @interface EventDescriptor {
    /**
     *
     * */
    String mixinString();
    Class targetClass();
    Class[] methodInfo();
}
