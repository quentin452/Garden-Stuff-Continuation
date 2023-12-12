package com.jaquadro.minecraft.gardentrees.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.jaquadro.minecraft.gardentrees.core.ClientProxy;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class IvyRenderer implements ISimpleBlockRenderingHandler {

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        int color = block.colorMultiplier(world, x, y, z);
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tessellator.setColorOpaque_F(r, g, b);
        double d = 0.05D;
        double[] uv = new double[4];
        int meta = world.getBlockMetadata(x, y, z);
        IIcon icon;
        if ((meta & 2) != 0) {
            icon = block.getIcon(world, x, y, z, 5);
            this.getIconUV(icon, uv);
            tessellator.addVertexWithUV((double) x + d, (double) (y + 1), (double) (z + 1), uv[0], uv[1]);
            tessellator.addVertexWithUV((double) x + d, (double) (y + 0), (double) (z + 1), uv[0], uv[3]);
            tessellator.addVertexWithUV((double) x + d, (double) (y + 0), (double) (z + 0), uv[2], uv[3]);
            tessellator.addVertexWithUV((double) x + d, (double) (y + 1), (double) (z + 0), uv[2], uv[1]);
            tessellator.addVertexWithUV((double) x + d, (double) (y + 1), (double) (z + 0), uv[2], uv[1]);
            tessellator.addVertexWithUV((double) x + d, (double) (y + 0), (double) (z + 0), uv[2], uv[3]);
            tessellator.addVertexWithUV((double) x + d, (double) (y + 0), (double) (z + 1), uv[0], uv[3]);
            tessellator.addVertexWithUV((double) x + d, (double) (y + 1), (double) (z + 1), uv[0], uv[1]);
        }

        if ((meta & 8) != 0) {
            icon = block.getIcon(world, x, y, z, 4);
            this.getIconUV(icon, uv);
            tessellator.addVertexWithUV((double) (x + 1) - d, (double) (y + 0), (double) (z + 1), uv[2], uv[3]);
            tessellator.addVertexWithUV((double) (x + 1) - d, (double) (y + 1), (double) (z + 1), uv[2], uv[1]);
            tessellator.addVertexWithUV((double) (x + 1) - d, (double) (y + 1), (double) (z + 0), uv[0], uv[1]);
            tessellator.addVertexWithUV((double) (x + 1) - d, (double) (y + 0), (double) (z + 0), uv[0], uv[3]);
            tessellator.addVertexWithUV((double) (x + 1) - d, (double) (y + 0), (double) (z + 0), uv[0], uv[3]);
            tessellator.addVertexWithUV((double) (x + 1) - d, (double) (y + 1), (double) (z + 0), uv[0], uv[1]);
            tessellator.addVertexWithUV((double) (x + 1) - d, (double) (y + 1), (double) (z + 1), uv[2], uv[1]);
            tessellator.addVertexWithUV((double) (x + 1) - d, (double) (y + 0), (double) (z + 1), uv[2], uv[3]);
        }

        if ((meta & 4) != 0) {
            icon = block.getIcon(world, x, y, z, 3);
            this.getIconUV(icon, uv);
            tessellator.addVertexWithUV((double) (x + 1), (double) (y + 0), (double) z + d, uv[2], uv[3]);
            tessellator.addVertexWithUV((double) (x + 1), (double) (y + 1), (double) z + d, uv[2], uv[1]);
            tessellator.addVertexWithUV((double) (x + 0), (double) (y + 1), (double) z + d, uv[0], uv[1]);
            tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) z + d, uv[0], uv[3]);
            tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) z + d, uv[0], uv[3]);
            tessellator.addVertexWithUV((double) (x + 0), (double) (y + 1), (double) z + d, uv[0], uv[1]);
            tessellator.addVertexWithUV((double) (x + 1), (double) (y + 1), (double) z + d, uv[2], uv[1]);
            tessellator.addVertexWithUV((double) (x + 1), (double) (y + 0), (double) z + d, uv[2], uv[3]);
        }

        if ((meta & 1) != 0) {
            icon = block.getIcon(world, x, y, z, 2);
            this.getIconUV(icon, uv);
            tessellator.addVertexWithUV((double) (x + 1), (double) (y + 1), (double) (z + 1) - d, uv[0], uv[1]);
            tessellator.addVertexWithUV((double) (x + 1), (double) (y + 0), (double) (z + 1) - d, uv[0], uv[3]);
            tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) (z + 1) - d, uv[2], uv[3]);
            tessellator.addVertexWithUV((double) (x + 0), (double) (y + 1), (double) (z + 1) - d, uv[2], uv[1]);
            tessellator.addVertexWithUV((double) (x + 0), (double) (y + 1), (double) (z + 1) - d, uv[2], uv[1]);
            tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) (z + 1) - d, uv[2], uv[3]);
            tessellator.addVertexWithUV((double) (x + 1), (double) (y + 0), (double) (z + 1) - d, uv[0], uv[3]);
            tessellator.addVertexWithUV((double) (x + 1), (double) (y + 1), (double) (z + 1) - d, uv[0], uv[1]);
        }

        return true;
    }

    private void getIconUV(IIcon icon, double[] uv) {
        uv[0] = (double) icon.getMinU();
        uv[1] = (double) icon.getMinV();
        uv[2] = (double) icon.getMaxU();
        uv[3] = (double) icon.getMaxV();
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return ClientProxy.ivyRenderID;
    }
}
