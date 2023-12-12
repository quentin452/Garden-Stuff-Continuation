package com.jaquadro.minecraft.gardenstuff.item;

import com.jaquadro.minecraft.gardenstuff.block.BlockLatticeMetal;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLatticeMetal extends ItemMultiTexture {

    public ItemLatticeMetal(Block block) {
        super(block, block, BlockLatticeMetal.subNames);
    }

    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (block instanceof BlockCauldron && side == 1 && itemStack.getItemDamage() == 0) {
            int waterLevel = BlockCauldron.func_150027_b(meta);
            if (waterLevel == 0) {
                return false;
            } else {
                ItemStack newItem = new ItemStack(ModBlocks.latticeMetal, 1, 1);
                --itemStack.stackSize;
                EntityItem itemEntity = new EntityItem(
                    world,
                    (double) x + 0.5D,
                    (double) y + 1.5D,
                    (double) z + 0.5D,
                    newItem);
                itemEntity
                    .playSound("random.splash", 0.25F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
                world.spawnEntityInWorld(itemEntity);
                return true;
            }
        } else {
            return super.onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
    }
}
