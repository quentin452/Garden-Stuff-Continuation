package com.jaquadro.minecraft.gardencore.integration;

import com.jaquadro.minecraft.gardencore.api.IPlantMetaResolver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;

public class VanillaMetaResolver implements IPlantMetaResolver {

    public int getPlantHeight(Block block, int meta) {
        return block instanceof BlockDoublePlant ? 2 : 1;
    }

    public int getPlantSectionMeta(Block block, int meta, int section) {
        if (block instanceof BlockDoublePlant) {
            switch (section) {
                case 1:
                    return meta & 7;
                case 2:
                    return meta | 8;
            }
        }

        return meta;
    }
}
