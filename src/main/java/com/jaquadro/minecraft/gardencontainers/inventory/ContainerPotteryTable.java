package com.jaquadro.minecraft.gardencontainers.inventory;

import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityPotteryTable;
import com.jaquadro.minecraft.gardencontainers.core.ModBlocks;
import com.jaquadro.minecraft.gardencontainers.item.crafting.PotteryManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ContainerPotteryTable extends Container {

    private static final int InventoryX = 10;
    private static final int InventoryY = 140;
    private static final int HotbarY = 198;
    private static final int StorageX1 = 26;
    private static final int StorageY1 = 18;
    private static final int StorageX2 = 138;
    private static final int StorageY2 = 18;
    private IInventory tableInventory;
    private IInventory craftResult = new InventoryCraftResult();
    private Slot inputSlot1;
    private Slot inputSlot2;
    private Slot outputSlot;
    private List storageSlots;
    private List playerSlots;
    private List hotbarSlots;

    public ContainerPotteryTable(InventoryPlayer inventory, TileEntityPotteryTable tileEntity) {
        this.tableInventory = new InventoryPottery(tileEntity, this);
        this.inputSlot1 = this.addSlotToContainer(new Slot(this.tableInventory, 0, 50, 44));
        this.inputSlot2 = this.addSlotToContainer(new Slot(this.tableInventory, 1, 50, 80));
        this.outputSlot = this
            .addSlotToContainer(new SlotPottery(inventory.player, this.tableInventory, this.craftResult, 2, 110, 62));
        this.storageSlots = new ArrayList();

        int i;
        for (i = 0; i < 6; ++i) {
            this.storageSlots.add(this.addSlotToContainer(new Slot(this.tableInventory, 3 + i, 26, 18 + i * 18)));
        }

        for (i = 0; i < 6; ++i) {
            this.storageSlots.add(this.addSlotToContainer(new Slot(this.tableInventory, 9 + i, 138, 18 + i * 18)));
        }

        this.playerSlots = new ArrayList();

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.playerSlots
                    .add(this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 10 + j * 18, 140 + i * 18)));
            }
        }

        this.hotbarSlots = new ArrayList();

        for (i = 0; i < 9; ++i) {
            this.hotbarSlots.add(this.addSlotToContainer(new Slot(inventory, i, 10 + i * 18, 198)));
        }

    }

    public boolean canInteractWith(EntityPlayer player) {
        return this.tableInventory.isUseableByPlayer(player);
    }

    public void onCraftMatrixChanged(IInventory inventory) {
        ItemStack pattern = this.tableInventory.getStackInSlot(0);
        ItemStack target = this.tableInventory.getStackInSlot(1);
        if (target != null && target.getItem() == Item.getItemFromBlock(Blocks.clay)) {
            if (pattern == null) {
                this.craftResult.setInventorySlotContents(0, new ItemStack(ModBlocks.largePot, 1, 1));
                return;
            }

            if (PotteryManager.instance()
                .isRegisteredPattern(pattern)) {
                target = new ItemStack(ModBlocks.largePot, target.stackSize, 1);
            }
        }

        if (pattern != null && pattern.getItem() == Items.dye
            && target != null
            && target.getItem() == Item.getItemFromBlock(ModBlocks.largePot)
            && (target.getItemDamage() & 15) == 0) {
            this.craftResult.setInventorySlotContents(
                0,
                new ItemStack(ModBlocks.largePotColored, 1, target.getItemDamage() | pattern.getItemDamage()));
        } else {
            this.craftResult.setInventorySlotContents(
                0,
                PotteryManager.instance()
                    .getStampResult(pattern, target));
        }
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
                if (slotIndex >= inventoryStart && slotIndex < hotbarStart) {
                    if (!this.mergeItemStack(slotStack, hotbarStart, hotbarEnd, false)) {
                        return null;
                    }
                } else if (slotIndex >= hotbarStart && slotIndex < hotbarEnd
                    && !this.mergeItemStack(slotStack, inventoryStart, hotbarStart, false)) {
                        return null;
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
