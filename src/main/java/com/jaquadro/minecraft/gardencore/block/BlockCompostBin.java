package com.jaquadro.minecraft.gardencore.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.GardenCore;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityCompostBin;
import com.jaquadro.minecraft.gardencore.client.particle.EntitySteamFX;
import com.jaquadro.minecraft.gardencore.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardencore.core.handlers.GuiHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCompostBin extends BlockContainer {

    private static final int ICON_SIDE = 0;
    private static final int ICON_TOP = 1;
    private static final int ICON_BOTTOM = 2;
    private static final int ICON_INNER = 3;
    @SideOnly(Side.CLIENT)
    IIcon[] icons;

    public BlockCompostBin(String name) {
        super(Material.wood);
        this.setBlockName(name);
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
        this.setTickRandomly(true);
        this.setHardness(1.5F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypeWood);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return ClientProxy.compostBinRenderID;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vx, float vy,
        float vz) {
        player.openGui(GardenCore.instance, GuiHandler.compostBinGuiID, world, x, y, z);
        return true;
    }

    public static void updateBlockState(World world, int x, int y, int z) {
        TileEntityCompostBin te = (TileEntityCompostBin) world.getTileEntity(x, y, z);
        if (te != null) {
            world.markBlockForUpdate(x, y, z);
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int data) {
        TileEntityCompostBin te = this.getTileEntity(world, x, y, z);
        if (te != null) {
            for (int i = 0; i < te.getSizeInventory(); ++i) {
                ItemStack item = te.getStackInSlot(i);
                if (item != null) {
                    this.dropBlockAsItem(world, x, y, z, item);
                }
            }
        }

        super.breakBlock(world, x, y, z, block, data);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        TileEntityCompostBin te = (TileEntityCompostBin) world.getTileEntity(x, y, z);
        if (te != null) {
            if (te.isDecomposing()) {
                float px = (float) x + 0.5F + random.nextFloat() * 0.6F - 0.3F;
                float py = (float) y + 0.5F + random.nextFloat() * 6.0F / 16.0F;
                float pz = (float) z + 0.5F + random.nextFloat() * 0.6F - 0.3F;
                EntitySteamFX.spawnParticle(world, (double) px, (double) py, (double) pz);
            }

        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch (side) {
            case 0:
                return this.icons[2];
            case 1:
                return this.icons[1];
            default:
                return this.icons[0];
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getInnerIcon() {
        return this.icons[3];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons = new IIcon[4];
        this.icons[0] = register.registerIcon("GardenCore:compost_bin_side");
        this.icons[1] = register.registerIcon("GardenCore:compost_bin_top");
        this.icons[2] = register.registerIcon("GardenCore:compost_bin_bottom");
        this.icons[3] = register.registerIcon("GardenCore:compost_bin_inner");
    }

    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityCompostBin();
    }

    public TileEntityCompostBin getTileEntity(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te != null && te instanceof TileEntityCompostBin ? (TileEntityCompostBin) te : null;
    }
}
