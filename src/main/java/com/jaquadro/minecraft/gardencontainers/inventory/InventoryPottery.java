package com.jaquadro.minecraft.gardencontainers.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryPottery implements IInventory {

    private IInventory parent;
    private Container container;

    public InventoryPottery(IInventory parentInventory, Container container) {
        this.parent = parentInventory;
        this.container = container;
    }

    public int getSizeInventory() {
        return this.parent.getSizeInventory();
    }

    public ItemStack getStackInSlot(int var1) {
        return this.parent.getStackInSlot(var1);
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

    public ItemStack getStackInSlotOnClosing(int var1) {
        return this.parent.getStackInSlotOnClosing(var1);
    }

    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.parent.setInventorySlotContents(var1, var2);
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

    public boolean isUseableByPlayer(EntityPlayer var1) {
        return this.parent.isUseableByPlayer(var1);
    }

    public void openInventory() {
        this.parent.openInventory();
    }

    public void closeInventory() {
        this.parent.closeInventory();
    }

    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return this.parent.isItemValidForSlot(var1, var2);
    }
}
