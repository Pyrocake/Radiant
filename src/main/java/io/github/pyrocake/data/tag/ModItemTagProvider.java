package io.github.pyrocake.data.tag;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.item.ModItems;
import io.github.pyrocake.util.TagsInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ModBlockTagsProvider modBlockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modBlockTagsProvider.contentsGetter(), Radiant.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(TagsInit.Items.PRISMALLON_INGOT_TAG).add(ModItems.PRISMALLON_INGOT.get());
        tag(TagsInit.Items.RAW_TAG).add(ModItems.RAW_PRISMALLON.get());

        // Hypothetical tag
        // tag(ItemTags.TRIMMABLE_ARMOR
    }
}
