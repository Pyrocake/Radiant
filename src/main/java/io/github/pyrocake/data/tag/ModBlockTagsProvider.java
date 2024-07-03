package io.github.pyrocake.data.tag;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.BlockInit;
import io.github.pyrocake.tag.TagsInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Radiant.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(TagsInit.BlockTagsInit.PRISMALLON_BLOCK_TAG).add(BlockInit.PRISMALLON_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(TagsInit.BlockTagsInit.PRISMALLON_BLOCK_TAG)
                .add(BlockInit.RAW_PRISMALLON_BLOCK.get())
                .add(BlockInit.PRISMALLON_ORE_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(TagsInit.BlockTagsInit.PRISMALLON_BLOCK_TAG);

        // Hypothetical
        // TagKey<Block> name = createNeoForgeTag( info )
    }
}
