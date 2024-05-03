package net.hex_circles.forge;

import net.hex_circles.Hex_circlesClient;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Forge client loading entrypoint.
 */
public class Hex_circlesClientForge {
    public static void init(FMLClientSetupEvent event) {
        Hex_circlesClient.init();
    }
}
