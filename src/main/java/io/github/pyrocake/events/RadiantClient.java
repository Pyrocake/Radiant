package io.github.pyrocake.events;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.entity.ModBlockEntities;
import io.github.pyrocake.block.entity.SolarOvenBlockEntity;
import io.github.pyrocake.block.entity.SolarOvenRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Radiant.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class RadiantClient {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {
        });
    }

//    @SubscribeEvent
//    static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(ModBlockEntities.SOLAR_OVEN_BLOCK_ENTITY.get(), SolarOvenRenderer::new);
//    }
}
