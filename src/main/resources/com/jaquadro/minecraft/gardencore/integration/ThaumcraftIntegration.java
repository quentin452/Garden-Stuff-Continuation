package com.jaquadro.minecraft.gardencore.integration;

import com.jaquadro.minecraft.gardencore.api.PlantRegistry;
import com.jaquadro.minecraft.gardencore.api.SaplingRegistry;
import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.api.plant.SimplePlantInfo;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ThaumcraftIntegration {
   public static final String MOD_ID = "Thaumcraft";

   public static void init() {
      if (Loader.isModLoaded("Thaumcraft")) {
         initWood();
         PlantRegistry plantReg = PlantRegistry.instance();
         plantReg.registerPlantInfo("Thaumcraft", "blockCustomPlant", 2, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
         plantReg.registerPlantInfo("Thaumcraft", "blockCustomPlant", 4, new SimplePlantInfo(PlantType.INVALID, PlantSize.LARGE));
      }
   }

   private static void initWood() {
      Block log = GameRegistry.findBlock("Thaumcraft", "blockMagicalLog");
      Block leaf = GameRegistry.findBlock("Thaumcraft", "blockMagicalLeaves");
      Item sapling = Item.getItemFromBlock(GameRegistry.findBlock("Thaumcraft", "blockCustomPlant"));
      WoodRegistry woodReg = WoodRegistry.instance();
      woodReg.registerWoodType(log, 0);
      woodReg.registerWoodType(log, 1);
      SaplingRegistry saplingReg = SaplingRegistry.instance();
      saplingReg.registerSapling(sapling, 0, log, 0, leaf, 0);
      saplingReg.registerSapling(sapling, 1, log, 1, leaf, 1);
   }
}
