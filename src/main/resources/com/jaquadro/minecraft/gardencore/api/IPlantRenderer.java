package com.jaquadro.minecraft.gardencore.api;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

public interface IPlantRenderer {
   void render(IBlockAccess var1, int var2, int var3, int var4, RenderBlocks var5, Block var6, int var7, int var8, AxisAlignedBB[] var9);
}
