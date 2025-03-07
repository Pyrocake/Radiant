package io.github.pyrocake.block.entity;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.custom.Solar_Oven_Block;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

import static io.github.pyrocake.block.custom.Solar_Oven_Block.INTENSITY;

public class SolarOvenBlockEntity extends BlockEntity implements Clearable {
    private static final int NUM_SLOTS = 4;
    public final NonNullList<ItemStack> items = NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
    private final int[] cookingProgress = new int[NUM_SLOTS];
    private final int[] cookingTime = new int[NUM_SLOTS];


    public SolarOvenBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY.get(), pos, blockState);
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
                int heat = state.getValue(INTENSITY);
                oven.cookingProgress[i] += heat;
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

                this.cookingTime[i] = ((SmeltingRecipe)((RecipeHolder)optional.get()).value()).cookingTime() * 15;
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

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.items.clear();
        ContainerHelper.loadAllItems(compoundTag, this.items, provider);
        if (compoundTag.contains("CookingTimes", 11)) {
            int[] aint = compoundTag.getIntArray("CookingTimes");
            System.arraycopy(aint, 0, this.cookingProgress, 0, Math.min(this.cookingTime.length, aint.length));
        }

        if (compoundTag.contains("CookingTotalTimes", 11)) {
            int[] aint1 = compoundTag.getIntArray("CookingTotalTimes");
            System.arraycopy(aint1, 0, this.cookingTime, 0, Math.min(this.cookingTime.length, aint1.length));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        ContainerHelper.saveAllItems(compoundTag, this.items, true, provider);
        //Radiant.logger.info("Stuff B: " + this.items);
        compoundTag.putIntArray("CookingTimes", this.cookingProgress);
        compoundTag.putIntArray("CookingTotalTimes", this.cookingTime);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput input) {
        super.applyImplicitComponents(input);
        input.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(this.getItems());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        ContainerHelper.saveAllItems(compoundTag, this.items, true, provider);
        return compoundTag;
    }
}
