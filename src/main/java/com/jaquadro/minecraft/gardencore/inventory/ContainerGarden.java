package com.jaquadro.minecraft.gardencore.inventory;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGarden extends Container {
   private static final int InventoryX = 8;
   private static final int InventoryY = 122;
   private static final int HotbarY = 180;
   private static final int StorageX = 44;
   private static final int StorageY = 18;
   private static final int SubstrateX = 17;
   private static final int SubstrateY = 86;
   private static final int CoverX = 143;
   private static final int CoverY = 86;
   private static final int[] storageSlotIndex = new int[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
   private static final int[] storageXIndex = new int[]{2, 1, 3, 1, 3, 0, 2, 4, 4, 4, 2, 0, 0};
   private static final int[] storageYIndex = new int[]{2, 1, 1, 3, 3, 0, 0, 0, 2, 4, 4, 4, 2};
   private IInventory gardenInventory;
   private Slot substrateSlot;
   private Slot coverSlot;
   private List storageSlots;
   private List playerSlots;
   private List hotbarSlots;

   public ContainerGarden(InventoryPlayer inventory, TileEntityGarden tileEntity) {
      this.gardenInventory = new InventoryGarden(tileEntity, this);
      this.coverSlot = this.addSlotToContainer(new Slot(this.gardenInventory, 1, 143, 86));
      this.storageSlots = new ArrayList();

      int i;
      for(i = 0; i < storageSlotIndex.length; ++i) {
         if (tileEntity.isSlotValid(storageSlotIndex[i])) {
            this.storageSlots.add(this.addSlotToContainer(new PlantSlot(this.gardenInventory, storageSlotIndex[i], 44 + 18 * storageXIndex[i], 18 + 18 * storageYIndex[i])));
         }
      }

      this.playerSlots = new ArrayList();

      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.playerSlots.add(this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 122 + i * 18)));
         }
      }

      this.hotbarSlots = new ArrayList();

      for(i = 0; i < 9; ++i) {
         this.hotbarSlots.add(this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 180)));
      }

   }

   public boolean canInteractWith(EntityPlayer player) {
      return this.gardenInventory.isUseableByPlayer(player);
   }

   public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
      ItemStack itemStack = null;
      Slot slot = (Slot)this.inventorySlots.get(slotIndex);
      int inventoryStart = ((Slot)this.playerSlots.get(0)).slotNumber;
      int hotbarStart = ((Slot)this.hotbarSlots.get(0)).slotNumber;
      int hotbarEnd = ((Slot)this.hotbarSlots.get(this.hotbarSlots.size() - 1)).slotNumber + 1;
      if (slot != null && slot.getHasStack()) {
         ItemStack slotStack = slot.getStack();
         itemStack = slotStack.copy();
         if (slotIndex >= inventoryStart && slotIndex < hotbarEnd) {
            if (slotIndex >= inventoryStart && slotIndex < hotbarStart) {
               if (!this.mergeItemStack(slotStack, hotbarStart, hotbarEnd, false)) {
                  return null;
               }
            } else if (slotIndex >= hotbarStart && slotIndex < hotbarEnd && !this.mergeItemStack(slotStack, inventoryStart, hotbarStart, false)) {
               return null;
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
