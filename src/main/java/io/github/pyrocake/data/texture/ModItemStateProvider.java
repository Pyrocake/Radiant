package io.github.pyrocake.data.texture;

import io.github.pyrocake.data.util.GenHandler;
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
