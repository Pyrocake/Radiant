package io.github.pyrocake.data.lang;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.BlockInit;
import io.github.pyrocake.block.custom.SunBlock;
import io.github.pyrocake.item.CreativeModeTabInit;
import io.github.pyrocake.item.ItemInit;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnLangProvider extends LanguageProvider {
    public ModEnLangProvider(PackOutput output) {
        super(output, Radiant.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Items
        addItem(ItemInit.PRISMALLON_INGOT, "Prismallon Ingot");
        addItem(ItemInit.RAW_PRISMALLON, "Raw Prismallon");
        addItem(ItemInit.PRISMALLON_NUGGET, "Prismallon Nugget");

        //Blocks
        addBlock(BlockInit.PRISMALLON_BLOCK, "Prismallon Block");
        addBlock(BlockInit.RAW_PRISMALLON_BLOCK, "Raw Prismallon Block");
        addBlock(BlockInit.PRISMALLON_ORE_BLOCK, "Prismallon Ore");
        addBlock(BlockInit.DEEPSLATE_PRISMALLON_ORE_BLOCK, "Deepslate Prismallon Ore");

        addBlock(BlockInit.SUN_BLOCK, "Sun Block WIP");

        //Misc
        add(CreativeModeTabInit.RADIANT_TAB_TITLE, "Radiant");
    }
}
