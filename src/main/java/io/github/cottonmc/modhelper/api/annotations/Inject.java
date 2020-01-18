package io.github.cottonmc.modhelper.api.annotations;

/**
 * Annotated static fields will be set from the registries after initialization is done.
 * <p>
 * Value is an identifier, if the namespace is missing, your modid will be used. Will throw an error if it's empty.
 * If the identifier is invalid, the value will be null.
 */
public @interface Inject {
    String value();


    /**
     * Classes annotated with this will use the included namespace for injection instead of the modid.
     */
    @interface Namespace {
        String value();
    }
}
