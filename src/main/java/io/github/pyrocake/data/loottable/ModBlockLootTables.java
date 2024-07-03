package io.github.pyrocake.data.loottable;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
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
        dropSelf(BlockInit.PRISMALLON_BLOCK.get());
        dropSelf(BlockInit.RAW_PRISMALLON_BLOCK.get());
        dropSelf(BlockInit.PRISMALLON_ORE_BLOCK.get());
        dropSelf(BlockInit.DEEPSLATE_PRISMALLON_ORE_BLOCK.get());
        dropSelf(BlockInit.SUN_BLOCK.get());
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
