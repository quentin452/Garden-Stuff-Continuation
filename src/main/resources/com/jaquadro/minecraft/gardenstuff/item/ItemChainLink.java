package com.jaquadro.minecraft.gardenstuff.item;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemChainLink extends Item {
   private static final String[] types = new String[]{"iron", "gold", "wrought_iron"};
   @SideOnly(Side.CLIENT)
   private IIcon[] iconArray;

   public ItemChainLink(String unlocalizedName) {
      this.setUnlocalizedName(unlocalizedName);
      this.setHasSubtypes(true);
      this.setTextureName("GardenStuff:chain_link");
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int damage) {
      return this.iconArray[MathHelper.clamp_int(damage, 0, types.length - 1)];
   }

   public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
      for(int i = 0; i < types.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   public String getUnlocalizedName(ItemStack item) {
      return super.getUnlocalizedName(item) + "." + types[MathHelper.clamp_int(item.getItemDamage(), 0, types.length - 1)];
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      this.iconArray = new IIcon[types.length];

      for(int i = 0; i < types.length; ++i) {
         this.iconArray[i] = iconRegister.registerIcon(this.getIconString() + "_" + types[i]);
      }

   }
}
