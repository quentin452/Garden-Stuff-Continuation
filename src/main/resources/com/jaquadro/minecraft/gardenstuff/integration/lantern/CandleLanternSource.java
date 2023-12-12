package com.jaquadro.minecraft.gardenstuff.integration.lantern;

import com.jaquadro.minecraft.gardenapi.api.component.StandardLanternSource;
import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import com.jaquadro.minecraft.gardenstuff.block.BlockCandelabra;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import com.jaquadro.minecraft.gardenstuff.core.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import java.util.Random;

public class CandleLanternSource extends StandardLanternSource {
   private ModularBoxRenderer boxrender = new ModularBoxRenderer();

   public CandleLanternSource() {
      super(new StandardLanternSource.LanternSourceInfo("candle", ModItems.candle, 14));
   }

   @SideOnly(Side.CLIENT)
   public void render(RenderBlocks renderer, int x, int y, int z, int meta, int pass) {
      float unit = 0.0625F;
      BlockCandelabra block = ModBlocks.candelabra;
      RenderHelper.instance.state.setRenderOffset(0.0D, -0.375D, 0.0D);
      RenderHelper.instance.state.setColorMult(1.0F, 0.9F, 0.8F, 0.5F);
      this.boxrender.setUnit(0.0D);
      this.boxrender.setColor(ModularBoxRenderer.COLOR_WHITE);
      this.boxrender.setIcon(block.getIconCandleTop(), 1);

      for(int i = 2; i < 6; ++i) {
         this.boxrender.setIcon(block.getIconCandleSide(), i);
      }

      this.boxrender.renderExterior(renderer.blockAccess, block, (double)x, (double)y, (double)z, (double)(unit * 6.5F), (double)(unit * 7.0F), (double)(unit * 6.5F), (double)(unit * 9.5F), (double)(unit * 13.0F), (double)(unit * 9.5F), 0, 1);
      RenderHelper.instance.state.resetColorMult();
      RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      RenderHelper.instance.renderCrossedSquares(renderer.blockAccess, block, x, y, z, block.getIconCandleSide());
      RenderHelper.instance.state.clearRenderOffset();
   }

   @SideOnly(Side.CLIENT)
   public void renderItem(RenderBlocks renderer, ItemRenderType renderType, int meta) {
      float unit = 0.0625F;
      BlockCandelabra block = ModBlocks.candelabra;
      RenderHelper.instance.state.setRenderOffset(0.0D, -0.375D, 0.0D);
      RenderHelper.instance.state.setColorMult(1.0F, 0.9F, 0.8F, 0.5F);
      this.boxrender.setUnit(0.0D);
      this.boxrender.setColor(ModularBoxRenderer.COLOR_WHITE);
      this.boxrender.setIcon(block.getIconCandleTop(), 1);

      for(int i = 2; i < 6; ++i) {
         this.boxrender.setIcon(block.getIconCandleSide(), i);
      }

      int x = 0;
      int y = 0;
      int z = 0;
      this.boxrender.renderExterior((IBlockAccess)null, block, (double)x, (double)y, (double)z, (double)(unit * 6.5F), (double)(unit * 7.0F), (double)(unit * 6.5F), (double)(unit * 9.5F), (double)(unit * 13.0F), (double)(unit * 9.5F), 0, 1);
      RenderHelper.instance.state.resetColorMult();
      RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      RenderHelper.instance.renderCrossedSquares(block, meta, block.getIconCandleSide());
      RenderHelper.instance.state.clearRenderOffset();
   }

   @SideOnly(Side.CLIENT)
   public void renderParticle(World world, int x, int y, int z, Random rand, int meta) {
      double px = (double)((float)x + 0.5F);
      double py = (double)((float)y + 0.625F);
      double pz = (double)((float)z + 0.5F);
      world.spawnParticle("flame", px, py, pz, 0.0D, 0.0D, 0.0D);
   }
}
