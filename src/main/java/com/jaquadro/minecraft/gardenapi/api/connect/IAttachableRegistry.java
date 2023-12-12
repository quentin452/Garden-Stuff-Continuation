package com.jaquadro.minecraft.gardenapi.api.connect;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IAttachableRegistry {

    void registerAttachable(String var1, String var2, int var3, IAttachable var4);

    void registerAttachable(String var1, String var2, IAttachable var3);

    void registerAttachable(Block var1, int var2, IAttachable var3);

    void registerAttachable(Block var1, IAttachable var2);

    void registerAttachable(ItemStack var1, IAttachable var2);

    IAttachable getAttachable(Block var1, int var2);
}
