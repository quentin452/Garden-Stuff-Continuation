package com.jaquadro.minecraft.gardencontainers.block;

import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityMediumPot;
import com.jaquadro.minecraft.gardencontainers.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.api.block.IChainAttachable;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.block.BlockGardenContainer;
import com.jaquadro.minecraft.gardencore.block.support.BasicConnectionProfile;
import com.jaquadro.minecraft.gardencore.block.support.BasicSlotProfile;
import com.jaquadro.minecraft.gardencore.block.support.Slot2Profile;
import com.jaquadro.minecraft.gardencore.block.support.SlotShare0Profile;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockMediumPot extends BlockGardenContainer implements IChainAttachable {
   private static final Vec3[] chainAttachPoints = new Vec3[]{Vec3.createVectorHelper(0.16125D, 0.75D, 0.15625D), Vec3.createVectorHelper(0.15625D, 0.75D, 0.83875D), Vec3.createVectorHelper(0.84375D, 0.75D, 0.16125D), Vec3.createVectorHelper(0.83875D, 0.75D, 0.84375D)};

   public BlockMediumPot(String blockName) {
      super(blockName, Material.rock);
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
      this.setHardness(0.5F);
      this.setResistance(5.0F);
      this.setStepSound(Block.soundTypeStone);
      this.connectionProfile = new BasicConnectionProfile();
      this.slotShareProfile = new SlotShare0Profile();
      PlantType[] commonType = new PlantType[]{PlantType.GROUND};
      PlantSize[] allSize = new PlantSize[]{PlantSize.LARGE, PlantSize.MEDIUM, PlantSize.SMALL};
      this.slotProfile = new BlockMediumPot.LocalSlotProfile(new BasicSlotProfile.Slot[]{new BasicSlotProfile.Slot(0, commonType, allSize), new BasicSlotProfile.Slot(1, new PlantType[]{PlantType.GROUND_COVER}, allSize)});
   }

   public abstract String[] getSubTypes();

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
      return ClientProxy.mediumPotRenderID;
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

   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList items = new ArrayList();
      int count = this.quantityDropped(metadata, fortune, world.rand);

      for(int i = 0; i < count; ++i) {
         Item item = this.getItemDropped(metadata, world.rand, fortune);
         if (item != null) {
            items.add(new ItemStack(item, 1, metadata));
         }
      }

      return items;
   }

   public TileEntityMediumPot createNewTileEntity(World world, int meta) {
      return new TileEntityMediumPot();
   }

   public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity colliding) {
      this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.75F, 0.875F);
      super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
      this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.75F, 0.875F);
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.75F, 0.875F);
   }

   public boolean canHarvestBlock(EntityPlayer player, int meta) {
      return true;
   }

   public TileEntityMediumPot getTileEntity(IBlockAccess world, int x, int y, int z) {
      TileEntity te = world.getTileEntity(x, y, z);
      return te != null && te instanceof TileEntityMediumPot ? (TileEntityMediumPot)te : null;
   }

   public Vec3[] getChainAttachPoints(int side) {
      return side == 1 ? chainAttachPoints : null;
   }

   public void registerBlockIcons(IIconRegister register) {
   }

   private class LocalSlotProfile extends Slot2Profile {
      private AxisAlignedBB[] mainClippingBounds = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), AxisAlignedBB.getBoundingBox(0.125D, 0.0625D, 0.125D, 0.875D, 0.125D, 0.875D), AxisAlignedBB.getBoundingBox(0.0D, 0.125D, 0.0D, 1.0D, 1.0D, 1.0D)};

      public LocalSlotProfile(BasicSlotProfile.Slot[] slots) {
         super(slots);
      }

      public float getPlantOffsetY(IBlockAccess blockAccess, int x, int y, int z, int slot) {
         return -0.3125F;
      }

      public AxisAlignedBB[] getClippingBounds(IBlockAccess blockAccess, int x, int y, int z, int slot) {
         return this.mainClippingBounds;
      }
   }
}
