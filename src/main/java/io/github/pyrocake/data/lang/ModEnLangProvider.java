package io.github.pyrocake.data.lang;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.init.CreativeModeTabInit;
import io.github.pyrocake.init.ItemInit;
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

        //Misc
        add(CreativeModeTabInit.RADIANT_TAB_TITLE, "Radiant");
    }
}
