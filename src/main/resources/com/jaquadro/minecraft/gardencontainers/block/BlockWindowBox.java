package com.jaquadro.minecraft.gardencontainers.block;

import com.jaquadro.minecraft.gardencontainers.block.support.WindowBoxConnectionProfile;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityWindowBox;
import com.jaquadro.minecraft.gardencontainers.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.support.BasicSlotProfile;
import com.jaquadro.minecraft.gardencore.block.support.Slot5Profile;
import com.jaquadro.minecraft.gardencore.block.support.SlotShare0Profile;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWindowBox extends BlockGarden {
   public static final String[] subTypes = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "big_oak"};
   private static ItemStack substrate;

   public BlockWindowBox(String blockName, Material material) {
      super(blockName, material);
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
      this.setHardness(0.5F);
      this.setStepSound(Block.soundTypeWood);
      this.connectionProfile = new WindowBoxConnectionProfile();
      this.slotShareProfile = new SlotShare0Profile();
      PlantType[] commonType = new PlantType[]{PlantType.GROUND};
      PlantSize[] smallSize = new PlantSize[]{PlantSize.LARGE, PlantSize.MEDIUM, PlantSize.SMALL};
      PlantSize[] allSize = new PlantSize[]{PlantSize.FULL, PlantSize.LARGE, PlantSize.MEDIUM, PlantSize.SMALL};
      this.slotProfile = new BlockWindowBox.LocalSlotProfile(new BasicSlotProfile.Slot[]{new BasicSlotProfile.Slot(0, new PlantType[]{PlantType.GROUND_COVER}, allSize), new BasicSlotProfile.Slot(1, commonType, smallSize), new BasicSlotProfile.Slot(2, commonType, smallSize), new BasicSlotProfile.Slot(3, commonType, smallSize), new BasicSlotProfile.Slot(4, commonType, smallSize)});
   }

   public int getRenderType() {
      return ClientProxy.windowBoxRenderID;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public ItemStack getGardenSubstrate(IBlockAccess world, int x, int y, int z, int slot) {
      return substrate;
   }

   protected int getSlot(World world, int x, int y, int z, EntityPlayer player, float hitX, float hitY, float hitZ) {
      TileEntityWindowBox tileEntity = this.getTileEntity(world, x, y, z);
      if ((double)hitX <= 0.5D) {
         if ((double)hitZ <= 0.5D && tileEntity.isSlotValid(1)) {
            return 1;
         }

         if (tileEntity.isSlotValid(3)) {
            return 3;
         }
      } else {
         if ((double)hitZ <= 0.5D && tileEntity.isSlotValid(2)) {
            return 2;
         }

         if (tileEntity.isSlotValid(4)) {
            return 4;
         }
      }

      return -1;
   }

   protected int getEmptySlotForPlant(World world, int x, int y, int z, EntityPlayer player, PlantItem plant, float hitX, float hitY, float hitZ) {
      return this.getSlot(world, x, y, z, player, hitX, hitY, hitZ);
   }

   public TileEntityWindowBox createNewTileEntity(World var1, int var2) {
      return new TileEntityWindowBox();
   }

   public int damageDropped(int meta) {
      return meta;
   }

   public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity colliding) {
      TileEntityWindowBox te = this.getTileEntity(world, x, y, z);
      boolean validNE = te.isSlotValid(2);
      boolean validNW = te.isSlotValid(1);
      boolean validSE = te.isSlotValid(4);
      boolean validSW = te.isSlotValid(3);
      float yMin = te.isUpper() ? 0.5F : 0.0F;
      float yMax = te.isUpper() ? 1.0F : 0.5F;
      if (validNW) {
         this.setBlockBounds(0.0F, yMin, 0.0F, 0.5F, yMax, 0.5F);
         super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
      }

      if (validNE) {
         this.setBlockBounds(0.5F, yMin, 0.0F, 1.0F, yMax, 0.5F);
         super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
      }

      if (validSW) {
         this.setBlockBounds(0.0F, yMin, 0.5F, 0.5F, yMax, 1.0F);
         super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
      }

      if (validSE) {
         this.setBlockBounds(0.5F, yMin, 0.5F, 1.0F, yMax, 1.0F);
         super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
      }

      this.setBlockBounds(world, x, y, z, validNW, validNE, validSW, validSE);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      TileEntityWindowBox te = this.getTileEntity(world, x, y, z);
      boolean validNE = te.isSlotValid(2);
      boolean validNW = te.isSlotValid(1);
      boolean validSE = te.isSlotValid(4);
      boolean validSW = te.isSlotValid(3);
      this.setBlockBounds(world, x, y, z, validNW, validNE, validSW, validSE);
   }

   private void setBlockBounds(IBlockAccess world, int x, int y, int z, boolean validNW, boolean validNE, boolean validSW, boolean validSE) {
      TileEntityWindowBox te = this.getTileEntity(world, x, y, z);
      float yMin = te.isUpper() ? 0.5F : 0.0F;
      float yMax = te.isUpper() ? 1.0F : 0.5F;
      float xMin = !validNW && !validSW ? 0.5F : 0.0F;
      float xMax = !validNE && !validSE ? 0.5F : 1.0F;
      float zMin = !validNW && !validNE ? 0.5F : 0.0F;
      float zMax = !validSW && !validSE ? 0.5F : 1.0F;
      this.setBlockBounds(xMin, yMin, zMin, xMax, yMax, zMax);
   }

   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
      TileEntityWindowBox te = (TileEntityWindowBox)world.getTileEntity(x, y, z);
      if (te != null && te.getDirection() == 0) {
         int quadrant = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
         switch(quadrant) {
         case 0:
            te.setDirection(3);
            break;
         case 1:
            te.setDirection(4);
            break;
         case 2:
            te.setDirection(2);
            break;
         case 3:
            te.setDirection(5);
         }

         if (world.isRemote) {
            te.invalidate();
            world.markBlockForUpdate(x, y, z);
         }

      }
   }

   public boolean canHarvestBlock(EntityPlayer player, int meta) {
      return true;
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List blockList) {
      for(int i = 0; i < 6; ++i) {
         blockList.add(new ItemStack(item, 1, i));
      }

   }

   public IIcon getIcon(int side, int meta) {
      return Blocks.planks.getIcon(side, meta);
   }

   public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
      return Blocks.planks.getIcon(world, x, y, z, side);
   }

   public String[] getSubTypes() {
      return subTypes;
   }

   public TileEntityWindowBox getTileEntity(IBlockAccess world, int x, int y, int z) {
      TileEntity te = world.getTileEntity(x, y, z);
      return te != null && te instanceof TileEntityWindowBox ? (TileEntityWindowBox)te : null;
   }

   public void registerBlockIcons(IIconRegister register) {
   }

   static {
      substrate = new ItemStack(Blocks.dirt, 1);
   }

   private class LocalSlotProfile extends Slot5Profile {
      private AxisAlignedBB[] mainClippingBounds = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.0625D, 0.6875D), AxisAlignedBB.getBoundingBox(0.25D, 0.0625D, 0.25D, 0.75D, 0.125D, 0.75D), AxisAlignedBB.getBoundingBox(0.1875D, 0.125D, 0.1875D, 0.8125D, 1.0D, 0.8125D)};

      public LocalSlotProfile(BasicSlotProfile.Slot[] slots) {
         super(slots);
      }

      public float getPlantOffsetY(IBlockAccess blockAccess, int x, int y, int z, int slot) {
         TileEntityWindowBox garden = BlockWindowBox.this.getTileEntity(blockAccess, x, y, z);
         return garden != null && !garden.isUpper() ? -0.5625F : -0.0625F;
      }

      public AxisAlignedBB[] getClippingBounds(IBlockAccess blockAccess, int x, int y, int z, int slot) {
         return this.mainClippingBounds;
      }
   }
}
