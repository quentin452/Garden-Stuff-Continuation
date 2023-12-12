package com.jaquadro.minecraft.gardencontainers.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockLargePotColored extends BlockLargePot {

    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public BlockLargePotColored(String blockName) {
        super(blockName);
    }

    public String[] getSubTypes() {
        return ItemDye.field_150921_b;
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List blockList) {
        for (int i = 0; i < 16; ++i) {
            blockList.add(new ItemStack(item, 1, i));
        }

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int data) {
        return this.iconArray[data & 15];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.iconArray = new IIcon[16];

        for (int i = 0; i < 16; ++i) {
            String colorName = ItemDye.field_150921_b[getBlockFromDye(i)];
            this.iconArray[i] = iconRegister.registerIcon("GardenContainers:large_pot_" + colorName);
        }

        super.registerBlockIcons(iconRegister);
    }

    public static int getBlockFromDye(int index) {
        return index & 15;
    }
}
