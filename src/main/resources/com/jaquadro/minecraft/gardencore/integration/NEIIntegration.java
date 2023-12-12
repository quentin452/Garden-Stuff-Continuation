package com.jaquadro.minecraft.gardencore.integration;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.jaquadro.minecraft.gardencore.client.gui.GuiCompostBin;
import com.jaquadro.minecraft.gardencore.core.ModItems;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;

public class NEIIntegration {
   public static final String MOD_ID = "NotEnoughItems";

   public static void init() {
      if (Loader.isModLoaded("NotEnoughItems")) {
         registerNEI();
      }
   }

   private static void registerNEI() {
   }

   public static class CompostBinRecipeHandler extends TemplateRecipeHandler {
      public Class getGuiClass() {
         return GuiCompostBin.class;
      }

      public String getRecipeName() {
         return "Compost Bin!";
      }

      public TemplateRecipeHandler newInstance() {
         return super.newInstance();
      }

      public void loadCraftingRecipes(String outputId, Object... results) {
         Object[] var3 = results;
         int var4 = results.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object obj = var3[var5];
            if (obj instanceof ItemStack && ((ItemStack)obj).getItem() == ModItems.compostPile) {
            }
         }

         super.loadCraftingRecipes(outputId, results);
      }

      public void loadCraftingRecipes(ItemStack result) {
         super.loadCraftingRecipes(result);
      }

      public String getGuiTexture() {
         return "GardenCore:textures/gui/compostBin.png";
      }

      public String getOverlayIdentifier() {
         return "composting";
      }
   }
}
