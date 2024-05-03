package net.hex_circles.registry;

import static net.hex_circles.Hex_circles.id;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

import net.hex_circles.Hex_circles;
import net.hex_circles.blocks.BlockDustRune;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import at.petrak.hexcasting.api.block.circle.*;


public class Hex_circlesBlockRegistry {
    // Register items through this
	
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Hex_circles.MOD_ID, Registry.BLOCK_KEY);
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES = DeferredRegister.create(Hex_circles.MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY);



    public static void init() {
        BLOCKS.register();
        //BLOCKENTITIES.register();
    }

    // A new creative tab. Notice how it is one of the few things that are not deferred

    // During the loading phase, refrain from accessing suppliers' items (e.g. EXAMPLE_ITEM.get()), they will not be available
    public static final RegistrySupplier<Block> CHALK_RUNE = BLOCKS.register("chalk_square_block", () -> new BlockDustRune(AbstractBlock.Settings.of(Material.AGGREGATE).nonOpaque()));

}
