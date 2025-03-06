package io.github.pyrocake.block;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.custom.Connector_Block;
import io.github.pyrocake.block.custom.Solar_Oven_Block;
import io.github.pyrocake.block.custom.Sun_Block;
import io.github.pyrocake.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Radiant.MOD_ID);

    //TODO Change MAP COLOR
    public static final DeferredBlock<Block> PRISMALLON_BLOCK = registerBlockWithItem("prismallon_block", PrismallonBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> RAW_PRISMALLON_BLOCK = registerBlockWithItem("raw_prismallon_block", RawPrismallonBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> PRISMALLON_ORE_BLOCK = registerBlockWithItem("prismallon_ore", PrismallonOreBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));
    public static final DeferredBlock<Block> DEEPSLATE_PRISMALLON_ORE_BLOCK = registerBlockWithItem("deepslate_prismallon_ore", DeepslatePrismallonOreBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE));
    public static final DeferredBlock<Sun_Block> SUN_BLOCK = registerBlockWithItem("sun_block", Sun_Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());
    public static final DeferredBlock<Connector_Block> CONNECTOR_BLOCK = registerBlockWithItem("connector_block", Connector_Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static final DeferredBlock<Solar_Oven_Block> SOLAR_OVEN_BLOCK = registerBlockWithItem("solar_oven_block", Solar_Oven_Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());

    public static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block, Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, () -> block.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, name)))));
        ModItems.register(
                name,
                itemProps -> new BlockItem(toReturn.get(), itemProps),
                () -> new Item.Properties().useBlockDescriptionPrefix());
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
