package io.github.pyrocake.data.texture;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.item.ItemInit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class ModItemStateProvider extends ItemModelProvider {
    public ModItemStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Radiant.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        item(ItemInit.PRISMALLON_INGOT.get());
        item(ItemInit.RAW_PRISMALLON.get());
        item(ItemInit.PRISMALLON_NUGGET.get());
    }

    private void item(Item item) {
        String name = getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/" + name);
    }

    private @NotNull String getItemName(Item item) {
       return BuiltInRegistries.ITEM.getKey(item).toString().replace(Radiant.MOD_ID + ":", "");
    }
}
