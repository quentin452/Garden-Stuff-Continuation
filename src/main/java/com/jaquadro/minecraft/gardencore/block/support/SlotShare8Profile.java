package com.jaquadro.minecraft.gardencore.block.support;

import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotMapping;
import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotShareProfile;

public class SlotShare8Profile implements ISlotShareProfile {
   int indexBase;
   ISlotMapping[][] map;

   public SlotShare8Profile(int slotXZNN, int slotZN, int slotXZPN, int slotXP, int slotXZPP, int slotZP, int slotXZNP, int slotXN) {
      this.indexBase = this.min(slotXZNN, slotZN, slotXZPN, slotXP, slotXZPP, slotZP, slotXZNP, slotXN);
      int length = this.max(slotXZNN, slotZN, slotXZPN, slotXP, slotXZPP, slotZP, slotXZNP, slotXN) - this.indexBase + 1;
      this.map = new ISlotMapping[length][];
      this.map[slotZN - this.indexBase] = new ISlotMapping[]{new SlotMapping(slotZN, slotZP, 0, -1)};
      this.map[slotXP - this.indexBase] = new ISlotMapping[]{new SlotMapping(slotXP, slotXN, 1, 0)};
      this.map[slotZP - this.indexBase] = new ISlotMapping[]{new SlotMapping(slotZP, slotZN, 0, 1)};
      this.map[slotXN - this.indexBase] = new ISlotMapping[]{new SlotMapping(slotXN, slotXP, -1, 0)};
      this.map[slotXZNN - this.indexBase] = new SlotMapping[]{new SlotMapping(slotXZNN, slotXZPN, -1, 0), new SlotMapping(slotXZNN, slotXZNP, 0, -1), new SlotMapping(slotXZNN, slotXZPP, -1, -1)};
      this.map[slotXZPN - this.indexBase] = new SlotMapping[]{new SlotMapping(slotXZPN, slotXZNN, 1, 0), new SlotMapping(slotXZPN, slotXZPP, 0, -1), new SlotMapping(slotXZPN, slotXZNP, 1, -1)};
      this.map[slotXZPP - this.indexBase] = new SlotMapping[]{new SlotMapping(slotXZPP, slotXZNP, 1, 0), new SlotMapping(slotXZPP, slotXZPN, 0, 1), new SlotMapping(slotXZPP, slotXZNN, 1, 1)};
      this.map[slotXZNP - this.indexBase] = new SlotMapping[]{new SlotMapping(slotXZNP, slotXZPP, -1, 0), new SlotMapping(slotXZNP, slotXZNN, 0, 1), new SlotMapping(slotXZNP, slotXZPN, -1, 1)};
   }

   public ISlotMapping[] getNeighborsForSlot(int slot) {
      return slot >= this.indexBase && slot < this.indexBase + this.map.length ? this.map[slot - this.indexBase] : null;
   }

   private int min(int... values) {
      int minValue = Integer.MAX_VALUE;
      int[] var3 = values;
      int var4 = values.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int val = var3[var5];
         minValue = Math.min(minValue, val);
      }

      return minValue;
   }

   private int max(int... values) {
      int maxValue = Integer.MIN_VALUE;
      int[] var3 = values;
      int var4 = values.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int val = var3[var5];
         maxValue = Math.max(maxValue, val);
      }

      return maxValue;
   }
}
