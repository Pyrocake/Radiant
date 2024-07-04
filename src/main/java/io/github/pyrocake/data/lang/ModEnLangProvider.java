package io.github.pyrocake.data.lang;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.item.CreativeModeTabInit;
import io.github.pyrocake.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnLangProvider extends LanguageProvider {
    public ModEnLangProvider(PackOutput output) {
        super(output, Radiant.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Items
        addItem(ModItems.PRISMALLON_INGOT, "Prismallon Ingot");
        addItem(ModItems.RAW_PRISMALLON, "Raw Prismallon");
        addItem(ModItems.PRISMALLON_NUGGET, "Prismallon Nugget");

        //Blocks
        addBlock(ModBlocks.PRISMALLON_BLOCK, "Prismallon Block");
        addBlock(ModBlocks.RAW_PRISMALLON_BLOCK, "Raw Prismallon Block");
        addBlock(ModBlocks.PRISMALLON_ORE_BLOCK, "Prismallon Ore");
        addBlock(ModBlocks.DEEPSLATE_PRISMALLON_ORE_BLOCK, "Deepslate Prismallon Ore");

        addBlock(ModBlocks.SUN_BLOCK, "Sun Block WIP");

        //Misc
        add(CreativeModeTabInit.RADIANT_TAB_TITLE, "Radiant");
    }
}
