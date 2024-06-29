package io.github.pyrocake.data;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.data.lang.ModEnLangProvider;
import io.github.pyrocake.data.tag.ModBlockTagProvider;
import io.github.pyrocake.data.texture.ModBlockStateProvider;
import io.github.pyrocake.data.texture.ModItemStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            generator.addProvider(true, new ModEnLangProvider(output));
            generator.addProvider(true, new ModItemStateProvider(output, existingFileHelper));
            generator.addProvider(true, new ModBlockStateProvider(output, existingFileHelper));
            generator.addProvider(true, new ModBlockTagProvider(output, event.getLookupProvider(), existingFileHelper));
        } catch (RuntimeException e) {
            Radiant.logger.error("Failed to get data", e);
        }
    }
}
