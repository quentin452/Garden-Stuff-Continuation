package com.jaquadro.minecraft.gardencore.block.support;

import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotProfile;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;

public class BasicSlotProfile implements ISlotProfile {
   private static AxisAlignedBB[] defaultClippingBounds = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
   protected BasicSlotProfile.Slot[] slots;
   protected int[] slotIndexes;

   protected BasicSlotProfile() {
   }

   public BasicSlotProfile(BasicSlotProfile.Slot[] slots) {
      this.slots = slots;
      this.slotIndexes = new int[slots.length];

      for(int i = 0; i < slots.length; ++i) {
         this.slotIndexes[i] = slots[i].slot;
      }

   }

   public int[] getPlantSlots() {
      return this.slotIndexes;
   }

   public boolean isPlantValidForSlot(IBlockAccess blockAccess, int x, int y, int z, int slot, PlantItem plant) {
      if (this.slots != null && slot >= 0 && slot < this.slots.length) {
         if (this.slots[slot] == null) {
            return false;
         } else if (!this.slots[slot].validTypeClasses.contains(plant.getPlantTypeClass())) {
            return false;
         } else {
            return this.slots[slot].validSizeClasses.contains(plant.getPlantSizeClass());
         }
      } else {
         return false;
      }
   }

   public float getPlantOffsetX(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      return 0.0F;
   }

   public float getPlantOffsetY(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      return 0.0F;
   }

   public float getPlantOffsetZ(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      return 0.0F;
   }

   public Object openPlantGUI(InventoryPlayer playerInventory, TileEntity gardenTile, boolean client) {
      return null;
   }

   public AxisAlignedBB[] getClippingBounds(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      return defaultClippingBounds;
   }

   public static class Slot {
      public int slot;
      public List validTypeClasses = new ArrayList();
      public List validSizeClasses = new ArrayList();

      public Slot(int slot, PlantType[] typeClasses, PlantSize[] sizeClasses) {
         this.slot = slot;

         int i;
         for(i = 0; i < typeClasses.length; ++i) {
            this.validTypeClasses.add(typeClasses[i]);
         }

         for(i = 0; i < sizeClasses.length; ++i) {
            this.validSizeClasses.add(sizeClasses[i]);
         }

      }
   }
}
