package com.jaquadro.minecraft.gardencore.api.plant;

import net.minecraft.block.Block;

public class DefaultPlantInfo implements IPlantInfo {

    public PlantType getPlantTypeClass(Block block, int meta) {
        return PlantType.GROUND;
    }

    public PlantSize getPlantSizeClass(Block block, int meta) {
        return block.getRenderType() == 6 ? PlantSize.FULL : PlantSize.MEDIUM;
    }

    public int getPlantMaxHeight(Block block, int meta) {
        return 1;
    }

    public int getPlantHeight(Block block, int meta) {
        return 1;
    }

    public int getPlantSectionMeta(Block block, int meta, int section) {
        return meta;
    }
}
