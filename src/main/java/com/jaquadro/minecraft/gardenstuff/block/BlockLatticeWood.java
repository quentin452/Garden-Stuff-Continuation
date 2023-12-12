package com.jaquadro.minecraft.gardenstuff.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityBlockMateralProxy;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLatticeWood;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLatticeWood extends BlockLattice {

    public static final String[] subNames = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };

    public BlockLatticeWood(String blockName) {
        super(blockName, Material.wood);
        this.setStepSound(Block.soundTypeWood);
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityLatticeWood();
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < subNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        Block protoBlock = TileEntityLatticeWood.instance.getBlockFromComposedMetadata(meta);
        return protoBlock != null
            ? protoBlock.getIcon(side, TileEntityLatticeWood.instance.getMetaFromComposedMetadata(meta))
            : Blocks.planks.getIcon(side, meta);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        TileEntityBlockMateralProxy te = this.getTileEntity(blockAccess, x, y, z);
        if (te != null && te.getProtoBlock() != null) {
            Block protoBlock = te.getProtoBlock();
            if (protoBlock == null) {
                protoBlock = Blocks.planks;
            }

            return protoBlock.getIcon(side, te.getProtoMeta());
        } else {
            return super.getIcon(blockAccess, x, y, z, side);
        }
    }
}
