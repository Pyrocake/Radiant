package io.github.pyrocake.api.energy;

import net.minecraft.core.Direction;

public class ILightEnergyHandler extends ILightEnergyConnection{
    int getEnergyStored(Direction from) {
        return 0;
    }

    int getMaxEnergyStored(Direction from) {
        return 0;
    }
}
