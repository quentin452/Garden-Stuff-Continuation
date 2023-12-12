package com.jaquadro.minecraft.gardencontainers.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPotteryTable extends TileEntity implements IInventory {

    private ItemStack[] tableItemStacks = new ItemStack[15];
    private String customName;

    public int getSizeInventory() {
        return this.tableItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        return this.tableItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int count) {
        if (this.tableItemStacks[slot] != null) {
            ItemStack stack;
            if (this.tableItemStacks[slot].stackSize <= count) {
                stack = this.tableItemStacks[slot];
                this.tableItemStacks[slot] = null;
                this.markDirty();
                return stack;
            } else {
                stack = this.tableItemStacks[slot].splitStack(count);
                if (this.tableItemStacks[slot].stackSize == 0) {
                    this.tableItemStacks[slot] = null;
                }

                this.markDirty();
                return stack;
            }
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.tableItemStacks[slot] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.gardencontainers.potteryTable";
    }

    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        NBTTagList itemList = tag.getTagList("Items", 10);
        this.tableItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < itemList.tagCount(); ++i) {
            NBTTagCompound item = itemList.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");
            if (slot >= 0 && slot < this.tableItemStacks.length) {
                this.tableItemStacks[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }

        if (tag.hasKey("CustomName", 8)) {
            this.customName = tag.getString("CustomName");
        }

    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        NBTTagList itemList = new NBTTagList();

        for (int i = 0; i < this.tableItemStacks.length; ++i) {
            if (this.tableItemStacks[i] != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                this.tableItemStacks[i].writeToNBT(item);
                itemList.appendTag(item);
            }
        }

        tag.setTag("Items", itemList);
        if (this.hasCustomInventoryName()) {
            tag.setString("CustomName", this.customName);
        }

    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public static boolean isItemValidTool(ItemStack itemStack) {
        return false;
    }

    public static boolean isItemValidPot(ItemStack itemStack) {
        return false;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
            return false;
        } else {
            return player
                .getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D)
                <= 64.0D;
        }
    }

    public void openInventory() {}

    public void closeInventory() {}

    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        if (slot == 2) {
            return false;
        } else if (slot == 0) {
            return isItemValidTool(itemStack);
        } else {
            return slot == 1 ? isItemValidPot(itemStack) : true;
        }
    }
}
