package net.hex_circles.forge;

import dev.architectury.platform.forge.EventBuses;
import net.hex_circles.Hex_circles;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(Hex_circles.MOD_ID)
public class Hex_circlesForge {
    public Hex_circlesForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Hex_circles.MOD_ID, bus);
        bus.addListener(Hex_circlesClientForge::init);
        Hex_circles.init();
    }
}
