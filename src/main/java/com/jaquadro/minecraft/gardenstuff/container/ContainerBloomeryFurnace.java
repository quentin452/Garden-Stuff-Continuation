package com.jaquadro.minecraft.gardenstuff.container;

import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityBloomeryFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ContainerBloomeryFurnace extends Container {

    private static final int InventoryX = 8;
    private static final int InventoryY = 84;
    private static final int HotbarY = 142;
    private TileEntityBloomeryFurnace tileFurnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    private Slot primarySlot;
    private Slot secondarySlot;
    private Slot fuelSlot;
    private Slot outputSlot;
    private List playerSlots;
    private List hotbarSlots;

    public ContainerBloomeryFurnace(InventoryPlayer inventory, TileEntityBloomeryFurnace tile) {
        this.tileFurnace = tile;
        this.primarySlot = this.addSlotToContainer(new Slot(tile, 0, 56, 17));
        this.secondarySlot = this.addSlotToContainer(new Slot(tile, 1, 35, 17));
        this.fuelSlot = this.addSlotToContainer(new Slot(tile, 2, 56, 53));
        this.outputSlot = this.addSlotToContainer(new SlotBloomeryOutput(inventory.player, tile, 3, 116, 35));
        this.playerSlots = new ArrayList();

        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.playerSlots
                    .add(this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)));
            }
        }

        this.hotbarSlots = new ArrayList();

        for (i = 0; i < 9; ++i) {
            this.hotbarSlots.add(this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142)));
        }

    }

    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.tileFurnace.furnaceCookTime);
        crafting.sendProgressBarUpdate(this, 1, this.tileFurnace.furnaceBurnTime);
        crafting.sendProgressBarUpdate(this, 2, this.tileFurnace.currentItemBurnTime);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafting = (ICrafting) this.crafters.get(i);
            if (this.lastCookTime != this.tileFurnace.furnaceCookTime) {
                crafting.sendProgressBarUpdate(this, 0, this.tileFurnace.furnaceCookTime);
            }

            if (this.lastBurnTime != this.tileFurnace.furnaceBurnTime) {
                crafting.sendProgressBarUpdate(this, 1, this.tileFurnace.furnaceBurnTime);
            }

            if (this.lastItemBurnTime != this.tileFurnace.currentItemBurnTime) {
                crafting.sendProgressBarUpdate(this, 2, this.tileFurnace.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.tileFurnace.furnaceCookTime;
        this.lastBurnTime = this.tileFurnace.furnaceBurnTime;
        this.lastItemBurnTime = this.tileFurnace.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        switch (id) {
            case 0:
                this.tileFurnace.furnaceCookTime = value;
                break;
            case 1:
                this.tileFurnace.furnaceBurnTime = value;
                break;
            case 2:
                this.tileFurnace.currentItemBurnTime = value;
        }

    }

    public boolean canInteractWith(EntityPlayer player) {
        return this.tileFurnace.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotIndex);
        int inventoryStart = ((Slot) this.playerSlots.get(0)).slotNumber;
        int hotbarStart = ((Slot) this.hotbarSlots.get(0)).slotNumber;
        int hotbarEnd = ((Slot) this.hotbarSlots.get(this.hotbarSlots.size() - 1)).slotNumber + 1;
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();
            if (slotIndex == this.outputSlot.slotNumber) {
                if (!this.mergeItemStack(slotStack, inventoryStart, hotbarEnd, true)) {
                    return null;
                }

                slot.onSlotChange(slotStack, itemStack);
            } else if (slotIndex >= inventoryStart && slotIndex < hotbarEnd) {
                boolean merged = false;
                if (TileEntityBloomeryFurnace.isItemFuel(slotStack)) {
                    merged = this
                        .mergeItemStack(slotStack, this.fuelSlot.slotNumber, this.fuelSlot.slotNumber + 1, false);
                } else if (TileEntityBloomeryFurnace.isItemPrimaryInput(slotStack)) {
                    merged = this
                        .mergeItemStack(slotStack, this.primarySlot.slotNumber, this.primarySlot.slotNumber + 1, false);
                } else if (TileEntityBloomeryFurnace.isItemSecondaryInput(slotStack)) {
                    merged = this.mergeItemStack(
                        slotStack,
                        this.secondarySlot.slotNumber,
                        this.secondarySlot.slotNumber + 1,
                        false);
                }

                if (!merged) {
                    if (slotIndex >= inventoryStart && slotIndex < hotbarStart) {
                        if (!this.mergeItemStack(slotStack, hotbarStart, hotbarEnd, false)) {
                            return null;
                        }
                    } else if (slotIndex >= hotbarStart && slotIndex < hotbarEnd
                        && !this.mergeItemStack(slotStack, inventoryStart, hotbarStart, false)) {
                            return null;
                        }
                }
            } else if (!this.mergeItemStack(slotStack, inventoryStart, hotbarEnd, false)) {
                return null;
            }

            if (slotStack.stackSize == 0) {
                slot.putStack((ItemStack) null);
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
