package io.github.pyrocake.init;

import io.github.pyrocake.Radiant;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(Radiant.MOD_ID);

    public static final DeferredItem<Item> PRISMALLON_INGOT = ITEMS.register("prismallon_ingot", () -> new Item(new Item.Properties()));
}
