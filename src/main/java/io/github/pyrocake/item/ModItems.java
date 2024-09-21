package io.github.pyrocake.item;

import io.github.pyrocake.Radiant;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Radiant.MOD_ID);

    public static final DeferredItem<Item> PRISMALLON_INGOT = ITEMS.register("prismallon_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_PRISMALLON = ITEMS.register("raw_prismallon",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PRISMALLON_NUGGET = ITEMS.register("prismallon_nugget",
            () -> new Item(new Item.Properties()));
}
