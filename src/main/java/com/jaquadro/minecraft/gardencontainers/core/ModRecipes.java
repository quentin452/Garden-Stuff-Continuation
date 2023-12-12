package com.jaquadro.minecraft.gardencontainers.core;

import com.jaquadro.minecraft.gardencontainers.GardenContainers;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

    private static String[] dyeOreDict = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue",
        "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue",
        "dyeMagenta", "dyeOrange", "dyeWhite" };

    public void init() {
        GameRegistry
            .addRecipe(new ItemStack(ModBlocks.largePot, 3, 1), new Object[] { "x x", "x x", "xxx", 'x', Blocks.clay });
        GameRegistry.addRecipe(
            new ItemStack(ModBlocks.largePot, 3),
            new Object[] { "x x", "x x", "xxx", 'x', Blocks.hardened_clay });

        int i;
        for (i = 0; i < 16; ++i) {
            GameRegistry.addRecipe(
                new ItemStack(ModBlocks.largePotColored, 3, i),
                new Object[] { "x x", "x x", "xxx", 'x', new ItemStack(Blocks.stained_hardened_clay, 1, 15 - i) });
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(ModBlocks.largePotColored, 1, i),
                    new Object[] { ModBlocks.largePot, dyeOreDict[i] }));
        }

        GameRegistry.addRecipe(
            new ItemStack(ModBlocks.mediumPot, 3),
            new Object[] { "x x", "x x", " x ", 'x', Blocks.hardened_clay });

        for (i = 0; i < 16; ++i) {
            GameRegistry.addRecipe(
                new ItemStack(ModBlocks.mediumPotColored, 3, i),
                new Object[] { "x x", "x x", " x ", 'x', new ItemStack(Blocks.stained_hardened_clay, 1, 15 - i) });
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(ModBlocks.mediumPotColored, 1, i),
                    new Object[] { ModBlocks.mediumPot, dyeOreDict[i] }));
        }

        GameRegistry.addRecipe(
            new ItemStack(ModBlocks.potteryTable),
            new Object[] { "x", "y", 'x', Items.clay_ball, 'y', Blocks.crafting_table });
        GameRegistry
            .addSmelting(new ItemStack(ModBlocks.largePot, 1, 1), new ItemStack(ModBlocks.largePot, 1, 0), 0.0F);

        for (i = 1; i < 256; ++i) {
            if (GardenContainers.config.hasPattern(i)) {
                GameRegistry.addSmelting(
                    new ItemStack(ModBlocks.largePot, 1, 1 | i << 8),
                    new ItemStack(ModBlocks.largePot, 1, i << 8),
                    0.0F);
            }
        }

        for (i = 0; i < 6; ++i) {
            GameRegistry.addRecipe(
                new ItemStack(ModBlocks.decorativePot, 3, i),
                new Object[] { "x x", "xxx", " x ", 'x', new ItemStack(Blocks.quartz_block, 1, i) });
        }

        for (i = 0; i < 6; ++i) {
            GameRegistry.addRecipe(
                new ItemStack(ModBlocks.woodWindowBox, 1, i),
                new Object[] { "yxy", 'x', Items.flower_pot, 'y', new ItemStack(Blocks.planks, 1, i) });
        }

        for (i = 0; i < ModBlocks.stoneWindowBox.getSubTypes().length; ++i) {
            GameRegistry.addRecipe(
                new ItemStack(ModBlocks.stoneWindowBox, 1, i),
                new Object[] { "yxy", 'x', Items.flower_pot, 'y',
                    new ItemStack(
                        ModBlocks.stoneWindowBox.getBlockFromMeta(i),
                        1,
                        ModBlocks.stoneWindowBox.getMetaFromMeta(i)) });
        }

        GameRegistry
            .addSmelting(new ItemStack(ModBlocks.largePot, 1, 1), new ItemStack(ModBlocks.largePot, 1, 0), 0.0F);

        for (i = 1; i < 256; ++i) {
            if (GardenContainers.config.hasPattern(i)) {
                GameRegistry.addSmelting(
                    new ItemStack(ModBlocks.largePot, 1, 1 | i << 8),
                    new ItemStack(ModBlocks.largePot, 1, i << 8),
                    0.0F);
            }
        }

    }
}
