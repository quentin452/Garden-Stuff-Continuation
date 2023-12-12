package com.jaquadro.minecraft.gardencore.client.renderer.plant;

import com.jaquadro.minecraft.gardencore.api.IPlantMetaResolver;
import com.jaquadro.minecraft.gardencore.api.IPlantRenderer;
import com.jaquadro.minecraft.gardencore.api.PlantRegistry;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class DoublePlantRenderer implements IPlantRenderer {

    public void render(IBlockAccess world, int x, int y, int z, RenderBlocks renderer, Block block, int meta,
        int height, AxisAlignedBB[] bounds) {
        if (block instanceof BlockDoublePlant) {
            BlockDoublePlant doublePlant = (BlockDoublePlant) block;
            IPlantMetaResolver resolver = PlantRegistry.instance()
                .getPlantMetaResolver(block, meta);
            if (resolver != null) {
                meta = resolver.getPlantSectionMeta(block, meta, height);
            }

            IIcon iicon = this.getIcon(block, world, meta);
            int var15;
            if (height == 1) {
                AxisAlignedBB[] var13 = bounds;
                int var14 = bounds.length;

                for (var15 = 0; var15 < var14; ++var15) {
                    AxisAlignedBB bound = var13[var15];
                    RenderHelper.instance
                        .setRenderBounds(bound.minX, bound.minY, bound.minZ, bound.maxX, bound.maxY, bound.maxZ);
                    RenderHelper.instance.drawCrossedSquaresBounded(iicon, (double) x, (double) y, (double) z, 1.0F);
                }
            } else {
                AxisAlignedBB bound = bounds[0];
                AxisAlignedBB[] var19 = bounds;
                var15 = bounds.length;

                for (int var20 = 0; var20 < var15; ++var20) {
                    AxisAlignedBB slice = var19[var20];
                    if (slice.maxY > bound.maxY) {
                        bound = slice;
                    }
                }

                RenderHelper.instance.setRenderBounds(bound.minX, 0.0D, bound.minZ, bound.maxX, 1.0D, bound.maxZ);
                RenderHelper.instance.drawCrossedSquaresBounded(iicon, (double) x, (double) y, (double) z, 1.0F);
            }

        }
    }

    public IIcon getIcon(Block block, IBlockAccess blockAccess, int meta) {
        boolean isTopHalf = BlockDoublePlant.func_149887_c(meta);
        int baseMeta = BlockDoublePlant.func_149890_d(meta);
        return Blocks.double_plant.func_149888_a(isTopHalf, baseMeta);
    }
}
