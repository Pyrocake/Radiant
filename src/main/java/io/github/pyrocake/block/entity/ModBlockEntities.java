package io.github.pyrocake.block.entity;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Radiant.MOD_ID);

    public static final Supplier<BlockEntityType<SunBlockBlockEntity>> SUN_BLOCK_BE = BLOCK_ENTITIES.register("sun_block_be",
            () -> new BlockEntityType<>(
                    SunBlockBlockEntity::new,
                    ModBlocks.SUN_BLOCK.get()
            ));
    public static final Supplier<BlockEntityType<ConnectorBlockEntity>> CONNECTOR_BE = BLOCK_ENTITIES.register("connector_be",
            () -> new BlockEntityType<>(
                    ConnectorBlockEntity::new,
                    ModBlocks.CONNECTOR_BLOCK.get()
            ));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
