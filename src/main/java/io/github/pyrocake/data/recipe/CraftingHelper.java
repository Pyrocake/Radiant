package io.github.pyrocake.data.recipe;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.item.ModItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.Iterator;
import java.util.List;

public class CraftingHelper extends RecipeProvider{
    protected CraftingHelper(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static final List<ItemLike> PRISMALLON_SMELTABLES = List.of(ModItems.RAW_PRISMALLON.get(),
            ModBlocks.PRISMALLON_ORE_BLOCK.get(),
            ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get());

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> getter = this.registries.lookupOrThrow(Registries.ITEM);
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
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, ModBlocks.SOLAR_OVEN_BLOCK.get())
                .pattern("P P")
                .pattern("LPL")
                .define('P', Ingredient.of(ModItems.PRISMALLON_INGOT.get()))
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_prismallon_ingot", has(ModItems.PRISMALLON_INGOT.get()))
                .save(this.output);


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
