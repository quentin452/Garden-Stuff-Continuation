package com.jaquadro.minecraft.gardencontainers.item;

import com.jaquadro.minecraft.gardencontainers.GardenContainers;
import com.jaquadro.minecraft.gardencontainers.config.PatternConfig;
import com.jaquadro.minecraft.gardencontainers.core.ModItems;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemPotteryPatternDirty extends Item {

    public ItemPotteryPatternDirty(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxStackSize(64);
        this.setHasSubtypes(false);
        this.setTextureName("GardenContainers:pottery_pattern_dirt");
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        String[] var5 = StatCollector.translateToLocal("item.gardencontainers.potteryPatternDirty.description")
            .split("\\\\n");
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String s = var5[var7];
            list.add(s);
        }

    }

    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (side != 1) {
            return false;
        } else {
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if (block instanceof BlockCauldron) {
                int waterLevel = BlockCauldron.func_150027_b(meta);
                if (waterLevel == 0) {
                    return false;
                } else {
                    int index = this.getPatternIndex(world);
                    if (index == -1) {
                        return false;
                    } else {
                        PatternConfig pattern = GardenContainers.config.getPattern(index);
                        if (pattern == null) {
                            return false;
                        } else {
                            ItemStack stamp = new ItemStack(ModItems.potteryPattern, 1, pattern.getId());
                            --itemStack.stackSize;
                            world.spawnEntityInWorld(
                                new EntityItem(world, (double) x + 0.5D, (double) y + 1.5D, (double) z + 0.5D, stamp));
                            return true;
                        }
                    }
                }
            } else {
                return false;
            }
        }
    }

    private int getPatternIndex(World world) {
        int count = GardenContainers.config.getPatternCount();
        int[] accumWeights = new int[count + 1];
        if (count == 0) {
            return -1;
        } else {
            int maxWeight;
            for (maxWeight = 1; maxWeight <= count; ++maxWeight) {
                PatternConfig pattern = GardenContainers.config.getPattern(maxWeight);
                accumWeights[maxWeight] = accumWeights[maxWeight - 1] + pattern.getWeight();
            }

            maxWeight = accumWeights[accumWeights.length - 1];
            if (maxWeight == 0) {
                return -1;
            } else {
                int pick = world.rand.nextInt(maxWeight);
                int index = 1;

                for (int i = 1; i <= count; ++i) {
                    if (pick >= accumWeights[i - 1]) {
                        index = i;
                    }
                }

                return index;
            }
        }
    }
}
