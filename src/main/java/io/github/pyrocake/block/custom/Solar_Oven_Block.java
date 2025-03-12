package io.github.pyrocake.block.custom;

import com.mojang.serialization.MapCodec;
import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.entity.ModBlockEntities;
import io.github.pyrocake.block.entity.SolarOvenBlockEntity;
import io.github.pyrocake.block.entity.SunBlockBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.client.renderer.LightTexture.getBrightness;

public class Solar_Oven_Block extends BaseEntityBlock implements EntityBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static IntegerProperty INTENSITY = IntegerProperty.create("intensity", 0, 15);
    public static final Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public Solar_Oven_Block(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(LIT, true)
                .setValue(INTENSITY, 0));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide) {
            if (blockState.getValue(LIT)) {
                return createTickerHelper(blockEntityType, ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY.get(), Solar_Oven_Block::tickEntity);
            } else {
                return createTickerHelper(blockEntityType, ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY.get(), SolarOvenBlockEntity::cooldownTick);
            }
        } else {
            return blockState.getValue(LIT) ? createTickerHelper(blockEntityType, ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY.get(), SolarOvenBlockEntity::particleTick) : null;
        }
    }

    private static void tickEntity(Level level, BlockPos pos, BlockState state, SolarOvenBlockEntity blockEntity) {
        if (level.getGameTime() % 20L == 0L) {
            updateSignalStrength(state, level, pos);
        }
        if (level instanceof ServerLevel serverLevel) {
            RecipeManager.CachedCheck<SingleRecipeInput, SmeltingRecipe> cachedCheck = RecipeManager.createCheck(RecipeType.SMELTING);
            SolarOvenBlockEntity.cookTick(serverLevel, pos, state, blockEntity, cachedCheck);
        }
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult
    ) {
        if (level.getBlockEntity(blockPos) instanceof SolarOvenBlockEntity oven) {
            ItemStack itemStack2 = player.getItemInHand(interactionHand);
            if (level.recipeAccess().propertySet(RecipePropertySet.FURNACE_INPUT).test(itemStack2)) {
                if (level instanceof ServerLevel serverLevel && oven.placeSmeltable(serverLevel, player, itemStack2)) {
                    return InteractionResult.SUCCESS_SERVER;
                }
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SolarOvenBlockEntity(blockPos, blockState);
    }

    public static void makeParticles(Level level, BlockPos blockPos, boolean bl, boolean bl2) {
        RandomSource randomSource = level.getRandom();
        SimpleParticleType simpleParticleType = bl ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        level.addAlwaysVisibleParticle(
                simpleParticleType,
                true,
                (double)blockPos.getX() + 0.5 + randomSource.nextDouble() / 3.0 * (double)(randomSource.nextBoolean() ? 1 : -1),
                (double)blockPos.getY() + randomSource.nextDouble() + randomSource.nextDouble(),
                (double)blockPos.getZ() + 0.5 + randomSource.nextDouble() / 3.0 * (double)(randomSource.nextBoolean() ? 1 : -1),
                0.0,
                0.07,
                0.0
        );
        if (bl2) {
            level.addParticle(
                    ParticleTypes.SMOKE,
                    (double)blockPos.getX() + 0.5 + randomSource.nextDouble() / 4.0 * (double)(randomSource.nextBoolean() ? 1 : -1),
                    (double)blockPos.getY() + 0.4,
                    (double)blockPos.getZ() + 0.5 + randomSource.nextDouble() / 4.0 * (double)(randomSource.nextBoolean() ? 1 : -1),
                    0.0,
                    0.005,
                    0.0
            );
        }
    }

    private static void updateSignalStrength(BlockState blockState, Level level, BlockPos blockPos) {
        int i = level.getBrightness(LightLayer.SKY, blockPos) - level.getSkyDarken();
        float f = level.getSunAngle(1.0F);
        if (i > 0) {
            float g = f < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            f += (g - f) * 0.2F;
            i = Math.round((float)i * Mth.cos(f));
        }

        i = Mth.clamp(i, 0, 15);
        if (blockState.getValue(INTENSITY) != i) {
            level.setBlock(blockPos, blockState.setValue(INTENSITY, i), 3);
        }
    }

    protected void onRemove(BlockState blockState, Level level, BlockPos pos,  BlockState state, boolean isMoving) {
        if (!blockState.is(state.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof SolarOvenBlockEntity) {
                Containers.dropContents(level, pos, ((SolarOvenBlockEntity)blockentity).getItems());
            }
            super.onRemove(blockState, level, pos, state, isMoving);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, INTENSITY, FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
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
