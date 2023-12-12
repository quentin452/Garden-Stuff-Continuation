package com.jaquadro.minecraft.gardencore.api;

import net.minecraft.block.Block;

public interface IPlantMetaResolver {

    int getPlantHeight(Block var1, int var2);

    int getPlantSectionMeta(Block var1, int var2, int var3);
}
