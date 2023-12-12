package com.jaquadro.minecraft.gardencore.client.renderer.plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.jaquadro.minecraft.gardencore.api.IPlantRenderer;

public class SunflowerRenderer implements IPlantRenderer {

    private DoublePlantRenderer plantRender = new DoublePlantRenderer();

    public void render(IBlockAccess world, int x, int y, int z, RenderBlocks renderer, Block block, int meta,
        int height, AxisAlignedBB[] bounds) {
        if (block instanceof BlockDoublePlant) {
            this.plantRender.render(world, x, y, z, renderer, block, meta, height, bounds);
            if (height == 2) {
                Tessellator tessellator = Tessellator.instance;
                double orientation = Math.cos(0.0D) * 3.141592653589793D * 0.1D;
                double aCos = Math.cos(orientation);
                double aSin = Math.sin(orientation);
                double xTR = 0.5D + 0.3D * aCos - 0.5D * aSin;
                double zTR = 0.5D + 0.5D * aCos + 0.3D * aSin;
                double xTL = 0.5D + 0.3D * aCos + 0.5D * aSin;
                double ZTL = 0.5D + -0.5D * aCos + 0.3D * aSin;
                double xBL = 0.5D + -0.05D * aCos + 0.5D * aSin;
                double zBL = 0.5D + -0.5D * aCos + -0.05D * aSin;
                double xBR = 0.5D + -0.05D * aCos - 0.5D * aSin;
                double zBR = 0.5D + 0.5D * aCos + -0.05D * aSin;
                IIcon icon = Blocks.double_plant.sunflowerIcons[0];
                double iconMinU = (double) icon.getMinU();
                double iconMinV = (double) icon.getMinV();
                double iconMaxU = (double) icon.getMaxU();
                double iconMaxV = (double) icon.getMaxV();
                tessellator.addVertexWithUV((double) x + xBL, (double) y + 1.0D, (double) z + zBL, iconMinU, iconMaxV);
                tessellator.addVertexWithUV((double) x + xBR, (double) y + 1.0D, (double) z + zBR, iconMaxU, iconMaxV);
                tessellator.addVertexWithUV((double) x + xTR, (double) y + 0.0D, (double) z + zTR, iconMaxU, iconMinV);
                tessellator.addVertexWithUV((double) x + xTL, (double) y + 0.0D, (double) z + ZTL, iconMinU, iconMinV);
                icon = Blocks.double_plant.sunflowerIcons[1];
                iconMinU = (double) icon.getMinU();
                iconMinV = (double) icon.getMinV();
                iconMaxU = (double) icon.getMaxU();
                iconMaxV = (double) icon.getMaxV();
                tessellator.addVertexWithUV((double) x + xBR, (double) y + 1.0D, (double) z + zBR, iconMinU, iconMaxV);
                tessellator.addVertexWithUV((double) x + xBL, (double) y + 1.0D, (double) z + zBL, iconMaxU, iconMaxV);
                tessellator.addVertexWithUV((double) x + xTL, (double) y + 0.0D, (double) z + ZTL, iconMaxU, iconMinV);
                tessellator.addVertexWithUV((double) x + xTR, (double) y + 0.0D, (double) z + zTR, iconMinU, iconMinV);
            }
        }
    }
}
