package io.github.pyrocake.block.custom;

import com.mojang.serialization.MapCodec;
import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.block.entity.ConnectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.core.config.Scheduled;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Connector_Block extends PipeBlock implements EntityBlock {
    public static final VoxelShape CORE = Block.box(5, 5,5,11,11,11);
    public static final VoxelShape N = Block.box(5, 5, 0, 11, 11, 5);
    public static final VoxelShape E = Block.box(11, 5, 5, 16, 11, 11);
    public static final VoxelShape S = Block.box(5, 5, 11, 11, 11, 16);
    public static final VoxelShape W = Block.box(0, 5, 5, 5, 11, 11);
    public static final VoxelShape U = Block.box(5, 11, 5, 11, 16, 11);
    public static final VoxelShape D = Block.box(5, 0, 5, 11, 5, 11);
//    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
//    public static final BooleanProperty EAST = BlockStateProperties.EAST;
//    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
//    public static final BooleanProperty WEST = BlockStateProperties.WEST;
//    public static final BooleanProperty UP = BlockStateProperties.UP;
//    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public Connector_Block(Properties properties) {
        super(.5f, properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)
                this.stateDefinition.any())
                .setValue(NORTH, false))
                .setValue(EAST, false))
                .setValue(SOUTH, false))
                .setValue(WEST, false))
                .setValue(UP, false))
                .setValue(DOWN, false));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess access, BlockPos facingPos, Direction facing, BlockPos currentPos, BlockState facingState, RandomSource randomSource) {
        boolean flag = facingState.is(this) || facingState.is(ModBlocks.CONNECTOR_BLOCK);
        return state.setValue(PROPERTY_BY_DIRECTION.get(facing), flag);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, Orientation direction, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, direction, movedByPiston);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape SHAPE = CORE;
        if (state.getValue(NORTH)){
            SHAPE = Shapes.join(SHAPE, N, BooleanOp.OR);
        }
        if (state.getValue(EAST)){
            SHAPE = Shapes.join(SHAPE, E, BooleanOp.OR);
        }
        if (state.getValue(SOUTH)){
            SHAPE = Shapes.join(SHAPE, S, BooleanOp.OR);
        }
        if (state.getValue(WEST)){
            SHAPE = Shapes.join(SHAPE, W, BooleanOp.OR);
        }
        if (state.getValue(UP)){
            SHAPE = Shapes.join(SHAPE, U, BooleanOp.OR);
        }
        if (state.getValue(DOWN)){
            SHAPE = Shapes.join(SHAPE, D, BooleanOp.OR);
        }
        return SHAPE;
    }

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

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateWithConnections(context.getLevel(), context.getClickedPos(), this.defaultBlockState());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ConnectorBlockEntity(blockPos, blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
