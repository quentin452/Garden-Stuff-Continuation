package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.api.block.IChain;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockHeavyChain extends Block implements IChain {

    public static final String[] types = new String[] { "iron", "gold", "rope", "rust", "wrought_iron", "moss" };
    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    public BlockHeavyChain(String blockName) {
        super(Material.iron);
        this.setBlockName(blockName);
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
        this.setBlockTextureName("GardenStuff:chain_heavy");
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return ClientProxy.heavyChainRenderID;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    public int damageDropped(int meta) {
        return MathHelper.clamp_int(meta, 0, types.length - 1);
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 3));
        list.add(new ItemStack(item, 1, 4));
        list.add(new ItemStack(item, 1, 5));
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        world.notifyBlockOfNeighborChange(x, y + 1, z, this);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (block == this || block == ModBlocks.lantern) {
            world.notifyBlockOfNeighborChange(x, y + 1, z, this);
        }

        if (world.getBlock(x, y + 1, z) != this) {
            world.notifyBlockOfNeighborChange(x, y + 2, z, this);
        }

    }

    public boolean canProvidePower() {
        return true;
    }

    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return this.isProvidingStrongPower(world, x, y, z, side);
    }

    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
        if (side != 0) {
            return 0;
        } else {
            for (int i = 1; i <= 8 && y - i > 0; ++i) {
                Block block = world.getBlock(x, y - i, z);
                if (block != this) {
                    return block.isProvidingWeakPower(world, x, y - i, z, side);
                }
            }

            return 0;
        }
    }

    public IIcon getIcon(int side, int meta) {
        return icons[MathHelper.clamp_int(meta, 0, types.length - 1)];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[types.length];

        for (int i = 0; i < types.length; ++i) {
            icons[i] = register.registerIcon(this.getTextureName() + "_" + types[i]);
        }

    }

    public boolean isMultiAttach() {
        return false;
    }
}
