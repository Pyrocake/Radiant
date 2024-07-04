package io.github.pyrocake.data.texture;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.data.util.GenHandler;
import io.github.pyrocake.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemStateProvider extends ItemModelProvider {
    public ModItemStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Radiant.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        item(ModItems.PRISMALLON_INGOT.get());
        item(ModItems.RAW_PRISMALLON.get());
        item(ModItems.PRISMALLON_NUGGET.get());
    }

    private void item(Item item) {
        String name = GenHandler.getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/" + name);
    }
}
