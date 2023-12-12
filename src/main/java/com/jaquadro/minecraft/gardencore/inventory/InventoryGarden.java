package com.jaquadro.minecraft.gardencore.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryGarden implements IInventory {

    private IInventory parent;
    private Container container;

    public InventoryGarden(IInventory parentInventory, Container container) {
        this.parent = parentInventory;
        this.container = container;
    }

    public int getSizeInventory() {
        return this.parent.getSizeInventory();
    }

    public ItemStack getStackInSlot(int slot) {
        return this.parent.getStackInSlot(slot);
    }

    public ItemStack decrStackSize(int slot, int count) {
        ItemStack stack = this.parent.getStackInSlot(slot);
        if (stack == null) {
            return null;
        } else {
            int stackCount = stack.stackSize;
            ItemStack result = this.parent.decrStackSize(slot, count);
            ItemStack stackAfter = this.parent.getStackInSlot(slot);
            if (stack != stackAfter || stackCount != stackAfter.stackSize) {
                this.container.onCraftMatrixChanged(this);
            }

            return result;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        return this.parent.getStackInSlot(slot);
    }

    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.parent.setInventorySlotContents(slot, itemStack);
        this.container.onCraftMatrixChanged(this);
    }

    public String getInventoryName() {
        return this.parent.getInventoryName();
    }

    public boolean hasCustomInventoryName() {
        return this.parent.hasCustomInventoryName();
    }

    public int getInventoryStackLimit() {
        return this.parent.getInventoryStackLimit();
    }

    public void markDirty() {
        this.parent.markDirty();
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.parent.isUseableByPlayer(player);
    }

    public void openInventory() {
        this.parent.openInventory();
    }

    public void closeInventory() {
        this.parent.closeInventory();
    }

    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return this.parent.isItemValidForSlot(slot, itemStack);
    }
}
