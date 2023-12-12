package com.jaquadro.minecraft.gardencore.block;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import com.jaquadro.minecraft.gardencore.api.IPlantProxy;
import com.jaquadro.minecraft.gardencore.api.block.IGardenBlock;
import com.jaquadro.minecraft.gardencore.api.block.garden.IConnectionProfile;
import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotProfile;
import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotShareProfile;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.core.ModItems;

public abstract class BlockGarden extends BlockContainer implements IGardenBlock {

    public static final int SLOT_INVALID = -1;
    protected IConnectionProfile connectionProfile;
    protected ISlotProfile slotProfile;
    protected ISlotShareProfile slotShareProfile;

    protected BlockGarden(String blockName, Material material) {
        super(material);
        this.setBlockName(blockName);
    }

    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plantable) {
        return false;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (side != ForgeDirection.UP.ordinal()) {
            return false;
        } else {
            TileEntityGarden tileEntity = this.getTileEntity(world, x, y, z);
            if (tileEntity == null) {
                tileEntity = this.createNewTileEntity(world, 0);
                world.setTileEntity(x, y, z, tileEntity);
            }

            return this.applyItemToGarden(world, x, y, z, player, (ItemStack) null, hitX, hitY, hitZ);
        }
    }

    public abstract TileEntityGarden createNewTileEntity(World var1, int var2);

    public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer player) {
        super.onBlockHarvested(world, x, y, z, p_149681_5_, player);
        if (player.capabilities.isCreativeMode) {
            TileEntityGarden te = this.getTileEntity(world, x, y, z);
            if (te != null) {
                te.clearPlantedContents();
            }
        }

    }

    public void breakBlock(World world, int x, int y, int z, Block block, int data) {
        TileEntityGarden te = this.getTileEntity(world, x, y, z);
        if (te != null) {
            Iterator var8 = te.getReachableContents()
                .iterator();

            while (var8.hasNext()) {
                ItemStack item = (ItemStack) var8.next();
                this.dropBlockAsItem(world, x, y, z, item);
            }

            te.clearReachableContents();
        }

        super.breakBlock(world, x, y, z, block, data);
    }

    public int getDefaultSlot() {
        return -1;
    }

    public IConnectionProfile getConnectionProfile() {
        return this.connectionProfile;
    }

    public ISlotProfile getSlotProfile() {
        return this.slotProfile;
    }

    public ISlotShareProfile getSlotShareProfile() {
        return this.slotShareProfile;
    }

    public void clearPlantedContents(IBlockAccess world, int x, int y, int z) {
        TileEntityGarden te = this.getTileEntity(world, x, y, z);
        if (te != null) {
            te.clearReachableContents();
        }

        validateBlockState(te);
    }

    public NBTTagCompound saveAndClearPlantedContents(IBlockAccess world, int x, int y, int z) {
        TileEntityGarden te = this.getTileEntity(world, x, y, z);
        if (te == null) {
            return null;
        } else {
            NBTTagCompound tag = new NBTTagCompound();
            te.writeToNBT(tag);
            this.clearPlantedContents(world, x, y, z);
            return tag;
        }
    }

    public void restorePlantedContents(IBlockAccess world, int x, int y, int z, NBTTagCompound tag) {
        TileEntityGarden te = this.getTileEntity(world, x, y, z);
        if (te != null) {
            te.readFromNBT(tag);
            validateBlockState(te);
        }
    }

    public Block getPlantBlockFromSlot(IBlockAccess blockAccess, int x, int y, int z, int slot) {
        TileEntityGarden te = this.getTileEntity(blockAccess, x, y, z);
        ItemStack stack = te.getPlantInSlot(slot);
        return stack != null && stack.getItem() != null ? Block.getBlockFromItem(stack.getItem()) : null;
    }

    public int getPlantMetaFromSlot(IBlockAccess blockAccess, int x, int y, int z, int slot) {
        TileEntityGarden te = this.getTileEntity(blockAccess, x, y, z);
        ItemStack stack = te.getPlantInSlot(slot);
        return stack != null && stack.getItem() != null ? stack.getItemDamage() : 0;
    }

    public static void validateBlockState(TileEntityGarden tileEntity) {
        Block baseBlock = tileEntity.getWorldObj()
            .getBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        if (baseBlock instanceof BlockGarden) {
            BlockGarden garden = (BlockGarden) baseBlock;
            int plantHeight = garden.getAggregatePlantHeight(
                tileEntity.getWorldObj(),
                tileEntity.xCoord,
                tileEntity.yCoord,
                tileEntity.zCoord);
            World world = tileEntity.getWorldObj();
            int x = tileEntity.xCoord;
            int y = tileEntity.yCoord + 1;
            int z = tileEntity.zCoord;

            for (int yLimit = y + plantHeight; y < yLimit; ++y) {
                Block block = world.getBlock(x, y, z);
                if (block.isAir(world, x, y, z)) {
                    world.setBlock(x, y, z, ModBlocks.gardenProxy, 0, 3);
                }

                world.func_147479_m(x, y, z);
            }

            while (world.getBlock(x, y, z) instanceof BlockGardenProxy) {
                world.setBlockToAir(x, y++, z);
            }

        }
    }

    public int getAggregatePlantHeight(IBlockAccess blockAccess, int x, int y, int z) {
        TileEntityGarden garden = this.getTileEntity(blockAccess, x, y, z);
        int height = 0;
        int[] var7 = this.slotProfile.getPlantSlots();
        int var8 = var7.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            int slot = var7[var9];
            ItemStack item = garden.getStackInSlot(slot);
            PlantItem plant = PlantItem.getForItem(blockAccess, item);
            if (plant != null) {
                height = Math.max(height, plant.getPlantHeight());
            }
        }

        return height;
    }

    public final boolean applyItemToGarden(World world, int x, int y, int z, EntityPlayer player, ItemStack itemStack,
        float hitX, float hitY, float hitZ) {
        return this.applyItemToGarden(world, x, y, z, player, itemStack, hitX, hitY, hitZ, true);
    }

    public final boolean applyItemToGarden(World world, int x, int y, int z, EntityPlayer player, ItemStack itemStack) {
        return this.applyItemToGarden(world, x, y, z, player, itemStack, 0.0F, 0.0F, 0.0F, false);
    }

    protected boolean applyItemToGarden(World world, int x, int y, int z, EntityPlayer player, ItemStack itemStack,
        float hitX, float hitY, float hitZ, boolean hitValid) {
        ItemStack item = itemStack == null ? player.inventory.getCurrentItem() : itemStack;
        if (item == null) {
            return false;
        } else {
            int slot = this.getSlot(world, x, y, z, player, hitX, hitY, hitZ);
            if (this.applyTestKitToGarden(world, x, y, z, slot, item)) {
                return true;
            } else {
                PlantItem plant = PlantItem.getForItem(world, item);
                if (plant != null) {
                    int plantSlot = hitValid
                        ? this.getEmptySlotForPlant(world, x, y, z, player, plant, hitX, hitY, hitZ)
                        : this.getEmptySlotForPlant(world, x, y, z, player, plant);
                    if (plantSlot == -1) {
                        return false;
                    } else {
                        return this.canSustainPlantIndependently(world, x, y, z, plant) ? false
                            : this.applyPlantToGarden(
                                world,
                                x,
                                y,
                                z,
                                itemStack == null ? player : null,
                                plantSlot,
                                plant);
                    }
                } else {
                    return false;
                }
            }
        }
    }

    public boolean canSustainPlantIndependently(IBlockAccess blockAccess, int x, int y, int z, PlantItem plant) {
        Item item = plant.getPlantSourceItem()
            .getItem();
        return item instanceof IPlantable
            ? this.canSustainPlant(blockAccess, x, y, z, ForgeDirection.UP, (IPlantable) item)
            : false;
    }

    public abstract ItemStack getGardenSubstrate(IBlockAccess var1, int var2, int var3, int var4, int var5);

    protected int getSlot(World world, int x, int y, int z, EntityPlayer player, float hitX, float hitY, float hitZ) {
        return -1;
    }

    protected int getEmptySlotForPlant(World world, int x, int y, int z, EntityPlayer player, PlantItem plant) {
        return -1;
    }

    protected int getEmptySlotForPlant(World world, int x, int y, int z, EntityPlayer player, PlantItem plant,
        float hitX, float hitY, float hitZ) {
        return this.getEmptySlotForPlant(world, x, y, z, player, plant);
    }

    protected boolean enoughSpaceAround(IBlockAccess blockAccess, int x, int y, int z, int slot, PlantItem plant) {
        int height = plant.getPlantHeight();
        boolean enough = true;

        for (int i = 0; i < height; ++i) {
            enough &= blockAccess.isAirBlock(x, y + 1 + i, z)
                || blockAccess.getBlock(x, y + 1 + i, z) instanceof IPlantProxy;
        }

        return enough;
    }

    protected boolean isPlantValidForSubstrate(ItemStack substrate, PlantItem plant) {
        return true;
    }

    public boolean isPlantValidForSlot(World world, int x, int y, int z, int slot, PlantItem plant) {
        if (plant == null) {
            return false;
        } else if (plant.getPlantBlock() instanceof BlockContainer) {
            return false;
        } else if (!this.slotProfile.isPlantValidForSlot(world, x, y, z, slot, plant)) {
            return false;
        } else if (!this.enoughSpaceAround(world, x, y, z, slot, plant)) {
            return false;
        } else if (!this.isPlantValidForSubstrate(this.getGardenSubstrate(world, x, y, z, slot), plant)) {
            return false;
        } else {
            return !this.canSustainPlantIndependently(world, x, y, z, plant);
        }
    }

    public boolean applyHoe(World world, int x, int y, int z) {
        return false;
    }

    protected boolean applyPlantToGarden(World world, int x, int y, int z, EntityPlayer player, int slot,
        PlantItem plant) {
        if (!this.isPlantValidForSlot(world, x, y, z, slot, plant)) {
            return false;
        } else {
            TileEntityGarden garden = this.getTileEntity(world, x, y, z);
            garden.setInventorySlotContents(
                slot,
                plant.getPlantSourceItem()
                    .copy());
            if (player != null && !player.capabilities.isCreativeMode) {
                ItemStack currentItem = player.inventory.getCurrentItem();
                if (--currentItem.stackSize <= 0) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                }
            }

            return true;
        }
    }

    protected boolean applyTestKitToGarden(World world, int x, int y, int z, int slot, ItemStack testKit) {
        if (testKit != null && testKit.getItem() == ModItems.usedSoilTestKit) {
            ItemStack substrateStack = this.getGardenSubstrate(world, x, y, z, slot);
            if (substrateStack != null && substrateStack.getItem() != null) {
                TileEntityGarden tileEntity = this.getTileEntity(world, x, y, z);
                if (tileEntity == null) {
                    return false;
                } else {
                    tileEntity.setBiomeData(testKit.getItemDamage());
                    world.markBlockForUpdate(x, y, z);

                    for (int i = 0; i < 5; ++i) {
                        double d0 = world.rand.nextGaussian() * 0.02D;
                        double d1 = world.rand.nextGaussian() * 0.02D;
                        double d2 = world.rand.nextGaussian() * 0.02D;
                        world.spawnParticle(
                            "happyVillager",
                            (double) ((float) x + world.rand.nextFloat()),
                            (double) ((float) y + 0.5F + world.rand.nextFloat()),
                            (double) ((float) z + world.rand.nextFloat()),
                            d0,
                            d1,
                            d2);
                    }

                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public TileEntityGarden getTileEntity(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te != null && te instanceof TileEntityGarden ? (TileEntityGarden) te : null;
    }
}
