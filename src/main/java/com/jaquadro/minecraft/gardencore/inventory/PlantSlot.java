package com.jaquadro.minecraft.gardencore.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class PlantSlot extends Slot {

    public PlantSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) {
        super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
    }

    public boolean isItemValid(ItemStack itemStack) {
        return this.inventory.isItemValidForSlot(this.getSlotIndex(), itemStack);
    }
}
