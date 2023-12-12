package com.jaquadro.minecraft.gardentrees.client.renderer;

import com.jaquadro.minecraft.gardentrees.block.BlockFlowerLeaves;
import com.jaquadro.minecraft.gardentrees.core.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class FlowerLeafRenderer implements ISimpleBlockRenderingHandler {
   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return !(block instanceof BlockFlowerLeaves) ? false : this.renderWorldBlock(world, x, y, z, (BlockFlowerLeaves)block, modelId, renderer);
   }

   private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockFlowerLeaves block, int modelId, RenderBlocks renderer) {
      renderer.renderStandardBlock(block, x, y, z);
      Tessellator tessellator = Tessellator.instance;
      tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
      tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      int meta = world.getBlockMetadata(x, y, z);
      float offset = 0.03125F;
      IIcon icon = block.getFlowerIcon(world, x, y, z, meta, 1);
      if (icon != null) {
         renderer.renderFaceYPos(block, (double)x, (double)((float)y + offset), (double)z, icon);
      }

      icon = block.getFlowerIcon(world, x, y, z, meta, 2);
      if (icon != null) {
         renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z - offset), icon);
      }

      icon = block.getFlowerIcon(world, x, y, z, meta, 3);
      if (icon != null) {
         renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z + offset), icon);
      }

      icon = block.getFlowerIcon(world, x, y, z, meta, 4);
      if (icon != null) {
         renderer.renderFaceXNeg(block, (double)((float)x - offset), (double)y, (double)z, icon);
      }

      icon = block.getFlowerIcon(world, x, y, z, meta, 5);
      if (icon != null) {
         renderer.renderFaceXPos(block, (double)((float)x + offset), (double)y, (double)z, icon);
      }

      return true;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return ClientProxy.flowerLeafRenderID;
   }
}
