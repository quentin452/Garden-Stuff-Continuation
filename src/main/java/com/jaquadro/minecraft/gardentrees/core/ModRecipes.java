package com.jaquadro.minecraft.gardentrees.core;

import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.block.BlockThinLog;
import com.jaquadro.minecraft.gardentrees.core.recipe.WoodBlockRecipe;
import com.jaquadro.minecraft.gardentrees.core.recipe.WoodFenceRecipe;
import com.jaquadro.minecraft.gardentrees.core.recipe.WoodPostRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

import java.util.Iterator;
import java.util.Map.Entry;

public class ModRecipes {

    private static final Item[] axeList;

    public void init() {
        for (int i = 0; i < BlockThinLog.subNames.length; ++i) {
            GameRegistry.addRecipe(
                new ItemStack(ModBlocks.thinLogFence, 3, i),
                new Object[] { "xyx", " y ", 'x', Items.string, 'y', new ItemStack(ModBlocks.thinLog, 1, i) });
            int j;
            if (i / 4 == 0) {
                GameRegistry.addRecipe(
                    new ItemStack(Blocks.log, 1, i % 4),
                    new Object[] { "xx", "xx", 'x', new ItemStack(ModBlocks.thinLog, 1, i) });

                for (j = 0; j < axeList.length; ++j) {
                    GameRegistry.addRecipe(
                        new ItemStack(ModBlocks.thinLog, 4, i),
                        new Object[] { "x", "y", 'x', new ItemStack(axeList[j], 1, 32767), 'y',
                            new ItemStack(Blocks.log, 1, i % 4) });
                }
            } else if (i / 4 == 1) {
                GameRegistry.addRecipe(
                    new ItemStack(Blocks.log2, 1, i % 4),
                    new Object[] { "xx", "xx", 'x', new ItemStack(ModBlocks.thinLog, 1, i) });

                for (j = 0; j < axeList.length; ++j) {
                    GameRegistry.addRecipe(
                        new ItemStack(ModBlocks.thinLog, 4, i),
                        new Object[] { "x", "y", 'x', new ItemStack(axeList[j], 1, 32767), 'y',
                            new ItemStack(Blocks.log2, 1, i % 4) });
                }
            }
        }

        ItemStack enrichedSoil = new ItemStack(com.jaquadro.minecraft.gardencore.core.ModItems.compostPile);
        GameRegistry.addShapelessRecipe(
            new ItemStack(ModBlocks.sapling),
            new Object[] { new ItemStack(Blocks.sapling, 1, 1), enrichedSoil });
        GameRegistry.addShapelessRecipe(
            new ItemStack(ModBlocks.sapling, 1, 1),
            new Object[] { new ItemStack(Blocks.sapling), new ItemStack(Blocks.vine), enrichedSoil });
        GameRegistry.addShapelessRecipe(
            new ItemStack(ModBlocks.sapling, 1, 2),
            new Object[] { new ItemStack(Blocks.sapling, 1, 2), enrichedSoil });
        GameRegistry.addShapelessRecipe(
            new ItemStack(ModBlocks.ivy),
            new Object[] { new ItemStack(Blocks.vine), enrichedSoil });
        GameRegistry
            .addSmelting(ModItems.candelilla, new ItemStack(com.jaquadro.minecraft.gardencore.core.ModItems.wax), 0.0F);
        this.addExtraWoodRecipes();
    }

    private void addExtraWoodRecipes() {
        RecipeSorter
            .register("GardenTrees:WoodBlock", WoodBlockRecipe.class, Category.SHAPED, "after:minecraft:shaped");
        RecipeSorter.register("GardenTrees:WoodPost", WoodPostRecipe.class, Category.SHAPED, "after:minecraft:shaped");
        RecipeSorter
            .register("GardenTrees:WoodFence", WoodFenceRecipe.class, Category.SHAPED, "after:minecraft:shaped");
        Iterator var1 = WoodRegistry.instance()
            .registeredTypes()
            .iterator();

        while (var1.hasNext()) {
            Entry entry = (Entry) var1.next();
            UniqueMetaIdentifier id = (UniqueMetaIdentifier) entry.getKey();
            CraftingManager.getInstance()
                .getRecipeList()
                .add(new WoodPostRecipe(id));
            CraftingManager.getInstance()
                .getRecipeList()
                .add(new WoodFenceRecipe(id));
            CraftingManager.getInstance()
                .getRecipeList()
                .add(new WoodBlockRecipe(id));
        }

    }

    static {
        axeList = new Item[] { Items.wooden_axe, Items.stone_axe, Items.iron_axe, Items.golden_axe, Items.diamond_axe };
    }
}
