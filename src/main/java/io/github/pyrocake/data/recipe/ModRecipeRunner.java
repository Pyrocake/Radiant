package io.github.pyrocake.data.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModRecipeRunner extends RecipeProvider.Runner {
    public ModRecipeRunner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        return new CraftingHelper(provider, output);
    }

    @Override
    public String getName() {
        return "Radiant Recipes";
    }
}
