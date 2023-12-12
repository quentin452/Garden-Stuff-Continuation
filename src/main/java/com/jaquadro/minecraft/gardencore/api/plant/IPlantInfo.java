package com.jaquadro.minecraft.gardencore.api.plant;

import net.minecraft.block.Block;

import com.jaquadro.minecraft.gardencore.api.IPlantMetaResolver;

public interface IPlantInfo extends IPlantMetaResolver {

    PlantType getPlantTypeClass(Block var1, int var2);

    PlantSize getPlantSizeClass(Block var1, int var2);

    int getPlantMaxHeight(Block var1, int var2);
}
