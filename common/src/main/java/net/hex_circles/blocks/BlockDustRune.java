package net.hex_circles.blocks;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import at.petrak.hexcasting.common.blocks.circles.BlockEntitySlate;
import at.petrak.hexcasting.common.blocks.circles.BlockSlate;
import at.petrak.hexcasting.common.lib.HexSounds;
import at.petrak.hexcasting.common.network.MsgOpenSpellGuiAck;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import at.petrak.hexcasting.api.block.circle.BlockAbstractImpetus;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import net.hex_circles.blockentity.BlockEntityChalk;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class BlockDustRune extends BlockSlate {
	
	private EnumSet<Direction> connectionDirections;


	public BlockDustRune(Settings p_53182_) {
		super(p_53182_);
		this.setDefaultState(super.stateManager.getDefaultState()
				.with(ENERGIZED, false)
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false)
				);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		// TODO Auto-generated method stub
		super.onPlaced(world, pos, state, placer, itemStack);
		PlayerEntity player = (PlayerEntity) placer; //hopefully nobody figures out a way to get a pig to place these
		Hand hand = player.getActiveHand();
		
		//open a casting grid
		if (player.isSneaking()) {
            if (world.isClient()) {
                player.playSound(HexSounds.FAIL_PATTERN, 1f, 1f);
            } else if (player instanceof ServerPlayerEntity serverPlayer) {
                IXplatAbstractions.INSTANCE.clearCastingData(serverPlayer);
            }
        }

        if (!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
            var harness = IXplatAbstractions.INSTANCE.getHarness(serverPlayer, hand);
            
        //TODO: change the patterns var to a list of only a consideration.
        
            var patterns = IXplatAbstractions.INSTANCE.getPatterns(serverPlayer);
            var descs = harness.generateDescs();

            IXplatAbstractions.INSTANCE.sendPacketToPlayer(serverPlayer,
                new MsgOpenSpellGuiAck(hand, patterns, descs.getFirst(), descs.getSecond(), descs.getThird(),
                    harness.getParenCount()));
        }
        
	}
	
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState pState, Direction pFacing, BlockState pFacingState,
			WorldAccess pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		// TODO Auto-generated method stub
		return super.getStateForNeighborUpdate(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		// TODO Auto-generated method stub
		super.appendProperties(builder);
	
	}
	
	@Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityChalk(pPos, pState);
    }

	
	 @Override
	 public @Nullable
    HexPattern getPattern(BlockPos pos, BlockState bs, World world) {
        if (world.getBlockEntity(pos) instanceof BlockEntityChalk tile) {
            return tile.pattern;
        } else {
            return null;
        }
    }
	
	
}
