package io.github.pyrocake.data.worldgen;

import io.github.pyrocake.Radiant;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomesModifiers {
    protected static ResourceKey<BiomeModifier> ADD_PRISMALLON_HIGH = createKey("add_prismallon_high");
    protected static ResourceKey<BiomeModifier> ADD_PRISMALLON_MID = createKey("add_prismallon_mid");
    protected static ResourceKey<BiomeModifier> ADD_PRISMALLON_LOW = createKey("add_prismallon_low");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(
                ADD_PRISMALLON_HIGH,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                    biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                    HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PRISMALLON_ORE_HIGH)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
        context.register(
                ADD_PRISMALLON_MID,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PRISMALLON_ORE_MID)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
        context.register(
                ADD_PRISMALLON_LOW,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PRISMALLON_ORE_LOW)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
    }

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, name));
    }
}
