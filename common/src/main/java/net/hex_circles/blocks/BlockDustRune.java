package net.hex_circles.blocks;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import at.petrak.hexcasting.common.blocks.circles.BlockEntitySlate;
import at.petrak.hexcasting.common.blocks.circles.BlockSlate;
import at.petrak.hexcasting.api.block.circle.BlockAbstractImpetus;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class BlockDustRune extends BlockSlate {
	
	private EnumSet<Direction> connectionDirections;
	private EnumProperty<ChalkShape> shape;
	private DirectionProperty baseDir;


	public BlockDustRune(Settings p_53182_) {
		super(p_53182_);
		//this.setDefaultState(getDefaultState().with(DIR1, Direction.SOUTH));
		//this.setDefaultState(getDefaultState().with(DIR1, Direction.NORTH));
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		// TODO Auto-generated method stub
		connectionDirections = super.exitDirections(pos, state, world);
		
		super.onPlaced(world, pos, state, placer, itemStack);
	}
	
	enum ChalkShape implements StringIdentifiable{
		dot,
		line,
		corner;

		@Override
		public String asString() {
			// TODO Auto-generated method stub
			return this.name();
		}
		
	}

	
	
	//TODO: write line connection stuff
	//step 1: take the "facing" direction and rotate the model so it faces that way
	//step 2: take the exit directions and figure out which model to use
	//step 3: take the exit directions and rotate the texture so it touches all the connections
	
	//EFFICIENT WAY TO DO IT I THINK:
	//have a DIR1 and a DIR2 -  just take any random 2 items in the connectionDirection set
	//super placement method already guarantees the model will be on the correct face
	//both textures have a connection at the south end. rotate the model so south becomes dir1
	//if dir2 is opposite of dir1, then use line model
	//if dir2 is 90deg from dir1, set to corner model
	//finally, rotate/mirror model if east became dir2
	//(if west became dir2, model will already be in correct location)
	
	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext pContext) {
		//get the placement state if it were a slate.
		//this has the face it's attached to and whether it's waterlogged.
		BlockState slateState = super.getPlacementState(pContext);
		//gets the first 2 directions in possibleExitDirections that have chalk in that direction
		//this is the most bullshit piece of code ive ever written but we ball
		Direction[] chalkDirections = new Direction[4];
		int index = 0;
		for (Direction D:this.connectionDirections) {
			//add blocks with connectible chalk to chalkDirections array
			BlockEntity BE =(pContext.getWorld().getBlockEntity((pContext.getBlockPos().offset(D))));
			if (BE instanceof BlockEntitySlate){
				chalkDirections[index] = D;
				index++;
			}
		}
		//now, chalkDirections has all the directions chalk is in. we'll only use the first 2
		//time for an if-else catastrophe, babey
		if(chalkDirections[0]==null) {
			// no surrounding chalk - just set to a default line
			slateState = slateState.with(shape, ChalkShape.line);
			if(getConnectedDirection(slateState) == Direction.UP || getConnectedDirection(slateState) == Direction.DOWN) {
				slateState = slateState.with(baseDir, Direction.SOUTH);
			}else {
				slateState = slateState.with(baseDir, Direction.DOWN);
			}	
		}else if(chalkDirections[1] == null) {
			//surrounding chalk on only one side
			slateState = slateState.with(shape, ChalkShape.line).with(baseDir, chalkDirections[0]);
		}else if(chalkDirections[0] == chalkDirections[1].getOpposite()) {
			//surrounding chalk on both sides, linear
			slateState = slateState.with(shape, ChalkShape.line).with(baseDir, chalkDirections[0]);
		}else {
			slateState = slateState.with(shape, ChalkShape.corner).with(baseDir, chalkDirections[0]);
		}
		//now shape and dir properties are good. the actual rotation is done in json (scary)
		return slateState;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState pState, Direction pFacing, BlockState pFacingState,
			WorldAccess pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		// TODO Auto-generated method stub
		return super.getStateForNeighborUpdate(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}
	
	
	
}
