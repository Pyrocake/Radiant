package io.github.pyrocake.block.custom;

import com.mojang.serialization.MapCodec;
import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.entity.ModBlockEntities;
import io.github.pyrocake.block.entity.SolarOvenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Solar_Oven_Block extends BaseEntityBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static IntegerProperty INTENSITY = BlockStateProperties.POWER;

    public Solar_Oven_Block(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(LIT, false)
                .setValue(INTENSITY, 0));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
//        if (level instanceof ServerLevel serverLevel) {
//            if (blockState.getValue(LIT)) {
//                RecipeManager.CachedCheck<SingleRecipeInput, SmeltingRecipe> cachedCheck = RecipeManager.createCheck(RecipeType.SMELTING);
//                return createTickerHelper(
//                        blockEntityType,
//                        ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY,
//                        (levelx, blockPos, blockStatex, solarOvenBlockEntity) -> CampfireBlockEntity.cookTick(serverLevel, blockPos, blockStatex, solarOvenBlockEntity, cachedCheck)
//                );
//            } else {
//                return createTickerHelper(blockEntityType, ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY, SolarOvenBlockEntity::cooldownTick);
//            }
//        } else {
//            return blockState.getValue(LIT) ? createTickerHelper(blockEntityType, BlockEntityType.CAMPFIRE, CampfireBlockEntity::particleTick) : null;
//        }
//    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    protected void onRemove(BlockState blockState, Level level, BlockPos pos,  BlockState state, boolean isMoving) {
        //TODO: drop contents if not empty
        super.onRemove(blockState, level, pos, state, isMoving);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, INTENSITY);
    }

    static {
        INTENSITY = BlockStateProperties.POWER;
    }
}
