package io.github.pyrocake.util;

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

    public static class Items {
        //Items
        public static final TagKey<Item> PRISMALLON_INGOT_TAG = ItemTags.create(createGenericItemsLocation("prismallon_ingot"));

        public static final TagKey<Item> RAW_TAG = ItemTags.create(createRawItemsLocation("raw_prismallon"));

        //Blocks
        public static final TagKey<Item> PRISMALLON_BLOCK_TAG = ItemTags.create(createBlockLocation("prismallon_block"));

        public static final TagKey<Item> ORE_TAG = ItemTags.create(createOreLocation("prismallon_ore"));
        public static final TagKey<Item> DEEPSLATE_ORE_TAG = ItemTags.create(createOreLocation("deepslate_prismallon_ore"));

    }

    public static class Blocks {
        public static final TagKey<Block> PRISMALLON_BLOCK_TAG =
                BlockTags.create(createBlockLocation("prismallon_block"));
        public static final TagKey<Block> PRISMALLON_ORE_TAG =
                BlockTags.create(createOreLocation("prismallon_ore"));
        public static final TagKey<Block> DEEPSLATE_PRISMALLON_ORE_TAG =
                BlockTags.create(createOreLocation("deepslate_prismallon_ore"));
    }
}
