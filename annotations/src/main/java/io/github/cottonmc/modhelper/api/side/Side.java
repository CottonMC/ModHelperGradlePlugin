package io.github.cottonmc.modhelper.api.side;

public enum Side {
	/**
	 * Content shared between the two sides.
	 */
	COMMON,

	/**
	 * Client-only content.
	 */
	CLIENT,

	/**
	 * Dedicated server-only content.
	 */
	SERVER
}
