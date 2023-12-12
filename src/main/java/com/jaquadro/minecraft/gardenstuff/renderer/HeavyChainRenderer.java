package com.jaquadro.minecraft.gardenstuff.renderer;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.connect.IAttachable;
import com.jaquadro.minecraft.gardenapi.api.connect.IChainSingleAttachable;
import com.jaquadro.minecraft.gardenstuff.block.BlockHeavyChain;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;

public class HeavyChainRenderer implements ISimpleBlockRenderingHandler {
   private static final Vec3[] defaultAttachPoint = new Vec3[]{Vec3.createVectorHelper(0.5D, 0.0D, 0.5D), Vec3.createVectorHelper(0.5D, 1.0D, 0.5D), Vec3.createVectorHelper(0.5D, 0.5D, 0.0D), Vec3.createVectorHelper(0.5D, 0.5D, 1.0D), Vec3.createVectorHelper(0.0D, 0.5D, 0.5D), Vec3.createVectorHelper(1.0D, 0.5D, 0.5D)};

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return block instanceof BlockHeavyChain ? this.renderWorldBlock(world, x, y, z, (BlockHeavyChain)block, modelId, renderer) : false;
   }

   private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockHeavyChain block, int modelId, RenderBlocks renderer) {
      renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      this.renderCrossedSquares(renderer, block, x, y, z);
      double upperDepth = 0.0D;
      double lowerDepth = 0.0D;
      Block upperBlock = world.getBlock(x, y + 1, z);
      if (upperBlock instanceof IChainSingleAttachable) {
         Vec3 attach = ((IChainSingleAttachable)upperBlock).getChainAttachPoint(world, x, y + 1, z, 0);
         if (attach != null && attach != defaultAttachPoint[0]) {
            upperDepth = attach.yCoord;
         }
      }

      if (upperDepth == 0.0D) {
         IAttachable attachable = GardenAPI.instance().registries().attachable().getAttachable(upperBlock, world.getBlockMetadata(x, y + 1, z));
         if (attachable != null && attachable.isAttachable(world, x, y + 1, z, 0)) {
            upperDepth = attachable.getAttachDepth(world, x, y + 1, z, 0);
         }
      }

      Block lowerBlock = world.getBlock(x, y - 1, z);
      if (lowerBlock instanceof IChainSingleAttachable) {
         Vec3 attach = ((IChainSingleAttachable)lowerBlock).getChainAttachPoint(world, x, y - 1, z, 1);
         if (attach != null && attach != defaultAttachPoint[1]) {
            lowerDepth = attach.yCoord;
         }
      }

      if (lowerDepth == 0.0D) {
         IAttachable attachable = GardenAPI.instance().registries().attachable().getAttachable(lowerBlock, world.getBlockMetadata(x, y - 1, z));
         if (attachable != null && attachable.isAttachable(world, x, y - 1, z, 1)) {
            lowerDepth = attachable.getAttachDepth(world, x, y - 1, z, 1);
         }
      }

      if (upperDepth > 0.0D) {
         renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, upperDepth, 1.0D);
         renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 0));
         this.renderCrossedSquares(renderer, block, x, y + 1, z);
         renderer.setOverrideBlockTexture((IIcon)null);
      }

      if (lowerDepth > 0.0D) {
         renderer.setRenderBounds(0.0D, lowerDepth, 0.0D, 1.0D, 1.0D, 1.0D);
         renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 0));
         this.renderCrossedSquares(renderer, block, x, y - 1, z);
         renderer.setOverrideBlockTexture((IIcon)null);
      }

      return true;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return false;
   }

   public int getRenderId() {
      return ClientProxy.heavyChainRenderID;
   }

   private void renderCrossedSquares(RenderBlocks renderer, Block block, int x, int y, int z) {
      Tessellator tessellator = Tessellator.instance;
      tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
      int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
      float f = (float)(l >> 16 & 255) / 255.0F;
      float f1 = (float)(l >> 8 & 255) / 255.0F;
      float f2 = (float)(l & 255) / 255.0F;
      if (EntityRenderer.anaglyphEnable) {
         float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
         float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
         float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
         f = f3;
         f1 = f4;
         f2 = f5;
      }

      tessellator.setColorOpaque_F(f, f1, f2);
      IIcon iicon = renderer.getBlockIconFromSideAndMetadata(block, 0, renderer.blockAccess.getBlockMetadata(x, y, z));
      this.drawCrossedSquares(renderer, iicon, (double)x, (double)y, (double)z, 1.0F);
   }

   private void drawCrossedSquares(RenderBlocks renderer, IIcon icon, double x, double y, double z, float scale) {
      Tessellator tessellator = Tessellator.instance;
      if (renderer.hasOverrideBlockTexture()) {
         icon = renderer.overrideBlockTexture;
      }

      double uMin = (double)icon.getInterpolatedU(renderer.renderMinX * 16.0D);
      double uMax = (double)icon.getInterpolatedU(renderer.renderMaxX * 16.0D);
      double vMin = (double)icon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
      double vMax = (double)icon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
      double d7 = 0.45D * (double)scale;
      double xMin = x + 0.5D - d7;
      double xMax = x + 0.5D + d7;
      double yMin = y + renderer.renderMinY * (double)scale;
      double yMax = y + renderer.renderMaxY * (double)scale;
      double zMin = z + 0.5D - d7;
      double zMax = z + 0.5D + d7;
      tessellator.addVertexWithUV(xMin, yMax, zMin, uMin, vMin);
      tessellator.addVertexWithUV(xMin, yMin, zMin, uMin, vMax);
      tessellator.addVertexWithUV(xMax, yMin, zMax, uMax, vMax);
      tessellator.addVertexWithUV(xMax, yMax, zMax, uMax, vMin);
      tessellator.addVertexWithUV(xMax, yMax, zMax, uMin, vMin);
      tessellator.addVertexWithUV(xMax, yMin, zMax, uMin, vMax);
      tessellator.addVertexWithUV(xMin, yMin, zMin, uMax, vMax);
      tessellator.addVertexWithUV(xMin, yMax, zMin, uMax, vMin);
      tessellator.addVertexWithUV(xMin, yMax, zMax, uMin, vMin);
      tessellator.addVertexWithUV(xMin, yMin, zMax, uMin, vMax);
      tessellator.addVertexWithUV(xMax, yMin, zMin, uMax, vMax);
      tessellator.addVertexWithUV(xMax, yMax, zMin, uMax, vMin);
      tessellator.addVertexWithUV(xMax, yMax, zMin, uMin, vMin);
      tessellator.addVertexWithUV(xMax, yMin, zMin, uMin, vMax);
      tessellator.addVertexWithUV(xMin, yMin, zMax, uMax, vMax);
      tessellator.addVertexWithUV(xMin, yMax, zMax, uMax, vMin);
   }
}
