package io.github.pyrocake.block.entity.util;

import net.neoforged.neoforge.energy.EnergyStorage;

public class LightEnergyStorage extends EnergyStorage {
    /**
     * @param capacity How much this Block Entity can hold
     */
    public LightEnergyStorage(int capacity) {
        super(capacity);
    }

    /**
     * @param capacity How much this Block Entity can hold
     * @param maxTransfer How quickly this BE can move Energy
     */
    public LightEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    /**
     * @param capacity How much this Block Entity can hold
     * @param maxReceive How quickly this BE can accept Energy
     * @param maxExtract How quickly this BE can discharge Energy
     */
    public LightEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    /**
     * @param capacity How much this Block Entity can hold
     * @param maxReceive How quickly this BE can accept Energy
     * @param maxExtract How quickly this BE can discharge Energy
     * @param energy The amount of Energy this has at current time
     */
    public LightEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setEnergy(int energy) {
        if(energy < 0)
            energy = 0;
        if(energy > this.capacity)
            energy = this.capacity;

        this.energy = energy;
    }

    public void addEnergy(int energy) {
        setEnergy(this.energy + energy);
    }

    public void removeEnergy(int energy) {
        setEnergy(this.energy - energy);
    }
}
