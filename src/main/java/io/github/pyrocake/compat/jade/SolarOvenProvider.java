package io.github.pyrocake.compat.jade;

import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.custom.Solar_Oven_Block;
import io.github.pyrocake.block.entity.SolarOvenBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static io.github.pyrocake.block.custom.Solar_Oven_Block.INTENSITY;

public enum SolarOvenProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        tooltip.add(Component.translatable("radiant.jade.heat", data.getInt("Intensity")));
        int activeSlots = data.getInt("ActiveSlots");
        if (activeSlots > 0) {
            tooltip.add(Component.translatable("radiant.jade.cooking", activeSlots));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Radiant.MOD_ID, "solaroven");
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (accessor.getBlockEntity() instanceof SolarOvenBlockEntity oven) {
            data.putInt("Intensity", oven.getBlockState().getValue(INTENSITY));
            int activeSlots = 0;
            for (ItemStack stack : oven.getItems()) {
                if (!stack.isEmpty()) {
                    activeSlots++;
                }
            }
            data.putInt("ActiveSlots", activeSlots);
        }
    }
}
