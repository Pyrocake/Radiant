package io.github.pyrocake.data.tag;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.item.ModItems;
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
        copy(TagsInit.Blocks.PRISMALLON_BLOCK_TAG, TagsInit.Items.PRISMALLON_BLOCK_TAG);

        copy(TagsInit.Blocks.PRISMALLON_ORE_TAG, TagsInit.Items.ORE_TAG);
        copy(TagsInit.Blocks.DEEPSLATE_PRISMALLON_ORE_TAG, TagsInit.Items.DEEPSLATE_ORE_TAG);

        tag(TagsInit.Items.PRISMALLON_INGOT_TAG).add(ModItems.PRISMALLON_INGOT.get());
        tag(TagsInit.Items.RAW_TAG).add(ModItems.RAW_PRISMALLON.get());

        // Hypothetical tag
        // tag(ItemTags.TRIMMABLE_ARMOR
    }
}
