package io.github.pyrocake;

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
        bus.addListener(FMLClientSetupEvent.class, (fmlClientSetupEvent -> {
            fmlClientSetupEvent.enqueueWork(() -> {
                ModList.get().getModContainerById(MOD_ID).ifPresent(modContainer -> {
                    logger.info("Waking up the sun, version: {}", modContainer.getModInfo().getVersion());
                });
            });
        }));
    }
}
