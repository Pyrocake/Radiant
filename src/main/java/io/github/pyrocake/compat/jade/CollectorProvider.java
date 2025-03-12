package io.github.pyrocake.compat.jade;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.custom.Collector_Block;
import io.github.pyrocake.block.entity.CollectorBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.ProgressStyle;
import snownee.jade.impl.ui.ElementHelper;
import snownee.jade.impl.ui.ProgressElement;
import snownee.jade.impl.ui.StyledElement;

import java.awt.*;
import java.util.Objects;

import static io.github.pyrocake.block.custom.Collector_Block.INTENSITY;
import static java.awt.Color.*;

public enum CollectorProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof CollectorBlockEntity entity) {
            if (blockAccessor.getServerData().contains("Energy")) {
                iTooltip.add(new ProgressElement((blockAccessor.getServerData().getFloat("Energy") / blockAccessor.getServerData().getFloat("Capacity")),
                        Component.translatable("radiant.energybar", (int) blockAccessor.getServerData().getFloat("Energy")),
                        IElementHelper.get().progressStyle().color(0x81d5f6).textColor(0xFFFFFF), BoxStyle.getNestedBox(), true));
            }
            if (blockAccessor.getServerData().contains("Intensity")) {
                iTooltip.add(new ProgressElement((blockAccessor.getServerData().getFloat("Intensity") / 15F),
                        Component.translatable("radiant.exposure", blockAccessor.getServerData().getInt("ChargeRate")),
                        IElementHelper.get().progressStyle().color(red.getRGB()).textColor(0xFFFFFF), BoxStyle.getNestedBox(), true));
            }

        }
    }


    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, "collector");
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {

        CollectorBlockEntity collector = (CollectorBlockEntity) accessor.getBlockEntity();
        data.putFloat("Energy", (float) collector.getEnergy().getEnergyStored());
        data.putFloat("Capacity", (float) collector.getEnergy().getMaxEnergyStored());

        Collector_Block block = (Collector_Block) accessor.getBlock();
        Level level = accessor.getLevel();
        BlockState state = accessor.getBlockEntity().getBlockState();
        data.putInt("ChargeRate", state.getValue(INTENSITY));
        data.putFloat("Intensity", block.intensity(level, accessor.getPosition()));
    }
}
