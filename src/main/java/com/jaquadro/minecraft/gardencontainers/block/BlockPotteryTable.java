package com.jaquadro.minecraft.gardencontainers.block;

import com.jaquadro.minecraft.gardencontainers.GardenContainers;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityPotteryTable;
import com.jaquadro.minecraft.gardencontainers.core.handlers.GuiHandler;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPotteryTable extends BlockContainer {

    private final Random rand = new Random();
    @SideOnly(Side.CLIENT)
    private IIcon iconSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    public BlockPotteryTable(String blockName) {
        super(Material.wood);
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
        this.setHardness(2.5F);
        this.setStepSound(Block.soundTypeWood);
        this.setBlockName(blockName);
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        this.setBlockDirection(world, x, y, z);
    }

    private void setBlockDirection(World world, int x, int y, int z) {
        if (!world.isRemote) {
            Block blockZNeg = world.getBlock(x, y, z - 1);
            Block blockZPos = world.getBlock(x, y, z + 1);
            Block blockXNeg = world.getBlock(x - 1, y, z);
            Block blockXPos = world.getBlock(x + 1, y, z);
            byte dir = 3;
            if (blockZNeg.func_149730_j() && !blockZPos.func_149730_j()) {
                dir = 3;
            }

            if (blockZPos.func_149730_j() && !blockZNeg.func_149730_j()) {
                dir = 2;
            }

            if (blockXNeg.func_149730_j() && !blockXPos.func_149730_j()) {
                dir = 5;
            }

            if (blockXPos.func_149730_j() && !blockXNeg.func_149730_j()) {
                dir = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, dir, 2);
        }

    }

    public TileEntity createNewTileEntity(World world, int data) {
        return new TileEntityPotteryTable();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vx, float vy,
        float vz) {
        player.openGui(GardenContainers.instance, GuiHandler.potteryTableGuiID, world, x, y, z);
        return true;
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TileEntityPotteryTable te = (TileEntityPotteryTable) world.getTileEntity(x, y, z);
        if (te != null) {
            for (int i = 0; i < te.getSizeInventory(); ++i) {
                ItemStack stack = te.getStackInSlot(i);
                if (stack != null) {
                    float ex = this.rand.nextFloat() * 0.8F + 0.1F;
                    float ey = this.rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entity;
                    for (float ez = this.rand.nextFloat() * 0.8F + 0.1F; stack.stackSize > 0; world
                        .spawnEntityInWorld(entity)) {
                        int stackPartSize = this.rand.nextInt(21) + 10;
                        if (stackPartSize > stack.stackSize) {
                            stackPartSize = stack.stackSize;
                        }

                        stack.stackSize -= stackPartSize;
                        entity = new EntityItem(
                            world,
                            (double) ((float) x + ex),
                            (double) ((float) y + ey),
                            (double) ((float) z + ez),
                            new ItemStack(stack.getItem(), stackPartSize, stack.getItemDamage()));
                        float motionUnit = 0.05F;
                        entity.motionX = this.rand.nextGaussian() * (double) motionUnit;
                        entity.motionY = this.rand.nextGaussian() * (double) motionUnit + 0.20000000298023224D;
                        entity.motionZ = this.rand.nextGaussian() * (double) motionUnit;
                        if (stack.hasTagCompound()) {
                            entity.getEntityItem()
                                .setTagCompound(
                                    (NBTTagCompound) stack.getTagCompound()
                                        .copy());
                        }
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, metadata);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int data) {
        if (side == 0) {
            return Blocks.planks.getBlockTextureFromSide(side);
        } else if (side == 1) {
            return this.iconTop;
        } else {
            return side == data ? this.blockIcon : this.iconSide;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("GardenContainers:pottery_table_front");
        this.iconSide = iconRegister.registerIcon("GardenContainers:pottery_table_side");
        this.iconTop = iconRegister.registerIcon("GardenContainers:pottery_table_top");
    }
}
