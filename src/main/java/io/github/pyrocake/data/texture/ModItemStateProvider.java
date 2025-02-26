package io.github.pyrocake.data.texture;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.data.util.GenHandler;
import io.github.pyrocake.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

public class ModItemStateProvider extends ModelProvider {
    public ModItemStateProvider(PackOutput output) {
        super(output, Radiant.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.PRISMALLON_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_PRISMALLON.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PRISMALLON_NUGGET.get(), ModelTemplates.FLAT_ITEM);
    }

    private void item(Item item) {
        String name = GenHandler.getItemName(item);
        //getBuilder(name)
        //        .parent(getExistingFile(mcLoc("item/generated")))
        //        .texture("layer0", "item/" + name);
    }
}
