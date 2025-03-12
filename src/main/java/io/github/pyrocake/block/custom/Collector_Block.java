package io.github.pyrocake.block.custom;

import com.mojang.serialization.MapCodec;
import io.github.pyrocake.block.entity.CollectorBlockEntity;
import io.github.pyrocake.block.entity.ModBlockEntities;
import io.github.pyrocake.block.entity.SolarOvenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Collector_Block extends BaseEntityBlock implements EntityBlock {
    public static IntegerProperty INTENSITY = IntegerProperty.create("intensity", 0, 15);

    public Collector_Block(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(INTENSITY, 0));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CollectorBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide) {
            return createTickerHelper(blockEntityType, ModBlockEntities.COLLECTOR_BLOCK_ENTITY.get(), Collector_Block::tickEntity);
        } else {
            return null;
        }
    }

    private static void tickEntity(Level level, BlockPos pos, BlockState state, CollectorBlockEntity blockEntity) {
        if (level.getGameTime() % 20L == 0L) {
            updateSignalStrength(state, level, pos);
        }
        if (level instanceof ServerLevel serverLevel) {
            CollectorBlockEntity.ticking(level, pos, state, blockEntity);
        }
    }

    protected void onRemove(BlockState blockState, Level level, BlockPos pos,  BlockState state, boolean isMoving) {
        if (!blockState.is(state.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
//            if (blockentity instanceof CollectorBlockEntity) {
//                Containers.dropContents(level, pos, ((CollectorBlockEntity)blockentity).getItems());
//            }
            super.onRemove(blockState, level, pos, state, isMoving);
        }
    }

    private static void updateSignalStrength(BlockState blockState, Level level, BlockPos blockPos) {
        int i = level.getBrightness(LightLayer.SKY, blockPos.above()) - level.getSkyDarken();
        float f = level.getSunAngle(1.0F);
        if (i > 0) {
            float g = f < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            f += (g - f) * 0.2F;
            i = Math.round((float)i * Mth.cos(f));
        }

        i = Mth.clamp(i, 0, 15);
        if (blockState.getValue(INTENSITY) != i) {
            level.setBlock(blockPos, blockState.setValue(INTENSITY, Integer.valueOf(i)), 3);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(INTENSITY);
    }

    public float intensity(Level level, BlockPos blockPos) {
        float i = level.getBrightness(LightLayer.SKY, blockPos.above()) - level.getSkyDarken();
        float f = level.getSunAngle(1.0F);
        if (i > 0) {
            float g = f < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            f += (g - f) * 0.2F;
            i = i * Mth.cos(f);
        }

        i = Mth.clamp(i, 0, 15);
        return i;
    }
}
