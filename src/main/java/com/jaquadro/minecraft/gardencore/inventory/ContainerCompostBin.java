package com.jaquadro.minecraft.gardencore.inventory;

import com.jaquadro.minecraft.gardencore.block.BlockCompostBin;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityCompostBin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCompostBin extends Container {
   private static final int InventoryX = 8;
   private static final int InventoryY = 84;
   private static final int HotbarY = 142;
   private TileEntityCompostBin tileCompost;
   private int lastDecompTime;
   private int lastItemDecompTime;
   private int lastDecompCount;
   private Slot outputSlot;
   private List compostSlots;
   private List playerSlots;
   private List hotbarSlots;

   public ContainerCompostBin(InventoryPlayer inventory, TileEntityCompostBin tileEntity) {
      this.tileCompost = tileEntity;
      this.compostSlots = new ArrayList();

      int i;
      int j;
      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 3; ++j) {
            this.compostSlots.add(this.addSlotToContainer(new SlotCompost(tileEntity, j + i * 3, 30 + j * 18, 17 + i * 18)));
         }
      }

      this.outputSlot = this.addSlotToContainer(new SlotCompostOutput(inventory.player, tileEntity, 9, 123, 34));
      this.playerSlots = new ArrayList();

      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 9; ++j) {
            this.playerSlots.add(this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)));
         }
      }

      this.hotbarSlots = new ArrayList();

      for(i = 0; i < 9; ++i) {
         this.hotbarSlots.add(this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142)));
      }

   }

   public void addCraftingToCrafters(ICrafting crafting) {
      super.addCraftingToCrafters(crafting);
      crafting.sendProgressBarUpdate(this, 0, this.tileCompost.getDecompTime());
      crafting.sendProgressBarUpdate(this, 1, this.tileCompost.getCurrentItemDecompTime());
      crafting.sendProgressBarUpdate(this, 2, this.tileCompost.itemDecomposeCount);
   }

   public void detectAndSendChanges() {
      super.detectAndSendChanges();

      for(int i = 0; i < this.crafters.size(); ++i) {
         ICrafting crafting = (ICrafting)this.crafters.get(i);
         if (this.lastDecompTime != this.tileCompost.getDecompTime()) {
            crafting.sendProgressBarUpdate(this, 0, this.tileCompost.getDecompTime());
         }

         if (this.lastItemDecompTime != this.tileCompost.getCurrentItemDecompTime()) {
            crafting.sendProgressBarUpdate(this, 1, this.tileCompost.getCurrentItemDecompTime());
         }

         if (this.lastDecompCount != this.tileCompost.itemDecomposeCount) {
            crafting.sendProgressBarUpdate(this, 2, this.tileCompost.itemDecomposeCount);
         }
      }

      this.lastDecompTime = this.tileCompost.getDecompTime();
      this.lastItemDecompTime = this.tileCompost.getCurrentItemDecompTime();
      this.lastDecompCount = this.tileCompost.itemDecomposeCount;
   }

   @SideOnly(Side.CLIENT)
   public void updateProgressBar(int id, int value) {
      if (id == 0) {
         this.tileCompost.binDecomposeTime = value;
      }

      if (id == 1) {
         this.tileCompost.currentItemDecomposeTime = value;
      }

      if (id == 2) {
         this.tileCompost.itemDecomposeCount = value;
      }

   }

   public boolean canInteractWith(EntityPlayer player) {
      return this.tileCompost.isUseableByPlayer(player);
   }

   public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
      ItemStack itemStack = null;
      Slot slot = (Slot)this.inventorySlots.get(slotIndex);
      int compostStart = ((Slot)this.compostSlots.get(0)).slotNumber;
      int compostEnd = ((Slot)this.compostSlots.get(this.compostSlots.size() - 1)).slotNumber + 1;
      int inventoryStart = ((Slot)this.playerSlots.get(0)).slotNumber;
      int hotbarStart = ((Slot)this.hotbarSlots.get(0)).slotNumber;
      int hotbarEnd = ((Slot)this.hotbarSlots.get(this.hotbarSlots.size() - 1)).slotNumber + 1;
      if (slot != null && slot.getHasStack()) {
         ItemStack slotStack = slot.getStack();
         itemStack = slotStack.copy();
         if (slotIndex == this.outputSlot.slotNumber) {
            if (!this.mergeItemStack(slotStack, inventoryStart, hotbarEnd, true)) {
               return null;
            }

            slot.onSlotChange(slotStack, itemStack);
            BlockCompostBin.updateBlockState(this.tileCompost.getWorldObj(), this.tileCompost.xCoord, this.tileCompost.yCoord, this.tileCompost.zCoord);
         } else if (slotIndex >= inventoryStart && slotIndex < hotbarEnd) {
            if (!TileEntityCompostBin.isItemDecomposable(slotStack) || !this.mergeItemStack(slotStack, compostStart, compostEnd, false)) {
               if (slotIndex >= inventoryStart && slotIndex < hotbarStart) {
                  if (!this.mergeItemStack(slotStack, hotbarStart, hotbarEnd, false)) {
                     return null;
                  }
               } else if (slotIndex >= hotbarStart && slotIndex < hotbarEnd && !this.mergeItemStack(slotStack, inventoryStart, hotbarStart, false)) {
                  return null;
               }
            }
         } else if (!this.mergeItemStack(slotStack, inventoryStart, hotbarEnd, false)) {
            return null;
         }

         if (slotStack.stackSize == 0) {
            slot.putStack((ItemStack)null);
         } else {
            slot.onSlotChanged();
         }

         if (slotStack.stackSize == itemStack.stackSize) {
            return null;
         }

         slot.onPickupFromSlot(player, slotStack);
      }

      return itemStack;
   }
}
