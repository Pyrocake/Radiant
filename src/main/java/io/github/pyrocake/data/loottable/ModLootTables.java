package io.github.pyrocake.data.loottable;

import com.google.common.collect.Sets;
import io.github.pyrocake.Radiant;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModLootTables extends LootTableProvider {
    public ModLootTables(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)), registries);
    }

    @Override
    protected void validate(@NotNull WritableRegistry<LootTable> writeableRegistry, @NotNull ValidationContext validationContext, ProblemReporter.@NotNull Collector problemReporter$collector) {
        var ModLootTablesId = BuiltInLootTables.all()
                .stream()
                .filter(id -> id.registry().getNamespace().equals(Radiant.MOD_ID))
                .collect(Collectors.toSet());
        for (var id : Sets.difference(ModLootTablesId, writeableRegistry.keySet())) {
            validationContext.reportProblem("Missing built-in table: " + id);
        }

        writeableRegistry.forEach((lootTable -> lootTable.validate(validationContext)));
    }
}
