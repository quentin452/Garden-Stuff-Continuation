package com.jaquadro.minecraft.gardencontainers.item;

import com.jaquadro.minecraft.gardencontainers.block.BlockMediumPot;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemMultiTexture;

public class ItemMediumPot extends ItemMultiTexture {

    public ItemMediumPot(Block block) {
        super(block, block, getSubTypes(block));
    }

    private static String[] getSubTypes(Block block) {
        return block instanceof BlockMediumPot ? ((BlockMediumPot) block).getSubTypes() : new String[0];
    }

    public void registerIcons(IIconRegister register) {}
}
