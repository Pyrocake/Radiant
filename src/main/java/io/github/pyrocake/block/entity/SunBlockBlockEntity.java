package io.github.pyrocake.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SunBlockBlockEntity extends BlockEntity{
    public SunBlockBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SUN_BLOCK_BE.get(), pos, blockState);
    }
}
