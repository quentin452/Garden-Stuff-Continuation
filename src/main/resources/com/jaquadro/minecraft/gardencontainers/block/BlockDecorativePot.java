package com.jaquadro.minecraft.gardencontainers.block;

import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityDecorativePot;
import com.jaquadro.minecraft.gardencontainers.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.block.BlockGardenContainer;
import com.jaquadro.minecraft.gardencore.block.support.BasicConnectionProfile;
import com.jaquadro.minecraft.gardencore.block.support.BasicSlotProfile;
import com.jaquadro.minecraft.gardencore.block.support.Slot2Profile;
import com.jaquadro.minecraft.gardencore.block.support.SlotShare0Profile;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockDecorativePot extends BlockGardenContainer {
   public BlockDecorativePot(String blockName) {
      super(blockName, Material.rock);
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
      this.setHardness(0.5F);
      this.setStepSound(Block.soundTypeStone);
      this.connectionProfile = new BasicConnectionProfile();
      this.slotShareProfile = new SlotShare0Profile();
      PlantType[] commonType = new PlantType[]{PlantType.GROUND, PlantType.AQUATIC, PlantType.AQUATIC_EMERGENT};
      PlantSize[] allSize = new PlantSize[]{PlantSize.FULL, PlantSize.LARGE, PlantSize.MEDIUM, PlantSize.SMALL};
      this.slotProfile = new BlockDecorativePot.LocalSlotProfile(new BasicSlotProfile.Slot[]{new BasicSlotProfile.Slot(0, commonType, allSize), new BasicSlotProfile.Slot(1, new PlantType[]{PlantType.GROUND_COVER}, allSize)});
   }

   public int damageDropped(int meta) {
      return meta;
   }

   protected boolean isValidSubstrate(World world, int x, int y, int z, int slot, ItemStack itemStack) {
      if (itemStack != null && itemStack.getItem() != null) {
         return Block.getBlockFromItem(itemStack.getItem()) == Blocks.netherrack ? true : super.isValidSubstrate(world, x, y, z, slot, itemStack);
      } else {
         return false;
      }
   }

   public int getDefaultSlot() {
      return 0;
   }

   protected int getSlot(World world, int x, int y, int z, EntityPlayer player, float hitX, float hitY, float hitZ) {
      return 0;
   }

   protected int getEmptySlotForPlant(World world, int x, int y, int z, EntityPlayer player, PlantItem plant) {
      return plant.getPlantTypeClass() == PlantType.GROUND_COVER ? 1 : 0;
   }

   public int getRenderType() {
      return ClientProxy.decorativePotRenderID;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
      return true;
   }

   public TileEntityDecorativePot getTileEntity(IBlockAccess world, int x, int y, int z) {
      TileEntity te = world.getTileEntity(x, y, z);
      return te != null && te instanceof TileEntityDecorativePot ? (TileEntityDecorativePot)te : null;
   }

   public TileEntityDecorativePot createNewTileEntity(World var1, int var2) {
      return new TileEntityDecorativePot();
   }

   public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity colliding) {
      this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
      this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.5F, 0.8125F);
      super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   protected boolean applyItemToGarden(World world, int x, int y, int z, EntityPlayer player, ItemStack itemStack, float hitX, float hitY, float hitZ, boolean hitValid) {
      ItemStack item = itemStack == null ? player.inventory.getCurrentItem() : itemStack;
      if (item != null && item.getItem() == Items.flint_and_steel) {
         ItemStack substrate = this.getGardenSubstrate(world, x, y, z, 0);
         if (substrate != null && substrate.getItem() == Item.getItemFromBlock(Blocks.netherrack)) {
            if (world.isAirBlock(x, y + 1, z)) {
               world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
               world.setBlock(x, y + 1, z, ModBlocks.smallFire);
               world.notifyBlocksOfNeighborChange(x, y, z, this);
               world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
            }

            item.damageItem(1, player);
            return true;
         }
      }

      return super.applyItemToGarden(world, x, y, z, player, itemStack, hitX, hitY, hitZ, hitValid);
   }

   public boolean canHarvestBlock(EntityPlayer player, int meta) {
      return true;
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int data) {
      if (this.isSconceLit(world, x, y, z)) {
         world.notifyBlocksOfNeighborChange(x, y, z, this);
         world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
      }

      super.breakBlock(world, x, y, z, block, data);
   }

   public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
      super.onNeighborBlockChange(world, x, y, z, block);
      if (block == ModBlocks.smallFire && world.getBlock(x, y + 1, z) != ModBlocks.smallFire) {
         world.notifyBlocksOfNeighborChange(x, y, z, this);
         world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
      }

   }

   public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
      return side == 1 && this.isSconceLit(world, x, y, z) ? 15 : 0;
   }

   public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
      return this.isSconceLit(world, x, y, z) ? 15 : 0;
   }

   public boolean canProvidePower() {
      return true;
   }

   private boolean isSconceLit(IBlockAccess world, int x, int y, int z) {
      ItemStack substrate = this.getGardenSubstrate(world, x, y, z, 0);
      if (substrate != null && substrate.getItem() == Item.getItemFromBlock(Blocks.netherrack)) {
         return world.getBlock(x, y + 1, z) == ModBlocks.smallFire;
      } else {
         return false;
      }
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List blockList) {
      for(int i = 0; i < 3; ++i) {
         blockList.add(new ItemStack(item, 1, i));
      }

   }

   public IIcon getIcon(int side, int meta) {
      if (meta == 2) {
         side = 1;
      }

      return Blocks.quartz_block.getIcon(side, meta);
   }

   public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
      if (world.getBlockMetadata(x, y, z) == 2) {
         side = 1;
      }

      return Blocks.quartz_block.getIcon(world, x, y, z, side);
   }

   public void registerBlockIcons(IIconRegister register) {
   }

   private class LocalSlotProfile extends Slot2Profile {
      public LocalSlotProfile(BasicSlotProfile.Slot[] slots) {
         super(slots);
      }

      public float getPlantOffsetY(IBlockAccess blockAccess, int x, int y, int z, int slot) {
         return -0.0625F;
      }
   }
}
