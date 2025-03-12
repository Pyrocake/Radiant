package io.github.pyrocake.compat.jade;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.custom.Collector_Block;
import io.github.pyrocake.block.custom.Solar_Oven_Block;
import io.github.pyrocake.block.entity.CollectorBlockEntity;
import io.github.pyrocake.block.entity.SolarOvenBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.ui.ProgressElement;

import static io.github.pyrocake.block.custom.Solar_Oven_Block.INTENSITY;
import static java.awt.Color.red;

public enum SolarOvenProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("Intensity")) {
            iTooltip.add(new ProgressElement((blockAccessor.getServerData().getFloat("Intensity") / 15F),
                    Component.translatable("radiant.heatdisp", blockAccessor.getServerData().getInt("ChargeRate")),
                    IElementHelper.get().progressStyle().color(red.getRGB()).textColor(0xFFFFFF), BoxStyle.getNestedBox(), true));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, "solaroven");
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        Solar_Oven_Block block = (Solar_Oven_Block) accessor.getBlock();
        Level level = accessor.getLevel();
        data.putFloat("Intensity", block.intensity(level, accessor.getPosition()));
    }
}
