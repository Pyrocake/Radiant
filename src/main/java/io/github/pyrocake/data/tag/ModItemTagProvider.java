package io.github.pyrocake.data.tag;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.item.ModItems;
import io.github.pyrocake.util.TagsInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ModBlockTagsProvider modBlockTagsProvider) {
        super(output, lookupProvider, Radiant.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(TagsInit.Items.PRISMALLON_INGOT_TAG).add(ModItems.PRISMALLON_INGOT.get());
        tag(TagsInit.Items.RAW_TAG).add(ModItems.RAW_PRISMALLON.get());

        // Hypothetical tag
        // tag(ItemTags.TRIMMABLE_ARMOR
    }
}
