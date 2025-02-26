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
import net.minecraft.server.packs.PackType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Radiant.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(true, new ModEnLangProvider(output));
            generator.addProvider(true, new ModItemStateProvider(output));
            generator.addProvider(true, new ModBlockStateProvider(output));
            ModBlockTagsProvider modBlockTagsProvider = new ModBlockTagsProvider(output, lookupProvider);
            generator.addProvider(true, modBlockTagsProvider);
            generator.addProvider(true, new ModItemTagProvider(output, event.getLookupProvider(), modBlockTagsProvider));
            generator.addProvider(true, new ModLootTables(output, lookupProvider));
            //generator.addProvider(true, new ModRecipeProvider(output, lookupProvider));
            Radiant.logger.info("World Gen Starting");
            generator.addProvider(true, new ModWorldGenProvider(output, lookupProvider));
            Radiant.logger.info("World Gen Done");
        } catch (RuntimeException e) {
            Radiant.logger.error("Failed to get data", e);
        }
    }
}
