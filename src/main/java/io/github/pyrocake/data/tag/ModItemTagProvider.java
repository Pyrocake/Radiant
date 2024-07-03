package io.github.pyrocake.data.tag;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.item.ItemInit;
import io.github.pyrocake.util.TagsInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider provider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, provider.contentsGetter(), Radiant.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        copy(TagsInit.BlockTagsInit.PRISMALLON_BLOCK_TAG, TagsInit.ItemTagsInit.PRISMALLON_BLOCK_TAG);

        tag(TagsInit.ItemTagsInit.PRISMALLON_INGOT_TAG).add(ItemInit.PRISMALLON_INGOT.get());

        // Hypothetical tag
        // tag(ItemTags.TRIMMABLE_ARMOR
    }
}
