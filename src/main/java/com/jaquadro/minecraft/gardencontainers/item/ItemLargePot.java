package com.jaquadro.minecraft.gardencontainers.item;

import com.jaquadro.minecraft.gardencontainers.GardenContainers;
import com.jaquadro.minecraft.gardencontainers.block.BlockLargePot;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityLargePot;
import com.jaquadro.minecraft.gardencontainers.config.PatternConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemLargePot extends ItemMultiTexture {

    public ItemLargePot(Block block) {
        super(block, block, getSubTypes(block));
    }

    private static String[] getSubTypes(Block block) {
        return block instanceof BlockLargePot ? ((BlockLargePot) block).getSubTypes() : new String[0];
    }

    protected Block getBlock() {
        return super.field_150939_a;
    }

    public String getUnlocalizedName(ItemStack stack) {
        int i = stack.getItemDamage() & 15;
        if (i < 0 || i >= this.field_150942_c.length) {
            i = 0;
        }

        return super.getUnlocalizedName() + "." + this.field_150942_c[i];
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        PatternConfig pattern = GardenContainers.config.getPattern(itemStack.getItemDamage() >> 8 & 255);
        if (pattern != null && pattern.getName() != null) {
            list.add(pattern.getName());
        }

    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        if (!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata & 15)) {
            return false;
        } else {
            TileEntityLargePot largePot = (TileEntityLargePot) world.getTileEntity(x, y, z);
            if (largePot != null) {
                largePot.setCarving(metadata >> 8 & 255);
            }

            return true;
        }
    }
}
