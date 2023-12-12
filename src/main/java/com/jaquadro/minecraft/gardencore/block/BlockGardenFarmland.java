package com.jaquadro.minecraft.gardencore.block;

import com.jaquadro.minecraft.gardencore.GardenCore;
import com.jaquadro.minecraft.gardencore.block.support.BasicConnectionProfile;
import com.jaquadro.minecraft.gardencore.block.support.BasicSlotProfile;
import com.jaquadro.minecraft.gardencore.block.support.SlotShare0Profile;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGardenFarmland;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGardenFarmland extends BlockGarden {
   @SideOnly(Side.CLIENT)
   IIcon iconTilledSoil;
   private static ItemStack substrate;

   public BlockGardenFarmland(String blockName) {
      super(blockName, Material.ground);
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
      this.setHardness(0.5F);
      this.setStepSound(Block.soundTypeGrass);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
      if (GardenCore.config.enableTilledSoilGrowthBonus) {
         this.setTickRandomly(true);
      }

      this.connectionProfile = new BasicConnectionProfile();
      this.slotShareProfile = new SlotShare0Profile();
      this.slotProfile = new BasicSlotProfile(new BasicSlotProfile.Slot[0]);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
      return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isFertile(World world, int x, int y, int z) {
      return true;
   }

   public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
      EnumPlantType plantType = plantable.getPlantType(world, x, y, z);
      return plantType == EnumPlantType.Crop;
   }

   public void updateTick(World world, int x, int y, int z, Random random) {
      Block block = world.getBlock(x, y + 1, z);
      if (block instanceof IPlantable || block instanceof IGrowable) {
         block.updateTick(world, x, y + 1, z, random);
      }

   }

   public Item getItemDropped(int meta, Random rand, int fortune) {
      return Item.getItemFromBlock(ModBlocks.gardenSoil);
   }

   public TileEntityGarden createNewTileEntity(World world, int meta) {
      return new TileEntityGardenFarmland();
   }

   public ItemStack getGardenSubstrate(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      return substrate;
   }

   public IIcon getIcon(int side, int meta) {
      return side == 1 ? this.iconTilledSoil : this.blockIcon;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister iconRegister) {
      this.blockIcon = iconRegister.registerIcon("GardenCore:garden_dirt");
      this.iconTilledSoil = iconRegister.registerIcon("GardenCore:garden_farmland");
   }

   static {
      substrate = new ItemStack(Blocks.farmland, 1);
   }
}
