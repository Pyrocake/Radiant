package io.github.pyrocake.item;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashSet;
import java.util.Set;

public class CreativeModeTabInit {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Radiant.MOD_ID);

    public static String RADIANT_TAB_TITLE = "radiant.tab";

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RADIANT_TAB = CREATIVE_MODE_TABS.register("radiant_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder();

        builder.displayItems((itemDisplay, output) -> {
            Set<Item> addedItems = new HashSet<>();

            ModItems.ITEMS.getEntries()
                    .stream()
                    .map((item) -> item.get().asItem())
                    .filter(addedItems::add)
                    .forEach(output::accept);

            ModBlocks.BLOCKS.getEntries()
                    .stream()
                    .map((block) -> block.get().asItem())
                    .filter(addedItems::add)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ModItems.PRISMALLON_INGOT.get()));
        builder.title(Component.translatable(RADIANT_TAB_TITLE));

        return builder.build();
    });
}
