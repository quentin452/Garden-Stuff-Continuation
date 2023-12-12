package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockStoneType extends Block implements IFuelHandler {
   public BlockStoneType(String name) {
      super(Material.rock);
      this.setBlockName(name);
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
      this.setHardness(5.0F);
      this.setResistance(10.0F);
      this.setStepSound(soundTypePiston);
      this.setBlockTextureName("GardenStuff:charcoal_block");
   }

   public int getBurnTime(ItemStack fuel) {
      return fuel != null && Block.getBlockFromItem(fuel.getItem()) == this && fuel.getItemDamage() == 0 ? 16000 : 0;
   }
}
