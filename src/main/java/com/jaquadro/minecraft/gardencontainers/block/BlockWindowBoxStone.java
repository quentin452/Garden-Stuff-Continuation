package com.jaquadro.minecraft.gardencontainers.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockWindowBoxStone extends BlockWindowBox {
   public static final String[] subTypes = new String[]{"stone_slab", "stone_brick", "mossy_stone_brick", "brick_block", "nether_brick", "sandstone"};

   public BlockWindowBoxStone(String blockName) {
      super(blockName, Material.rock);
      this.setHardness(1.0F);
      this.setStepSound(Block.soundTypeStone);
   }

   public String[] getSubTypes() {
      return subTypes;
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List blockList) {
      for(int i = 0; i < subTypes.length; ++i) {
         blockList.add(new ItemStack(item, 1, i));
      }

   }

   public IIcon getIcon(int side, int meta) {
      return this.getBlockFromMeta(meta).getIcon(side, this.getMetaFromMeta(meta));
   }

   public Block getBlockFromMeta(int meta) {
      switch(meta) {
      case 0:
         return Blocks.stone_slab;
      case 1:
         return Blocks.stonebrick;
      case 2:
         return Blocks.stonebrick;
      case 3:
         return Blocks.brick_block;
      case 4:
         return Blocks.nether_brick;
      case 5:
         return Blocks.sandstone;
      default:
         return null;
      }
   }

   public int getMetaFromMeta(int meta) {
      switch(meta) {
      case 2:
         return 1;
      default:
         return 0;
      }
   }
}
