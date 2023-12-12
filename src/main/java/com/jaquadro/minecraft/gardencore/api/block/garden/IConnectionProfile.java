package com.jaquadro.minecraft.gardencore.api.block.garden;

import net.minecraft.world.IBlockAccess;

public interface IConnectionProfile {

    boolean isAttachedNeighbor(IBlockAccess var1, int var2, int var3, int var4, int var5);

    boolean isAttachedNeighbor(IBlockAccess var1, int var2, int var3, int var4, int var5, int var6, int var7);
}
