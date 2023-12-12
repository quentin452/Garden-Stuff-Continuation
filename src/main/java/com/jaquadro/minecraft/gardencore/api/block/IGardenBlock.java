package com.jaquadro.minecraft.gardencore.api.block;

import com.jaquadro.minecraft.gardencore.api.block.garden.IConnectionProfile;
import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotProfile;
import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotShareProfile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public interface IGardenBlock {

    int getDefaultSlot();

    ItemStack getGardenSubstrate(IBlockAccess var1, int var2, int var3, int var4, int var5);

    IConnectionProfile getConnectionProfile();

    ISlotProfile getSlotProfile();

    ISlotShareProfile getSlotShareProfile();
}
