package io.github.pyrocake.data.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public abstract class CraftingHelper extends RecipeProvider {
    protected CraftingHelper(RecipeOutput output, HolderLookup.Provider provider) {
        super(provider, output);
    }
}
