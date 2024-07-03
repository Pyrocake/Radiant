package io.github.pyrocake.block;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.item.ItemInit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockInit {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Radiant.MOD_ID);

    public static final DeferredBlock<Block> PRISMALLON_BLOCK = registerBlock("prismallon_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> RAW_PRISMALLON_BLOCK = registerBlock("raw_prismallon_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<Block> PRISMALLON_ORE_BLOCK = registerBlock("prismallon_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));

    public static DeferredBlock<Block> registerBlock(String name, Supplier<Block> block) {
        DeferredBlock<Block> blockReg = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties()));
        return blockReg;
    }
}
