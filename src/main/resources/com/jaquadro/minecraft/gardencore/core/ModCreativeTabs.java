package com.jaquadro.minecraft.gardencore.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public final class ModCreativeTabs {
   public static final CreativeTabs tabGardenCore = new CreativeTabs("gardenCore") {
      @SideOnly(Side.CLIENT)
      public Item getTabIconItem() {
         return Item.getItemFromBlock(ModBlocks.gardenSoil);
      }
   };

   private ModCreativeTabs() {
   }
}
