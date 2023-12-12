package com.jaquadro.minecraft.gardencontainers.client.renderer;

import com.jaquadro.minecraft.gardencontainers.block.BlockWindowBox;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityWindowBox;
import com.jaquadro.minecraft.gardencontainers.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class WindowBoxRenderer implements ISimpleBlockRenderingHandler {
   private float[] baseColor = new float[3];
   private float[] activeRimColor = new float[3];
   private float[] activeInWallColor = new float[3];
   private float[] activeBottomColor = new float[3];
   private float[] activeSubstrateColor = new float[3];
   private ModularBoxRenderer boxRenderer = new ModularBoxRenderer();

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      if (block instanceof BlockWindowBox) {
         this.renderInventoryBlock((BlockWindowBox)block, metadata, modelId, renderer);
      }
   }

   private void renderInventoryBlock(BlockWindowBox block, int metadata, int modelId, RenderBlocks renderer) {
      Tessellator tessellator = Tessellator.instance;
      IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, metadata);
      float unit = 0.0625F;
      this.boxRenderer.setIcon(icon);
      this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.boxRenderer.renderBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, (double)(0.0F * unit), (double)(4.0F * unit), (double)(4.0F * unit), (double)(16.0F * unit), (double)(12.0F * unit), (double)(12.0F * unit), 0, 2);
      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return !(block instanceof BlockWindowBox) ? false : this.renderWorldBlock(world, x, y, z, (BlockWindowBox)block, modelId, renderer);
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockWindowBox block, int modelId, RenderBlocks renderer) {
      int data = world.getBlockMetadata(x, y, z);
      Tessellator tessellator = Tessellator.instance;
      tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
      IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, data);
      RenderHelper.calculateBaseColor(this.baseColor, block.colorMultiplier(world, x, y, z));
      RenderHelper.scaleColor(this.activeRimColor, this.baseColor, 0.8F);
      RenderHelper.scaleColor(this.activeInWallColor, this.baseColor, 0.7F);
      RenderHelper.scaleColor(this.activeBottomColor, this.baseColor, 0.6F);
      this.boxRenderer.setIcon(icon);
      this.boxRenderer.setExteriorColor(this.baseColor);
      this.boxRenderer.setInteriorColor(this.activeInWallColor);
      this.boxRenderer.setInteriorColor(this.activeBottomColor, 0);
      this.boxRenderer.setCutColor(this.activeRimColor);
      TileEntityWindowBox te = block.getTileEntity(world, x, y, z);
      boolean validNE = te.isSlotValid(2);
      boolean validNW = te.isSlotValid(1);
      boolean validSE = te.isSlotValid(4);
      boolean validSW = te.isSlotValid(3);
      int connect;
      if (validNW) {
         connect = 0 | (validNE ? 32 : 0) | (validSW ? 8 : 0);
         this.boxRenderer.renderOctant(world, block, (double)x, (double)y + (te.isUpper() ? 0.5D : 0.0D), (double)z, connect, 2);
      }

      if (validNE) {
         connect = 0 | (validNW ? 16 : 0) | (validSE ? 8 : 0);
         this.boxRenderer.renderOctant(world, block, (double)x + 0.5D, (double)y + (te.isUpper() ? 0.5D : 0.0D), (double)z, connect, 2);
      }

      if (validSW) {
         connect = 0 | (validSE ? 32 : 0) | (validNW ? 4 : 0);
         this.boxRenderer.renderOctant(world, block, (double)x, (double)y + (te.isUpper() ? 0.5D : 0.0D), (double)z + 0.5D, connect, 2);
      }

      if (validSE) {
         connect = 0 | (validSW ? 16 : 0) | (validNE ? 4 : 0);
         this.boxRenderer.renderOctant(world, block, (double)x + 0.5D, (double)y + (te.isUpper() ? 0.5D : 0.0D), (double)z + 0.5D, connect, 2);
      }

      ItemStack substrateItem = block.getGardenSubstrate(world, x, y, z, -1);
      if (substrateItem != null && substrateItem.getItem() instanceof ItemBlock) {
         Block substrate = Block.getBlockFromItem(substrateItem.getItem());
         IIcon substrateIcon = renderer.getBlockIconFromSideAndMetadata(substrate, 1, substrateItem.getItemDamage());
         RenderHelper.calculateBaseColor(this.activeSubstrateColor, substrate.getBlockColor());
         RenderHelper.scaleColor(this.activeSubstrateColor, this.activeSubstrateColor, 0.8F);
         RenderHelper.setTessellatorColor(tessellator, this.activeSubstrateColor);
         double ySubstrate = (te.isUpper() ? 1.0D : 0.5D) - 0.0625D;
         if (validNW) {
            renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 0.5D, ySubstrate, 0.5D);
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, substrateIcon);
         }

         if (validNE) {
            renderer.setRenderBounds(0.5D, 0.0D, 0.0D, 1.0D, ySubstrate, 0.5D);
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, substrateIcon);
         }

         if (validSW) {
            renderer.setRenderBounds(0.0D, 0.0D, 0.5D, 0.5D, ySubstrate, 1.0D);
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, substrateIcon);
         }

         if (validSE) {
            renderer.setRenderBounds(0.5D, 0.0D, 0.5D, 1.0D, ySubstrate, 1.0D);
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, substrateIcon);
         }
      }

      return true;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return ClientProxy.windowBoxRenderID;
   }
}
