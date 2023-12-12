package com.jaquadro.minecraft.gardencore.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCompostOutput extends Slot {

    private final IInventory inputInventory;
    private EntityPlayer player;
    private int amountCrafted;

    public SlotCompostOutput(EntityPlayer player, IInventory inputInventory, int par2, int par3, int par4) {
        super(inputInventory, par2, par3, par4);
        this.player = player;
        this.inputInventory = inputInventory;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    public ItemStack decrStackSize(int count) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(count, this.getStack().stackSize);
        }

        return super.decrStackSize(count);
    }

    protected void onCrafting(ItemStack itemStack, int count) {
        this.amountCrafted += count;
        super.onCrafting(itemStack, count);
    }

    protected void onCrafting(ItemStack itemStack) {
        itemStack.onCrafting(this.player.worldObj, this.player, this.amountCrafted);
        this.amountCrafted = 0;
    }

    public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
        this.onCrafting(itemStack);
        super.onPickupFromSlot(player, itemStack);
    }
}
