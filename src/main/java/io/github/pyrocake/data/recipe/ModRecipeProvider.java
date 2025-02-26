package io.github.pyrocake.data.recipe;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.NeoForgeConditions;


import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends CraftingHelper {
    private static final List<ItemLike> PRISMALLON_SMELTABLES = List.of(ModItems.RAW_PRISMALLON.get(),
            ModBlocks.PRISMALLON_ORE_BLOCK.get(),
            ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get());

    public ModRecipeProvider(RecipeOutput output, HolderLookup.Provider provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes() {
        oreSmelting(PRISMALLON_SMELTABLES, RecipeCategory.MISC, ModItems.PRISMALLON_INGOT.get(), 1f, 200, "prismallon_ingot");
        oreBlasting(PRISMALLON_SMELTABLES, RecipeCategory.MISC, ModItems.PRISMALLON_INGOT.get(), 1f, 100, "prismallon_ingot");

        nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.RAW_PRISMALLON.get(), RecipeCategory.MISC, ModBlocks.RAW_PRISMALLON_BLOCK);

        nineBlockStorageRecipesRecipesWithCustomUnpacking(RecipeCategory.MISC, ModItems.PRISMALLON_INGOT,
                RecipeCategory.MISC, ModBlocks.PRISMALLON_BLOCK,
                "prismallon_ingot_from_prismallon_block", "prismallon_ingot"
        );
        nineBlockStorageRecipesWithCustomPacking(RecipeCategory.MISC, ModItems.PRISMALLON_NUGGET,
                RecipeCategory.MISC, ModItems.PRISMALLON_INGOT,
                "prismallon_ingot_from_nuggets", "prismallon_ingot"
        );

    }

    protected void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_smelting");
    }

    protected void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_blasting");
    }

    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group, String suffix) {
        Iterator var10 = ingredients.iterator();

        while(var10.hasNext()) {
            ItemLike itemlike = (ItemLike)var10.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), category, result, experience, cookingTime, serializer, recipeFactory)
                    .group(group).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Radiant.MOD_ID + ":" + getItemName(result) + suffix + "_" + getItemName(itemlike));
        }

    }
}
