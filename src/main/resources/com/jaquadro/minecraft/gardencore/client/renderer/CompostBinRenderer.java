package com.jaquadro.minecraft.gardencore.client.renderer;

import com.jaquadro.minecraft.gardencore.block.BlockCompostBin;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityCompostBin;
import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class CompostBinRenderer implements ISimpleBlockRenderingHandler {
   private ModularBoxRenderer boxRenderer = new ModularBoxRenderer();

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      if (block instanceof BlockCompostBin) {
         this.renderInventoryBlock((BlockCompostBin)block, metadata, modelId, renderer);
      }
   }

   private void renderInventoryBlock(BlockCompostBin block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.boxRenderer.setUnit(0.0625D);
      this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
      this.boxRenderer.setCutIcon(block.getInnerIcon());

      for(int side = 0; side < 6; ++side) {
         this.boxRenderer.setExteriorIcon(block.getIcon(side, metadata), side);
         this.boxRenderer.setInteriorIcon(block.getIcon(side, metadata), side);
      }

      this.boxRenderer.renderBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0, 2);
      this.boxRenderer.setUnit(0.0D);
      this.boxRenderer.setInteriorIcon(block.getIcon(1, metadata));
      this.boxRenderer.renderInterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.125D, 0.625D, 0.9375D, 0.875D, 0.75D, 1.0D, 0, 12);
      this.boxRenderer.renderInterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.125D, 0.25D, 0.9375D, 0.875D, 0.375D, 1.0D, 0, 12);
      this.boxRenderer.renderInterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.125D, 0.625D, 0.0D, 0.875D, 0.75D, 0.0625D, 0, 12);
      this.boxRenderer.renderInterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.125D, 0.25D, 0.0D, 0.875D, 0.375D, 0.0625D, 0, 12);
      this.boxRenderer.renderInterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.9375D, 0.625D, 0.125D, 1.0D, 0.75D, 0.875D, 0, 48);
      this.boxRenderer.renderInterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.9375D, 0.25D, 0.125D, 1.0D, 0.375D, 0.875D, 0, 48);
      this.boxRenderer.renderInterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.0D, 0.625D, 0.125D, 0.0625D, 0.75D, 0.875D, 0, 48);
      this.boxRenderer.renderInterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.0D, 0.25D, 0.125D, 0.0625D, 0.375D, 0.875D, 0, 48);
      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return !(block instanceof BlockCompostBin) ? false : this.renderWorldBlock(world, x, y, z, (BlockCompostBin)block, modelId, renderer);
   }

   private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockCompostBin block, int modelId, RenderBlocks renderer) {
      Tessellator tessellator = Tessellator.instance;
      tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
      this.boxRenderer.setUnit(0.0625D);
      this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
      this.boxRenderer.setCutIcon(block.getInnerIcon());

      for(int side = 0; side < 6; ++side) {
         this.boxRenderer.setExteriorIcon(block.getIcon(world, x, y, z, side), side);
         this.boxRenderer.setInteriorIcon(block.getIcon(world, x, y, z, side), side);
      }

      this.boxRenderer.renderBox(world, block, (double)x, (double)y, (double)z, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0, 2);
      this.boxRenderer.setUnit(0.0D);
      this.boxRenderer.setInteriorIcon(block.getIcon(world, x, y, z, 1));
      this.boxRenderer.renderInterior(world, block, (double)x, (double)y, (double)z, 0.125D, 0.625D, 0.9375D, 0.875D, 0.75D, 1.0D, 0, 12);
      this.boxRenderer.renderInterior(world, block, (double)x, (double)y, (double)z, 0.125D, 0.25D, 0.9375D, 0.875D, 0.375D, 1.0D, 0, 12);
      this.boxRenderer.renderInterior(world, block, (double)x, (double)y, (double)z, 0.125D, 0.625D, 0.0D, 0.875D, 0.75D, 0.0625D, 0, 12);
      this.boxRenderer.renderInterior(world, block, (double)x, (double)y, (double)z, 0.125D, 0.25D, 0.0D, 0.875D, 0.375D, 0.0625D, 0, 12);
      this.boxRenderer.renderInterior(world, block, (double)x, (double)y, (double)z, 0.9375D, 0.625D, 0.125D, 1.0D, 0.75D, 0.875D, 0, 48);
      this.boxRenderer.renderInterior(world, block, (double)x, (double)y, (double)z, 0.9375D, 0.25D, 0.125D, 1.0D, 0.375D, 0.875D, 0, 48);
      this.boxRenderer.renderInterior(world, block, (double)x, (double)y, (double)z, 0.0D, 0.625D, 0.125D, 0.0625D, 0.75D, 0.875D, 0, 48);
      this.boxRenderer.renderInterior(world, block, (double)x, (double)y, (double)z, 0.0D, 0.25D, 0.125D, 0.0625D, 0.375D, 0.875D, 0, 48);
      TileEntityCompostBin te = (TileEntityCompostBin)world.getTileEntity(x, y, z);
      if (te != null) {
         if (te.hasInputItems()) {
            this.boxRenderer.setExteriorIcon(Blocks.dirt.getIcon(1, 2));
            this.boxRenderer.renderSolidBox(world, block, (double)x, (double)y, (double)z, 0.0625D, 0.0625D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
         } else if (te.hasOutputItems()) {
            this.boxRenderer.setExteriorIcon(ModBlocks.gardenSoil.getIcon(1, 0));
            this.boxRenderer.renderSolidBox(world, block, (double)x, (double)y, (double)z, 0.0625D, 0.0625D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
         }
      }

      return true;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return 0;
   }
}
