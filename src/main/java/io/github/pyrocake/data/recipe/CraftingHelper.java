package io.github.pyrocake.data.recipe;

import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CraftingHelper extends RecipeProvider{
    public CraftingHelper(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    private static final List<ItemLike> PRISMALLON_SMELTABLES = List.of(ModItems.RAW_PRISMALLON.get(),
            ModBlocks.PRISMALLON_ORE_BLOCK.get(),
            ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get());

    @Override
    protected void buildRecipes(RecipeOutput output) {
        oreSmelting(output, PRISMALLON_SMELTABLES, RecipeCategory.MISC, ModItems.PRISMALLON_INGOT.get(), 1f, 200, "prismallon_ingot");
        oreBlasting(output, PRISMALLON_SMELTABLES, RecipeCategory.MISC, ModItems.PRISMALLON_INGOT.get(), 1f, 100, "prismallon_ingot");

        nineBlockStorageRecipes(output, RecipeCategory.MISC, ModItems.RAW_PRISMALLON.get(), RecipeCategory.MISC, ModBlocks.RAW_PRISMALLON_BLOCK);

        nineBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ModItems.PRISMALLON_INGOT,
                RecipeCategory.MISC, ModBlocks.PRISMALLON_BLOCK,
                "prismallon_ingot_from_prismallon_block", "prismallon_ingot"
        );
        nineBlockStorageRecipesWithCustomPacking(output, RecipeCategory.MISC, ModItems.PRISMALLON_NUGGET,
                RecipeCategory.MISC, ModItems.PRISMALLON_INGOT,
                "prismallon_ingot_from_nuggets", "prismallon_ingot"
        );
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOLAR_OVEN_BLOCK.get())
                .pattern("P P")
                .pattern("LPL")
                .define('P', Ingredient.of(ModItems.PRISMALLON_INGOT.get()))
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_prismallon_ingot", has(ModItems.PRISMALLON_INGOT.get()))
                .save(output);


    }
}
