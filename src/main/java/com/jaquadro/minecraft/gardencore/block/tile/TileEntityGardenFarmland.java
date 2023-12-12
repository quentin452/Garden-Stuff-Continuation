package com.jaquadro.minecraft.gardencore.block.tile;

import com.jaquadro.minecraft.gardencore.core.ModBlocks;

public class TileEntityGardenFarmland extends TileEntityGarden {

    protected int containerSlotCount() {
        return ModBlocks.gardenFarmland.getSlotProfile()
            .getPlantSlots().length;
    }
}
