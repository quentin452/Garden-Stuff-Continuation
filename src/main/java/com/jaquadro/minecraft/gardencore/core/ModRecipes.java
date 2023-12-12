package com.jaquadro.minecraft.gardencore.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes {
   public void init() {
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.compostBin), new Object[]{"xxx", "xxx", "yyy", 'x', "stickWood", 'y', "slabWood"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.soilTestKit), new Object[]{"xy", "zz", 'x', "dyeRed", 'y', "dyeGreen", 'z', Items.glass_bottle}));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.soilTestKit), new Object[]{"yx", "zz", 'x', "dyeRed", 'y', "dyeGreen", 'z', Items.glass_bottle}));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gardenTrowel), new Object[]{"  z", " y ", "x  ", 'x', "stickWood", 'y', "ingotIron", 'z', ModItems.compostPile}));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gardenTrowel), new Object[]{"  x", " y ", "z  ", 'x', "stickWood", 'y', "ingotIron", 'z', ModItems.compostPile}));
      GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.gardenSoil), new Object[]{Blocks.dirt, ModItems.compostPile});
   }
}
