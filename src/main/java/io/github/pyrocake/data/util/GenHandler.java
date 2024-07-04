package io.github.pyrocake.data.util;

import io.github.pyrocake.Radiant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class GenHandler {
    public static String getItemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString().replace(Radiant.MOD_ID + ":", "");
    }

    public static String getBlockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).toString().replace(Radiant.MOD_ID + ":", "");
    }
}
