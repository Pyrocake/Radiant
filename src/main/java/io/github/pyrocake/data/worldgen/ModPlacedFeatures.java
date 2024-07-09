package io.github.pyrocake.data.worldgen;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.data.worldgen.ore.ModOrePlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static ResourceKey<PlacedFeature> PRISMALLON_ORE_HIGH = createKey("prismallon_ore_high");
    public static ResourceKey<PlacedFeature> PRISMALLON_ORE_MID = createKey("prismallon_ore_mid");
    public static ResourceKey<PlacedFeature> PRISMALLON_ORE_LOW = createKey("prismallon_ore_low");

    public static void bootstrap(BootstrapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        Holder<ConfiguredFeature<?,?>> holder =
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PRISMALLON_ORE);

        register(context, PRISMALLON_ORE_HIGH, holder, ModOrePlacement
                .commonOrePlacements(90, HeightRangePlacement.uniform(VerticalAnchor.absolute(60), VerticalAnchor.absolute(380))));
        register(context, PRISMALLON_ORE_MID, holder, ModOrePlacement
                .commonOrePlacements(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(70))));
        register(context, PRISMALLON_ORE_LOW, holder, ModOrePlacement
                .commonOrePlacements(3, HeightRangePlacement.uniform(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(0))));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placementModifiers) {
        context.register(key, new PlacedFeature(feature, placementModifiers));
    }
}
