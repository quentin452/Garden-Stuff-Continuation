package com.jaquadro.minecraft.gardenstuff.block.tile;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityBlockMateralProxy;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import net.minecraft.block.Block;

public class TileEntityLatticeMetal extends TileEntityLattice {

    public static final TileEntityLatticeMetal instance = new TileEntityLatticeMetal();

    protected TileEntityBlockMateralProxy createTileEntity() {
        return new TileEntityLatticeMetal();
    }

    protected Block getBlockFromStandardMetadata(int metadata) {
        return ModBlocks.latticeMetal;
    }
}
