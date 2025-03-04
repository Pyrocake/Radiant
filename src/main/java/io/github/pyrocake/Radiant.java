package io.github.pyrocake;

import io.github.pyrocake.block.entity.ModBlockEntities;
import io.github.pyrocake.data.DataGenerators;
import io.github.pyrocake.block.ModBlocks;
import io.github.pyrocake.item.CreativeModeTabInit;
import io.github.pyrocake.item.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Radiant.MOD_ID)
public class Radiant {
    public static final String MOD_ID = "radiant";
    public static final Logger logger = LoggerFactory.getLogger(Radiant.class);

    public Radiant(@NotNull IEventBus bus, ModContainer container) {
        bus.addListener(DataGenerators::gatherData);
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(bus);

        ModBlocks.register(bus);

        CreativeModeTabInit.CREATIVE_MODE_TABS.register(bus);

        ModBlockEntities.register(bus);

        bus.addListener(FMLClientSetupEvent.class, (fmlClientSetupEvent -> {
            fmlClientSetupEvent.enqueueWork(() -> {
                ModList.get().getModContainerById(MOD_ID).ifPresent(modContainer -> {
                    logger.info("Waking up the sun, version: {}", modContainer.getModInfo().getVersion());
                    ItemBlockRenderTypes.setRenderLayer(ModBlocks.CONNECTOR_BLOCK.get(), RenderType.cutout());
                });
            });
        }));

        //container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
        logger.info("Server starting!");
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientStartup(FMLClientSetupEvent event) {

        }
    }
}
