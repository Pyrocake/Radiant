package io.github.pyrocake;

import io.github.pyrocake.block.entity.ModBlockEntities;
import io.github.pyrocake.data.DataGenerators;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.item.CreativeModeTabInit;
import io.github.pyrocake.item.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Radiant.MOD_ID)
public class Radiant {
    public static final String MOD_ID = "radiant";
    public static final Logger logger = LoggerFactory.getLogger(Radiant.class);

    public Radiant(@NotNull IEventBus bus) {
        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        CreativeModeTabInit.CREATIVE_MODE_TABS.register(bus);

        ModBlockEntities.register(bus);

        bus.addListener(DataGenerators::gatherData);

        bus.addListener(FMLClientSetupEvent.class, (fmlClientSetupEvent -> {
            fmlClientSetupEvent.enqueueWork(() -> {
                ModList.get().getModContainerById(MOD_ID).ifPresent(modContainer -> {
                    logger.info("Waking up the sun, version: {}", modContainer.getModInfo().getVersion());
                    ItemBlockRenderTypes.setRenderLayer(ModBlocks.CONNECTOR_BLOCK.get(), RenderType.cutout());
                });
            });
        }));
    }
}
