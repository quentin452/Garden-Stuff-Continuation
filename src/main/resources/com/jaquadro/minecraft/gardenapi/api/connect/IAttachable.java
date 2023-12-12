package com.jaquadro.minecraft.gardenapi.api.connect;

import net.minecraft.world.IBlockAccess;

public interface IAttachable {
   boolean isAttachable(IBlockAccess var1, int var2, int var3, int var4, int var5);

   double getAttachDepth(IBlockAccess var1, int var2, int var3, int var4, int var5);
}
