package io.github.pyrocake.block.custom;

import com.mojang.serialization.MapCodec;
import io.github.pyrocake.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.Nullable;

public class Connector_Block extends PipeBlock implements EntityBlock {
    public Connector_Block(float apothem, Properties properties) {
        super(apothem, properties);

        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)
                this.stateDefinition.any())
                .setValue(NORTH, false))
                .setValue(EAST, false))
                .setValue(SOUTH, false))
                .setValue(WEST, false))
                .setValue(UP, false))
                .setValue(DOWN, false));

    }

//    @Override
//    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//        return super.getCollisionShape(state, level, pos, context);
//    }

    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (false) {
            level.scheduleTick(currentPos, this, 1);
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            boolean flag = facingState.is(this) || facingState.is(ModBlocks.CONNECTOR_BLOCK);
            return (BlockState)state.setValue((Property)PROPERTY_BY_DIRECTION.get(facing), flag);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

//        if (level.getBlockState(neighborPos).getBlock() == ModBlocks.CONNECTOR_BLOCK.get()) {
//
//        }

        getStateWithConnections(level, pos, state);

    }

//    protected boolean useShapeForLightOcclusion(BlockState state) {
//        return true;
//    }

    public static BlockState getStateWithConnections(BlockGetter level, BlockPos pos, BlockState state) {
        BlockState blockstate = level.getBlockState(pos.below());
        BlockState blockstate1 = level.getBlockState(pos.above());
        BlockState blockstate2 = level.getBlockState(pos.north());
        BlockState blockstate3 = level.getBlockState(pos.east());
        BlockState blockstate4 = level.getBlockState(pos.south());
        BlockState blockstate5 = level.getBlockState(pos.west());
        Block block = state.getBlock();
        return state.trySetValue(DOWN, Boolean.valueOf(blockstate.is(block) || blockstate.is(ModBlocks.CONNECTOR_BLOCK)))
                .trySetValue(UP, Boolean.valueOf(blockstate1.is(block) || blockstate1.is(ModBlocks.CONNECTOR_BLOCK)))
                .trySetValue(NORTH, Boolean.valueOf(blockstate2.is(block) || blockstate2.is(ModBlocks.CONNECTOR_BLOCK)))
                .trySetValue(EAST, Boolean.valueOf(blockstate3.is(block) || blockstate3.is(ModBlocks.CONNECTOR_BLOCK)))
                .trySetValue(SOUTH, Boolean.valueOf(blockstate4.is(block) || blockstate4.is(ModBlocks.CONNECTOR_BLOCK)))
                .trySetValue(WEST, Boolean.valueOf(blockstate5.is(block) || blockstate5.is(ModBlocks.CONNECTOR_BLOCK)));
    }

    @Override
    protected MapCodec<? extends PipeBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{NORTH, EAST, SOUTH, WEST, UP, DOWN});
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
