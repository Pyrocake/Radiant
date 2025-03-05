package io.github.pyrocake.block.entity;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Radiant.MOD_ID);

    public static final Supplier<BlockEntityType<SunBlockBlockEntity>> SUN_BLOCK_BLOCK_ENTITY = BLOCK_ENTITIES.register("sun_block_block_entity",
            () -> new BlockEntityType<>(
                    SunBlockBlockEntity::new,
                    ModBlocks.SUN_BLOCK.get()
            ));
    public static final Supplier<BlockEntityType<ConnectorBlockEntity>> CONNECTOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("connector_block_entity",
            () -> new BlockEntityType<>(
                    ConnectorBlockEntity::new,
                    ModBlocks.CONNECTOR_BLOCK.get()
            ));
    public static final Supplier<BlockEntityType<SolarOvenBlockEntity>> SOLAR_OVEN_BLOCK_ENTITY = BLOCK_ENTITIES.register("solar_oven_block_entity",
            () -> new BlockEntityType<>(
                    SolarOvenBlockEntity::new,
                    ModBlocks.SOLAR_OVEN_BLOCK.get()
            ));
    //public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SunBlockBlockEntity>> SUN_BLOCK_BE = BLOCK_ENTITIES.register("sun_block_be", () -> new BlockEntityType<>(SunBlockBlockEntity::new, ModBlocks.SUN_BLOCK.get()));
    //public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConnectorBlockEntity>> CONNECTOR_BE = BLOCK_ENTITIES.register("connector_be", () -> new BlockEntityType<>(ConnectorBlockEntity::new, ModBlocks.CONNECTOR_BLOCK.get()));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
        //Radiant.logger.debug("ModBlockEntities done!");
    }
}
