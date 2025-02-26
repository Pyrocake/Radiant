package io.github.pyrocake.block;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.custom.Connector_Block;
import io.github.pyrocake.block.custom.Sun_Block;
import io.github.pyrocake.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Radiant.MOD_ID);

    //TODO Change MAP COLOR
    public static final DeferredBlock<Block> PRISMALLON_BLOCK = registerBlock("prismallon_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> RAW_PRISMALLON_BLOCK = registerBlock("raw_prismallon_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> PRISMALLON_ORE_BLOCK = registerBlock("prismallon_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final DeferredBlock<Block> DEEPSLATE_PRISMALLON_ORE_BLOCK = registerBlock("deepslate_prismallon_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final DeferredBlock<Block> SUN_BLOCK = registerBlock("sun_block", () -> new Sun_Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final DeferredBlock<Block> CONNECTOR_BLOCK = registerBlock("connector_block", () -> new Connector_Block(0.5f, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    //THIS IS RAMSHACKLE
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<Block> toReturn = BLOCKS.registerBlock(name, p->new Block(p),
                BlockBehaviour.Properties.of());
        registerBlockItem(name, toReturn);
        return (DeferredBlock<T>) toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerSimpleBlockItem(block);
        //ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        Radiant.logger.info("success");
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        Radiant.logger.info("ModBlocks done!");
    }
}
