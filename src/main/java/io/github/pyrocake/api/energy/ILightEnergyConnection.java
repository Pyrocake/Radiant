package io.github.pyrocake.api.energy;

import net.minecraft.core.Direction;

public interface ILightEnergyConnection {
    boolean canConnectEnergy(Direction from);
}
