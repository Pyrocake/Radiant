package io.github.pyrocake.api.energy;

import net.minecraft.core.Direction;

public interface ILightEnergyReciever extends ILightEnergyHandler {
    int recieveEnergy(Direction from, int maxRecieve, boolean simulate);
}
