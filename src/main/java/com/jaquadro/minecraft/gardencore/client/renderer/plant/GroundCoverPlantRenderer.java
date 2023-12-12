package com.jaquadro.minecraft.gardencore.client.renderer.plant;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.jaquadro.minecraft.gardencore.api.IPlantMetaResolver;
import com.jaquadro.minecraft.gardencore.api.IPlantRenderer;
import com.jaquadro.minecraft.gardencore.api.PlantRegistry;

public class GroundCoverPlantRenderer implements IPlantRenderer {

    public void render(IBlockAccess world, int x, int y, int z, RenderBlocks renderer, Block block, int meta,
        int height, AxisAlignedBB[] bounds) {
        IPlantMetaResolver resolver = PlantRegistry.instance()
            .getPlantMetaResolver(block, meta);
        if (resolver != null) {
            meta = resolver.getPlantSectionMeta(block, meta, height);
        }

        IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 0, meta);
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.03125D, 1.0D);
        renderer.renderFaceYPos(block, (double) x, (double) y, (double) z, icon);
    }
}
