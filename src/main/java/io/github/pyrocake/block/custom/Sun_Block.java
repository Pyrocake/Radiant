package io.github.pyrocake.block.custom;

import com.mojang.serialization.MapCodec;
import io.github.pyrocake.Radiant;
import io.github.pyrocake.block.entity.ModBlockEntities;
import io.github.pyrocake.block.entity.SunBlockBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public class Sun_Block extends Block implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0,0,16,12,16);
    public static final IntegerProperty POWER;

    public Sun_Block(Properties properties) {
        super(properties);
    }

    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return (Integer)blockState.getValue(POWER);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SunBlockBlockEntity(blockPos,  blockState);
    }


    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            //PlayerChatMessage message = PlayerChatMessage.unsigned(player.getUUID(), "Power: " + (Integer)state.getValue(POWER));
        }
        return InteractionResult.SUCCESS;
    }

    private static void updateSignalStrength(BlockState state, Level level, BlockPos pos) {
        int i = level.getBrightness(LightLayer.SKY, pos) - level.getSkyDarken();
        float f = level.getSunAngle(1.0F);
        if (!level.canSeeSky(pos)) {
            i = 0;
        } else if (i > 0) {
            float f1 = f < 3.1415927F ? 0.0F : 6.2831855F;
            f += (f1 - f) * 0.2F;
            i = Math.round((float)i * Mth.cos(f));
        }

        i = Mth.clamp(i, 0, 15);
        if ((Integer)state.getValue(POWER) != i) {
            level.setBlock(pos, (BlockState)state.setValue(POWER, i), 3);
        }
        Radiant.logger.info("{}", i);
    }

    //@SuppressWarnings("unchecked")
    //@Nullable
    //public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
     //   return !level.isClientSide && level.dimensionType().hasSkyLight() ? SunBlockBlockEntity::tickEntity : null;
    //}

    private static void tick(Level level, BlockPos pos, BlockState state, SunBlockBlockEntity blockEntity) {
        if (level.getGameTime() % 20L == 0L) {
            updateSignalStrength(state, level, pos);
            Radiant.logger.info("Power: {}", (Integer)state.getValue(POWER));
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

    static {
        POWER = BlockStateProperties.POWER;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);

        if (!level.isClientSide) {
            Radiant.logger.info("Block placed at " + pos + ", checking for Block Entity...");
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity == null) {
                Radiant.logger.error("No Block Entity found at " + pos + ", trying to force creation...");
                level.setBlockEntity(new SunBlockBlockEntity(pos, state));
            } else {
                Radiant.logger.info("Block Entity exists at " + pos);
            }
        }
    }
}
