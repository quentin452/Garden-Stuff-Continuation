package com.jaquadro.minecraft.gardencore.client.renderer.plant;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

import com.jaquadro.minecraft.gardencore.api.IPlantMetaResolver;
import com.jaquadro.minecraft.gardencore.api.IPlantRenderer;
import com.jaquadro.minecraft.gardencore.api.PlantRegistry;

public class CropsPlantRenderer implements IPlantRenderer {

    public void render(IBlockAccess world, int x, int y, int z, RenderBlocks renderer, Block block, int meta,
        int height, AxisAlignedBB[] bounds) {
        IPlantMetaResolver resolver = PlantRegistry.instance()
            .getPlantMetaResolver(block, meta);
        if (resolver != null) {
            meta = resolver.getPlantSectionMeta(block, meta, height);
        }

        renderer.renderBlockCropsImpl(block, meta, (double) x, (double) y, (double) z);
    }
}
