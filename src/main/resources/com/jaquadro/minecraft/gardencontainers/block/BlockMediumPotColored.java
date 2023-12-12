package com.jaquadro.minecraft.gardencontainers.block;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockMediumPotColored extends BlockMediumPot {
   public BlockMediumPotColored(String blockName) {
      super(blockName);
   }

   public String[] getSubTypes() {
      return ItemDye.field_150921_b;
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List blockList) {
      for(int i = 0; i < 16; ++i) {
         blockList.add(new ItemStack(item, 1, i));
      }

   }

   public IIcon getIcon(int side, int data) {
      return Blocks.stained_hardened_clay.getIcon(side, 15 - (data & 15));
   }

   public static int getBlockFromDye(int index) {
      return index & 15;
   }
}
