package io.github.pyrocake.data.texture;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Radiant.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //Blocks + BlockItems
        simpleBlockWithItem(ModBlocks.PRISMALLON_BLOCK.get(), cubeAll(ModBlocks.PRISMALLON_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.RAW_PRISMALLON_BLOCK.get(), cubeAll(ModBlocks.RAW_PRISMALLON_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.PRISMALLON_ORE_BLOCK.get(), cubeAll(ModBlocks.PRISMALLON_ORE_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get(), cubeAll(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.CONNECTOR_BLOCK.get(), cubeAll(ModBlocks.CONNECTOR_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SUN_BLOCK.get(), cubeAll(ModBlocks.SUN_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SOLAR_OVEN_BLOCK.get(), cubeAll(ModBlocks.SOLAR_OVEN_BLOCK.get()));
        //simpleBlock(ModBlocks.SUN_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/sun_block")));
        simpleBlockWithItem(ModBlocks.COLLECTOR_BLOCK.get(), cubeAll(ModBlocks.COLLECTOR_BLOCK.get()));

        //Items
        itemModels().basicItem(ModItems.PRISMALLON_INGOT.get());
        itemModels().basicItem(ModItems.RAW_PRISMALLON.get());
        itemModels().basicItem(ModItems.PRISMALLON_NUGGET.get());
        }
    }
