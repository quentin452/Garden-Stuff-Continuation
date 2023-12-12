package com.jaquadro.minecraft.gardentrees.block;

import com.jaquadro.minecraft.gardentrees.GardenTrees;
import com.jaquadro.minecraft.gardentrees.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockStrangePlant extends BlockFlower implements IShearable {
   @SideOnly(Side.CLIENT)
   private IIcon icon;

   public BlockStrangePlant(String blockName) {
      super(0);
      this.setBlockTextureName("GardenTrees:strange_plant");
      this.setBlockName(blockName);
      this.setHardness(0.0F);
      this.setStepSound(Block.soundTypeGrass);
      this.setCreativeTab(ModCreativeTabs.tabGardenTrees);
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
      return true;
   }

   public ArrayList onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
      ArrayList ret = new ArrayList();
      ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
      return ret;
   }

   public Item getItemDropped(int meta, Random rand, int fortune) {
      return null;
   }

   public ArrayList getDrops(World world, int x, int y, int z, int meta, int fortune) {
      ArrayList ret = new ArrayList();
      ItemStack[] strangePlantDrops = GardenTrees.config.getStrangePlantDrops();
      if (strangePlantDrops.length == 0) {
         return ret;
      } else if (world.rand.nextDouble() > GardenTrees.config.strangePlantDropChance) {
         return ret;
      } else {
         ItemStack item = strangePlantDrops[world.rand.nextInt(strangePlantDrops.length)].copy();
         int range = GardenTrees.config.strangePlantDropMax - GardenTrees.config.strangePlantDropMin;
         int count = GardenTrees.config.strangePlantDropMin + (range > 0 ? world.rand.nextInt(range) : 0);

         for(int i = 0; i < count; ++i) {
            ret.add(item);
         }

         return ret;
      }
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
      list.add(new ItemStack(item));
   }

   public IIcon getIcon(int side, int meta) {
      return this.icon;
   }

   public void registerBlockIcons(IIconRegister register) {
      this.icon = register.registerIcon(this.getTextureName());
   }
}
