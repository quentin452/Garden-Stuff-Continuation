package com.jaquadro.minecraft.gardenapi.api.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import java.util.Random;

public abstract class StandardLanternSource implements ILanternSource {
   private StandardLanternSource.LanternSourceInfo info;

   public StandardLanternSource(StandardLanternSource.LanternSourceInfo info) {
      this.info = info;
   }

   public String getSourceID() {
      return this.info.sourceID;
   }

   public int getSourceMeta(ItemStack item) {
      return item != null ? item.getItemDamage() : 0;
   }

   public boolean isValidSourceItem(ItemStack item) {
      return item != null && item.getItem() == this.info.item;
   }

   public ItemStack getRemovedItem(int meta) {
      return new ItemStack(this.info.item, 1, meta);
   }

   public int getLightLevel(int meta) {
      return this.info.lightLevel;
   }

   public String getLanguageKey(int meta) {
      return "gardenstuff.lanternSource." + this.info.sourceID;
   }

   @SideOnly(Side.CLIENT)
   public void renderParticle(World world, int x, int y, int z, Random rand, int meta) {
   }

   @SideOnly(Side.CLIENT)
   public void render(RenderBlocks renderer, int x, int y, int z, int meta, int pass) {
   }

   @SideOnly(Side.CLIENT)
   public void renderItem(RenderBlocks renderer, ItemRenderType renderType, int meta) {
   }

   public boolean renderInPass(int pass) {
      return pass == 0;
   }

   public static class LanternSourceInfo {
      public String sourceID;
      public Item item;
      public int lightLevel;

      public LanternSourceInfo(String sourceID, Item item, int lightLevel) {
         this.sourceID = sourceID;
         this.item = item;
         this.lightLevel = lightLevel;
      }
   }
}
