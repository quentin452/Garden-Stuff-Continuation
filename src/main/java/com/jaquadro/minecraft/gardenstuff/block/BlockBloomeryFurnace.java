package com.jaquadro.minecraft.gardenstuff.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardenstuff.GardenStuff;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityBloomeryFurnace;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import com.jaquadro.minecraft.gardenstuff.core.handlers.GuiHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBloomeryFurnace extends BlockContainer {

    private Random random = new Random();
    @SideOnly(Side.CLIENT)
    private IIcon iconSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconBottom;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconFrontLit;

    public BlockBloomeryFurnace(String name) {
        super(Material.rock);
        this.setHardness(3.5F);
        this.setStepSound(Block.soundTypePiston);
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
        this.setBlockName(name);
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.bloomeryFurnace);
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        if (!world.isRemote) {
            Block neighborZN = world.getBlock(x, y, z - 1);
            Block neighborZP = world.getBlock(x, y, z + 1);
            Block neighborXN = world.getBlock(x - 1, y, z);
            Block neighborXP = world.getBlock(x + 1, y, z);
            byte direction = 3;
            if (neighborZP.func_149730_j() && !neighborZN.func_149730_j()) {
                direction = 3;
            }

            if (neighborZN.func_149730_j() && !neighborZP.func_149730_j()) {
                direction = 2;
            }

            if (neighborXP.func_149730_j() && !neighborXN.func_149730_j()) {
                direction = 5;
            }

            if (neighborXN.func_149730_j() && !neighborXP.func_149730_j()) {
                direction = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, direction, 2);
        }

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0) {
            return this.iconBottom;
        } else if (side == 1) {
            return this.iconTop;
        } else {
            boolean lit = (meta & 8) > 0;
            int metaDir = meta & 7;
            if (metaDir == 0) {
                metaDir = 3;
            }

            if (metaDir == side) {
                return lit ? this.iconFrontLit : this.blockIcon;
            } else {
                return this.iconSide;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("GardenStuff:bloomery_furnace_front_off");
        this.iconFrontLit = register.registerIcon("GardenStuff:bloomery_furnace_front_on");
        this.iconSide = register.registerIcon("GardenStuff:bloomery_furnace_side");
        this.iconBottom = register.registerIcon("GardenStuff:bloomery_furnace_bottom");
        this.iconTop = register.registerIcon("GardenStuff:bloomery_furnace_top");
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityBloomeryFurnace tile = (TileEntityBloomeryFurnace) world.getTileEntity(x, y, z);
            if (tile != null) {
                player.openGui(GardenStuff.instance, GuiHandler.bloomeryFurnaceGuiID, world, x, y, z);
            }

            return true;
        }
    }

    public static void updateFurnaceBlockState(World world, int x, int y, int z, boolean lit) {
        int meta = world.getBlockMetadata(x, y, z);
        int litFlag = lit ? 8 : 0;
        world.setBlockMetadataWithNotify(x, y, z, meta & 7 | litFlag, 3);
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBloomeryFurnace();
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta & 8) == 0 ? 0 : 14;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch (dir) {
            case 0:
                world.setBlockMetadataWithNotify(x, y, z, 2, 2);
                break;
            case 1:
                world.setBlockMetadataWithNotify(x, y, z, 5, 2);
                break;
            case 2:
                world.setBlockMetadataWithNotify(x, y, z, 3, 2);
                break;
            case 3:
                world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        if (stack.hasDisplayName()) {
            TileEntityBloomeryFurnace tile = (TileEntityBloomeryFurnace) world.getTileEntity(x, y, z);
            if (tile != null) {
                tile.setCustomName(stack.getDisplayName());
            }
        }

    }

    public void breakBlock(World world, int x, int y, int z, Block block, int side) {
        TileEntityBloomeryFurnace tile = (TileEntityBloomeryFurnace) world.getTileEntity(x, y, z);
        if (tile != null) {
            int i = 0;

            for (int n = tile.getSizeInventory(); i < n; ++i) {
                ItemStack stack = tile.getStackInSlot(i);
                if (stack != null) {
                    float fx = this.random.nextFloat() * 0.8F + 0.1F;
                    float fy = this.random.nextFloat() * 0.8F + 0.1F;
                    float fz = this.random.nextFloat() * 0.8F + 0.1F;

                    while (stack.stackSize > 0) {
                        int amount = this.random.nextInt(21) + 10;
                        if (amount > stack.stackSize) {
                            amount = stack.stackSize;
                        }

                        stack.stackSize -= amount;
                        EntityItem entity = new EntityItem(
                            world,
                            (double) ((float) x + fx),
                            (double) ((float) y + fy),
                            (double) ((float) z + fz),
                            new ItemStack(stack.getItem(), amount, stack.getItemDamage()));
                        if (stack.hasTagCompound()) {
                            entity.getEntityItem()
                                .setTagCompound(
                                    (NBTTagCompound) stack.getTagCompound()
                                        .copy());
                        }

                        entity.motionX = this.random.nextGaussian() * 0.05000000074505806D;
                        entity.motionY = this.random.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
                        entity.motionZ = this.random.nextGaussian() * 0.05000000074505806D;
                        world.spawnEntityInWorld(entity);
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, side);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        if ((meta & 8) != 0) {
            int dir = meta & 7;
            float fx = (float) x + 0.5F;
            float fy = (float) y + rand.nextFloat() * 6.0F / 16.0F;
            float fz = (float) z + 0.5F;
            float depth = 0.52F;
            float adjust = rand.nextFloat() * 0.6F - 0.3F;
            switch (dir) {
                case 2:
                    world.spawnParticle(
                        "smoke",
                        (double) (fx + adjust),
                        (double) fy,
                        (double) (fz - depth),
                        0.0D,
                        0.0D,
                        0.0D);
                    world.spawnParticle(
                        "flame",
                        (double) (fx + adjust),
                        (double) fy,
                        (double) (fz - depth),
                        0.0D,
                        0.0D,
                        0.0D);
                    break;
                case 3:
                    world.spawnParticle(
                        "smoke",
                        (double) (fx + adjust),
                        (double) fy,
                        (double) (fz + depth),
                        0.0D,
                        0.0D,
                        0.0D);
                    world.spawnParticle(
                        "flame",
                        (double) (fx + adjust),
                        (double) fy,
                        (double) (fz + depth),
                        0.0D,
                        0.0D,
                        0.0D);
                    break;
                case 4:
                    world.spawnParticle(
                        "smoke",
                        (double) (fx - depth),
                        (double) fy,
                        (double) (fz + adjust),
                        0.0D,
                        0.0D,
                        0.0D);
                    world.spawnParticle(
                        "flame",
                        (double) (fx - depth),
                        (double) fy,
                        (double) (fz + adjust),
                        0.0D,
                        0.0D,
                        0.0D);
                    break;
                case 5:
                    world.spawnParticle(
                        "smoke",
                        (double) (fx + depth),
                        (double) fy,
                        (double) (fz + adjust),
                        0.0D,
                        0.0D,
                        0.0D);
                    world.spawnParticle(
                        "flame",
                        (double) (fx + depth),
                        (double) fy,
                        (double) (fz + adjust),
                        0.0D,
                        0.0D,
                        0.0D);
            }

            if (!world.getBlock(x, y + 1, z)
                .isOpaqueCube()) {
                world.spawnParticle("smoke", (double) fx, (double) (fy + 0.5F), (double) fz, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double) fx, (double) (fy + 0.5F), (double) fz, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double) fx, (double) (fy + 0.5F), (double) fz, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
    }

    public Item getItem(World world, int x, int y, int z) {
        return Item.getItemFromBlock(ModBlocks.bloomeryFurnace);
    }
}
