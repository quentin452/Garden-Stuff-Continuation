package com.jaquadro.minecraft.gardencontainers.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import cpw.mods.fml.common.FMLCommonHandler;

public class SlotPottery extends Slot {

    private final IInventory inputInventory;
    private EntityPlayer player;
    private int amountCrafted;

    public SlotPottery(EntityPlayer player, IInventory inputInventory, IInventory inventory, int par2, int par3,
        int par4) {
        super(inventory, par2, par3, par4);
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
        FMLCommonHandler.instance()
            .firePlayerCraftingEvent(player, itemStack, this.inputInventory);
        this.onCrafting(itemStack);
        ItemStack itemTarget = this.inputInventory.getStackInSlot(1);
        ItemStack itemContainer;
        if (itemTarget != null) {
            this.inputInventory.decrStackSize(1, 1);
            if (itemTarget.getItem()
                .hasContainerItem(itemTarget)) {
                itemContainer = itemTarget.getItem()
                    .getContainerItem(itemTarget);
                if (itemContainer != null && itemContainer.isItemStackDamageable()
                    && itemContainer.getItemDamage() > itemContainer.getMaxDamage()) {
                    MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(this.player, itemContainer));
                    return;
                }

                if (!itemTarget.getItem()
                    .doesContainerItemLeaveCraftingGrid(itemTarget)
                    || !this.player.inventory.addItemStackToInventory(itemContainer)) {
                    if (this.inputInventory.getStackInSlot(1) == null) {
                        this.inputInventory.setInventorySlotContents(1, itemContainer);
                    } else {
                        this.player.dropPlayerItemWithRandomChoice(itemContainer, false);
                    }
                }
            }
        }

        itemContainer = this.inputInventory.getStackInSlot(0);
        if (itemContainer != null && itemContainer.getItem() == Items.dye) {
            this.inputInventory.decrStackSize(0, 1);
        }

    }
}
