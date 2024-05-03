package net.hex_circles;

import net.hex_circles.registry.Hex_circlesIotaTypeRegistry;
import net.hex_circles.registry.Hex_circlesItemRegistry;
import net.hex_circles.registry.Hex_circlesPatternRegistry;
import net.hex_circles.networking.Hex_circlesNetworking;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is effectively the loading entrypoint for most of your code, at least
 * if you are using Architectury as intended.
 */
public class Hex_circles {
    public static final String MOD_ID = "hex_circles";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        LOGGER.info("Hex Circles says hello!");

        Hex_circlesAbstractions.initPlatformSpecific();
        Hex_circlesItemRegistry.init();
        Hex_circlesIotaTypeRegistry.init();
        Hex_circlesPatternRegistry.init();
		Hex_circlesNetworking.init();

        LOGGER.info(Hex_circlesAbstractions.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    /**
     * Shortcut for identifiers specific to this mod.
     */
    public static Identifier id(String string) {
        return new Identifier(MOD_ID, string);
    }
}
