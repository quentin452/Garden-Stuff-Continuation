package com.jaquadro.minecraft.gardentrees.block;

import com.jaquadro.minecraft.gardentrees.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardentrees.core.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;

public class BlockCandelilla extends BlockCrops implements IShearable {
   @SideOnly(Side.CLIENT)
   IIcon[] icons;
   private boolean shearScratch;

   public BlockCandelilla(String blockName) {
      this.setBlockTextureName("GardenTrees:candelilla");
      this.setBlockName(blockName);
      this.setHardness(0.0F);
      this.setStepSound(BlockBush.soundTypeGrass);
      this.setCreativeTab(ModCreativeTabs.tabGardenTrees);
   }

   public int getRenderType() {
      return 6;
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
      return true;
   }

   public ArrayList onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
      this.shearScratch = true;
      ArrayList ret = new ArrayList();
      ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
      return ret;
   }

   public boolean canHarvestBlock(EntityPlayer player, int meta) {
      if (this.shearScratch) {
         this.shearScratch = false;
         return false;
      } else {
         return super.canHarvestBlock(player, meta);
      }
   }

   public IIcon getIcon(int side, int meta) {
      switch(meta) {
      case 0:
         return this.icons[0];
      case 1:
         return this.icons[1];
      case 2:
         return this.icons[2];
      case 3:
      case 4:
         return this.icons[3];
      case 5:
      case 6:
         return this.icons[4];
      case 7:
      default:
         return this.icons[5];
      }
   }

   protected Item func_149866_i() {
      return ModItems.candelilla_seeds;
   }

   protected Item func_149865_P() {
      return ModItems.candelilla;
   }

   public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
      return EnumPlantType.Crop;
   }

   public boolean canBlockStay(World world, int x, int y, int z) {
      return super.canBlockStay(world, x, y, z) ? true : this.canPlaceBlockOn(world.getBlock(x, y - 1, z));
   }

   protected boolean canPlaceBlockOn(Block block) {
      return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland || block == Blocks.sand;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      this.icons = new IIcon[6];

      for(int i = 0; i < this.icons.length; ++i) {
         this.icons[i] = register.registerIcon(this.getTextureName() + "_stage_" + i);
      }

   }
}
