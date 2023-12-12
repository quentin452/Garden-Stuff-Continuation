package com.jaquadro.minecraft.gardencontainers.item;

import com.jaquadro.minecraft.gardencontainers.GardenContainers;
import com.jaquadro.minecraft.gardencontainers.config.PatternConfig;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemPotteryPattern extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] iconArray;

   public ItemPotteryPattern(String unlocalizedName) {
      this.setUnlocalizedName(unlocalizedName);
      this.setMaxStackSize(1);
      this.setHasSubtypes(true);
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int damage) {
      return this.iconArray[damage & 15];
   }

   public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
      for(int i = 1; i < 256; ++i) {
         if (GardenContainers.config.hasPattern(i)) {
            list.add(new ItemStack(item, 1, i));
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
      PatternConfig pattern = GardenContainers.config.getPattern(itemStack.getItemDamage());
      if (pattern != null && pattern.getName() != null) {
         list.add(pattern.getName());
      }

   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      this.iconArray = new IIcon[16];

      for(int i = 0; i < 16; ++i) {
         this.iconArray[i] = iconRegister.registerIcon("GardenContainers:pottery_pattern_" + i);
      }

   }
}
