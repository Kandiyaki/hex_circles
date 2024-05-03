package net.hex_circles.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.hex_circles.Hex_circlesAbstractions;

import java.nio.file.Path;

public class Hex_circlesAbstractionsImpl {
    /**
     * This is the actual implementation of {@link Hex_circlesAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
	
    public static void initPlatformSpecific() {
        Hex_circlesConfigFabric.init();
    }
}
