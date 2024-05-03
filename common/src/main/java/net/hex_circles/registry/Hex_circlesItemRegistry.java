package net.hex_circles.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.hex_circles.Hex_circles;
import net.hex_circles.item.ItemChalkStick;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import static net.hex_circles.Hex_circles.id;

public class Hex_circlesItemRegistry {
    // Register items through this
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Hex_circles.MOD_ID, Registry.ITEM_KEY);
    private static final Item.Settings CHALKCIRCLES_64STACK = new Item.Settings().maxCount(64);
    private static final Item.Settings CHALKCIRCLES_16STACK = new Item.Settings().maxCount(16);
    private static final Item.Settings CHALKCIRCLES_UNSTACKABLE = new Item.Settings().maxCount(1);
    
    public static void init() {
        ITEMS.register();
    }

    // A new creative tab. Notice how it is one of the few things that are not deferred
    public static final ItemGroup CIRCLES_GROUP = CreativeTabRegistry.create(id("circles_group"), () -> new ItemStack(Hex_circlesItemRegistry.DUMMY_ITEM.get()));

    // During the loading phase, refrain from accessing suppliers' items (e.g. EXAMPLE_ITEM.get()), they will not be available
    public static final RegistrySupplier<Item> DUMMY_ITEM = ITEMS.register("dummy_item", () -> new Item(new Item.Settings().group(CIRCLES_GROUP)));
    public static final RegistrySupplier<Item> CHALKSTICK_ITEM = ITEMS.register("amethyst_chalk", () -> new ItemChalkStick(CHALKCIRCLES_16STACK.group(CIRCLES_GROUP)));
    public static final RegistrySupplier<BlockItem> CHALKSQUARE_BLOCK_ITEM = ITEMS.register("chalk_square_block", () -> new BlockItem(Hex_circlesBlockRegistry.CHALK_RUNE.get(), CHALKCIRCLES_64STACK.group(CIRCLES_GROUP)));


}
