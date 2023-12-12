package com.jaquadro.minecraft.gardencore.block;

import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.block.support.BasicConnectionProfile;
import com.jaquadro.minecraft.gardencore.block.support.BasicSlotProfile;
import com.jaquadro.minecraft.gardencore.block.support.Slot14ProfileBounded;
import com.jaquadro.minecraft.gardencore.block.support.SlotShare8Profile;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGardenSoil;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGardenSoil extends BlockGarden {

    private static ItemStack substrate;

    public BlockGardenSoil(String blockName) {
        super(blockName, Material.ground);
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
        this.setHardness(0.5F);
        this.setStepSound(Block.soundTypeGrass);
        PlantType[] commonType = new PlantType[] { PlantType.GROUND, PlantType.AQUATIC, PlantType.AQUATIC_EMERGENT };
        PlantSize[] commonSize = new PlantSize[] { PlantSize.LARGE, PlantSize.MEDIUM, PlantSize.SMALL };
        PlantSize[] allSize = new PlantSize[] { PlantSize.FULL, PlantSize.LARGE, PlantSize.MEDIUM, PlantSize.SMALL };
        this.connectionProfile = new BasicConnectionProfile();
        this.slotShareProfile = new SlotShare8Profile(6, 7, 8, 9, 10, 11, 12, 13);
        this.slotProfile = new Slot14ProfileBounded(
            this,
            new BasicSlotProfile.Slot[] { new BasicSlotProfile.Slot(0, commonType, allSize),
                new BasicSlotProfile.Slot(1, new PlantType[] { PlantType.GROUND_COVER }, allSize),
                new BasicSlotProfile.Slot(2, commonType, commonSize),
                new BasicSlotProfile.Slot(3, commonType, commonSize),
                new BasicSlotProfile.Slot(4, commonType, commonSize),
                new BasicSlotProfile.Slot(5, commonType, commonSize),
                new BasicSlotProfile.Slot(6, commonType, commonSize),
                new BasicSlotProfile.Slot(7, commonType, commonSize),
                new BasicSlotProfile.Slot(8, commonType, commonSize),
                new BasicSlotProfile.Slot(9, commonType, commonSize),
                new BasicSlotProfile.Slot(10, commonType, commonSize),
                new BasicSlotProfile.Slot(11, commonType, commonSize),
                new BasicSlotProfile.Slot(12, commonType, commonSize),
                new BasicSlotProfile.Slot(13, commonType, commonSize) });
    }

    public TileEntityGardenSoil createNewTileEntity(World var1, int var2) {
        return new TileEntityGardenSoil();
    }

    public ItemStack getGardenSubstrate(IBlockAccess blockAccess, int x, int y, int z, int slot) {
        return substrate;
    }

    public int getDefaultSlot() {
        return 0;
    }

    protected int getSlot(World world, int x, int y, int z, EntityPlayer player, float hitX, float hitY, float hitZ) {
        return 0;
    }

    protected int getEmptySlotForPlant(World world, int x, int y, int z, EntityPlayer player, PlantItem plant) {
        TileEntityGarden garden = this.getTileEntity(world, x, y, z);
        if (plant.getPlantTypeClass() == PlantType.GROUND_COVER) {
            return garden.getStackInSlot(1) == null ? 1 : -1;
        } else if (plant.getPlantSizeClass() == PlantSize.FULL) {
            return garden.getStackInSlot(0) == null ? 0 : -1;
        } else if (garden.getStackInSlot(0) == null) {
            return 0;
        } else {
            int[] var8;
            int var9;
            int var10;
            int slot;
            if (plant.getPlantSizeClass() == PlantSize.SMALL) {
                var8 = new int[] { 3, 4, 2, 5 };
                var9 = var8.length;

                for (var10 = 0; var10 < var9; ++var10) {
                    slot = var8[var10];
                    if (garden.getStackInSlot(slot) == null) {
                        return slot;
                    }
                }
            }

            var8 = new int[] { 13, 9, 7, 11, 6, 10, 8, 12 };
            var9 = var8.length;

            for (var10 = 0; var10 < var9; ++var10) {
                slot = var8[var10];
                if (garden.isSlotValid(slot) && garden.getStackInSlot(slot) == null) {
                    return slot;
                }
            }

            return -1;
        }
    }

    public boolean applyHoe(World world, int x, int y, int z) {
        TileEntityGarden te = this.getTileEntity(world, x, y, z);
        if (te != null && te.isEmpty()) {
            this.convertToFarm(world, x, y, z);
            return true;
        } else {
            return false;
        }
    }

    public void convertToFarm(World world, int x, int y, int z) {
        world.playSoundEffect(
            (double) x + 0.5D,
            (double) y + 0.5D,
            (double) z + 0.5D,
            this.stepSound.getStepResourcePath(),
            (this.stepSound.getVolume() + 1.0F) / 2.0F,
            this.stepSound.getPitch() * 0.8F);
        if (!world.isRemote) {
            world.setBlock(x, y, z, ModBlocks.gardenFarmland);
        }

    }

    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("GardenCore:garden_dirt");
    }

    static {
        substrate = new ItemStack(Blocks.dirt, 1);
    }
}
