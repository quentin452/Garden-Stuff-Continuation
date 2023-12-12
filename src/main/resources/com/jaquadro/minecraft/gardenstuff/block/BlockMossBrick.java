package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockMossBrick extends Block {
   public static final String[] subNames = new String[]{"mossy_2", "mossy_3", "mossy_4", "cracked_mossy_1", "cracked_mossy_2", "cracked_mossy_3", "cracked_mossy_4"};
   @SideOnly(Side.CLIENT)
   private IIcon[] icons;

   public BlockMossBrick(String name) {
      super(Material.rock);
      this.setBlockName(name);
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
      this.setHardness(1.5F);
      this.setResistance(10.0F);
      this.setStepSound(soundTypePiston);
      this.setBlockTextureName("GardenStuff:stonebrick");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      if (meta < 0 || meta >= subNames.length) {
         meta = 0;
      }

      return this.icons[meta];
   }

   public int damageDropped(int meta) {
      return meta;
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
      for(int i = 0; i < subNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      this.icons = new IIcon[subNames.length];

      for(int i = 0; i < this.icons.length; ++i) {
         this.icons[i] = register.registerIcon(this.getTextureName() + "_" + subNames[i]);
      }

   }
}
