package com.jaquadro.minecraft.gardenstuff.renderer;

import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardenstuff.block.BlockHoop;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class HoopRenderer implements ISimpleBlockRenderingHandler {
   private ModularBoxRenderer boxrender = new ModularBoxRenderer();

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      if (block instanceof BlockHoop) {
         this.renderInventoryBlock((BlockHoop)block, metadata, modelId, renderer);
      }
   }

   private void renderInventoryBlock(BlockHoop block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.boxrender.setUnit(0.0625D);
      this.boxrender.setIcon(block.getIcon(0, 0));
      this.boxrender.setColor(ModularBoxRenderer.COLOR_WHITE);
      this.boxrender.renderBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.0D, 0.0625D, 0.0D, 1.0D, 0.375D, 1.0D, 0, 3);
      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return !(block instanceof BlockHoop) ? false : this.renderWorldBlock(world, x, y, z, (BlockHoop)block, modelId, renderer);
   }

   private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockHoop block, int modelId, RenderBlocks renderer) {
      this.boxrender.setUnit(0.0625D);
      this.boxrender.setIcon(block.getIcon(0, 0));
      this.boxrender.setColor(ModularBoxRenderer.COLOR_WHITE);
      this.boxrender.renderBox(world, block, (double)x, (double)y, (double)z, 0.0D, 0.0625D, 0.0D, 1.0D, 0.375D, 1.0D, 0, 3);
      return true;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return ClientProxy.hoopRenderID;
   }
}
