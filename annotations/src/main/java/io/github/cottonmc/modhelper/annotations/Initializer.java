package io.github.cottonmc.modhelper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.METHOD
})
public @interface Initializer {
    EntryPoint entryPoint() default EntryPoint.MAIN;
    String adapter() default "";
}
