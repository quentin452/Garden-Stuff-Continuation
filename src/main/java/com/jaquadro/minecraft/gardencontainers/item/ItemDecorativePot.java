package com.jaquadro.minecraft.gardencontainers.item;

import com.jaquadro.minecraft.gardencontainers.block.BlockDecorativePot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockQuartz;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemMultiTexture;

public class ItemDecorativePot extends ItemMultiTexture {

    public ItemDecorativePot(Block block) {
        super(block, block, getSubTypes(block));
    }

    private static String[] getSubTypes(Block block) {
        return block instanceof BlockDecorativePot ? BlockQuartz.field_150191_a : new String[0];
    }

    public void registerIcons(IIconRegister register) {}
}
