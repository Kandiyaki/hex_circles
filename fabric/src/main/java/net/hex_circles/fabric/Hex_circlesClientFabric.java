package net.hex_circles.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.hex_circles.Hex_circlesClient;

/**
 * Fabric client loading entrypoint.
 */
public class Hex_circlesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Hex_circlesClient.init();
    }
}
