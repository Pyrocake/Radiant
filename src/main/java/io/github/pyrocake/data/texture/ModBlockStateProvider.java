package io.github.pyrocake.data.texture;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.BlockInit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Radiant.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        standardBlock(BlockInit.PRISMALLON_BLOCK.get());
        standardBlock(BlockInit.RAW_PRISMALLON_BLOCK.get());
        standardBlock(BlockInit.PRISMALLON_ORE_BLOCK.get());
    }

    private void standardBlock(Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        simpleBlock(block, models().cubeAll(path, modLoc("block/" + path)));
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path)));
    }
}
