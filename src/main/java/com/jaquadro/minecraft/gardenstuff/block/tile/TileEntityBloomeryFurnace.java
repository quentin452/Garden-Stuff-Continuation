package com.jaquadro.minecraft.gardenstuff.block.tile;

import com.jaquadro.minecraft.gardenstuff.block.BlockBloomeryFurnace;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import com.jaquadro.minecraft.gardenstuff.core.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBloomeryFurnace extends TileEntity implements ISidedInventory {

    private static final int SLOT_PRIMARY = 0;
    private static final int SLOT_SECONDARY = 1;
    private static final int SLOT_FUEL = 2;
    private static final int SLOT_OUTPUT = 3;
    private static final int[] slots = new int[] { 0, 1, 2, 3 };
    private ItemStack[] furnaceItemStacks = new ItemStack[4];
    private String customName;
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        NBTTagList list = tag.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        int i = 0;

        for (int n = list.tagCount(); i < n; ++i) {
            NBTTagCompound itemTag = list.getCompoundTagAt(i);
            byte slot = itemTag.getByte("Slot");
            if (slot >= 0 && slot < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[slot] = ItemStack.loadItemStackFromNBT(itemTag);
            }
        }

        this.furnaceBurnTime = tag.getShort("BurnTime");
        this.furnaceCookTime = tag.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[2]);
        if (tag.hasKey("CustomName", 8)) {
            this.customName = tag.getString("CustomName");
        }

    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setShort("BurnTime", (short) this.furnaceBurnTime);
        tag.setShort("CookTime", (short) this.furnaceCookTime);
        NBTTagList list = new NBTTagList();
        int i = 0;

        for (int n = this.furnaceItemStacks.length; i < n; ++i) {
            if (this.furnaceItemStacks[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(itemTag);
                list.appendTag(itemTag);
            }
        }

        tag.setTag("Items", list);
        if (this.hasCustomInventoryName()) {
            tag.setString("CustomName", this.customName);
        }

    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int ceiling) {
        return ceiling * this.furnaceCookTime / 200;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int ceiling) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }

        return ceiling * this.furnaceBurnTime / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    public void updateEntity() {
        boolean hasPrevBurnTime = this.furnaceBurnTime > 0;
        boolean isDirty = false;
        if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote) {
            if (this.furnaceBurnTime != 0 || this.furnaceItemStacks[2] != null && this.furnaceItemStacks[0] != null
                && this.furnaceItemStacks[1] != null) {
                if (this.furnaceBurnTime == 0 && this.canSmelt()) {
                    this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[2]);
                    if (this.furnaceBurnTime > 0) {
                        isDirty = true;
                        if (this.furnaceItemStacks[2] != null) {
                            --this.furnaceItemStacks[2].stackSize;
                            if (this.furnaceItemStacks[2].stackSize == 0) {
                                this.furnaceItemStacks[2] = this.furnaceItemStacks[2].getItem()
                                    .getContainerItem(this.furnaceItemStacks[2]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    ++this.furnaceCookTime;
                    if (this.furnaceCookTime == 200) {
                        this.furnaceCookTime = 0;
                        this.smeltItem();
                        isDirty = true;
                    }
                } else {
                    this.furnaceCookTime = 0;
                }
            }

            if (hasPrevBurnTime != this.furnaceBurnTime > 0) {
                isDirty = true;
                BlockBloomeryFurnace.updateFurnaceBlockState(
                    this.worldObj,
                    this.xCoord,
                    this.yCoord,
                    this.zCoord,
                    this.furnaceBurnTime > 0);
            }
        }

        if (isDirty) {
            this.markDirty();
        }

    }

    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] != null && this.furnaceItemStacks[1] != null) {
            if (!isItemPrimaryInput(this.furnaceItemStacks[0])) {
                return false;
            } else if (!isItemSecondaryInput(this.furnaceItemStacks[1])) {
                return false;
            } else {
                ItemStack itemOutput = new ItemStack(ModItems.wroughtIronIngot);
                if (this.furnaceItemStacks[3] == null) {
                    return true;
                } else if (!this.furnaceItemStacks[3].isItemEqual(itemOutput)) {
                    return false;
                } else {
                    int result = this.furnaceItemStacks[3].stackSize + itemOutput.stackSize;
                    return result <= this.getInventoryStackLimit()
                        && result <= this.furnaceItemStacks[3].getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemOutput = new ItemStack(ModItems.wroughtIronIngot);
            if (this.furnaceItemStacks[3] == null) {
                this.furnaceItemStacks[3] = itemOutput.copy();
            } else if (this.furnaceItemStacks[3].getItem() == itemOutput.getItem()) {
                ItemStack var10000 = this.furnaceItemStacks[3];
                var10000.stackSize += itemOutput.stackSize;
            }

            --this.furnaceItemStacks[0].stackSize;
            --this.furnaceItemStacks[1].stackSize;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }

            if (this.furnaceItemStacks[1].stackSize <= 0) {
                this.furnaceItemStacks[1] = null;
            }

        }
    }

    public static int getItemBurnTime(ItemStack stack) {
        if (stack == null) {
            return 0;
        } else {
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                Block block = Block.getBlockFromItem(item);
                if (block == ModBlocks.stoneBlock && stack.getItemDamage() == 0) {
                    return ModBlocks.stoneBlock.getBurnTime(stack);
                }
            }

            return item == Items.coal && stack.getItemDamage() == 1 ? 1600 : 0;
        }
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getItemBurnTime(stack) > 0;
    }

    public static boolean isItemPrimaryInput(ItemStack stack) {
        if (stack == null) {
            return false;
        } else if (stack.getItem() == Items.iron_ingot) {
            return true;
        } else {
            return stack.getItem() == Item.getItemFromBlock(Blocks.iron_ore);
        }
    }

    public static boolean isItemSecondaryInput(ItemStack stack) {
        if (stack == null) {
            return false;
        } else {
            return stack.getItem() == Item.getItemFromBlock(Blocks.sand);
        }
    }

    public int[] getAccessibleSlotsFromSide(int side) {
        return slots;
    }

    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return this.isItemValidForSlot(slot, stack);
    }

    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == 3;
    }

    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        return this.furnaceItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int count) {
        if (this.furnaceItemStacks[slot] != null) {
            ItemStack stack;
            if (this.furnaceItemStacks[slot].stackSize <= count) {
                stack = this.furnaceItemStacks[slot];
                this.furnaceItemStacks[slot] = null;
                return stack;
            } else {
                stack = this.furnaceItemStacks[slot].splitStack(count);
                if (this.furnaceItemStacks[slot].stackSize == 0) {
                    this.furnaceItemStacks[slot] = null;
                }

                return stack;
            }
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.furnaceItemStacks[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

    }

    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.gardenstuff.bloomeryFurnace";
    }

    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player
            .getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D)
            <= 64.0D;
    }

    public void openInventory() {}

    public void closeInventory() {}

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 3) {
            return false;
        } else if (slot == 2) {
            return isItemFuel(stack);
        } else if (slot == 0) {
            return isItemPrimaryInput(stack);
        } else {
            return slot == 1 ? isItemSecondaryInput(stack) : false;
        }
    }
}
