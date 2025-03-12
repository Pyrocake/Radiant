package io.github.pyrocake.block.entity;


import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.custom.Collector_Block;
import io.github.pyrocake.block.entity.util.LightEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.security.DrbgParameters;

import static io.github.pyrocake.block.custom.Collector_Block.INTENSITY;

public class CollectorBlockEntity extends BlockEntity {

    public CollectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.COLLECTOR_BLOCK_ENTITY.get(), pos, blockState);
    }
    public final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private final Lazy<NonNullList<ItemStack>> inventoryOptional = Lazy.of(() -> this.items);

    private LightEnergyStorage energy = new LightEnergyStorage(10000 * 15, 0, 100, 0);
    private final Lazy<LightEnergyStorage> energyOptional = Lazy.of(() -> this.energy);

    public static void ticking(Level level, BlockPos blockPos, BlockState blockState, CollectorBlockEntity collector) {
        if (collector.level == null || collector.level.isClientSide())
            return;

        if(collector.energy.getEnergyStored() < collector.energy.getMaxEnergyStored()) {
            if(false) {
//                if(canBurn(this.inventory.getStackInSlot(0))) {
//                    this.burnTime = this.maxBurnTime = getBurnTime(this.inventory.getStackInSlot(0));
//                    this.inventory.getStackInSlot(0).shrink(1);
//                    sendUpdate();
//                }
            } else {
                collector.energy.addEnergy(blockState.getValue(INTENSITY));
                collector.markUpdated();
                //collector.sendUpdate();
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);

        var radiantData = new CompoundTag();
        //radiantData.put("Inventory", this.items.serializeNBT());
        radiantData.put("Energy", this.energy.serializeNBT(provider));
        compoundTag.put(Radiant.MOD_ID, radiantData);
    }

    @Override
    public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        CompoundTag radiantData = compoundTag.getCompound(Radiant.MOD_ID);
        if(radiantData.isEmpty())
            return;
        if (radiantData.contains("Inventory", Tag.TAG_COMPOUND)) {
            //this.items.(tutorialmodData.getCompound("Inventory"));
        }
        if(radiantData.contains("Energy", Tag.TAG_INT)) {
            this.energy.deserializeNBT(provider, radiantData.get("Energy"));
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        tag.put("Energy", this.energy.serializeNBT(provider));
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

//    @Override
//    public @NotNull <T> Lazy<T> getCapability(@NotNull DrbgParameters.Capability<T> cap) {
//        if (cap == Capabilities.ItemHandler.BLOCK) {
//            return this.inventoryOptional.cast();
//        } else if (cap == ForgeCapabilities.ENERGY) {
//            return this.energyOptional.cast();
//        } else {
//            return super.getCapability(cap);
//        }
//    }

//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        this.inventoryOptional.invalidate();
//        this.energyOptional.invalidate();
//    }

//    @Nullable
//    @Override
//    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
//        return new ExampleEnergyGeneratorMenu(pContainerId, pPlayerInventory, this, this.containerData);
//    }

    public Lazy<NonNullList<ItemStack>> getInventoryOptional() {
        return this.inventoryOptional;
    }

    public NonNullList<ItemStack> getInventory() {
        return this.items;
    }

    public Lazy<LightEnergyStorage> getEnergyOptional() {
        return this.energyOptional;
    }

    public LightEnergyStorage getEnergy() {
        return this.energy;
    }

    public Integer getStoredEnergy() {
        return this.energy.getEnergyStored();
    }

    private void sendUpdate() {
        setChanged();

        if(this.level != null)
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}
