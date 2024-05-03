package net.hex_circles.item;

import at.petrak.hexcasting.common.lib.HexSounds;
import at.petrak.hexcasting.common.network.MsgOpenSpellGuiAck;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import dev.architectury.event.events.common.TickEvent.Player;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import at.petrak.hexcasting.common.items.ItemStaff;


public abstract class AbstractChalkBlockItem extends BlockItem {

	public AbstractChalkBlockItem(Block block, Settings properties) {
		super(block, properties);
		// TODO Auto-generated constructor stub
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
