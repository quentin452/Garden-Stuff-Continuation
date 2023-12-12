package com.jaquadro.minecraft.gardenapi.api.connect;

import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;

public interface IChainSingleAttachable {

    Vec3 getChainAttachPoint(IBlockAccess var1, int var2, int var3, int var4, int var5);
}
