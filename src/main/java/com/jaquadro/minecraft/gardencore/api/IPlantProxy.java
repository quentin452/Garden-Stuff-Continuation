package com.jaquadro.minecraft.gardencore.api;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;

public interface IPlantProxy {

    TileEntityGarden getGardenEntity(IBlockAccess var1, int var2, int var3, int var4);

    boolean applyBonemeal(World var1, int var2, int var3, int var4);
}
