package io.github.cottonmc.modhelper.api.initializer;

/**
 * Common entrypoint types.
 */
public final class Entrypoints {
    /**
     * The main entrypoint. Will be run on both sides.
     */
    public static final String MAIN = "main";

    /**
     * The client-side entrypoint. Will be run on the client.
     */
    public static final String CLIENT = "client";

    /**
     * The server-side entrypoint. Will be run on dedicated servers.
     */
    public static final String SERVER = "server";
}
