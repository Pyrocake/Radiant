package io.github.pyrocake.api.energy;

import net.minecraft.core.Direction;

public interface ILightEnergyHandler extends ILightEnergyConnection{
    int getEnergyStored(Direction from);

    int getMaxEnergyStored(Direction from);
}
