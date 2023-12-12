package com.jaquadro.minecraft.gardenstuff.block.tile;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityBlockMateralProxy;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class TileEntityLatticeWood extends TileEntityLattice {
   public static final TileEntityLatticeWood instance = new TileEntityLatticeWood();

   protected TileEntityBlockMateralProxy createTileEntity() {
      return new TileEntityLatticeWood();
   }

   protected Block getBlockFromStandardMetadata(int metadata) {
      return Blocks.planks;
   }
}
