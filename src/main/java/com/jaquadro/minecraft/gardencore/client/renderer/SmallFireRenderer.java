package com.jaquadro.minecraft.gardencore.client.renderer;

import com.jaquadro.minecraft.gardencore.block.BlockSmallFire;
import com.jaquadro.minecraft.gardencore.core.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class SmallFireRenderer implements ISimpleBlockRenderingHandler {

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return block instanceof BlockSmallFire
            ? this.renderWorldBlock(world, x, y, z, (BlockSmallFire) block, modelId, renderer)
            : false;
    }

    private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockSmallFire block, int modelId,
        RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        IIcon icon0 = block.getFireIcon(0);
        IIcon icon1 = block.getFireIcon(1);
        IIcon icon2 = icon0;
        if (renderer.hasOverrideBlockTexture()) {
            icon2 = renderer.overrideBlockTexture;
        }

        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        double uMin = (double) icon2.getMinU();
        double vMin = (double) icon2.getMinV();
        double uMax = (double) icon2.getMaxU();
        double vMax = (double) icon2.getMaxV();
        double y0 = (double) y - 0.0625D;
        double y1 = (double) (y + 1);
        double x0 = (double) x + 0.5D + 0.2D;
        double x1 = (double) x + 0.5D - 0.2D;
        double x2 = (double) x + 0.5D - 0.3D;
        double x3 = (double) x + 0.5D + 0.3D;
        double z0 = (double) z + 0.5D + 0.2D;
        double z1 = (double) z + 0.5D - 0.2D;
        double z2 = (double) z + 0.5D - 0.3D;
        double z3 = (double) z + 0.5D + 0.3D;
        tessellator.addVertexWithUV(x2, y1, (double) ((float) (z + 1) - 0.0625F), uMax, vMin);
        tessellator.addVertexWithUV(x0, y0, (double) ((float) (z + 1) - 0.0625F), uMax, vMax);
        tessellator.addVertexWithUV(x0, y0, (double) ((float) (z + 0) + 0.0625F), uMin, vMax);
        tessellator.addVertexWithUV(x2, y1, (double) ((float) (z + 0) + 0.0625F), uMin, vMin);
        tessellator.addVertexWithUV(x3, y1, (double) ((float) (z + 0) + 0.0625F), uMax, vMin);
        tessellator.addVertexWithUV(x1, y0, (double) ((float) (z + 0) + 0.0625F), uMax, vMax);
        tessellator.addVertexWithUV(x1, y0, (double) ((float) (z + 1) - 0.0625F), uMin, vMax);
        tessellator.addVertexWithUV(x3, y1, (double) ((float) (z + 1) - 0.0625F), uMin, vMin);
        uMin = (double) icon1.getMinU();
        vMin = (double) icon1.getMinV();
        uMax = (double) icon1.getMaxU();
        vMax = (double) icon1.getMaxV();
        tessellator.addVertexWithUV((double) ((float) (x + 1) - 0.0625F), y1, z3, uMax, vMin);
        tessellator.addVertexWithUV((double) ((float) (x + 1) - 0.0625F), y0, z1, uMax, vMax);
        tessellator.addVertexWithUV((double) ((float) (x + 0) + 0.0625F), y0, z1, uMin, vMax);
        tessellator.addVertexWithUV((double) ((float) (x + 0) + 0.0625F), y1, z3, uMin, vMin);
        tessellator.addVertexWithUV((double) ((float) (x + 0) + 0.0625F), y1, z2, uMax, vMin);
        tessellator.addVertexWithUV((double) ((float) (x + 0) + 0.0625F), y0, z0, uMax, vMax);
        tessellator.addVertexWithUV((double) ((float) (x + 1) - 0.0625F), y0, z0, uMin, vMax);
        tessellator.addVertexWithUV((double) ((float) (x + 1) - 0.0625F), y1, z2, uMin, vMin);
        x0 = (double) x + 0.5D - 0.5D + 0.125D;
        x1 = (double) x + 0.5D + 0.5D - 0.125D;
        x2 = (double) x + 0.5D - 0.4D + 0.125D;
        x3 = (double) x + 0.5D + 0.4D - 0.125D;
        z0 = (double) z + 0.5D - 0.5D + 0.125D;
        z1 = (double) z + 0.5D + 0.5D - 0.125D;
        z2 = (double) z + 0.5D - 0.4D + 0.125D;
        z3 = (double) z + 0.5D + 0.4D - 0.125D;
        tessellator.addVertexWithUV(x2, y1, (double) (z + 0), uMax, vMin);
        tessellator.addVertexWithUV(x0, y0, (double) (z + 0), uMax, vMax);
        tessellator.addVertexWithUV(x0, y0, (double) (z + 1), uMin, vMax);
        tessellator.addVertexWithUV(x2, y1, (double) (z + 1), uMin, vMin);
        tessellator.addVertexWithUV(x3, y1, (double) (z + 1), uMax, vMin);
        tessellator.addVertexWithUV(x1, y0, (double) (z + 1), uMax, vMax);
        tessellator.addVertexWithUV(x1, y0, (double) (z + 0), uMin, vMax);
        tessellator.addVertexWithUV(x3, y1, (double) (z + 0), uMin, vMin);
        uMin = (double) icon0.getMinU();
        vMin = (double) icon0.getMinV();
        uMax = (double) icon0.getMaxU();
        vMax = (double) icon0.getMaxV();
        tessellator.addVertexWithUV((double) (x + 0), y1, z3, uMax, vMin);
        tessellator.addVertexWithUV((double) (x + 0), y0, z1, uMax, vMax);
        tessellator.addVertexWithUV((double) (x + 1), y0, z1, uMin, vMax);
        tessellator.addVertexWithUV((double) (x + 1), y1, z3, uMin, vMin);
        tessellator.addVertexWithUV((double) (x + 1), y1, z2, uMax, vMin);
        tessellator.addVertexWithUV((double) (x + 1), y0, z0, uMax, vMax);
        tessellator.addVertexWithUV((double) (x + 0), y0, z0, uMin, vMax);
        tessellator.addVertexWithUV((double) (x + 0), y1, z2, uMin, vMin);
        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return ClientProxy.smallFireRenderID;
    }
}
