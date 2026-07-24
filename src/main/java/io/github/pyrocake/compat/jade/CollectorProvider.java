package io.github.pyrocake.compat.jade;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.entity.CollectorBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import static io.github.pyrocake.block.custom.Collector_Block.INTENSITY;

public enum CollectorProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        tooltip.add(Component.translatable(
                "radiant.jade.energy",
                data.getInt("Energy"),
                data.getInt("Capacity")));
        tooltip.add(Component.translatable(
                "radiant.jade.exposure",
                data.getInt("Intensity"),
                data.getInt("ChargeRate")));
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, "collector");
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (accessor.getBlockEntity() instanceof CollectorBlockEntity collector) {
            data.putInt("Energy", collector.getEnergy().getEnergyStored());
            data.putInt("Capacity", collector.getEnergy().getMaxEnergyStored());
            data.putInt("Intensity", collector.getBlockState().getValue(INTENSITY));
            data.putInt("ChargeRate", collector.getBlockState().getValue(INTENSITY));
        }
    }
}
