package io.github.pyrocake.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ConnectorBlockEntity extends BlockEntity{
    public ConnectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CONNECTOR_BE.get(), pos, blockState);
    }
}
