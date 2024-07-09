package io.github.pyrocake.data.worldgen;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    protected static ResourceKey<ConfiguredFeature<?,?>> PRISMALLON_ORE = createKey("prismallon_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {
        RuleTest stoneReplacable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> prismallonOre =
                List.of(OreConfiguration.target(stoneReplacable, ModBlocks.PRISMALLON_ORE_BLOCK.get().defaultBlockState()),
                        OreConfiguration.target(deepslateReplacable, ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get().defaultBlockState()));

        register(context, PRISMALLON_ORE, Feature.ORE, new OreConfiguration(prismallonOre, 9));
    }

    private static ResourceKey<ConfiguredFeature<?,?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?,?>> key,  F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
