<<<<<<<< HEAD:src/main/java/io/github/pyrocake/util/TagsInit.java
package io.github.pyrocake.util;
========
package io.github.pyrocake.tag;
>>>>>>>> origin/main:src/main/java/io/github/pyrocake/tag/TagsInit.java

import io.github.pyrocake.Radiant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagsInit {
    public static ResourceLocation createOreLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, "ores/" + name);
    }

    public static ResourceLocation createBlockLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, name);
    }

    public static ResourceLocation createGenericItemsLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, name);
    }

    public static ResourceLocation createRawItemsLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, "raw/" + name);
    }

    public static TagKey<Block> createToolTag(String name) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, name));
    }

    public static class ItemTagsInit {
        //Items
        public static final TagKey<Item> PRISMALLON_INGOT_TAG = ItemTags.create(createGenericItemsLocation("prismallon_ingot"));

        //Blocks
        public static final TagKey<Item> PRISMALLON_BLOCK_TAG = ItemTags.create(createBlockLocation("prismallon_block"));
    }

    public static class BlockTagsInit {
        public static final TagKey<Block> PRISMALLON_BLOCK_TAG =
                BlockTags.create(createBlockLocation("prismallon_block"));
    }
}
