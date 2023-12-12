package com.jaquadro.minecraft.gardencontainers.client.renderer;

import com.jaquadro.minecraft.gardencontainers.block.BlockLargePot;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityLargePot;
import com.jaquadro.minecraft.gardencontainers.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class LargePotRenderer implements ISimpleBlockRenderingHandler {
   private ModularBoxRenderer boxRenderer = new ModularBoxRenderer();
   private float[] colorScratch = new float[3];

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      if (block instanceof BlockLargePot) {
         this.renderInventoryBlock((BlockLargePot)block, metadata, modelId, renderer);
      }
   }

   private void renderInventoryBlock(BlockLargePot block, int metadata, int modelId, RenderBlocks renderer) {
      IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, metadata & 15);
      boolean blendEnabled = GL11.glIsEnabled(3042);
      if (blendEnabled) {
         GL11.glDisable(3042);
      }

      GL11.glDepthMask(true);
      this.boxRenderer.setUnit(0.0625D);
      this.boxRenderer.setIcon(icon);
      this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.boxRenderer.renderBox((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0, 2);
      GL11.glEnable(3042);
      if ((metadata & '\uff00') != 0) {
         int cutFlags = 3;
         this.boxRenderer.setUnit(0.0D);
         this.boxRenderer.setIcon(block.getOverlayIcon(metadata >> 8 & 255));
         this.boxRenderer.renderExterior((IBlockAccess)null, block, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0, cutFlags);
      }

      if (!blendEnabled) {
         GL11.glDisable(3042);
      }

      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      if (!(block instanceof BlockLargePot)) {
         return false;
      } else {
         try {
            if (ClientProxy.renderPass == 0) {
               return this.renderWorldBlockPass0(world, x, y, z, (BlockLargePot)block, modelId, renderer);
            }

            if (ClientProxy.renderPass == 1) {
               return this.renderWorldBlockPass1(world, x, y, z, (BlockLargePot)block, modelId, renderer);
            }
         } catch (Exception var9) {
         }

         return false;
      }
   }

   private boolean renderWorldBlockPass0(IBlockAccess world, int x, int y, int z, BlockLargePot block, int modelId, RenderBlocks renderer) {
      int metadata = world.getBlockMetadata(x, y, z);
      this.boxRenderer.setUnit(0.0625D);
      this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);

      for(int i = 0; i < 6; ++i) {
         this.boxRenderer.setIcon(renderer.getBlockIconFromSideAndMetadata(block, i, metadata), i);
      }

      TileEntityLargePot te = block.getTileEntity(world, x, y, z);
      int connectFlags = 0;
      int connectFlags = connectFlags | (te.isAttachedNeighbor(x - 1, y, z - 1) ? 16384 : 0);
      connectFlags |= te.isAttachedNeighbor(x, y, z - 1) ? 4 : 0;
      connectFlags |= te.isAttachedNeighbor(x + 1, y, z - 1) ? '耀' : 0;
      connectFlags |= te.isAttachedNeighbor(x - 1, y, z) ? 16 : 0;
      connectFlags |= te.isAttachedNeighbor(x + 1, y, z) ? 32 : 0;
      connectFlags |= te.isAttachedNeighbor(x - 1, y, z + 1) ? 65536 : 0;
      connectFlags |= te.isAttachedNeighbor(x, y, z + 1) ? 8 : 0;
      connectFlags |= te.isAttachedNeighbor(x + 1, y, z + 1) ? 131072 : 0;
      this.boxRenderer.renderBox(world, block, (double)x, (double)y, (double)z, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, connectFlags, 2);
      if (te != null && te.getSubstrate() != null && te.getSubstrate().getItem() instanceof ItemBlock) {
         Block substrate = Block.getBlockFromItem(te.getSubstrate().getItem());
         int substrateData = te.getSubstrate().getItemDamage();
         if (substrate != Blocks.water) {
            IIcon substrateIcon = renderer.getBlockIconFromSideAndMetadata(substrate, 1, substrateData);
            int color = substrate.colorMultiplier(world, x, y, z);
            if (color == Blocks.grass.colorMultiplier(world, x, y, z)) {
               color = ColorizerGrass.getGrassColor((double)te.getBiomeTemperature(), (double)te.getBiomeHumidity());
            }

            RenderHelper.calculateBaseColor(this.colorScratch, color);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
            RenderHelper.instance.renderFace(1, world, block, x, y, z, substrateIcon, this.colorScratch[0], this.colorScratch[1], this.colorScratch[2]);
         }
      }

      return true;
   }

   private boolean renderWorldBlockPass1(IBlockAccess world, int x, int y, int z, BlockLargePot block, int modelId, RenderBlocks renderer) {
      TileEntityLargePot tileEntity = block.getTileEntity(world, x, y, z);
      if (tileEntity == null) {
         RenderHelper.instance.renderEmptyPlane(x, y, z);
         return true;
      } else {
         boolean didRender = false;
         IIcon icon = block.getOverlayIcon(tileEntity.getCarving());
         if (icon != null) {
            int connectFlags = 0;
            int cutFlags = 3;
            int connectFlags = connectFlags | (tileEntity.isAttachedNeighbor(x, y, z - 1) ? 4 : 0);
            connectFlags |= tileEntity.isAttachedNeighbor(x - 1, y, z) ? 16 : 0;
            connectFlags |= tileEntity.isAttachedNeighbor(x + 1, y, z) ? 32 : 0;
            connectFlags |= tileEntity.isAttachedNeighbor(x, y, z + 1) ? 8 : 0;
            if (connectFlags != 60) {
               this.boxRenderer.setUnit(0.0D);
               this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
               this.boxRenderer.setExteriorIcon(icon);
               this.boxRenderer.renderExterior(world, block, (double)x, (double)y, (double)z, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, connectFlags, cutFlags);
               didRender = true;
            }
         }

         if (tileEntity.getSubstrate() != null && tileEntity.getSubstrate().getItem() instanceof ItemBlock) {
            Block substrate = Block.getBlockFromItem(tileEntity.getSubstrate().getItem());
            int substrateData = tileEntity.getSubstrate().getItemDamage();
            if (substrate == Blocks.water) {
               IIcon substrateIcon = renderer.getBlockIconFromSideAndMetadata(substrate, 1, substrateData);
               RenderHelper.instance.state.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
               RenderHelper.instance.renderFace(1, world, block, x, y, z, substrateIcon);
               didRender = true;
            }
         }

         if (!didRender) {
            RenderHelper.instance.renderEmptyPlane(x, y, z);
         }

         return true;
      }
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return ClientProxy.largePotRenderID;
   }
}
