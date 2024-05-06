package net.hex_circles.item;

import at.petrak.hexcasting.common.items.ItemStaff;

import at.petrak.hexcasting.common.lib.HexSounds;
import at.petrak.hexcasting.common.network.MsgOpenSpellGuiAck;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.hex_circles.registry.Hex_circlesBlockRegistry;


public class ItemChalkStick extends AbstractChalkBlockItem {
	
	private static final Block BLOCK_DUST_RUNE= Hex_circlesBlockRegistry.CHALK_RUNE.get();

	public ItemChalkStick(Settings pProperties) {
		super(BLOCK_DUST_RUNE, pProperties);
	}
	
	
	
	
	 @Override
	public ActionResult place(ItemPlacementContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		Hand hand = context.getHand();
		use(world, player, hand);
		return super.place(context);
	}




	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
	        if (player.isSneaking()) {
	            if (world.isClient()) {
	                player.playSound(HexSounds.FAIL_PATTERN, 1f, 1f);
	            } else if (player instanceof ServerPlayerEntity serverPlayer) {
	                IXplatAbstractions.INSTANCE.clearCastingData(serverPlayer);
	            }
	        }

	        if (!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
	            var harness = IXplatAbstractions.INSTANCE.getHarness(serverPlayer, hand);
	            var patterns = IXplatAbstractions.INSTANCE.getPatterns(serverPlayer);
	            var descs = harness.generateDescs();

	            IXplatAbstractions.INSTANCE.sendPacketToPlayer(serverPlayer,
	                new MsgOpenSpellGuiAck(hand, patterns, descs.getFirst(), descs.getSecond(), descs.getThird(),
	                    harness.getParenCount()));
	        }

	        player.incrementStat(Stats.USED.getOrCreateStat(this));
//	        player.gameEvent(GameEvent.ITEM_INTERACT_START);

	        return TypedActionResult.success(player.getStackInHand(hand));
	    }

	

}
