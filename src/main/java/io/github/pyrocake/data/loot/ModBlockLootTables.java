package io.github.pyrocake.data.loot;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(ModBlocks.PRISMALLON_ORE_BLOCK.get(), block -> createOreDrop(ModBlocks.PRISMALLON_ORE_BLOCK.get(), ModItems.RAW_PRISMALLON.get()));
        add(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get(), block -> createOreDrop(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get(), ModItems.RAW_PRISMALLON.get()));
        dropSelf(ModBlocks.RAW_PRISMALLON_BLOCK.get());
        dropSelf(ModBlocks.PRISMALLON_BLOCK.get());
        dropSelf(ModBlocks.SUN_BLOCK.get());
        dropSelf(ModBlocks.CONNECTOR_BLOCK.get());
        dropSelf(ModBlocks.SOLAR_OVEN_BLOCK.get());
        dropSelf(ModBlocks.COLLECTOR_BLOCK.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> Optional.of(BuiltInRegistries.BLOCK.getKey(block))
                        .filter(key -> key.getNamespace().equals(Radiant.MOD_ID))
                        .isPresent())
                .collect(Collectors.toSet());
    }
}
