package com.jaquadro.minecraft.gardencore.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.machine.ICompostMaterial;
import com.jaquadro.minecraft.gardencore.block.BlockCompostBin;
import com.jaquadro.minecraft.gardencore.core.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCompostBin extends TileEntity implements ISidedInventory {

    private ItemStack[] compostItemStacks = new ItemStack[10];
    public int binDecomposeTime;
    private int currentItemSlot;
    public int currentItemDecomposeTime;
    public int itemDecomposeCount;
    private String customName;
    private int[] accessSlots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    public int getDecompTime() {
        return this.binDecomposeTime;
    }

    public int getCurrentItemDecompTime() {
        return this.currentItemDecomposeTime;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        NBTTagList tagList = tag.getTagList("Items", 10);
        this.compostItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
            byte slot = itemTag.getByte("Slot");
            if (slot >= 0 && slot < this.compostItemStacks.length) {
                this.compostItemStacks[slot] = ItemStack.loadItemStackFromNBT(itemTag);
            }
        }

        this.binDecomposeTime = tag.getShort("DecompTime");
        this.currentItemSlot = tag.getByte("DecompSlot");
        this.itemDecomposeCount = tag.getByte("DecompCount");
        if (this.currentItemSlot >= 0) {
            this.currentItemDecomposeTime = getItemDecomposeTime(this.compostItemStacks[this.currentItemSlot]);
        } else {
            this.currentItemDecomposeTime = 0;
        }

        if (tag.hasKey("CustomName", 8)) {
            this.customName = tag.getString("CustomName");
        }

    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setShort("DecompTime", (short) this.binDecomposeTime);
        tag.setByte("DecompSlot", (byte) this.currentItemSlot);
        tag.setByte("DecompCount", (byte) this.itemDecomposeCount);
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < this.compostItemStacks.length; ++i) {
            if (this.compostItemStacks[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                this.compostItemStacks[i].writeToNBT(itemTag);
                tagList.appendTag(itemTag);
            }
        }

        tag.setTag("Items", tagList);
        if (this.hasCustomInventoryName()) {
            tag.setString("CustomName", this.customName);
        }

    }

    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 5, tag);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
        this.getWorldObj()
            .func_147479_m(this.xCoord, this.yCoord, this.zCoord);
    }

    public boolean isDecomposing() {
        return this.binDecomposeTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public int getDecomposeTimeRemainingScaled(int scale) {
        if (this.currentItemDecomposeTime == 0) {
            this.currentItemDecomposeTime = 200;
        }

        return (8 - this.itemDecomposeCount) * scale / 9
            + this.binDecomposeTime * scale / (this.currentItemDecomposeTime * 9);
    }

    public void updateEntity() {
        boolean isDecomposing = this.binDecomposeTime > 0;
        int decompCount = this.itemDecomposeCount;
        boolean shouldUpdate = false;
        if (this.binDecomposeTime > 0) {
            --this.binDecomposeTime;
        }

        if (!this.worldObj.isRemote) {
            int filledSlotCount = 0;

            for (int i = 0; i < 9; ++i) {
                filledSlotCount += this.compostItemStacks[i] != null ? 1 : 0;
            }

            if ((this.binDecomposeTime != 0 || filledSlotCount > 0) && this.binDecomposeTime == 0) {
                if (this.canCompost()) {
                    this.compostItem();
                    shouldUpdate = true;
                }

                this.currentItemSlot = this.selectRandomFilledSlot();
                this.currentItemDecomposeTime = 0;
                if (this.currentItemSlot >= 0
                    && (this.compostItemStacks[9] == null || this.compostItemStacks[9].stackSize < 64)) {
                    this.currentItemDecomposeTime = getItemDecomposeTime(this.compostItemStacks[this.currentItemSlot]);
                    this.binDecomposeTime = this.currentItemDecomposeTime;
                    if (this.binDecomposeTime > 0) {
                        shouldUpdate = true;
                    }
                }
            }

            if (isDecomposing != this.binDecomposeTime > 0 || decompCount != this.itemDecomposeCount) {
                shouldUpdate = true;
                BlockCompostBin.updateBlockState(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (shouldUpdate) {
            this.markDirty();
        }

    }

    private boolean canCompost() {
        if (this.currentItemSlot == -1) {
            return false;
        } else if (this.compostItemStacks[this.currentItemSlot] == null) {
            return false;
        } else if (this.compostItemStacks[this.currentItemSlot].stackSize == 0) {
            return false;
        } else if (this.compostItemStacks[9] == null) {
            return true;
        } else {
            int result = this.compostItemStacks[9].stackSize + 1;
            return result <= this.getInventoryStackLimit() && result <= this.compostItemStacks[9].getMaxStackSize();
        }
    }

    public void compostItem() {
        if (this.canCompost()) {
            if (this.itemDecomposeCount < 8) {
                ++this.itemDecomposeCount;
            }

            if (this.itemDecomposeCount == 8) {
                ItemStack resultStack = new ItemStack(ModItems.compostPile);
                if (this.compostItemStacks[9] == null) {
                    this.compostItemStacks[9] = resultStack;
                } else if (this.compostItemStacks[9].getItem() == resultStack.getItem()) {
                    ItemStack var10000 = this.compostItemStacks[9];
                    var10000.stackSize += resultStack.stackSize;
                }

                this.itemDecomposeCount = 0;
            }

            --this.compostItemStacks[this.currentItemSlot].stackSize;
            if (this.compostItemStacks[this.currentItemSlot].stackSize == 0) {
                this.compostItemStacks[this.currentItemSlot] = null;
            }

            this.currentItemSlot = -1;
        }

    }

    public boolean hasInputItems() {
        int filledSlotCount = 0;

        for (int i = 0; i < 9; ++i) {
            filledSlotCount += this.compostItemStacks[i] != null ? 1 : 0;
        }

        return filledSlotCount > 0;
    }

    public boolean hasOutputItems() {
        return this.compostItemStacks[9] != null && this.compostItemStacks[9].stackSize > 0;
    }

    private int selectRandomFilledSlot() {
        int filledSlotCount = 0;

        int index;
        for (index = 0; index < 9; ++index) {
            filledSlotCount += this.compostItemStacks[index] != null ? 1 : 0;
        }

        if (filledSlotCount == 0) {
            return -1;
        } else {
            index = this.worldObj.rand.nextInt(filledSlotCount);
            int i = 0;

            for (int var4 = 0; i < 9; ++i) {
                if (this.compostItemStacks[i] != null && var4++ == index) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int getItemDecomposeTime(ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        } else {
            ICompostMaterial material = GardenAPI.instance()
                .registries()
                .compost()
                .getCompostMaterialInfo(itemStack);
            return material == null ? 0 : material.getDecomposeTime();
        }
    }

    public static boolean isItemDecomposable(ItemStack itemStack) {
        return getItemDecomposeTime(itemStack) > 0;
    }

    public int getSizeInventory() {
        return this.compostItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        return this.compostItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int count) {
        ItemStack returnStack = null;
        if (this.compostItemStacks[slot] != null) {
            if (this.compostItemStacks[slot].stackSize <= count) {
                returnStack = this.compostItemStacks[slot];
                this.compostItemStacks[slot] = null;
            } else {
                returnStack = this.compostItemStacks[slot].splitStack(count);
                if (this.compostItemStacks[slot].stackSize == 0) {
                    this.compostItemStacks[slot] = null;
                }
            }
        }

        if (slot == 9 && this.compostItemStacks[slot] == null) {
            BlockCompostBin.updateBlockState(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        }

        return returnStack;
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.compostItemStacks[slot] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

    }

    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.gardencore.compostBin";
    }

    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public int getInventoryStackLimit() {
        return 64;
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

    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot >= 0 && slot < 9 ? isItemDecomposable(item) : false;
    }

    public int[] getAccessibleSlotsFromSide(int side) {
        return this.accessSlots;
    }

    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return slot == 9 ? false : this.isItemValidForSlot(slot, item);
    }

    public boolean canExtractItem(int slot, ItemStack item, int side) {
        if (slot != 9) {
            return false;
        } else if (item == null) {
            return false;
        } else {
            return item.getItem() == ModItems.compostPile;
        }
    }
}
