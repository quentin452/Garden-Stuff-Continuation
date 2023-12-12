package com.jaquadro.minecraft.gardenstuff.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.jaquadro.minecraft.gardenstuff.client.gui.GuiBloomeryFurnace;
import com.jaquadro.minecraft.gardenstuff.core.ModItems;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;

public class BloomeryFurnaceRecipeHandler extends TemplateRecipeHandler {

    public static ArrayList afuels;
    public static HashSet efuels;

    public void loadTransferRects() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel", new Object[0]));
        this.transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "smelting", new Object[0]));
    }

    public Class getGuiClass() {
        return GuiBloomeryFurnace.class;
    }

    public String getGuiTexture() {
        return "GardenStuff:textures/gui/bloomery_furnace.png";
    }

    public String getRecipeName() {
        return NEIClientUtils.translate("recipe.gardenstuff.bloomeryFurnace", new Object[0]);
    }

    public TemplateRecipeHandler newInstance() {
        if (afuels == null || afuels.isEmpty()) {
            findFuels();
        }

        return super.newInstance();
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("bloomerySmelting") && this.getClass() == BloomeryFurnaceRecipeHandler.class) {
            this.arecipes.add(
                new BloomeryFurnaceRecipeHandler.SmeltingPair(
                    new ItemStack(Items.iron_ingot),
                    new ItemStack(Blocks.sand),
                    new ItemStack(ModItems.wroughtIronIngot)));
            this.arecipes.add(
                new BloomeryFurnaceRecipeHandler.SmeltingPair(
                    new ItemStack(Blocks.iron_ore),
                    new ItemStack(Blocks.sand),
                    new ItemStack(ModItems.wroughtIronIngot)));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }

    }

    public void loadCraftingRecipes(ItemStack result) {
        if (NEIServerUtils.areStacksSameType(new ItemStack(ModItems.wroughtIronIngot), result)) {
            this.arecipes.add(
                new BloomeryFurnaceRecipeHandler.SmeltingPair(
                    new ItemStack(Items.iron_ingot),
                    new ItemStack(Blocks.sand),
                    new ItemStack(ModItems.wroughtIronIngot)));
            this.arecipes.add(
                new BloomeryFurnaceRecipeHandler.SmeltingPair(
                    new ItemStack(Blocks.iron_ore),
                    new ItemStack(Blocks.sand),
                    new ItemStack(ModItems.wroughtIronIngot)));
        }

    }

    public void loadUsageRecipes(String inputId, Object... ingredients) {
        super.loadUsageRecipes(inputId, ingredients);
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        ItemStack[] primary = new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Blocks.iron_ore) };
        ItemStack[] secondary = new ItemStack[] { new ItemStack(Blocks.sand) };
        ItemStack[] var4 = primary;
        int var5 = primary.length;

        int var6;
        ItemStack stack;
        ItemStack[] var8;
        int var9;
        int var10;
        ItemStack primaryStack;
        BloomeryFurnaceRecipeHandler.SmeltingPair arecipe;
        for (var6 = 0; var6 < var5; ++var6) {
            stack = var4[var6];
            if (NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
                var8 = secondary;
                var9 = secondary.length;

                for (var10 = 0; var10 < var9; ++var10) {
                    primaryStack = var8[var10];
                    arecipe = new BloomeryFurnaceRecipeHandler.SmeltingPair(
                        stack,
                        primaryStack,
                        new ItemStack(ModItems.wroughtIronIngot));
                    arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred1), ingredient);
                    this.arecipes.add(arecipe);
                }
            }
        }

        var4 = secondary;
        var5 = secondary.length;

        for (var6 = 0; var6 < var5; ++var6) {
            stack = var4[var6];
            if (NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
                var8 = primary;
                var9 = primary.length;

                for (var10 = 0; var10 < var9; ++var10) {
                    primaryStack = var8[var10];
                    arecipe = new BloomeryFurnaceRecipeHandler.SmeltingPair(
                        primaryStack,
                        stack,
                        new ItemStack(ModItems.wroughtIronIngot));
                    arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred2), ingredient);
                    this.arecipes.add(arecipe);
                }
            }
        }

    }

    public void drawExtras(int recipe) {
        this.drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
        this.drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
    }

    private static void findFuels() {
        afuels = new ArrayList();
        afuels.add(new BloomeryFurnaceRecipeHandler.FuelPair(new ItemStack(Items.coal, 1, 1), 1600));
    }

    public String getOverlayIdentifier() {
        return "bloomerySmelting";
    }

    public class SmeltingPair extends CachedRecipe {

        PositionedStack ingred1;
        PositionedStack ingred2;
        PositionedStack result;

        public SmeltingPair(ItemStack ingred1, ItemStack ingred2, ItemStack result) {
            super();
            ingred1.stackSize = 1;
            ingred2.stackSize = 1;
            this.ingred1 = new PositionedStack(ingred1, 51, 6);
            this.ingred2 = new PositionedStack(ingred2, 30, 6);
            this.result = new PositionedStack(result, 111, 24);
        }

        public List getIngredients() {
            return this.getCycledIngredients(
                BloomeryFurnaceRecipeHandler.this.cycleticks / 48,
                Arrays.asList(this.ingred1, this.ingred2));
        }

        public PositionedStack getResult() {
            return this.result;
        }

        public PositionedStack getOtherStack() {
            return ((BloomeryFurnaceRecipeHandler.FuelPair) BloomeryFurnaceRecipeHandler.afuels.get(
                BloomeryFurnaceRecipeHandler.this.cycleticks / 48 % BloomeryFurnaceRecipeHandler.afuels.size())).stack;
        }
    }

    private static class FuelPair {

        public PositionedStack stack;
        public int burnTime;

        public FuelPair(ItemStack ingred, int burnTime) {
            this.stack = new PositionedStack(ingred, 51, 42, false);
            this.burnTime = burnTime;
        }
    }
}
