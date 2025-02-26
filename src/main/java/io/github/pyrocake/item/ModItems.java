package io.github.pyrocake.item;

import io.github.pyrocake.Radiant;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Radiant.MOD_ID);

    public static final DeferredItem<Item> PRISMALLON_INGOT = registerItem("prismallon_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_PRISMALLON = registerItem("raw_prismallon",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PRISMALLON_NUGGET = registerItem("prismallon_nugget",
            () -> new Item(new Item.Properties()));

    //Ramshackle
    private static <T extends Item> DeferredItem<T> registerItem(String name, Supplier<T> item) {
        DeferredItem<Item> toReturn = ITEMS.registerItem(name, p->new Item(p));
        return (DeferredItem<T>) toReturn;
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
