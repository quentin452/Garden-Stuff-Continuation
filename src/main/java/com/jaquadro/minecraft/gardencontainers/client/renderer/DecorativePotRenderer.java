package com.jaquadro.minecraft.gardencontainers.client.renderer;

import com.jaquadro.minecraft.gardencontainers.block.BlockDecorativePot;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityDecorativePot;
import com.jaquadro.minecraft.gardencontainers.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class DecorativePotRenderer implements ISimpleBlockRenderingHandler {
   private float[] baseColor = new float[3];
   private float[] activeSubstrateColor = new float[3];
   private ModularBoxRenderer boxRenderer = new ModularBoxRenderer();

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      if (block instanceof BlockDecorativePot) {
         this.renderInventoryBlock((BlockDecorativePot)block, metadata, modelId, renderer);
      }
   }

   private void renderInventoryBlock(BlockDecorativePot block, int metadata, int modelId, RenderBlocks renderer) {
      IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, metadata);
      float unit = 0.0625F;
      this.boxRenderer.setIcon(icon);
      this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.boxRenderer.renderBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.0D, (double)(14.0F * unit), 0.0D, 1.0D, 1.0D, 1.0D, 0, 3);
      this.boxRenderer.renderBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, (double)(1.0F * unit), (double)(8.0F * unit), (double)(1.0F * unit), (double)(15.0F * unit), (double)(16.0F * unit), (double)(15.0F * unit), 0, 2);
      this.boxRenderer.renderSolidBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, (double)(3.0F * unit), (double)(6.0F * unit), (double)(3.0F * unit), (double)(13.0F * unit), (double)(8.0F * unit), (double)(13.0F * unit));
      this.boxRenderer.renderSolidBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, (double)(5.0F * unit), (double)(3.0F * unit), (double)(5.0F * unit), (double)(11.0F * unit), (double)(6.0F * unit), (double)(11.0F * unit));
      this.boxRenderer.renderSolidBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, (double)(2.0F * unit), (double)(0.0F * unit), (double)(2.0F * unit), (double)(14.0F * unit), (double)(3.0F * unit), (double)(14.0F * unit));
      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return !(block instanceof BlockDecorativePot) ? false : this.renderWorldBlock(world, x, y, z, (BlockDecorativePot)block, modelId, renderer);
   }

   private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockDecorativePot block, int modelId, RenderBlocks renderer) {
      int data = world.getBlockMetadata(x, y, z);
      Tessellator tessellator = Tessellator.instance;
      tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
      RenderHelper.calculateBaseColor(this.baseColor, block.colorMultiplier(world, x, y, z));
      float unit = 0.0625F;

      for(int i = 0; i < 6; ++i) {
         this.boxRenderer.setIcon(renderer.getBlockIconFromSideAndMetadata(block, i, data), i);
      }

      this.boxRenderer.setColor(this.baseColor);
      this.boxRenderer.renderBox(world, block, (double)x, (double)y, (double)z, 0.0D, (double)(14.0F * unit), 0.0D, 1.0D, 1.0D, 1.0D, 0, 3);
      this.boxRenderer.setScaledColor(this.baseColor, 0.9375F);
      this.boxRenderer.renderBox(world, block, (double)x, (double)y, (double)z, (double)(1.0F * unit), (double)(8.0F * unit), (double)(1.0F * unit), (double)(15.0F * unit), (double)(16.0F * unit), (double)(15.0F * unit), 0, 2);
      this.boxRenderer.setScaledExteriorColor(this.baseColor, 0.875F);
      this.boxRenderer.renderSolidBox(world, block, (double)x, (double)y, (double)z, (double)(3.0F * unit), (double)(6.0F * unit), (double)(3.0F * unit), (double)(13.0F * unit), (double)(8.0F * unit), (double)(13.0F * unit));
      this.boxRenderer.setScaledExteriorColor(this.baseColor, 0.8125F);
      this.boxRenderer.renderSolidBox(world, block, (double)x, (double)y, (double)z, (double)(5.0F * unit), (double)(3.0F * unit), (double)(5.0F * unit), (double)(11.0F * unit), (double)(6.0F * unit), (double)(11.0F * unit));
      this.boxRenderer.setScaledExteriorColor(this.baseColor, 0.9375F);
      this.boxRenderer.setScaledExteriorColor(this.baseColor, 0.75F, 1);
      this.boxRenderer.renderSolidBox(world, block, (double)x, (double)y, (double)z, (double)(2.0F * unit), (double)(0.0F * unit), (double)(2.0F * unit), (double)(14.0F * unit), (double)(3.0F * unit), (double)(14.0F * unit));
      TileEntityDecorativePot te = block.getTileEntity(world, x, y, z);
      ItemStack substrateItem = block.getGardenSubstrate(world, x, y, z, 0);
      if (te != null && substrateItem != null && substrateItem.getItem() instanceof ItemBlock) {
         Block substrate = Block.getBlockFromItem(substrateItem.getItem());
         IIcon substrateIcon = renderer.getBlockIconFromSideAndMetadata(substrate, 1, substrateItem.getItemDamage());
         int color = substrate.colorMultiplier(world, x, y, z);
         if (color == Blocks.grass.colorMultiplier(world, x, y, z)) {
            color = ColorizerGrass.getGrassColor((double)te.getBiomeTemperature(), (double)te.getBiomeHumidity());
         }

         RenderHelper.calculateBaseColor(this.activeSubstrateColor, color);
         RenderHelper.scaleColor(this.activeSubstrateColor, this.activeSubstrateColor, 0.8F);
         RenderHelper.instance.setRenderBounds(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
         RenderHelper.instance.renderFace(1, world, block, x, y, z, substrateIcon, this.activeSubstrateColor[0], this.activeSubstrateColor[1], this.activeSubstrateColor[2]);
      }

      return true;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return ClientProxy.decorativePotRenderID;
   }
}
