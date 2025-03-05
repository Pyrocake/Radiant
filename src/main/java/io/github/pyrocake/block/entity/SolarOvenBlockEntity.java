package io.github.pyrocake.block.entity;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.custom.Solar_Oven_Block;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.Optional;

public class SolarOvenBlockEntity extends BlockEntity implements Clearable {
    private static final int NUM_SLOTS = 4;
    private final NonNullList<ItemStack> items;
    private final int[] cookingProgress;
    private final int[] cookingTime;


    public SolarOvenBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
        this.cookingProgress = new int[NUM_SLOTS];
        this.cookingTime = new int[NUM_SLOTS];
    }

    public static void cooldownTick(Level level, BlockPos blockPos, BlockState blockState, SolarOvenBlockEntity blockEntity) {
        boolean bl = false;

        for (int i = 0; i < blockEntity.items.size(); i++) {
            if (blockEntity.cookingProgress[i] > 0) {
                bl = true;
                blockEntity.cookingProgress[i] = Mth.clamp(blockEntity.cookingProgress[i] - 2, 0, blockEntity.cookingTime[i]);
            }
        }

        if (bl) {
            setChanged(level, blockPos, blockState);
        }
    }

    @Override
    public void clearContent() {this.items.clear();}

    public static void cookTick(
            ServerLevel level,
            BlockPos pos,
            BlockState state,
            SolarOvenBlockEntity oven,
            RecipeManager.CachedCheck<SingleRecipeInput, SmeltingRecipe> cachedCheck) {
        boolean flag = false;

        for (int i = 0; i < oven.items.size(); i++) {
            ItemStack itemStack = oven.items.get(i);
            if (!itemStack.isEmpty()) {
                flag = true;
                oven.cookingProgress[i]++;
                if (oven.cookingProgress[i] >= oven.cookingTime[i]) {
                    SingleRecipeInput singleRecipeInput = new SingleRecipeInput(itemStack);
                    ItemStack itemStack1 = cachedCheck.getRecipeFor(singleRecipeInput, level)
                            .map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().assemble(singleRecipeInput, level.registryAccess()))
                            .orElse(itemStack);
                    if (itemStack1.isItemEnabled(level.enabledFeatures())) {
                        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemStack1);
                        oven.items.set(i, ItemStack.EMPTY);
                        level.sendBlockUpdated(pos, state, state, 3);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
                    }
                }
            }
        }

        if (flag) {
            setChanged(level, pos, state);
        }
    }

    public boolean placeSmeltable(ServerLevel serverLevel, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemStack2 = this.items.get(i);
            if (itemStack2.isEmpty()) {
                Optional<RecipeHolder<SmeltingRecipe>> optional = serverLevel.recipeAccess()
                        .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(itemStack), serverLevel);
                if (optional.isEmpty()) {
                    return false;
                }

                this.cookingTime[i] = ((SmeltingRecipe)((RecipeHolder)optional.get()).value()).cookingTime();
                this.cookingProgress[i] = 0;
                this.items.set(i, itemStack.consumeAndReturn(1, livingEntity));
                serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(livingEntity, this.getBlockState()));
                this.markUpdated();
                return true;
            }
        }

        return false;
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public static void particleTick(Level level, BlockPos blockPos, BlockState blockState, SolarOvenBlockEntity oven) {
        RandomSource randomSource = level.random;
        if (randomSource.nextFloat() < 0.11F) {
            for (int i = 0; i < randomSource.nextInt(2) + 2; i++) {
                Solar_Oven_Block.makeParticles(level, blockPos, false, false);
            }
        }
    }
}
