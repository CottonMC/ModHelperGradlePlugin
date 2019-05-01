package io.github.cottonmc.modhelper.api;

/**
 * Raw information about item types
 * Custom ones are loaded as a service
 * */
public interface RawItemType {
    /**
     * The name of the item, used for equality checks.
     * */
    String getName();
    /**
     * The fully qualified name of the item class that we're implementing.
     * */
    String getImplemenationClass();
}
