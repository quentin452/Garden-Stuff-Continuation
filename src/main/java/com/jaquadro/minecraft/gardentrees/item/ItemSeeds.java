package com.jaquadro.minecraft.gardentrees.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import com.jaquadro.minecraft.gardentrees.core.ModCreativeTabs;

public class ItemSeeds extends Item implements IPlantable {

    private Block plantBlock;

    public ItemSeeds(Block block) {
        this.plantBlock = block;
        this.setCreativeTab(ModCreativeTabs.tabGardenTrees);
    }

    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (side != 1) {
            return false;
        } else if (player.canPlayerEdit(x, y, z, side, itemStack) && player.canPlayerEdit(x, y + 1, z, side, itemStack)
            && world.getBlock(x, y, z)
                .canSustainPlant(world, x, y, z, ForgeDirection.UP, this)
            && world.isAirBlock(x, y + 1, z)) {
                world.setBlock(x, y + 1, z, this.plantBlock);
                --itemStack.stackSize;
                return true;
            } else {
                return false;
            }
    }

    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return null;
    }

    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 0;
    }
}
