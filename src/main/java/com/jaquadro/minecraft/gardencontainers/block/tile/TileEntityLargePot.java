package com.jaquadro.minecraft.gardencontainers.block.tile;

import com.jaquadro.minecraft.gardencontainers.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityLargePot extends TileEntityGarden {
   private int carving;

   protected int containerSlotCount() {
      return ModBlocks.largePot.getSlotProfile().getPlantSlots().length;
   }

   public int getCarving() {
      return this.carving;
   }

   public void setCarving(int id) {
      this.carving = id;
   }

   public void writeToNBT(NBTTagCompound tag) {
      super.writeToNBT(tag);
      if (this.carving != 0) {
         tag.setShort("Carv", (short)this.carving);
      }

   }

   public void readFromNBT(NBTTagCompound tag) {
      super.readFromNBT(tag);
      this.carving = tag.hasKey("Carv") ? tag.getShort("Carv") : 0;
   }
}
