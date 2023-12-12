package com.jaquadro.minecraft.gardencore.api;

import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import net.minecraft.world.World;

public interface IBonemealHandler {
   boolean applyBonemeal(World var1, int var2, int var3, int var4, BlockGarden var5, int var6);
}
