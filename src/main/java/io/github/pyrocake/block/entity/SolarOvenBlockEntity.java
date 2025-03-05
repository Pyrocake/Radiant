package io.github.pyrocake.block.entity;

import io.github.pyrocake.block.custom.Solar_Oven_Block;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class SolarOvenBlockEntity extends BlockEntity implements Clearable {
    private static final int NUM_SLOTS = 4;
    private final NonNullList<ItemStack> items;
    private final int[] cookingProgress;
    private final int[] cookingTime;


    public SolarOvenBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
        this.cookingProgress = new int[4];
        this.cookingTime = new int[4];
    }

//    public static void cookTick(ServerLevel level, BlockPos pos, BlockState state, SolarOvenBlockEntity oven, RecipeManager.CachedCheck<SingleRecipeInput, SmeltingRecipe> check) {
//        boolean flag = false;
//
//        for (int i = 0; i < oven.items.size(); i++) {
//            ItemStack itemStack = (ItemStack) oven.items.get(i);
//            if (!itemStack.isEmpty()) {
//                flag = true;
//                int progplus = oven.cookingProgress[i]++;
//                if (oven.cookingProgress[i] >= oven.cookingTime[i]) {
//                    SingleRecipeInput singleRecipeInput = new SingleRecipeInput(itemStack);
//                    ItemStack itemStack1 = (ItemStack) check.getRecipeFor(singleRecipeInput, level)
//                            .map(smeltingRecipeRecipeHolder -> ((SmeltingRecipe)smeltingRecipeRecipeHolder.value()).assemble(singleRecipeInput, level.registryAccess()))
//                            .orElse(itemStack);
//                    if (itemStack1.isItemEnabled(level.enabledFeatures())) {
//                        Containers.dropItemStack(level, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemStack1);
//                        oven.items.set(i, ItemStack.EMPTY);
//                        level.sendBlockUpdated(pos, state, state, 3);
//                        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
//                    }
//                }
//            }
//        }
//
//        if (flag) {
//            setChanged(level, pos, state);
//        }
//    }

    public static <E extends BlockEntity> void cookTick(ServerLevel level, BlockPos pos, BlockState state, SolarOvenBlockEntity oven, RecipeManager.CachedCheck<SingleRecipeInput, SmeltingRecipe> check) {
        boolean flag = false;

        for (int i = 0; i < oven.items.size(); i++) {
            ItemStack itemStack = (ItemStack) oven.items.get(i);
            if (!itemStack.isEmpty()) {
                flag = true;
                int progplus = oven.cookingProgress[i]++;
                if (oven.cookingProgress[i] >= oven.cookingTime[i]) {
                    SingleRecipeInput singleRecipeInput = new SingleRecipeInput(itemStack);
                    ItemStack itemStack1 = (ItemStack) check.getRecipeFor(singleRecipeInput, level)
                            .map(smeltingRecipeRecipeHolder -> ((SmeltingRecipe)smeltingRecipeRecipeHolder.value()).assemble(singleRecipeInput, level.registryAccess()))
                            .orElse(itemStack);
                    if (itemStack1.isItemEnabled(level.enabledFeatures())) {
                        Containers.dropItemStack(level, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemStack1);
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

    @Override
    public void clearContent() {this.items.clear();}
}
