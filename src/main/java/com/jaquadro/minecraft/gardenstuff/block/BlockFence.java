package com.jaquadro.minecraft.gardenstuff.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFence extends BlockConnected {

    public static final String[] subNames = new String[] { "0", "1", "2", "3" };
    public static final int[] postInterval = new int[] { 8, 16, 8, 8 };
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    @SideOnly(Side.CLIENT)
    private IIcon[] iconsTB;

    public BlockFence(String blockName) {
        super(blockName, Material.iron);
        this.setBlockTextureName("GardenStuff:wrought_iron_fence");
    }

    public int getRenderType() {
        return ClientProxy.fenceRenderID;
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < subNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }

    }

    protected float getCollisionHeight() {
        return 1.5F;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.iconsTB[meta % this.icons.length];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int meta) {
        return this.icons[meta % this.icons.length];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconTB(int meta) {
        return this.iconsTB[meta % this.icons.length];
    }

    public int getPostInterval(int meta) {
        return postInterval[meta % this.icons.length];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons = new IIcon[subNames.length];
        this.iconsTB = new IIcon[subNames.length];

        for (int i = 0; i < this.icons.length; ++i) {
            this.icons[i] = register.registerIcon(this.getTextureName() + "_" + subNames[i]);
            this.iconsTB[i] = register.registerIcon(this.getTextureName() + "_" + subNames[i] + "_tb");
        }

    }
}
