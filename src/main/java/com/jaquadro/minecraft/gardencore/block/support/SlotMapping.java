package com.jaquadro.minecraft.gardencore.block.support;

import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotMapping;

public class SlotMapping implements ISlotMapping {
   public int slot;
   public int mappedSlot;
   public int mappedX;
   public int mappedZ;

   public SlotMapping(int slot, int mappedSlot, int mappedX, int mappedY) {
      this.slot = slot;
      this.mappedSlot = mappedSlot;
      this.mappedX = mappedX;
      this.mappedZ = mappedY;
   }

   public int getSlot() {
      return this.slot;
   }

   public int getMappedSlot() {
      return this.mappedSlot;
   }

   public int getMappedX() {
      return this.mappedX;
   }

   public int getMappedZ() {
      return this.mappedZ;
   }
}
