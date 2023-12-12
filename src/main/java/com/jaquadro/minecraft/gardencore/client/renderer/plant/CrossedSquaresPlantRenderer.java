package com.jaquadro.minecraft.gardencore.client.renderer.plant;

import com.jaquadro.minecraft.gardencore.api.IPlantMetaResolver;
import com.jaquadro.minecraft.gardencore.api.IPlantRenderer;
import com.jaquadro.minecraft.gardencore.api.PlantRegistry;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class CrossedSquaresPlantRenderer implements IPlantRenderer {
   public void render(IBlockAccess world, int x, int y, int z, RenderBlocks renderer, Block block, int meta, int height, AxisAlignedBB[] bounds) {
      if (bounds != null) {
         IPlantMetaResolver resolver = PlantRegistry.instance().getPlantMetaResolver(block, meta);
         if (resolver != null) {
            meta = resolver.getPlantSectionMeta(block, meta, height);
         }

         IIcon iicon = renderer.getBlockIconFromSideAndMetadata(block, 0, meta);
         AxisAlignedBB[] var12 = bounds;
         int var13 = bounds.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            AxisAlignedBB bound = var12[var14];
            RenderHelper.instance.setRenderBounds(bound.minX, bound.minY, bound.minZ, bound.maxX, bound.maxY, bound.maxZ);
            RenderHelper.instance.drawCrossedSquaresBounded(iicon, (double)x, (double)y, (double)z, 1.0F);
         }

      }
   }
}
