package com.jaquadro.minecraft.gardenstuff.integration.lantern;

import com.jaquadro.minecraft.gardenapi.api.component.StandardLanternSource;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ThaumcraftCandleSource extends StandardLanternSource {
   private Block blockCandle;

   public ThaumcraftCandleSource(Block blockCandle) {
      super(new StandardLanternSource.LanternSourceInfo("thaumcraftCandle", Item.getItemFromBlock(blockCandle), blockCandle.getLightValue()));
      this.blockCandle = blockCandle;
   }

   @SideOnly(Side.CLIENT)
   public void renderParticle(World world, int x, int y, int z, Random rand, int meta) {
      world.spawnParticle("flame", (double)((float)x + 0.5F), (double)((float)y + 0.7F), (double)((float)z + 0.5F), 0.0D, 0.0D, 0.0D);
   }

   @SideOnly(Side.CLIENT)
   public void render(RenderBlocks renderer, int x, int y, int z, int meta, int pass) {
      renderer.renderBlockAllFaces(this.blockCandle, x, y, z);
   }

   @SideOnly(Side.CLIENT)
   public void renderItem(RenderBlocks renderer, ItemRenderType renderType, int meta) {
      RenderHelper renderHelper = RenderHelper.instance;
      renderHelper.setRenderBounds(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D);
      renderHelper.renderFace(2, (IBlockAccess)null, this.blockCandle, this.blockCandle.getIcon(2, meta), meta);
      renderHelper.renderFace(3, (IBlockAccess)null, this.blockCandle, this.blockCandle.getIcon(3, meta), meta);
      renderHelper.renderFace(4, (IBlockAccess)null, this.blockCandle, this.blockCandle.getIcon(4, meta), meta);
      renderHelper.renderFace(5, (IBlockAccess)null, this.blockCandle, this.blockCandle.getIcon(5, meta), meta);
      renderHelper.renderFace(1, (IBlockAccess)null, this.blockCandle, this.blockCandle.getIcon(1, meta), meta);
      renderHelper.setRenderBounds(0.46875D, 0.0D, 0.46875D, 0.5325D, 1.0D, 0.53125D);
      renderHelper.renderFace(2, (IBlockAccess)null, Blocks.torch, Blocks.torch.getIcon(2, 0), meta);
      renderHelper.renderFace(3, (IBlockAccess)null, Blocks.torch, Blocks.torch.getIcon(3, 0), meta);
      renderHelper.renderFace(4, (IBlockAccess)null, Blocks.torch, Blocks.torch.getIcon(4, 0), meta);
      renderHelper.renderFace(5, (IBlockAccess)null, Blocks.torch, Blocks.torch.getIcon(5, 0), meta);
      renderHelper.setRenderBounds(0.46875D, 0.0D, 0.46875D, 0.5325D, 0.625D, 0.5325D);
      renderHelper.renderFace(1, (IBlockAccess)null, Blocks.torch, Blocks.torch.getIcon(1, 0), meta);
   }
}
