package net.hex_circles.forge;

import net.hex_circles.Hex_circlesAbstractions;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class Hex_circlesAbstractionsImpl {
    /**
     * This is the actual implementation of {@link Hex_circlesAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
	
    public static void initPlatformSpecific() {
        Hex_circlesConfigForge.init();
    }
}
