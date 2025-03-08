package io.github.pyrocake.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.pyrocake.block.custom.Solar_Oven_Block;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SolarOvenRenderer implements BlockEntityRenderer<SolarOvenBlockEntity> {
    private static final float SIZE = 0.375F;
    private final ItemRenderer itemRenderer;

    public SolarOvenRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SolarOvenBlockEntity oven, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Direction direction = oven.getBlockState().getValue(Solar_Oven_Block.FACING);
        NonNullList<ItemStack> items = oven.getItems();
        int k = (int)oven.getBlockPos().asLong();
        for (int l = 0; l < items.size(); l++) {
            ItemStack itemStack = items.get(l);
            if (itemStack != ItemStack.EMPTY) {
                poseStack.pushPose();
                poseStack.translate(0.5F, .23F, 0.5F);
                Direction direction2 = Direction.from2DDataValue((l + direction.get2DDataValue()) % 4);
                float g = -direction2.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(g));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(-0.1875F, -0.1875F, 0.0F);
                poseStack.scale(SIZE, SIZE, SIZE);
                this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, i, j, poseStack, multiBufferSource, oven.getLevel(), k + l);
                poseStack.popPose();
            }
        }
    }
}
