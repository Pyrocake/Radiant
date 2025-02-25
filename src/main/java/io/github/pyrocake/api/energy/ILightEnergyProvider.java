package io.github.pyrocake.api.energy;

import net.minecraft.core.Direction;

public interface ILightEnergyProvider extends ILightEnergyHandler {
    int extractEnergy(Direction from, int maxExtract, boolean simulate);
}
