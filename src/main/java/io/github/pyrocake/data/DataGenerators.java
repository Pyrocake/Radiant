package io.github.pyrocake.data;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.data.lang.ModEnLangProvider;
import io.github.pyrocake.data.loot.ModLootTables;
import io.github.pyrocake.data.recipe.ModRecipeProvider;
import io.github.pyrocake.data.tag.ModBlockTagsProvider;
import io.github.pyrocake.data.tag.ModItemTagProvider;
import io.github.pyrocake.data.texture.ModBlockStateProvider;
import io.github.pyrocake.data.texture.ModItemStateProvider;
import io.github.pyrocake.data.worldgen.ModWorldGenProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(true, new ModEnLangProvider(output));
            generator.addProvider(true, new ModItemStateProvider(output, existingFileHelper));
            generator.addProvider(true, new ModBlockStateProvider(output, existingFileHelper));
            ModBlockTagsProvider modBlockTagsProvider = new ModBlockTagsProvider(output, event.getLookupProvider(), existingFileHelper);
            generator.addProvider(true, modBlockTagsProvider);
            generator.addProvider(true, new ModItemTagProvider(output, event.getLookupProvider(), modBlockTagsProvider, existingFileHelper));
            generator.addProvider(true, new ModLootTables(output, event.getLookupProvider()));
            generator.addProvider(true, new ModRecipeProvider(output, lookupProvider));
            Radiant.logger.info("World Gen Starting");
            generator.addProvider(true, new ModWorldGenProvider(output, lookupProvider));
            Radiant.logger.info("World Gen Done");
        } catch (RuntimeException e) {
            Radiant.logger.error("Failed to get data", e);
        }
    }
}
