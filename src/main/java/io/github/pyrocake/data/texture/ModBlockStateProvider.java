package io.github.pyrocake.data.texture;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends ModelProvider {
    public ModBlockStateProvider(PackOutput output) {
        super(output, Radiant.MOD_ID);
    }

    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(ModBlocks.PRISMALLON_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.RAW_PRISMALLON_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.PRISMALLON_ORE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.CONNECTOR_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.SUN_BLOCK.get());
        //simpleBlock(ModBlocks.SUN_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/sun_block")));
    }
}
