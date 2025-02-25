package io.github.pyrocake.api.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

public class BlockEnergyHandler extends BlockEntity implements ILightEnergyReciever, ILightEnergyProvider {

    protected EnergyStorage storage = new EnergyStorage(32000);

    public BlockEnergyHandler(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    /* IEnergyConnection */
    @Override
    public boolean canConnectEnergy(Direction from) {

        return true;
    }

    /* IEnergyProvider */
    @Override
    public int extractEnergy(Direction from, int maxExtract, boolean simulate) {

        return storage.extractEnergy(maxExtract, simulate);
    }

    /* IEnergyHandler */
    @Override
    public int getEnergyStored(Direction from) {

        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(Direction from) {

        return storage.getMaxEnergyStored();
    }

    @Override
    public int recieveEnergy(Direction from, int maxRecieve, boolean simulate) {
        return storage.receiveEnergy(storage.getMaxEnergyStored(), simulate);
    }
}
