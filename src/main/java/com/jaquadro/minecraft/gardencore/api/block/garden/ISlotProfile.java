package com.jaquadro.minecraft.gardencore.api.block.garden;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;

public interface ISlotProfile {

    int[] getPlantSlots();

    boolean isPlantValidForSlot(IBlockAccess var1, int var2, int var3, int var4, int var5, PlantItem var6);

    float getPlantOffsetX(IBlockAccess var1, int var2, int var3, int var4, int var5);

    float getPlantOffsetY(IBlockAccess var1, int var2, int var3, int var4, int var5);

    float getPlantOffsetZ(IBlockAccess var1, int var2, int var3, int var4, int var5);

    Object openPlantGUI(InventoryPlayer var1, TileEntity var2, boolean var3);

    AxisAlignedBB[] getClippingBounds(IBlockAccess var1, int var2, int var3, int var4, int var5);
}
