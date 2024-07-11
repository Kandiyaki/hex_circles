package net.hex_circles.blockentity;

import at.petrak.hexcasting.api.block.HexBlockEntity;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BlockEntityChalk extends HexBlockEntity{
	public static final String TAG_PATTERN = "pattern";

    @Nullable
    public HexPattern pattern;
    
	public BlockEntityChalk(BlockPos pWorldPosition, BlockState pBlockState) {
		super(HexBlockEntities.SLATE_TILE, pWorldPosition, pBlockState);
		// TODO Auto-generated constructor stub
	}

	 @Override
    protected void saveModData(NbtCompound tag) {
        if (this.pattern != null) {
            tag.put(TAG_PATTERN, this.pattern.serializeToNBT());
        } else {
            tag.put(TAG_PATTERN, new NbtCompound());
        }
    }

	@Override
    protected void loadModData(NbtCompound tag) {
       if (tag.contains(TAG_PATTERN, NbtElement.COMPOUND_TYPE)) {
            NbtCompound patternTag = tag.getCompound(TAG_PATTERN);
            if (HexPattern.isPattern(patternTag)) {
                this.pattern = HexPattern.fromNBT(patternTag);
            } else {
                this.pattern = null;
            }
        } else {
            this.pattern = null;
        }
    }
}
