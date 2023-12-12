package com.jaquadro.minecraft.gardencore.block.tile;

import com.jaquadro.minecraft.gardencore.core.ModBlocks;

public class TileEntityGardenSoil extends TileEntityGarden {
   protected int containerSlotCount() {
      return ModBlocks.gardenSoil.getSlotProfile().getPlantSlots().length;
   }
}
