package io.github.pyrocake.data.texture;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output) {
        super(output, Radiant.MOD_ID);
    }

    @Override
    protected void registerStatesAndModels() {
        standardBlock(ModBlocks.PRISMALLON_BLOCK.get());
        standardBlock(ModBlocks.RAW_PRISMALLON_BLOCK.get());
        standardBlock(ModBlocks.PRISMALLON_ORE_BLOCK.get());
        standardBlock(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get());
        standardBlock(ModBlocks.CONNECTOR_BLOCK.get());
        simpleBlock(ModBlocks.SUN_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/sun_block")));
    }

    private void standardBlock(Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        simpleBlock(block, models().cubeAll(path, modLoc("block/" + path)));
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path)));
    }
}
