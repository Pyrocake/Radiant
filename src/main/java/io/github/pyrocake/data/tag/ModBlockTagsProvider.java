package io.github.pyrocake.data.tag;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.util.TagsInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
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
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(TagsInit.Blocks.PRISMALLON_BLOCK_TAG)
                .addTag(TagsInit.Blocks.PRISMALLON_ORE_TAG)
                .addTag(TagsInit.Blocks.DEEPSLATE_PRISMALLON_ORE_TAG)

                .add(ModBlocks.RAW_PRISMALLON_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(TagsInit.Blocks.PRISMALLON_BLOCK_TAG)
                .addTag(TagsInit.Blocks.PRISMALLON_ORE_TAG)
                .addTag(TagsInit.Blocks.DEEPSLATE_PRISMALLON_ORE_TAG);
        tag(Tags.Blocks.ORES)
                .add(ModBlocks.PRISMALLON_ORE_BLOCK.get())
                .add(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get());

        // Hypothetical
        // TagKey<Block> name = createNeoForgeTag( info )


        // TAG LINKING
        tag(TagsInit.Blocks.PRISMALLON_BLOCK_TAG).add(ModBlocks.PRISMALLON_BLOCK.get());
        tag(TagsInit.Blocks.PRISMALLON_ORE_TAG).add(ModBlocks.PRISMALLON_ORE_BLOCK.get());
        tag(TagsInit.Blocks.DEEPSLATE_PRISMALLON_ORE_TAG).add(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get());

    }
}
