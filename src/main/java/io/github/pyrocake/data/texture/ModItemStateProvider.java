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

public class ModItemStateProvider {

    //TODO learn how to split from blocks
    private void item(Item item) {
        String name = GenHandler.getItemName(item);
        //getBuilder(name)
        //        .parent(getExistingFile(mcLoc("item/generated")))
        //        .texture("layer0", "item/" + name);
    }
}
