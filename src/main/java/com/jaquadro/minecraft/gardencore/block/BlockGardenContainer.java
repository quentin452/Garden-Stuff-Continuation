package com.jaquadro.minecraft.gardencore.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;

public abstract class BlockGardenContainer extends BlockGarden {

    protected BlockGardenContainer(String blockName, Material material) {
        super(blockName, material);
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int data) {
        TileEntityGarden te = this.getTileEntity(world, x, y, z);
        if (te != null) {
            ItemStack substrate = te.getSubstrateSource();
            if (substrate == null) {
                substrate = te.getSubstrate();
            }

            if (substrate != null && Block.getBlockFromItem(substrate.getItem()) != Blocks.water) {
                ItemStack item = substrate.copy();
                item.stackSize = 1;
                this.dropBlockAsItem(world, x, y, z, item);
            }
        }

        super.breakBlock(world, x, y, z, block, data);
    }

    public ItemStack getGardenSubstrate(IBlockAccess world, int x, int y, int z, int slot) {
        TileEntityGarden te = this.getTileEntity(world, x, y, z);
        return te != null ? te.getSubstrate() : null;
    }

    protected boolean applyItemToGarden(World world, int x, int y, int z, EntityPlayer player, ItemStack itemStack,
        float hitX, float hitY, float hitZ, boolean hitValid) {
        ItemStack item = itemStack == null ? player.inventory.getCurrentItem() : itemStack;
        if (item == null) {
            return false;
        } else {
            int slot = this.getSlot(world, x, y, z, player, hitX, hitY, hitZ);
            return this.applySubstrateToGarden(world, x, y, z, itemStack == null ? player : null, slot, item) ? true
                : super.applyItemToGarden(world, x, y, z, player, itemStack, hitX, hitY, hitZ, hitValid);
        }
    }

    protected boolean applySubstrateToGarden(World world, int x, int y, int z, EntityPlayer player, int slot,
        ItemStack itemStack) {
        if (this.getGardenSubstrate(world, x, y, z, slot) != null) {
            return false;
        } else if (!this.isValidSubstrate(world, x, y, z, slot, itemStack)) {
            return false;
        } else {
            TileEntityGarden garden = this.getTileEntity(world, x, y, z);
            ItemStack translation = this.translateSubstrate(world, x, y, z, slot, itemStack);
            if (translation != null && translation != itemStack) {
                garden.setSubstrate(itemStack, translation);
            } else {
                garden.setSubstrate(itemStack);
            }

            garden.markDirty();
            world.markBlockForUpdate(x, y, z);
            if (player != null && !player.capabilities.isCreativeMode) {
                ItemStack currentItem = player.inventory.getCurrentItem();
                if (--currentItem.stackSize <= 0) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                }
            }

            return true;
        }
    }

    protected boolean isPlantValidForSubstrate(ItemStack substrate, PlantItem plant) {
        if (substrate != null && substrate.getItem() != null) {
            switch (plant.getPlantTypeClass()) {
                case AQUATIC:
                case AQUATIC_COVER:
                case AQUATIC_EMERGENT:
                case AQUATIC_SURFACE:
                    if (Block.getBlockFromItem(substrate.getItem()) != Blocks.water) {
                        return false;
                    }
                    break;
                case GROUND:
                case GROUND_COVER:
                    if (Block.getBlockFromItem(substrate.getItem()) == Blocks.water) {
                        return false;
                    }
            }

            return super.isPlantValidForSubstrate(substrate, plant);
        } else {
            return false;
        }
    }

    protected boolean isValidSubstrate(World world, int x, int y, int z, int slot, ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() != null) {
            Block block = Block.getBlockFromItem(itemStack.getItem());
            if (block == null) {
                return false;
            } else {
                return block == Blocks.dirt || block == Blocks.sand
                    || block == Blocks.gravel
                    || block == Blocks.soul_sand
                    || block == Blocks.grass
                    || block == Blocks.water
                    || block == Blocks.farmland
                    || block == Blocks.mycelium
                    || block == ModBlocks.gardenSoil
                    || block == ModBlocks.gardenFarmland;
            }
        } else {
            return false;
        }
    }

    protected ItemStack translateSubstrate(World world, int x, int y, int z, int slot, ItemStack itemStack) {
        return Block.getBlockFromItem(itemStack.getItem()) == Blocks.farmland ? new ItemStack(Blocks.farmland, 1, 1)
            : itemStack;
    }
}
