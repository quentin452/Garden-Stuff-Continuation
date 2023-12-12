package com.jaquadro.minecraft.gardenstuff.renderer;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.connect.IAttachable;
import com.jaquadro.minecraft.gardenapi.api.connect.IChainSingleAttachable;
import com.jaquadro.minecraft.gardencore.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardenstuff.block.BlockLightChain;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;

public class LightChainRenderer implements ISimpleBlockRenderingHandler {

    private static final Vec3[] defaultAttachPoints = new Vec3[] { Vec3.createVectorHelper(0.03125D, 1.0D, 0.03125D),
        Vec3.createVectorHelper(0.03125D, 1.0D, 0.96875D), Vec3.createVectorHelper(0.96875D, 1.0D, 0.03125D),
        Vec3.createVectorHelper(0.96875D, 1.0D, 0.96875D) };
    private static final Vec3[] singleAttachPoint = new Vec3[] { Vec3.createVectorHelper(0.5D, 1.0D, 0.5D) };
    private static final Vec3 defaultSingleAttachPoint = Vec3.createVectorHelper(0.5D, 0.0D, 0.5D);

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return block instanceof BlockLightChain
            ? this.renderWorldBlock(world, x, y, z, (BlockLightChain) block, modelId, renderer)
            : false;
    }

    private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockLightChain block, int modelId,
        RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        int color = block.colorMultiplier(world, x, y, z);
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        tessellator.setColorOpaque_F(r, g, b);
        IIcon icon = block.getIcon(0, world.getBlockMetadata(x, y, z));
        if (renderer.hasOverrideBlockTexture()) {
            icon = renderer.overrideBlockTexture;
        }

        int yMin = block.findMinY(world, x, y, z);
        int yMax = block.findMaxY(world, x, y, z);
        double upperDepth = 0.0D;
        double lowerDepth = 0.0D;
        Block blockTop = world.getBlock(x, yMax + 1, z);
        if (blockTop instanceof IChainSingleAttachable) {
            Vec3 topAttach = ((IChainSingleAttachable) blockTop).getChainAttachPoint(world, x, yMax + 1, z, 0);
            if (topAttach != null) {
                upperDepth = topAttach.yCoord;
            }
        }

        if (upperDepth == 0.0D) {
            IAttachable attachable = GardenAPI.instance()
                .registries()
                .attachable()
                .getAttachable(blockTop, world.getBlockMetadata(x, y + 1, z));
            if (attachable != null && attachable.isAttachable(world, x, y + 1, z, 0)) {
                upperDepth = attachable.getAttachDepth(world, x, y + 1, z, 0);
            }
        }

        Vec3[] var45 = block.getAttachPoints(world, x, y, z);
        int var22 = var45.length;

        for (int var23 = 0; var23 < var22; ++var23) {
            Vec3 point = var45[var23];
            float height = (float) (yMax - yMin + 2) - (float) point.yCoord + (float) upperDepth;
            double cx = 0.5D;
            double cz = 0.5D;
            double dx = cx - point.xCoord;
            double dz = cz - point.zCoord;
            double yt = (double) (y + 1);
            double yb = (double) y;
            double localYMin = (double) yMin + point.yCoord - 1.0D;
            if (y == yMin) {
                yb = (double) (y - 1) + point.yCoord;
            }

            if (y == yMax) {
                yt = (double) (y + 1) + upperDepth;
            }

            double lerpB = 1.0D - (yb - localYMin) / (double) height;
            double lerpT = 1.0D - (yt - localYMin) / (double) height;
            this.drawBetween(
                renderer,
                icon,
                (double) x + dx * lerpB + cx,
                yb,
                (double) z + dz * lerpB + cz,
                (double) x + dx * lerpT + cx,
                yt,
                (double) z + dz * lerpT + cz);
        }

        ClientProxy.gardenProxyRenderer
            .renderWorldBlock(world, x, y, z, ModBlocks.gardenProxy, ModBlocks.gardenProxy.getRenderType(), renderer);
        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return com.jaquadro.minecraft.gardenstuff.core.ClientProxy.lightChainRenderID;
    }

    private void drawBetween(RenderBlocks renderer, IIcon icon, double x0, double y0, double z0, double x1, double y1,
        double z1) {
        Tessellator tessellator = Tessellator.instance;
        double minU = (double) icon.getMinU();
        double minV = (double) icon.getMinV();
        double maxU = (double) icon.getMaxU();
        double maxV = (double) icon.getMaxV();
        Vec3 vT = Vec3.createVectorHelper(x1 - x0, y1 - y0, z1 - z0);
        Vec3 vB = Vec3.createVectorHelper(x1 - x0, 0.0D, z1 - z0);
        Vec3 vN = vT.crossProduct(vB);
        Vec3 vU = vT.crossProduct(vN);
        vU = vU.normalize();
        vN = vN.normalize();
        double vUx = vU.xCoord / 2.0D;
        double vUy = vU.yCoord / 2.0D;
        double vUz = vU.zCoord / 2.0D;
        if (vUx == 0.0D && vUy == 0.0D) {
            vUx = -0.5D;
            vUz = 0.5D;
        }

        tessellator.addVertexWithUV(x0 + vUx, y0 + vUy, z0 + vUz, maxU, minV);
        tessellator.addVertexWithUV(x0 - vUx, y0 - vUy, z0 - vUz, minU, minV);
        tessellator.addVertexWithUV(x1 - vUx, y1 - vUy, z1 - vUz, minU, maxV);
        tessellator.addVertexWithUV(x1 + vUx, y1 + vUy, z1 + vUz, maxU, maxV);
        tessellator.addVertexWithUV(x1 + vUx, y1 + vUy, z1 + vUz, maxU, maxV);
        tessellator.addVertexWithUV(x1 - vUx, y1 - vUy, z1 - vUz, minU, maxV);
        tessellator.addVertexWithUV(x0 - vUx, y0 - vUy, z0 - vUz, minU, minV);
        tessellator.addVertexWithUV(x0 + vUx, y0 + vUy, z0 + vUz, maxU, minV);
        double vNx = vN.xCoord / 2.0D;
        double vNy = vN.yCoord / 2.0D;
        double vNz = vN.zCoord / 2.0D;
        if (vNx == 0.0D && vNy == 0.0D) {
            vNx = 0.5D;
            vNz = 0.5D;
        }

        tessellator.addVertexWithUV(x0 + vNx, y0 + vNy, z0 + vNz, maxU, minV);
        tessellator.addVertexWithUV(x0 - vNx, y0 - vNy, z0 - vNz, minU, minV);
        tessellator.addVertexWithUV(x1 - vNx, y1 - vNy, z1 - vNz, minU, maxV);
        tessellator.addVertexWithUV(x1 + vNx, y1 + vNy, z1 + vNz, maxU, maxV);
        tessellator.addVertexWithUV(x1 + vNx, y1 + vNy, z1 + vNz, maxU, maxV);
        tessellator.addVertexWithUV(x1 - vNx, y1 - vNy, z1 - vNz, minU, maxV);
        tessellator.addVertexWithUV(x0 - vNx, y0 - vNy, z0 - vNz, minU, minV);
        tessellator.addVertexWithUV(x0 + vNx, y0 + vNy, z0 + vNz, maxU, minV);
    }
}
