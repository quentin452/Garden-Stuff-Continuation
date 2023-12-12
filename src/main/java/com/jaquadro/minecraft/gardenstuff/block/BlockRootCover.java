package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockRootCover extends Block {

    @SideOnly(Side.CLIENT)
    private static IIcon[][] icons;

    public BlockRootCover(String name) {
        super(Material.wood);
        this.setBlockName(name);
        this.setHardness(1.5F);
        this.setResistance(2.5F);
        this.setStepSound(Block.soundTypeWood);
        this.setBlockTextureName("GardenStuff:root_cover");
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return 1;
    }

    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (side != 0 && side != 1) {
            return side != 2 && side != 3 ? icons[this.mod2(z)][this.mod2(y)] : icons[this.mod2(x)][this.mod2(y)];
        } else {
            return icons[this.mod2(x)][this.mod2(z)];
        }
    }

    private int mod2(int x) {
        return (x % 2 + 2) % 2;
    }

    public IIcon getIcon(int side, int meta) {
        return icons[0][0];
    }

    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[2][2];
        icons[0][0] = register.registerIcon(this.getTextureName() + "_00");
        icons[0][1] = register.registerIcon(this.getTextureName() + "_01");
        icons[1][0] = register.registerIcon(this.getTextureName() + "_10");
        icons[1][1] = register.registerIcon(this.getTextureName() + "_10");
    }
}
