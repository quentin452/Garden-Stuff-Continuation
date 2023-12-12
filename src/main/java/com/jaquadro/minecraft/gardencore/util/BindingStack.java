package com.jaquadro.minecraft.gardencore.util;

import net.minecraft.world.World;
import scala.Array;

public class BindingStack {
   private int[] slotStack = new int[16];
   private int[] dataStack = new int[16];
   int index = -1;
   int defaultMeta = 0;

   public void setDefaultMeta(int defaultMeta) {
      this.defaultMeta = defaultMeta;
   }

   public void bind(World world, int x, int y, int z, int slot, int data) {
      if (++this.index >= this.slotStack.length) {
         this.growStack();
      }

      this.slotStack[this.index] = slot;
      this.dataStack[this.index] = data;
      world.setBlockMetadataWithNotify(x, y, z, data, 0);
   }

   public void unbind(World world, int x, int y, int z) {
      if (this.index >= 0) {
         --this.index;
      }

      world.setBlockMetadataWithNotify(x, y, z, this.getData(), 0);
   }

   public void softUnbind() {
      if (this.index >= 0) {
         --this.index;
      }

   }

   public void refreshWorld(World world, int x, int y, int z) {
      if (this.index >= 0) {
         world.setBlockMetadataWithNotify(x, y, z, this.getData(), 0);
      }

   }

   public int getSlot() {
      return this.index >= 0 ? this.slotStack[this.index] : -1;
   }

   public int getData() {
      return this.index >= 0 ? this.dataStack[this.index] : this.defaultMeta;
   }

   private void growStack() {
      int[] newSlotStack = new int[this.slotStack.length * 2];
      int[] newDataStack = new int[this.dataStack.length * 2];
      Array.copy(this.slotStack, 0, newSlotStack, 0, this.slotStack.length);
      Array.copy(this.dataStack, 0, newDataStack, 0, this.dataStack.length);
      this.slotStack = newSlotStack;
      this.dataStack = newDataStack;
   }
}
